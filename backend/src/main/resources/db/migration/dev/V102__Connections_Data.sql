INSERT INTO
    hexrite.connections (
        "id",
        "optlock",
        "name",
        "description",
        "type",
        "base_url",
        "api_key",
        "created_at",
        "updated_at",
        "enabled"
    )
VALUES
    (
        '33e8015c-d306-4cf1-a983-a0c248ba2809',
        0,
        'Ollama Work',
        'Ollama Workstation',
        'OLLAMA',
        'http://yankee:11434',
        '',
        '2025-01-15 10:25:30.12345+00',
        '2025-01-15 12:30:45.54321+00',
        TRUE
    ),
    (
        'c683af6f-7968-460d-bf97-09e9aa5c5888',
        0,
        'Ollama Home',
        'Ollama Homestation',
        'OLLAMA',
        'http://127.0.0.1:11434',
        '',
        '2025-02-20 14:10:05.67890+00',
        '2025-02-20 16:15:20.98765+00',
        FALSE
    ),
    (
        'ba907b31-9175-4ed3-ad24-f35e764145c4',
        0,
        'Gemini API Project',
        'GCP Project 210308234727',
        'GEMINI',
        'https://generativelanguage.googleapis.com/v1beta',
        'XXXXXXXXXXXXXXXXXXXXXXXXXXXX',
        '2025-03-10 09:45:15.11111+00',
        '2025-03-10 11:50:25.22222+00',
        TRUE
    );
