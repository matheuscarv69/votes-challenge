create table if not exists backvotes.order
(
    id         bigserial   not null,
    theme      varchar(50) not null,
    created_at timestamp   not null,
    whishes_id bigint,

    primary key (id),
    foreign key (whishes_id) references backvotes.vote (id)
)