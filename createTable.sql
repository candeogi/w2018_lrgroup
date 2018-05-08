CREATE TABLE User (
    email VARCHAR(50) NOT NULL,
    name VARCHAR(20) NOT NULL,                  
    surname VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL,
    profilePicture -- no idea 
    password  VARCHAR(20) NOT NULL, -- not sure its the best data type
    registrationDate DATE,
    birthday DATE,
    description VARCHAR(300),   
 
    PRIMARY KEY(email) 
);
 
CREATE TABLE Question (
    id SERIAL,
    title VARCHAR(50),
    user VARCHAR(50), --abbiamo scritto 0 sull'er
    ts TIMESTAMP,
    lastModified TIMESTAMP,
    body VARCHAR,
 
    PRIMARY KEY(id),
     
    FOREIGN KEY(user) REFERENCES User(email)
    ON DELETE NO ACTION ON UPDATE CASCADE,
);
 
CREATE TABLE VoteQuestion(
    question INTEGER NOT NULL,
    user VARCHAR(50) NOT NULL,
    vote INTEGER DEFAULT 0, --can be -1,0,1
 
    PRIMARY KEY(question,user),
 
    FOREIGN KEY(question) REFERENCES Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(user) REFERENCES User(email)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
 
CREATE TABLE Category (
    name VARCHAR(20) NOT NULL,
    description VARCHAR(50),
    isCompany BOOLEAN DEFAULT FALSE,
 
    PRIMARY KEY(name)
);
 
CREATE TABLE Below(
    category VARCHAR(20) NOT NULL, 
    question INTEGER NOT NULL,
 
    PRIMARY KEY(category,question),
 
    FOREIGN KEY(category) REFERENCES Category(name)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(question) REFERENCES Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE Answer (
    id SERIAL,
    isFixed BOOLEAN DEFAULT FALSE,
    body VARCHAR,
    ts TIMESTAMP,
    user VARCHAR(50), --abbiamo scritto 0 sull'er
    parentId INTEGER, --if null it answer to the main question
 
    PRIMARY KEY(id),
 
    FOREIGN KEY(user) REFERENCES User(email)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(parentId) REFERENCES Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE VoteAnswer(
    answer INTEGER NOT NULL,
    user VARCHAR(50) NOT NULL,
    vote INTEGER DEFAULT 0, --can be -1,0,1
 
    PRIMARY KEY(answer,user),
 
    FOREIGN KEY(answer) REFERENCES Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(user) REFERENCES User(email)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE Have(
    idQuestion INTEGER NOT NULL,
    idAnswer INTEGER NOT NULL,
 
    PRIMARY KEY(idQuestion,idAnswer),
 
    FOREIGN KEY(idQuestion) REFERENCES Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(idAnswer) REFERENCES Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE IsInterested(
    question INTEGER NOT NULL,
    user VARCHAR(50) NOT NULL,
 
    PRIMARY KEY(question,user),
 
    FOREIGN KEY(question) REFERENCES Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(user) REFERENCES User(email)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE Website(
    name VARCHAR(50) NOT NULL,
 
    PRIMARY KEY(name)
);
 
CREATE TABLE HaveWebsite(
    website VARCHAR(50) NOT NULL,
    link VARCHAR(50) NOT NULL,
 
    PRIMARY KEY(website,link),
 
    FOREIGN KEY(website) REFERENCES Website(name)
    ON DELETE NO ACTION ON UPDATE CASCADE
);