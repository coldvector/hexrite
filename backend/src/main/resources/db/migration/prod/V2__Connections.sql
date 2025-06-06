CREATE TABLE hexrite.connections (
    "id" VARCHAR(255) NOT NULL,
    "optlock" BIGINT NOT NULL,
    "name" VARCHAR(255) NOT NULL UNIQUE,
    "description" VARCHAR(255),
    "type" VARCHAR(255) NOT NULL CHECK (
        "type" IN (
            'OLLAMA',
            'CHATGPT',
            'GEMINI',
            'CLAUDE',
            'MISTRAL',
            'OPENAI_COMPATIBLE',
            'CUSTOM'
        )
    ),
    "base_url" VARCHAR(255) NOT NULL,
    "api_key" VARCHAR(255),
    "created_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    "enabled" BOOLEAN NOT NULL,
    PRIMARY KEY ("id")
);

CREATE VIEW hexrite.connections_simplified AS
SELECT
    "id",
    "name",
    "type",
    "base_url",
    "enabled"
FROM
    hexrite.connections
ORDER BY
    "updated_at" DESC;
