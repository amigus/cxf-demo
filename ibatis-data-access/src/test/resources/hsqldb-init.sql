DROP TABLE Content IF EXISTS
CREATE TABLE Content(
	id char(36) not null,
	author varchar(100) not null,
	title varchar(100) not null,
	text varchar(32768) not null,
	created date not null,
	primary key (id)
);

