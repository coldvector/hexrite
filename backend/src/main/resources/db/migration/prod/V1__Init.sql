create table hexrite.connections (
    "id" varchar(255) not null,
    "name" varchar(255) not null unique,
    "description" varchar(255),
    "type" varchar(255) check (
        "type" in (
            'OLLAMA',
            'CHATGPT',
            'GEMINI',
            'CLAUDE',
            'MISTRAL',
            'OPENAI_COMPATIBLE',
            'CUSTOM'
        )
    ) not null,
    "base_url" varchar(255) not null,
    "api_key" varchar(255),
    "created_at" timestamp(6) with time zone not null,
    "updated_at" timestamp(6) with time zone not null,
    "enabled" boolean not null,
    primary key (id)
);
create table hexrite.settings (
    "id" bigint not null,
    "key" varchar(255) not null unique,
    "value" varchar(255),
    primary key (id)
);
