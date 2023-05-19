create schema if not exists on_trust_test;
use on_trust_test;

create table if not exists post (
    id bigint not null,
    created_date datetime(6) not null,
    updated_date datetime(6),
    contents varchar(255),
    title varchar(255),
    primary key (id)
) engine=InnoDB;

create table if not exists post_seq (
    next_val bigint
) engine=InnoDB;

# when post_seq is empty
INSERT INTO post_seq
SELECT 1
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM post_seq
);
