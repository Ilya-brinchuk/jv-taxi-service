CREATE SCHEMA `service_taxi` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `manufacturers` (
                                 `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                 `manufacturer_name` varchar(225) NOT NULL,
                                 `manufacturer_country` varchar(225) NOT NULL,
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`manufacturer_id`),
                                 UNIQUE KEY `manufacturer_id_UNIQUE` (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
