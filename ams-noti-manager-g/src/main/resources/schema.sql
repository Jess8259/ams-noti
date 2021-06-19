DROP TABLE IF EXISTS analysis_result;

create table analysis_result (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  start_dttm timestamp not null,
  end_dttm timestamp not null,
  usage int not null
);