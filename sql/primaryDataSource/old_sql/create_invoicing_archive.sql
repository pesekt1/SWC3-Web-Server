create table invoicing_archive
(
    invoice_id int not null,
    customer_id int not null,
    client varchar(50) not null,
    invoice_total decimal(9,2) not null,
    payment_total decimal(9,2) default 0.00 not null,
    invoice_date date not null,
    due_date date not null,
    payment_date date null
);

