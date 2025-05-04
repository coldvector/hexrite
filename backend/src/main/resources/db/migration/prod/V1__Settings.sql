create table hexrite.settings (
    "id" bigint not null,
    "key" varchar(255) not null unique,
    "value" varchar(255),
    primary key (id)
);
