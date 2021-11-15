DROP TABLE IF EXISTS book;
CREATE TABLE book (
    id integer not null auto_increment,
    title varchar(50),
    author varchar(50),
    price decimal(15),
    parent_id integer,
    PRIMARY KEY (id)
);
INSERT INTO book (title, author, price) VALUES
    ('Harry Potter', 'J.K.Rowling', 20),
    ('Twilight', 'S.Meyer', 14),
    ('The Accenture Book', 'J.Sweet', 25),
    ('Pride and Prejudice', 'J.Austen', 15),
    ('War and Peace', 'L.Tolstoy', 30);