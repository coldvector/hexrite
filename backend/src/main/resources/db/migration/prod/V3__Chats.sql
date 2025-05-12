create table hexrite.chats (
    "id" varchar(255) not null,
    "optlock" bigint not null,
    "title" varchar(255) not null,
    "connection_id" varchar(255),
    "model" varchar(255),
    "created_at" timestamp(6) with time zone not null,
    "updated_at" timestamp(6) with time zone not null,
    constraint "chat_to_connection_fk" foreign key ("connection_id") references connections("id"),
    primary key ("id")
);
create table hexrite.messages (
    "id" varchar(255) not null,
    "chat_id" varchar(255) not null,
    "role" varchar(255) not null check (
        "role" in (
            'user',
            'model',
            'system',
            'assistant',
            'tool'
        )
    ),
    "content" text not null,
    "timestamp" timestamp(6) with time zone not null,
    constraint "message_to_chat_fk" foreign key ("chat_id") references chats("id"),
    primary key ("id")
);
