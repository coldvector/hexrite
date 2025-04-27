CREATE VIEW hexrite.connections_simplified AS
SELECT "id",
    "name",
    "type",
    "base_url",
    "enabled"
FROM hexrite.connections;
