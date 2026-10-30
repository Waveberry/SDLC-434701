# План верификации Cheq

Статус: сценарии подготовлены, испытания приложения в ходе лабораторной не запускались. Прежние записи docs/verification.md приложения являются историческим источником, а не новыми результатами. Проверяемая версия исходников: 0.9.0, дата сверки 30.10.2026. Исходный проект находится в D:/VS Code/Cheq; он не включён целиком в учебный fork. Ссылки вида core/... сокращают общий путь пакета; реальные относительные пути тестов перечислены ниже.

## VC-01

**Ввод и проверка чека**

Данные и условия: Ручной чек: молоко 2 × 2,50 = 5,00; хлеб 1 × 1,80 = 1,80; итог 6,80 BYN. Повтор с итогом 6,00; затем квитанция 25,00 без позиций.

Проверка и ожидаемый результат: Создать черновик, изменить строку, проверить расхождение, сохранить подтверждённые значения и снова открыть. До сохранения запись отсутствует в архиве. Квитанция создаёт покупку без товаров.

Существующие точки проверки или материалы: core/.../ReceiptArithmeticTest.java; core/.../TotalOnlyReceiptTest.java; app/.../TotalOnlyStoreTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-02

**Локальный ввод и очередь автоматического разбора**

Данные и условия: Читаемое искусственное фото на русском/английском, отключённая сеть; затем включённая сеть с имитацией ошибки или задержки Groq.

Проверка и ожидаемый результат: В авиарежиме импортировать фото, проверить сохранение очереди, завершить процесс и открыть приложение. Восстановить сеть: анализ возобновляется. Изменить текст до позднего ответа: форма не затирается. Имитировать HTTP 403 при рабочей сети: показана ошибка Groq. Сохранить готовый чек дважды: одна запись. Ожидающие чеки не входят в расходы. Проверить ручной ввод без облака и результат второго OCR-движка при отказе первого.

Существующие точки проверки или материалы: core/.../GroqErrorsTest.java; core/.../ReceiptRefinementTest.java; app/.../EditorRefinementTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-03

**Архив и история цен**

Данные и условия: Два чека с одинаковым наименованием товара, разными датами и ценами; третий чек другого магазина.

Проверка и ожидаемый результат: Проверить поиск по магазину, товару и дате, переключение карточки/список, сортировку, историю двух цен и сохранение настроек после перезапуска.

Существующие точки проверки или материалы: app/.../ReceiptBrowserTest.java; app/.../GalleryUiTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-04

**Курсы и неполный расчёт**

Данные и условия: Синтетические курсы: 1 USD = 3 BYN, 1 EUR = 4 BYN; 100 RUB = 3,50 BYN; чек 10 USD и чек 200 RUB на одной дате.

Проверка и ожидаемый результат: Ожидать 7,50 EUR для 10 USD и 7,00 BYN для 200 RUB. Удалить один курс из тестового набора: сводка обозначает неполноту; чек остаётся. Восстановить курс: полный итог обновляется. Числа не являются актуальными котировками.

Существующие точки проверки или материалы: core/.../HistoricalRateTest.java; app/.../MixedCurrencyUiTest.java; app/.../HistoricalRatesStoreTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-05

**Месячный бюджет**

Данные и условия: Лимит 100 BYN; расходы 79, 80 и 101 BYN; независимый следующий месяц; один сценарий без курса.

Проверка и ожидаемый результат: Проверить границу 80%, превышение 100%, остаток/превышение, отключение лимита значением 0, независимость месяцев и отсутствие окончательного предупреждения при неполных курсах.

Существующие точки проверки или материалы: core/.../BudgetProgressTest.java; app/.../PreferencesDataTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-06

**Карты и расходы по картам**

Данные и условия: Карта А с цифрами 2468; карта Б с теми же цифрами; карта В 1357; покупка без карты.

Проверка и ожидаемый результат: Проверить неоднозначный автоматический выбор, приоритет ручного «Без карты», групповые суммы, карту списания в переводе. Удаление карты не удаляет её покупки.

Существующие точки проверки или материалы: core/.../CardLastFourTest.java; app/.../EditorCardExtractionTest.java; app/.../CardSpendingStoreTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-07

**Массовые действия и TXT**

Данные и условия: Архив с несколькими покупками и поисковой выборкой; общее фото у двух чеков.

Проверка и ожидаемый результат: Выбрать все результаты поиска, отменить удаление, затем подтвердить; проверить ссылки на фото. Назначить карту и категорию, экспортировать TXT и убедиться в отсутствии OCR, фото и карточных данных.

Существующие точки проверки или материалы: app/.../MultiSelectionTest.java; app/.../BatchSelectionStoreTest.java; app/.../GalleryStoreTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-08

**Сохранность состояния и базы**

Данные и условия: Заполненный черновик; тестовые базы v1/v2/v3; искусственный сбой массовой записи.

