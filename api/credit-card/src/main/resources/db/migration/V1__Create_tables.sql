create table Card (
  id varchar(100) PRIMARY KEY,
  userId varchar(100) NOT NULL,
  number varchar(16) NOT NULL,
  balance DOUBLE NOT NULL
);