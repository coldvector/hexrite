CREATE TABLE hexrite.chats (
    "id" VARCHAR(255) NOT NULL,
    "optlock" BIGINT NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "connection_id" VARCHAR(255),
    "project_id" VARCHAR(255),
    "model" VARCHAR(255),
    "created_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    CONSTRAINT "chat_to_connection_fk" FOREIGN key ("connection_id") REFERENCES connections ("id"),
    CONSTRAINT "chat_to_project_fk" FOREIGN key ("project_id") REFERENCES projects ("id"),
    PRIMARY KEY ("id")
);

CREATE TABLE hexrite.messages (
    "id" VARCHAR(255) NOT NULL,
    "chat_id" VARCHAR(255) NOT NULL,
    "role" VARCHAR(255) NOT NULL CHECK (
        "role" IN ('user', 'model', 'system', 'assistant', 'tool')
    ),
    "content" TEXT NOT NULL,
    "timestamp" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    CONSTRAINT "message_to_chat_fk" FOREIGN key ("chat_id") REFERENCES chats ("id"),
    PRIMARY KEY ("id")
);

CREATE VIEW hexrite.chats_simplified AS
SELECT
    c."id" AS "chat_id",
    c."title" AS "chat_title",
    c."model" AS "model",
    p."title" AS "project_title",
    m."id" AS "message_id",
    m."role" AS "role",
    CASE
        WHEN LENGTH(m."content") > 20 THEN SUBSTRING(
            m."content"
            FROM
                1 FOR 15
        ) || '...' || SUBSTRING(
            m."content"
            FROM
                GREATEST(LENGTH(m."content") - 4, 1) FOR 5
        )
        ELSE m."content"
    END AS "content",
    m."timestamp" AS "timestamp"
FROM
    hexrite.chats c
    LEFT JOIN hexrite.messages m ON c."id" = m."chat_id"
    LEFT JOIN hexrite.projects p ON c."project_id" = p."id"
ORDER BY
    c."updated_at" DESC,
    m."timestamp" ASC;
