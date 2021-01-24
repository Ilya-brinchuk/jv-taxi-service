CREATE SCHEMA `service_taxi` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `manufacturers` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(225) NOT NULL,
                                 `country` varchar(225) NOT NULL,
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `manufacturer_id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;

CREATE TABLE `cars` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `model` varchar(225) NOT NULL,
                        `manufacturer_id` bigint NOT NULL,
                        `deleted` tinyint(1) NOT NULL DEFAULT '0',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `car_id_UNIQUE` (`id`),
                        KEY `cars_manufacturers_idx` (`manufacturer_id`),
                        CONSTRAINT `cars_manufacturers` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

CREATE TABLE `drivers` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `name` varchar(225) NOT NULL,
                           `licence` varchar(225) NOT NULL,
                           `deleted` tinyint(1) NOT NULL DEFAULT '0',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `driver_id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

CREATE TABLE `cars_drivers` (
                                `driver_id` bigint NOT NULL,
                                `car_id` bigint NOT NULL,
                                KEY `cars_drivers_cars_idx` (`car_id`),
                                KEY `car_drivers_drivers_idx` (`driver_id`),
                                CONSTRAINT `car_drivers_drivers` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`),
                                CONSTRAINT `cars_drivers_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `drivers`
    ADD COLUMN `login` VARCHAR(45) NOT NULL AFTER `deleted`,
    ADD COLUMN `password` VARCHAR(45) NOT NULL AFTER `login`;


ALTER TABLE `drivers`
    ADD UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE;
;
