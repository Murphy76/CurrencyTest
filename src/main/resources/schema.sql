DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `password` varchar(50)  DEFAULT NULL,
  `roles` varchar(255)  DEFAULT NULL,
  `user_name` varchar(50)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT UC_User UNIQUE (user_name)
) ;

CREATE TABLE `rates` (
  `id` int(11) NOT NULL,
  `commissions` decimal(5,2) DEFAULT NULL,
  `curr_from` int(11) DEFAULT NULL,
  `curr_to` int(11) DEFAULT NULL,
  `rate` decimal(10,3) DEFAULT NULL,
  PRIMARY KEY (`id`),
   CONSTRAINT UC_Currency_Couple UNIQUE (curr_from, curr_to)
) ;