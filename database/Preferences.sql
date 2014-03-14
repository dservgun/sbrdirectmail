CREATE TABLE Preference(
  id INT PRIMARY KEY AUTO_INCREMENT,
  logo TEXT,
  headerAddressLine VARCHAR(255),
  tagLine1 VARCHAR(255),
  tagLine2 VARCHAR(255),
  tagLine3 VARCHAR(255),
  userId INT,
  createdBy VARCHAR(255),
  creationDate DATETIME,
  creationIP VARCHAR(255),
  modifiedBy VARCHAR(255),
  modificationDate DATETIME,
  modificationIP VARCHAR(255),
  deleted BOOLEAN,
  FOREIGN KEY (userId) REFERENCES UserTable(id)  
  )Type=INNODB;

CREATE TABLE Letter(
  id INT PRIMARY KEY AUTO_INCREMENT,
  content TEXT,
  sent BOOLEAN,
  contactId INT,
  createdBy VARCHAR(255),
  creationDate DATETIME,
  creationIP VARCHAR(255),
  modifiedBy VARCHAR(255),
  modificationDate DATETIME,
  modificationIP VARCHAR(255),
  deleted BOOLEAN,
  FOREIGN KEY (contactId) REFERENCES Contact(id)
)Type=INNODB;

CREATE TABLE LetterTemplate(
  id INT PRIMARY KEY AUTO_INCREMENT,
  templateName VARCHAR(255),
  content TEXT,
  userId INT,
  createdBy VARCHAR(255),
  creationDate DATETIME,
  creationIP VARCHAR(255),
  modifiedBy VARCHAR(255),
  modificationDate DATETIME,
  modificationIP VARCHAR(255),
  deleted BOOLEAN,
  FOREIGN KEY (userId) REFERENCES UserTable(id)
)Type=INNODB;
