create table Plan (
  id varchar(100) PRIMARY KEY,
  name varchar(100) NOT NULL,
  price DOUBLE NOT NULL,
  data SMALLINT NOT NULL,
  calls SMALLINT NOT NULL
);

create table Card (
  id varchar(100) PRIMARY KEY,
  number varchar(11) NOT NULL,
  balance DOUBLE NOT NULL,
  data SMALLINT NOT NULL,
  calls SMALLINT NOT NULL,
  planId VARCHAR(100) NOT NULL,
  FOREIGN KEY (planId) REFERENCES Plan(id)
);

INSERT INTO Plan VALUES ('1', '88 suite', 88, 500, 100);
INSERT INTO Card VALUES ('1', '13800000000', 0, 0, 0, '1');