Проверка и ожидаемый результат: Повернуть экран и сравнить поля; проверить миграцию в v4 и атомарность при ошибке. После успешной записи перезапустить приложение и сравнить итог, позиции и связи.

Существующие точки проверки или материалы: app/.../EditorStateTest.java; app/.../ReceiptStoreTest.java; app/.../SettingsChoiceLifecycleTest.java

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-09

**Измерение отклика**

Данные и условия: Согласованное устройство Android с 4 ГБ памяти, 1000 искусственных чеков, 20 повторов операции без сети.

Проверка и ожидаемый результат: Записать устройство, версию ОС и приложения, условия кеша, все 20 длительностей и отсортировать. Сравнить 19-й результат с предложенным порогом 2 с; отдельно сравнить время ввода 10 одинаковых чеков вручную и по фото. До согласования числа не являются обязательством заказчика.

Существующие точки проверки или материалы: Новый протокол измерений должен быть создан при фактическом испытании

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-10

**Приватность и состав поставки**

Данные и условия: Манифест, FileProvider, код сетевых запросов, THIRD_PARTY.md и создаваемый TXT.

Проверка и ожидаемый результат: Проверить приватные пути, отключённую резервную копию, состав запроса и экспорта, отсутствие вывода ключей/чеков в журналы. Зафиксировать извлекаемость встроенного ключа как ограничение учебной сборки.

Существующие точки проверки или материалы: app/src/main/AndroidManifest.xml; cloud/GroqClient.java; ui/SelectionSharing.java; THIRD_PARTY.md

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-11

**Сборка и установка**

Данные и условия: JDK 17, подготовленный Android SDK, копия проекта без GroqApi.key, Android API 26+.

Проверка и ожидаемый результат: Запустить scripts/build.ps1 -Check, затем сборку APK и установку на тестовое устройство. Проверить локальный режим без ключа, запуск core-проверок и отсутствие требования Android Studio.

Существующие точки проверки или материалы: scripts/build.ps1; app/build.gradle; core/build.gradle

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## VC-12

**Требования и согласование**

Данные и условия: Список ID, ссылки на PNG и модель, отдельная ветка и коммит, фактические отзывы заказчиков при их получении.

Проверка и ожидаемый результат: Проверить уникальность ID, покрытие матрицей, открытие ссылок и соответствие PNG структуре модели. Создать PR при доступности репозитория; внести реальные замечания с решениями.

Существующие точки проверки или материалы: srs.md; models/cheq.sysml; review.md; repository.md

Статус: запланировано. Фактический результат и доказательство заполняются после выполнения.

## Исходники существующих проверок

Следующие файлы найдены в предоставленном проекте. Существование файла не означает успешный запуск и не гарантирует полное покрытие каждого критерия.

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/cloud/GroqCardLastFourTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/BatchSelectionStoreTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/CardSpendingStoreTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/GalleryStoreTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/HistoricalRatesStoreTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/PreferencesDataTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/ReceiptStoreTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/data/TotalOnlyStoreTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/scan/OcrPreparationTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/BottomPanelTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/CardSpendingUiTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/ChoiceInteractionTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/EditorCardExtractionTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/EditorRefinementTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/EditorStateTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/ExpensesHeaderTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/FrostedSurfaceShadowTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/FrostedSurfaceTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/GalleryPreviewTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/GalleryUiTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/MixedCurrencyUiTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/MultiSelectionTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/NativeFlowTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/RateRefreshLifecycleTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/ReceiptBrowserTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/SelectionHeaderGeometryTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/SettingsChoiceLifecycleTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/SettingsRequirementsTest.java`

- `D:/VS Code/Cheq/app/src/test/java/com/waveberry/cheq/ui/TotalOnlyUiTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/BudgetProgressTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/CardLastFourTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/DigitalReceiptTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/GroqConnectionTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/GroqErrorsTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/GroqReceiptApiTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/GroqReviewTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/GroqTransferTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/HistoricalRateTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/NbrbRateClientTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/OcrTextMergerTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/ReceiptArithmeticTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/ReceiptCoreTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/ReceiptDiversityTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/ReceiptRefinementTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/ServerPolicyTest.java`

- `D:/VS Code/Cheq/core/src/test/java/com/receiptbox/core/TotalOnlyReceiptTest.java`

- `D:/VS Code/Cheq/proxy/src/test/java/com/receiptbox/proxy/BoundedHttpTest.java`

- `D:/VS Code/Cheq/proxy/src/test/java/com/receiptbox/proxy/ProxyServerTest.java`

## Документальная проверка редакции 1.2

30.10.2026 выполнены: загрузка шести тематических моделей и реестра требований официальным ядром 0.63.1; экспорт 13 представлений; проверка PNG на непустое изображение; сверка 50 ID в спецификации, модели и матрице; проверка локальных ссылок и постраничная проверка Word-отчёта. Доказательства экспорта находятся в images/sysml/validation.log и render-manifest.json. Эти результаты не меняют запланированный статус приёмочных сценариев приложения.
