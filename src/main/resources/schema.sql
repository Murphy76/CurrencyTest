DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `roles` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `user_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `rates` (
  `id` int(11) NOT NULL,
  `comissions` decimal(5,2) DEFAULT NULL,
  `curr_from` int(11) DEFAULT NULL,
  `curr_to` int(11) DEFAULT NULL,
  `rate` decimal(10,3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;