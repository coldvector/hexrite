CREATE TABLE hexrite.projects (
    "id" VARCHAR(255) NOT NULL,
    "optlock" BIGINT NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "context" TEXT,
    "created_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    "updated_at" TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
)
