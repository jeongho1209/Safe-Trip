CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT      NOT NULL,
    account_id VARCHAR(20) UNIQUE NOT NULL,
    password   CHAR(60)    NOT NULL,
    age        TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (id)
);
