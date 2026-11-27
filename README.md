# Cheq — проектная документация (ЖЦРПО)

**Автор:** Голуб А. В., группа 434701  
**Дисциплина:** Жизненный цикл разработки программного обеспечения (БГУИР, ФКСиС, кафедра ЭВМ)  
**Базовые шаблоны:** [BSUIR-SDLC/SDLC](https://github.com/BSUIR-SDLC/SDLC)

Учебный репозиторий проектной документации для нативного Android-приложения **Cheq** (личный учёт чеков, купленных товаров, истории цен, мультивалютных расходов и месячного бюджета).

## Структура репозитория

- `srs/` — шаблоны SRS и материалы **лабораторной работы № 3**:
  - [Спецификация требований (SRS v1.2)](srs/srs-cheq.md)
  - [Модели и диаграммы SysML v2](srs/sysml)
  - [Отчёт по лабораторной работе № 3](srs/lab3.docx)
- `sdd/` — шаблоны SDD (IEEE 1016 / ISO 42010) и материалы **лабораторной работы № 4**:
  - [Описание дизайна ПО (SDD v0.3)](sdd/sdd-cheq.md)
  - [Первичный план v1](sdd/plan-v1.md) · [Изменённый план v2](sdd/plan-v2.md) · [Согласованный план v3](sdd/plan-v3.md)
  - [Журнал переписки и согласования (PR #3)](sdd/negotiations.md)
  - [Архитектурные и плановые диаграммы](sdd/diagrams)
  - [Отчёт по лабораторной работе № 4](sdd/lab4.docx)
- `adr/` — шаблоны MADR 4.0 и протоколы архитектурных решений (**лабораторная работа № 4**):
  - [ADR-0001 — Выделение чистого Java-модуля `:core`](adr/0001-separate-pure-java-core-module.md)
  - [ADR-0002 — Две изолированные SQLite БД с WAL и идемпотентным сохранением](adr/0002-dual-sqlite-databases-with-wal-and-idempotent-save.md)
  - [ADR-0003 — Гибридный локальный OCR (ML Kit + Tesseract + `OcrTextMerger`)](adr/0003-hybrid-on-device-ocr-mlkit-and-tesseract.md)
  - [ADR-0004 — Прямой HTTPS-доступ к Groq API (`accepted`)](adr/0004-groq-cloud-parsing-route-worker-vs-direct-https.md)
  - [ADR-0005 — Целые минорные доли (`long`) и исторический кросс-курс НБРБ](adr/0005-integer-minor-units-and-nbrb-historical-cross-rates.md)
  - [ADR-0006 — Минимизация карточных данных, приватность и `FileProvider` TXT](adr/0006-card-data-minimization-and-local-privacy.md)
  - [ADR-0007 — Поставка ARM64 APK (`arm64-v8a`) с R8 и CLI-сборкой](adr/0007-arm64-only-distribution-and-r8-packaging.md)
  - [ADR-0008 — Локальный бэкап SAF (`F-15`), шаблоны (`F-16`) и Junior-разработчик на Итер. 4–5](adr/0008-local-backup-templates-and-junior-onboarding.md)
- `docs/` — справочные материалы курса по стандартам, трассируемости и атрибутам качества
- Ветка `lab1` — материалы лабораторной работы № 1
