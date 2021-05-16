create table if not exists invoices
(
    invoice_id int not null
        primary key,
    number varchar(50) not null,
    invoice_total decimal(9,2) not null,
    payment_total decimal(9,2) default 0.00 not null,
    invoice_date date not null,
    due_date date not null,
    payment_date date null,
    order_id int not null,
    constraint fk_order_id
        foreign key (order_id) references orders (order_id)
);