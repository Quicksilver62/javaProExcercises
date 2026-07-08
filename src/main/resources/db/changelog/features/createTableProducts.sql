create table if not exists products
(
    id              bigserial primary key,
    account         varchar(255),
    amount          double,
    product_type    varchar(255),
    user_id         bigint not null,
    created_at      timestamp,
    updated_at      timestamp,
    constraint fk_products_user foreign key (user_id) references users(id)
);