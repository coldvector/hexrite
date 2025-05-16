CREATE TABLE hexrite.chats (
    "id" VARCHAR(255) NOT NULL,
    "optlock" BIGINT NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "connection_id" VARCHAR(255),
    "model" VARCHAR(255),
    "created_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    CONSTRAINT "chat_to_connection_fk" FOREIGN key ("connection_id") REFERENCES connections ("id"),
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
