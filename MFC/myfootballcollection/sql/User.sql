drop table if exists "user";

CREATE TABLE "user"
(
    user_id     varchar(50) PRIMARY KEY,
    email       varchar(25) UNIQUE,
    name        varchar(250),
    tag         varchar(25) UNIQUE,
    birthday	date,
    country     varchar(50),
    profilePictureUrl     varchar,
    headerPictureUrl      varchar,
    followingTeams varchar[]
);
