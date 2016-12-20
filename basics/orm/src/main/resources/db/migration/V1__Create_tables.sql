create table User (
  id varchar(100) PRIMARY KEY,
  firstName varchar(100) NOT NULL,
  lastName varchar(100) NOT NULL,
  age SMALLINT
);

INSERT INTO User VALUES ('1', 'Sid ', 'Shen', 24);