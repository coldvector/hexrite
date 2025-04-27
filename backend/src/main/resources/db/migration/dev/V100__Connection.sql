INSERT INTO hexrite.connections (
        "id",
        "name",
        "description",
        "type",
        "base_url",
        "api_key",
        "created_at",
        "updated_at",
        "enabled"
    )
VALUES (
        '33e8015c-d306-4cf1-a983-a0c248ba2809',
        'Ollama-1',
        'Workstation Ollama',
        'OLLAMA',
        'http://testnet2:11434',
        '',
        '2025-04-20 18:19:15.10535+00',
        '2025-04-20 18:19:15.10535+00',
        true
    ),
    (
        'c683af6f-7968-460d-bf97-09e9aa5c5888',
        'Ollama-2',
        'Homestation Ollama',
        'OLLAMA',
        'http://127.0.0.1:11434',
        '',
        '2025-04-23 18:19:15.10535+00',
        '2025-04-23 18:19:15.10535+00',
        false
    ),
    (
        'ba907b31-9175-4ed3-ad24-f35e764145c4',
        'Gemini API Key 1',
        'GCP Project 111122223333',
        'GEMINI',
        'https://generativelanguage.googleapis.com/v1beta',
        'XXXXXXXXXXXXXXXXXXXXXXXXXXXX',
        '2025-04-25 11:19:15.10535+00',
        '2025-04-25 13:19:15.10535+00',
        true
    );
