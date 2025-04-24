create table hexrite.connection (
    id varchar(255) not null,
    name varchar(255) not null unique,
    description varchar(255),
    type varchar(255) check (
        type in (
            'OLLAMA',
            'CHATGPT',
            'GEMINI',
            'CLAUDE',
            'MISTRAL',
            'OPENAI_COMPATIBLE',
            'CUSTOM'
        )
    ),
    base_url bytea,
    api_key varchar(255),
    created_at timestamp(6) with time zone not null,
    updated_at timestamp(6) with time zone,
    enabled boolean not null,
    primary key (id)
);
