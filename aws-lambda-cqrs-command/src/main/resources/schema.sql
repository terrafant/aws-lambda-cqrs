CREATE SCHEMA `command`;
CREATE TABLE `command`.`events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(45) NOT NULL,
  `timestamp` DATETIME NOT NULL DEFAULT now(),
  `data` MEDIUMTEXT NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;




