create table hexrite.settings (
    "id" bigint not null,
    "key" varchar(255) not null unique,
    "value" varchar(255),
    "optlock" bigint not null,
    primary key (id)
);
