alter table backvotes.vote
    add column order_id bigint;

alter table backvotes.vote
    add foreign key (order_id) references backvotes.order (id);