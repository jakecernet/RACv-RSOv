--  ---------------
--   glasovanja 
--
--      popravi fixture z datumi !
--
create database glasovanja;

use glasovanja;

CREATE TABLE
    `poll` (
        `id` tinyint DEFAULT NULL,
        `vprasanje` varchar(28) DEFAULT NULL,
        `moznosti` varchar(100) DEFAULT NULL,
        `openedAt` datetime not null default current_timestamp,
        `closedAt` datetime not null default current_timestamp
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_slovenian_ci ROW_FORMAT = DYNAMIC;

LOCK TABLES `poll` WRITE;

INSERT INTO
    `poll`
VALUES
    (
        1,
        'Poznaš mehotovega sestriča?',
        '[\"da\",\"ne\",\"kaj te briga\"]',
        '1970-01-21',
        '1970-01-21'
    ),
    (
        2,
        'Pada zunaj dež?',
        '[\"da\",\"ne\"]',
        '1970-01-20',
        '1970-01-20'
    ),
    (
        3,
        'Prestavimo današnji test?',
        '[\"da\",\"niti pod razno\"]',
        '1970-01-21',
        '1970-01-21'
    ),
    (
        4,
        'Kdo je ubil Janeza?',
        '[\"cia\",\"nsa\",\"meho\",\"gavrilo\"]',
        '1970-01-21',
        '1970-01-21'
    ),
    (
        5,
        'Najvišja gora na Balkanu je:',
        '[\"everest\",\"k2\",\"mckinley\\/denali\",\"Alahova gora\"]',
        '1970-01-21',
        '1970-01-21'
    ),
    (
        6,
        'Kateri dan bi pisali test?',
        '[\"pon\",\"tor\",\"\\u010det\",\"sob\"]',
        '1970-01-20',
        '1970-01-21'
    );

UNLOCK TABLES;

CREATE TABLE
    `pollanswer` (
        `id` tinyint DEFAULT NULL,
        `poll_id` tinyint DEFAULT NULL,
        `user` varchar(6) DEFAULT NULL,
        `answer` varchar(30) DEFAULT NULL,
        `answeredAt` datetime not null default current_timestamp
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_slovenian_ci ROW_FORMAT = DYNAMIC;

LOCK TABLES `pollanswer` WRITE;

INSERT INTO
    `pollanswer`
VALUES
    (1, 1, 'jovo', 'da', '1970-01-21'),
    (2, 1, 'meho', 'da', '1970-01-21'),
    (3, 1, 'bob', 'da', '1970-01-21'),
    (4, 1, 'jagoda', 'da', '1970-01-21'),
    (5, 1, 'matic', 'da', '1970-01-21'),
    (6, 2, 'meho', 'da', '1970-01-20'),
    (7, 2, 'matic', 'da', '1970-01-20'),
    (8, 2, 'bili', 'ne', '1970-01-20'),
    (9, 2, 'jan', 'da', '1970-01-20'),
    (10, 3, 'tim', 'niti pod razno', '1970-01-21'),
    (11, 3, 'jovo', 'da', '1970-01-21'),
    (12, 3, 'jan', 'da', '1970-01-21'),
    (13, 3, 'roža', 'niti pod razno', '1970-01-21'),
    (14, 3, 'zana', 'da', '1970-01-21'),
    (15, 3, 'matic', 'niti pod razno', '1970-01-21'),
    (16, 4, 'stane', 'gavrilo', '1970-01-21'),
    (17, 4, 'roža', 'gavrilo', '1970-01-21'),
    (18, 4, 'bob', 'cia', '1970-01-21'),
    (19, 4, 'tim', 'meho', '1970-01-21'),
    (20, 4, 'zana', 'cia', '1970-01-21'),
    (21, 5, 'bob', 'k2', '1970-01-21'),
    (22, 5, 'zana', 'k2', '1970-01-21'),
    (23, 5, 'meho', 'everest', '1970-01-21'),
    (24, 5, 'tim', 'Alahova gora', '1970-01-21'),
    (25, 5, 'jagoda', 'mckinley/denali', '1970-01-21'),
    (26, 5, 'cilka', 'mckinley/denali', '1970-01-21'),
    (27, 6, 'tim', 'pon', '1970-01-20'),
    (28, 6, 'jovo', 'čet', '1970-01-20'),
    (29, 6, 'jagoda', 'čet', '1970-01-20'),
    (30, 6, 'bob', 'pon', '1970-01-20');

UNLOCK TABLES;

-- Dump completed on 2025-04-11 13:29:55