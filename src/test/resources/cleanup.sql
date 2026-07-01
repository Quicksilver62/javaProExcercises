delete from users.users;
alter table users.users alter column id restart with 1;