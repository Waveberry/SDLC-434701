"""Validate SysML v2 models and export native Pilot Implementation views.

pip install jupyter_client PyMuPDF
python render.py --kernel-home PATH_TO_EXTRACTED_KERNEL/sysml --dot PATH_TO_DOT
"""
from pathlib import Path
import argparse,hashlib,json,os,re,sys,subprocess
import xml.etree.ElementTree as ET
from jupyter_client import KernelManager
from jupyter_client.kernelspec import KernelSpec
import fitz

def main():
    parser=argparse.ArgumentParser()
    parser.add_argument('--kernel-home',required=True,type=Path)
    parser.add_argument('--dot',required=True,type=Path)
    parser.add_argument('--java',default='java')
    parser.add_argument('--output',type=Path)
    args=parser.parse_args()
    folder=Path(__file__).resolve().parent
    output=(args.output or folder.parent/'images').resolve();output.mkdir(parents=True,exist_ok=True)
    kernel=args.kernel_home.resolve();dot=args.dot.resolve()
    jars=list(kernel.glob('jupyter-sysml-kernel-*-all.jar'))
    if len(jars)!=1:raise RuntimeError('Expected one official SysML kernel JAR')
    km=KernelManager()
    km._kernel_spec=KernelSpec(argv=[args.java,'-Xmx2g','-cp',str(kernel/'*'),'org.omg.sysml.jupyter.kernel.ISysML','{connection_file}'],env={'ISYSML_LIBRARY_PATH':str(kernel/'sysml.library').replace('\\','/'),'ISYSML_GRAPHVIZ_PATH':str(dot)},language='sysml',display_name='SysML')
    transcript=[];manifest={'kernel':jars[0].name,'models':[],'views':[]}
    java_log=open(output/'kernel.log','w',encoding='utf-8')
    km.start_kernel(cwd=str(folder),stdout=java_log,stderr=java_log)
    client=km.client();client.start_channels()
    def execute(code,label):
        mid=client.execute(code);texts=[];svg=None;errors=[]
        while True:
            message=client.get_iopub_msg(timeout=60)
            if message.get('parent_header',{}).get('msg_id')!=mid:continue
            kind=message['msg_type'];body=message['content']
            if kind=='error':errors.append(body.get('evalue','Unknown error'))
            if kind=='stream':texts.append(body.get('text',''))
            if kind in ['display_data','execute_result']:
                data=body.get('data',{})
                if 'text/plain' in data:texts.append(data['text/plain'])
                if 'image/svg+xml' in data:svg=data['image/svg+xml']
            if kind=='status' and body.get('execution_state')=='idle':break
        reply=client.get_shell_msg(timeout=60)
        while reply.get('parent_header',{}).get('msg_id')!=mid:reply=client.get_shell_msg(timeout=60)
        if reply['content'].get('status')=='error':errors.append(str(reply['content'].get('evalue','Execution failed')))
        text='\n'.join(texts);transcript.append(label+'\n'+text)
        if errors or re.search(r'(?im)^\s*(?:ERROR|syntax error|\[error\])',text):
            raise RuntimeError(label+'\n'+text+'\n'+'\n'.join(errors))
        return svg,text
    try:
        client.wait_for_ready(timeout=60)
        model_paths=sorted(folder.glob('[0-9][0-9]-*.sysml'))
        if (folder/'cheq.sysml').exists():model_paths.append(folder/'cheq.sysml')
        for model in model_paths:
            code=model.read_text(encoding='utf-8');svg,text=execute(code,model.name)
            manifest['models'].append({'file':model.name,'sha256':hashlib.sha256(model.read_bytes()).hexdigest(),'status':'valid'})
            print('VALID',model.name,text.strip()[:150],flush=True)
        for line in (folder/'views.txt').read_text(encoding='utf-8').splitlines():
            if not line.strip() or line.startswith('#'):continue
            parts=line.split('|');name,view,element=parts[:3]
            style=parts[3] if len(parts)>3 else 'TB'
            command=f'%viz --view={view} --style={style} {element}'
            svg,text=execute(command,command)
            if not svg:raise RuntimeError('No SVG for '+command+'\n'+text)
            # Normalise generated line endings and comment whitespace only.
            # Visible SVG geometry, labels and interactive links are retained.
            svg='\n'.join(line.rstrip() for line in svg.splitlines())+'\n'
            (output/(name+'.svg')).write_text(svg,encoding='utf-8',newline='\n')
            # MuPDF ignores SVG <a> wrappers. Remove link wrappers for rasterisation
            # while retaining their visible child geometry; preserve the native SVG.
            tree=ET.fromstring(svg)
            for parent in tree.iter():
                for child in list(parent):
                    if child.tag.endswith('}a'):
                        index=list(parent).index(child)
                        for content in list(child):parent.insert(index,content);index+=1
                        parent.remove(child)
            document=fitz.open(stream=ET.tostring(tree),filetype='svg')
            scale=max(2.0,1800/document[0].rect.width)
            document[0].get_pixmap(matrix=fitz.Matrix(scale,scale),alpha=False).save(output/(name+'.png'))
            # Reject an empty raster rather than silently publishing a white image.
            pixels=(output/(name+'.png')).read_bytes()
            from PIL import Image,ImageStat
            if max(ImageStat.Stat(Image.open(output/(name+'.png'))).stddev)<1:
                raise RuntimeError('Blank PNG: '+name)
            manifest['views'].append({'file':name+'.png','element':element,'view':view,'style':style,'command':command,'sha256':hashlib.sha256(pixels).hexdigest()})
            print('RENDERED',name,view,flush=True)
        (output/'render-manifest.json').write_text(json.dumps(manifest,ensure_ascii=False,indent=2),encoding='utf-8')
    finally:
        log_text='\n'.join(line.rstrip() for line in '\n\n'.join(transcript).splitlines())+'\n'
        (output/'validation.log').write_text(log_text,encoding='utf-8',newline='\n')
        client.stop_channels()
        # Oracle javapath may launch a child JVM. On Windows terminate the entire
        # owned process tree before disposing of the manager's launcher process.
        pid=getattr(km.provisioner,'pid',None)
        if os.name=='nt' and pid:
            subprocess.run(['taskkill','/PID',str(pid),'/T','/F'],capture_output=True)
        km.shutdown_kernel(now=True);java_log.close()

if __name__=='__main__':main()
