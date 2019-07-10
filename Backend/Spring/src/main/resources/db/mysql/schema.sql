CREATE TABLE IF NOT EXISTS users1 (
  user_id Integer PRIMARY KEY AUTOINCREMENT
  user_name VARCHAR(30),
  myrank INT,
  password VARCHAR(30),
  score INT,
  success boolean
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS leaderboard (
  user_name VARCHAR(15),
  myrank INT,
  score INT
  PRIMARY KEY(user_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS profile (
  user_name VARCHAR(15),
  avatar VARCHAR(30),
  display_name VARCHAR(20),
  high_score INT
) engine=InnoDB;