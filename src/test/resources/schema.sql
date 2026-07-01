create schema if not exists users;

create table if not exists users.users
(
    id              bigserial primary key,
    username        varchar(255) unique
);