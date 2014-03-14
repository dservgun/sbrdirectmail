-- A Calendar specific table entries.
CREATE TABLE Note(
  id INT PRIMARY KEY AUTO_INCREMENT,
  notes VARCHAR(255),
  contactId int,
  createdBy VARCHAR(255),
  creationDate DATETIME,
  creationIP VARCHAR(255),
  modifiedBy VARCHAR(255),
  modificationDate DATETIME,
  modificationIP VARCHAR(255),
  deleted BOOLEAN,
  FOREIGN KEY (contactId) REFERENCES Contact(id)
)Type=INNODB; 

CREATE TABLE Reminder(
  id INT PRIMARY KEY AUTO_INCREMENT,
  reminderDate DATETIME,
  contactId INT,
  description VARCHAR(255),
  reminderAction enum ('Email','Mail','Call'),
  actionTaken BOOLEAN,
  createdBy VARCHAR(255),
  creationDate DATETIME,
  creationIP VARCHAR(255),
  modifiedBy VARCHAR(255),
  modificationDate DATETIME,
  modificationIP VARCHAR(255),
  deleted BOOLEAN,
  FOREIGN KEY (contactId) REFERENCES Contact(id)
)Type=INNODB;