# Зависимости OCR и источники

- ML Kit Text Recognition bundled 16.0.1: https://developers.google.com/ml-kit/vision/text-recognition/v2/android (Google SDK terms).
- Tesseract4Android 4.9.0: https://github.com/adaptech-cz/Tesseract4Android (Apache-2.0; bundled Tesseract/Leptonica/libjpeg/libpng имеют собственные лицензии в upstream).
- `app/src/main/assets/tessdata/{rus,eng}.traineddata`: https://github.com/tesseract-ocr/tessdata_fast/tree/4.1.0 (Apache-2.0). Модели встроены в APK; для первого OCR сеть не требуется.
- CameraX / AndroidX: https://developer.android.com/jetpack/androidx (Apache-2.0).
- Material Components Android: https://github.com/material-components/material-components-android (Apache-2.0).
- Gson: https://github.com/google/gson (Apache-2.0).
- Groq API: https://console.groq.com/docs/openai ; JSON Object Mode: https://console.groq.com/docs/structured-outputs.
- AGP 8.13.2 / Gradle 8.13 / JDK 17: https://developer.android.com/build/releases/agp-8-13-0-release-notes.

Gradle скачивает исходные Maven/JitPack-артефакты; они не копируются в репозиторий. Перед публикацией приложения проверь условия всех поставщиков и подготовь полный список лицензий транзитивных компонентов.

- Roboto variable / кириллица, SIL Open Font License: https://github.com/google/fonts/tree/main/ofl/roboto ; полный текст включён в app/src/main/assets/licenses/Roboto-OFL.txt.
- Lucide SVG, ISC: https://lucide.dev/license ; иконки адаптированы в Android VectorDrawable, полный текст в app/src/main/assets/licenses/Lucide-LICENSE.txt.
