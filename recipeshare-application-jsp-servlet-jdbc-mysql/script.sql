CREATE TABLE `demo`.`users` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `demo`.`recipes` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `title` varchar(250) DEFAULT NULL,
  `owner` varchar(250) DEFAULT NULL,
  `filename` varchar(250) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `publication_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `demo`.`recipes`
	ADD `likes` int(3) DEFAULT '0';