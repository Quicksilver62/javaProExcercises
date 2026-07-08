set referential_integrity false;

truncate table products restart identity;
truncate table users restart identity;

insert into users (username) values
                                 ('John Doe'),
                                 ('Richard Roe'),
                                 ('Jane Doe');

insert into products (account, amount, product_type, user_id) values
                                 ('12345678', 1000000, 'ACCOUNT', 1);