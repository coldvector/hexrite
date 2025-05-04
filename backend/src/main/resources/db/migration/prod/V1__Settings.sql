create table hexrite.settings (
    "key" varchar(255) not null,
    "value" varchar(255),
    "optlock" bigint not null,
    primary key (key)
);
