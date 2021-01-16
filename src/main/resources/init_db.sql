CREATE SCHEMA `service_taxi` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `manufacturers` (
                                 `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                 `manufacturer_name` varchar(225) NOT NULL,
                                 `manufacturer_country` varchar(225) NOT NULL,
                                 `deleted` tinyint(1) NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`manufacturer_id`),
                                 UNIQUE KEY `manufacturer_id_UNIQUE` (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `service_taxi`.`drivers` (
                                          `driver_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                          `driver_name` VARCHAR(225) NOT NULL,
                                          `drivers_licence` VARCHAR(225) NOT NULL,
                                          `deleted` TINYINT(1) NOT NULL DEFAULT 0,
                                          PRIMARY KEY (`driver_id`),
                                          UNIQUE INDEX `driver_id_UNIQUE` (`driver_id` ASC) VISIBLE)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `service_taxi`.`drivers`
    CHANGE COLUMN `driver_id` `driver_id` BIGINT(11) NOT NULL ;


CREATE TABLE `service_taxi`.`cars` (
                                       `car_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                       `model` VARCHAR(225) NOT NULL,
                                       `manufacturer_id` BIGINT(11) NOT NULL,
                                       `deleted` TINYINT(1) NOT NULL DEFAULT 0,
                                       PRIMARY KEY (`car_id`),
                                       UNIQUE INDEX `car_id_UNIQUE` (`car_id` ASC) VISIBLE,
                                       INDEX `cars_manufacturers_idx` (`manufacturer_id` ASC) VISIBLE,
                                       CONSTRAINT `cars_manufacturers`
                                           FOREIGN KEY (`manufacturer_id`)
                                               REFERENCES `service_taxi`.`manufacturers` (`manufacturer_id`)
                                               ON DELETE NO ACTION
                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE `service_taxi`.`cars_drivers` (
                                               `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                               `driver_id` BIGINT(11) NOT NULL,
                                               `cars_id` BIGINT(11) NOT NULL,
                                               PRIMARY KEY (`id`),
                                               INDEX `cars_drivers_drivers_idx` (`driver_id` ASC) VISIBLE,
                                               INDEX `cars_drivers_cars_idx` (`cars_id` ASC) VISIBLE,
                                               UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                               CONSTRAINT `cars_drivers_drivers`
                                                   FOREIGN KEY (`driver_id`)
                                                       REFERENCES `service_taxi`.`drivers` (`driver_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
                                               CONSTRAINT `cars_drivers_cars`
                                                   FOREIGN KEY (`cars_id`)
                                                       REFERENCES `service_taxi`.`cars` (`car_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

ALTER TABLE `service_taxi`.`cars_drivers`
    ADD COLUMN `deleted` TINYINT(1) NOT NULL DEFAULT 0 AFTER `cars_id`,
    CHANGE COLUMN `id` `id` BIGINT(11) NOT NULL ;

ALTER TABLE `service_taxi`.`cars_drivers`
    CHANGE COLUMN `cars_id` `car_id` BIGINT NOT NULL ,
    ADD INDEX `cars_drivers_drivers_idx` (`driver_id` ASC) VISIBLE,
    ADD INDEX `cars_drivers_cars_idx` (`car_id` ASC) VISIBLE;

ALTER TABLE `service_taxi`.`cars_drivers`
    DROP COLUMN `id`,
    DROP INDEX `id_UNIQUE` ,
    DROP PRIMARY KEY;
;
ALTER TABLE `service_taxi`.`cars_drivers`
    DROP COLUMN `deleted`;

ALTER TABLE `service_taxi`.`cars_drivers`
    ADD CONSTRAINT `cars_drivers_drivers`
        FOREIGN KEY (`driver_id`)
            REFERENCES `service_taxi`.`drivers` (`driver_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `cars_drivers_cars`
        FOREIGN KEY (`car_id`)
            REFERENCES `service_taxi`.`cars` (`car_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;



