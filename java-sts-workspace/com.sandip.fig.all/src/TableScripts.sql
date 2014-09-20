CREATE DATABASE `fig_db` /*!40100 COLLATE 'utf8_general_ci' */

CREATE TABLE `registration` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`active` BIT(1) NOT NULL DEFAULT b'0',
	`username` VARCHAR(100) NOT NULL DEFAULT '0',
	`password` VARCHAR(512) NOT NULL DEFAULT '0',
	`email` VARCHAR(100) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `upload_images` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`filepath` VARCHAR(200) NOT NULL DEFAULT '0',
	`uploaded_by` BIGINT(20) NOT NULL DEFAULT '0',
	`created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`latitude` VARCHAR(50) NULL DEFAULT '0',
	`longitude` VARCHAR(50) NULL DEFAULT '0',
	`comments` VARCHAR(500) NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	INDEX `FK__registration` (`uploaded_by`),
	CONSTRAINT `FK__registration` FOREIGN KEY (`uploaded_by`) REFERENCES `registration` (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
