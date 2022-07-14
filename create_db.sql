CREATE TABLE IF NOT EXISTS novel (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(1000),
    author VARCHAR(200),
    novelDesc TEXT,
    coverUrl VARCHAR(3000),
    tag VARCHAR(200),
    chapterCount INT,
    originUrl VARCHAR(3000),
    nid VARCHAR(1000),
    updateTime LONG,
    extra VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS chapter (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    novelId LONG,
    pageIndex INT,
    originUrl VARCHAR(3000),
    title VARCHAR(1000),
    content TEXT,
    updateTime LONG,
    extra VARCHAR(1000)
);
