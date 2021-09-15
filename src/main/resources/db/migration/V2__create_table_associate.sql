create table if not exists backvotes.associate
(
    id       bigserial    not null,
    name     varchar(100) not null,
    document varchar(11)  not null,

    primary key (id)
)