INSERT INTO book (id, title, author, isbn) VALUES (1, 'Clean Code', 'Robert C. Martin', '978-0132350884');
INSERT INTO book (id, title, author, isbn) VALUES (2, 'Effective Java', 'Joshua Bloch', '978-0134685991');
INSERT INTO book (id, title, author, isbn) VALUES (3, 'Design Patterns', 'Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides', '978-0201633610');

INSERT INTO review (id, reviewer_name, content, rating, book_id) VALUES (1, 'Alice', 'Great book on clean coding practices.', 5, 1);
INSERT INTO review (id, reviewer_name, content, rating, book_id) VALUES (2, 'Mark', 'Nice book', 4, 1);
INSERT INTO review (id, reviewer_name, content, rating, book_id) VALUES (3, 'John', 'Love the book', 3, 1);
INSERT INTO review (id, reviewer_name, content, rating, book_id) VALUES (4, 'Bob', 'A must-read for Java developers.', 4, 2);
INSERT INTO review (id, reviewer_name, content, rating, book_id) VALUES (5, 'Charlie', 'Excellent introduction to design patterns.', 5, 3);
