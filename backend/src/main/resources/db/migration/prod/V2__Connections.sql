create table hexrite.connections (
    "id" varchar(255) not null,
    "optlock" bigint not null,
    "name" varchar(255) not null unique,
    "description" varchar(255),
    "type" varchar(255) not null check (
        "type" in (
            'OLLAMA',
            'CHATGPT',
            'GEMINI',
            'CLAUDE',
            'MISTRAL',
            'OPENAI_COMPATIBLE',
            'CUSTOM'
        )
    ),
    "base_url" varchar(255) not null,
    "api_key" varchar(255),
    "created_at" timestamp(6) with time zone not null,
    "updated_at" timestamp(6) with time zone not null,
    "enabled" boolean not null,
    primary key (id)
);
CREATE VIEW hexrite.connections_simplified AS
SELECT "id",
    "name",
    "type",
    "base_url",
    "enabled"
FROM hexrite.connections;
