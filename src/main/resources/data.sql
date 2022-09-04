INSERT INTO "mpa" (name)
VALUES ('G'),
	     ('PG'),
	     ('PG-13'),
	     ('R'),
	     ('PG-13'),
	     ('NC-17');

INSERT INTO "genre" (name)
VALUES ('Комедия'),
	     ('Драма'),
	     ('Мультфильм'),
	     ('Триллер'),
	     ('Документальный'),
	     ('Боевик');

INSERT INTO "film" (name, description, release_date, duration, mpa_id)
VALUES ('testFilmName', 'testFilmDescription', NOW(), 190, 1);

INSERT INTO "user" (email, login, name, birthday)
VALUES ('testUserEmail@mail.com', 'testUserLogin', 'testUserName', DATEADD(YEAR, -25, NOW())::date),
	     ('testUser2Email@mail.com', 'testUser2Login', 'testUser2Name', DATEADD(YEAR, -25, NOW())::date),
	     ('testUser3Email@mail.com', 'testUser3Login', 'testUser3Name', DATEADD(YEAR, -25, NOW())::date);

INSERT INTO "friendship" (user_id, friend_id, is_confirmed)
VALUES (1, 2, false),
	     (3, 2, false);

INSERT INTO "film_genres" (film_id, genre_id)
VALUES (1, 3),
	     (1, 5);

INSERT INTO "likes" (user_id, film_id)
VALUES (1, 1),
       (2, 1),
       (3,1);
