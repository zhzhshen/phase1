create table Users (
  id varchar(100) PRIMARY KEY,
  firstName varchar(100) NOT NULL,
  lastName varchar(100) NOT NULL,
  age SMALLINT
);

create table UserInfo (
  id varchar(100) PRIMARY KEY,
  phone varchar(11),
  address varchar(100)
);

INSERT INTO Users VALUES ('1', 'Sid', 'Shen', 24);