# Репозиторий и публикация

Учебный репозиторий: [Waveberry/SDLC-434701](https://github.com/Waveberry/SDLC-434701), публичный fork [BSUIR-SDLC/SDLC](https://github.com/BSUIR-SDLC/SDLC).

| Материалы | Ветка | База | Pull request |
|---|---|---|---|
| Лабораторная 1 | lab1 | main | — |
| Заготовка проекта | ProjectTemplate | main | [№ 1](https://github.com/Waveberry/SDLC-434701/pull/1) |
| Требования Cheq | lab3-cheq-requirements | ProjectTemplate | [№ 2](https://github.com/Waveberry/SDLC-434701/pull/2) |

В каталоге srs находятся спецификация, шесть графических моделей, реестр 50 требований, 13 представлений SysML v2 в SVG/PNG, план верификации, журнал согласования и отчёт. Журнал проверки моделей и контрольные суммы результатов доступны в images/sysml.

Изменения фиксируются отдельными коммитами и публикуются в ветку lab3-cheq-requirements. PR № 2 используется для обсуждения требований. Его база — ProjectTemplate; после слияния PR № 1 базу можно перевести в main. Оба PR оставлены для проверки, автоматическое слияние отключено.

Модели проверены официальным ядром SysML v2 Pilot Implementation 0.63.1. Испытания Android-приложения выполняются отдельно по verification.md; статус приёмочных сценариев указан в спецификации.
