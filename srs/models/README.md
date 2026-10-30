# Модели и экспорт SysML v2 для Cheq

Шесть тематических моделей и отдельный реестр 50 требований проверены официальным Jupyter-ядром SysML v2 Pilot Implementation 0.63.1. Из моделей экспортированы 13 представлений. Исходные SVG сохраняются рядом с PNG; это вывод ядра, а не отдельно нарисованные мокапы и не скриншоты Android-приложения.

| Представление | Модель | Вид |
|---|---|---|
| [Бизнес-цели Cheq](../images/sysml/01-business-goals.png) | [03-business-goals.sysml](03-business-goals.sysml) | Tree |
| [Системный контекст](../images/sysml/02-context.png) | [01-context.sysml](01-context.sysml) | Interconnection |
| [Варианты использования](../images/sysml/03-usecases.png) | [02-usecases.sysml](02-usecases.sysml) | Case |
| [Состояния импортированного чека](../images/sysml/04-receipt-states.png) | [04-receipt-states.sysml](04-receipt-states.sysml) | State |
| [Импорт и подтверждение покупки](../images/sysml/05-receipt-import.png) | [05-receipt-import.sysml](05-receipt-import.sysml) | Action |
| [Карта экранов](../images/sysml/06-navigation.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [Экран Покупки](../images/sysml/07-gui-purchases.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [Редактор чека](../images/sysml/08-gui-editor.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [Экран Товары](../images/sysml/09-gui-products.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [История цен](../images/sysml/10-gui-history.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [Экран Расходы](../images/sysml/11-gui-expenses.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [Экран Настройки](../images/sysml/12-gui-settings.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |
| [Диалог банковской карты](../images/sysml/13-gui-card.png) | [06-gui.sysml](06-gui.sysml) | Interconnection |

`cheq.sysml` содержит реестр требований с теми же ID и формулировками, что и SRS. Файл также загружен ядром без ошибок; отдельная диаграмма всех 50 требований не создаётся.

## Воспроизведение

Нужны Java 21 или новее, официальный архив ядра 0.63.1, Graphviz и Python. В этой работе использованы Java 25 и Graphviz 16.1.0. Бинарные зависимости в учебный fork не включены.

```text
python -m pip install -r srs/models/requirements-render.txt
python srs/models/render.py --kernel-home <каталог ядра>/sysml --dot <каталог Graphviz>/bin/dot.exe
```

Список команд находится в [views.txt](views.txt). Скрипт сначала проверяет все модели, затем выполняет `%viz`; при ошибке или пустом PNG завершает работу с ошибкой. Ссылочные оболочки SVG удаляются только в копии для растеризации, поскольку MuPDF не отображает их содержимое; геометрия и подписи сохраняются. В SVG нормализованы только переносы строк и концевые пробелы; геометрия и подписи совпадают с экспортом ядра. На Windows по завершении закрывается всё дерево запущенного ядра.

Проверяемые результаты: [журнал](../images/sysml/validation.log), [манифест и контрольные суммы](../images/sysml/render-manifest.json). Проверка модели не является испытанием Android-приложения. Общие цели и численные метрики ещё требуют согласования заказчиком.
