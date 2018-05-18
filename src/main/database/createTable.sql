--  Copyright 2018 University of Padua, Italy
--
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and
--  limitations under the License.
--
--  Version: 1.0
--  Since: 1.0


--
-- DB creation code
--
DROP DATABASE IF EXISTS webapp; 

CREATE DATABASE  webapp ENCODING 'UTF-8';

\c webapp;

CREATE TYPE webSiteType AS ENUM (
    'BitBucket','Github','Linkedin','OwnSite'
);


/*creation of the user*/

CREATE TABLE Utente (
    email VARCHAR(300) NOT NULL,
    name VARCHAR(30),                  
    surname VARCHAR(30),
    username VARCHAR(20) NOT NULL,
    photoprofile BYTEA DEFAULT NULL,
    password  VARCHAR(32) NOT NULL, -- not sure its the best data type
    registrationDate DATE NOT NULL,
    birthday DATE,
    description TEXT,
 
    PRIMARY KEY(username)
);
 
CREATE TABLE Question (
    id SERIAL,
    title VARCHAR(50),
    idUser VARCHAR(20), --abbiamo scritto 0 sull'er
    ts TIMESTAMP,
    lastModified TIMESTAMP,
    body VARCHAR,
 
    PRIMARY KEY(id),
     
    FOREIGN KEY(idUser) REFERENCES Utente(username)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE VoteQuestion(
    question INTEGER NOT NULL,
    idUser VARCHAR(20) NOT NULL,
    vote INTEGER
      DEFAULT 0
      CHECK (vote=0 OR vote=1 OR vote=-1), --can be -1,0,1
 
    PRIMARY KEY(question,idUser),
 
    FOREIGN KEY(question) REFERENCES Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(idUser) REFERENCES Utente(username)
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
    idUser VARCHAR(20),
    parentId INTEGER,
 
    PRIMARY KEY(id),
 
    FOREIGN KEY(idUser) REFERENCES Utente(username)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(parentId) REFERENCES Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE VoteAnswer(
    answer INTEGER NOT NULL,
    idUser VARCHAR(20) NOT NULL,
    vote INTEGER
      DEFAULT 0
      CHECK (vote=0 OR vote=1 OR vote=-1), --can be -1,0,1
 
    PRIMARY KEY(answer,idUser),
 
    FOREIGN KEY(answer) REFERENCES Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(idUser) REFERENCES Utente(username)
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
    idUser VARCHAR(50) NOT NULL,
 
    PRIMARY KEY(question,idUser),
 
    FOREIGN KEY(question) REFERENCES Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(idUser) REFERENCES Utente(username)
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