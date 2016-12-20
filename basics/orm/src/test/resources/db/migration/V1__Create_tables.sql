create table Users (
  id varchar(100) PRIMARY KEY,
  firstName varchar(100) NOT NULL,
  lastName varchar(100) NOT NULL,
  age SMALLINT
);

INSERT INTO Users VALUES ('1', 'Sid', 'Shen', 24);