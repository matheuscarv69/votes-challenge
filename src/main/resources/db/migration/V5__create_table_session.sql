create table if not exists backvotes.session
(
    id           bigserial not null,
    order_id     bigint    not null,
    created_at   timestamp not null,
    finished_at  timestamp,
    session_open boolean   not null,

    primary key (id),
    foreign key (order_id) references backvotes.order (id)
)