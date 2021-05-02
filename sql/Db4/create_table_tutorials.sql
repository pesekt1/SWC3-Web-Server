create table if not exists tutorials
(
    id serial not null
        constraint tutorials_pkey
        primary key,
    description varchar(50) not null,
    title varchar(50) not null,
    published bit not null
);

alter table tutorials owner to postgres;