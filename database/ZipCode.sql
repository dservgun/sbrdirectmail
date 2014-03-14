CREATE TABLE ZipCode(
  id INT PRIMARY KEY AUTO_INCREMENT,
  zipCode VARCHAR(255),
  city VARCHAR(255),
  stateAbbreviation VARCHAR(255),
  createdBy VARCHAR(255),
  creationDate DATETIME,
  creationIP VARCHAR(255),
  modifiedBy VARCHAR(255),
  modificationDate DATETIME,
  modificationIP VARCHAR(255),
  deleted BOOLEAN
)Type=INNODB; 
 
