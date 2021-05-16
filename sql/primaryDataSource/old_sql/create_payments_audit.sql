create table payments_audit
(
    customer_id int not null,
    date date not null,
    amount decimal(9,2) not null,
    action_type varchar(50) not null,
    action_date datetime not null
);

