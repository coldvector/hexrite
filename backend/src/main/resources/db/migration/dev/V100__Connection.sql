INSERT INTO hexrite.connections (
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
VALUES (
        '33e8015c-d306-4cf1-a983-a0c248ba2809',
        1,
        'Ollama Work',
        'Ollama Workstation',
        'OLLAMA',
        'http://testnet2:11434',
        '',
        '2025-04-20 18:19:15.10535+00',
        '2025-04-20 18:19:15.10535+00',
        true
    ),
    (
        'c683af6f-7968-460d-bf97-09e9aa5c5888',
        1,
        'Ollama Home',
        'Ollama Homestation',
        'OLLAMA',
        'http://127.0.0.1:11434',
        '',
        '2025-04-23 18:19:15.10535+00',
        '2025-04-23 18:19:15.10535+00',
        false
    ),
    (
        'ba907b31-9175-4ed3-ad24-f35e764145c4',
        1,
        'Gemini API Project',
        'GCP Project 210308234727',
        'GEMINI',
        'https://generativelanguage.googleapis.com/v1beta',
        'XXXXXXXXXXXXXXXXXXXXXXXXXXXX',
        '2025-04-25 11:19:15.10535+00',
        '2025-04-25 13:19:15.10535+00',
        true
    );
