create table if not exists backvotes.vote
(
    id           bigserial  not null,
    created_at   timestamp  not null,
    type_vote    varchar(3) not null,
    associate_id bigint,

    primary key (id),
    foreign key (associate_id) references backvotes.associate (id)
)