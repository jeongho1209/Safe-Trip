CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT             NOT NULL AUTO_INCREMENT,
    account_id VARCHAR(20) UNIQUE NOT NULL,
    password   CHAR(60)           NOT NULL,
    age        TINYINT UNSIGNED   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS travel_destination
(
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    name     VARCHAR(30) NOT NULL,
    eng_name VARCHAR(30) NOT NULL,
    code     CHAR(20)    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS country_safe_info
(
    id                    BIGINT        NOT NULL AUTO_INCREMENT,
    title                 VARCHAR(40)   NOT NULL,
    content               VARCHAR(1000) NOT NULL,
    created_date          DATE          NOT NULL,
    travel_destination_id BIGINT        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (travel_destination_id) REFERENCES travel_destination (id)
);

CREATE TABLE IF NOT EXISTS review
(
    id                    BIGINT        NOT NULL AUTO_INCREMENT,
    title                 VARCHAR(40)   NOT NULL,
    content               VARCHAR(1000) NOT NULL,
    created_date          DATE          NOT NULL,
    image_url1            VARCHAR(500)  NULL,
    image_url2            VARCHAR(500)  NULL,
    image_url3            VARCHAR(500)  NULL,
    is_deleted            BIT(1)        NOT NULL DEFAULT false,
    travel_destination_id BIGINT        NOT NULL,
    user_id               BIGINT        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (travel_destination_id) REFERENCES travel_destination (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS travel_info
(
    id                    BIGINT        NOT NULL AUTO_INCREMENT,
    title                 VARCHAR(40)   NOT NULL,
    content               VARCHAR(1000) NOT NULL,
    created_date          DATE          NOT NULL,
    is_deleted            BIT(1)        NOT NULL DEFAULT false,
    travel_destination_id BIGINT        NOT NULL,
    user_id               BIGINT        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (travel_destination_id) REFERENCES travel_destination (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);