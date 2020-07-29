DROP TABLE IF EXISTS covid_report;
CREATE TABLE covid_report(
  id INT AUTO_INCREMENT  PRIMARY KEY,
  location VARCHAR(250) NOT NULL,
  active int NOT NULL,
  dead int,
  confirmed int,
  recovered int,
  tested int
);

