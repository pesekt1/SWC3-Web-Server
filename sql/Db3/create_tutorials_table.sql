create table tutorials
(
    id int identity
        primary key,
    description nvarchar(50) not null,
    title nvarchar(50) not null,
    published bit not null
)
    go
