create table charm(
id serial PRIMARY KEY,
name varchar(100),
description varchar(255),
energy float
);

create type gender as ENUM('MALE', 'FEMALE');
create type address as ENUM('FACT', 'REG');
create type phone as ENUM('HOME', 'WORK', 'MOBILE');
create table client(
id serial PRIMARY KEY,
surname varchar(100) NOT NULL,
name varchar(100) NOT NULL,
patronymic varchar(200),
gender sex NOT NULL,
birth_date date,
actual boolean DEFAULT true,
charm integer references charm(id)
);

create table client_addr(
client integer references client(id),
type address,
street varchar(255),
house varchar(255),
flat varchar(255),
PRIMARY KEY(client, type)
);

create table client_phone(
client integer references client(id),
number varchar(100),
type phone
);

create table client_account(
id serial PRIMARY KEY,
client integer references client(id),
money float,
number varchar(100),
registered_at timestamp
);

create table transaction_type(
id serial PRIMARY KEY,
code varchar(100),
name varchar(255)
);

create table client_account_transaction(
id serial PRIMARY KEY,
account integer references client_account(id),
money float,
finished_at timestamp,
type integer references transaction_type(id)
);



select c.id, c.name, c.surname, c.patronymic, ch.name charmName, (current_date-c.birth_date)/365 age, sum(c_acc.money) commonMoney, MAX(c_acc.money) maxMoney, MIN(c_acc.money) minMoney 
from client c inner join charm ch on c.charm = ch.id inner join client_account c_acc on c.id = c_acc.client where c.actual=true group by c.id, ch.name
