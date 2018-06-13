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

CREATE SCHEMA lr_group;

CREATE TYPE lr_group.webSiteType AS ENUM (
    'BitBucket','Github','Linkedin','OwnSite'
);

CREATE EXTENSION pgcrypto WITH SCHEMA lr_group;

/*creation of the user*/

CREATE TABLE lr_group.Utente (
    email VARCHAR(300) NOT NULL,
    name VARCHAR(30),
    surname VARCHAR(30),
    username VARCHAR(20) NOT NULL,
    photoprofile VARCHAR DEFAULT NULL,
    /*password  VARCHAR(32) NOT NULL, -- not sure its the best data type*/
    password TEXT NOT NULL, -- not sure its the best data type
    isAdmin BOOLEAN DEFAULT FALSE,
    registrationDate DATE NOT NULL,
    birthday DATE,
    description TEXT,
    companyUser boolean,

 
    PRIMARY KEY(username)
);
 
CREATE TABLE lr_group.Question (
    id SERIAL,
    title VARCHAR(50),
    idUser VARCHAR(20), --abbiamo scritto 0 sull'er
    ts TIMESTAMP,
    lastModified TIMESTAMP,
    body VARCHAR,
 
    PRIMARY KEY(id),
     
    FOREIGN KEY(idUser) REFERENCES lr_group.Utente(username)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE lr_group.VoteQuestion(
    question INTEGER NOT NULL,
    idUser VARCHAR(20) NOT NULL,
    vote INTEGER
      DEFAULT 0
      CHECK (vote=0 OR vote=1 OR vote=-1), --can be -1,0,1
 
    PRIMARY KEY(question,idUser),
 
    FOREIGN KEY(question) REFERENCES lr_group.Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(idUser) REFERENCES lr_group.Utente(username)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
 
CREATE TABLE lr_group.Category (
    id SERIAL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(50),
    isCompany BOOLEAN DEFAULT FALSE,
 
    PRIMARY KEY(id)
);
 
CREATE TABLE lr_group.Below(
    idcategory INTEGER NOT NULL,
    question INTEGER NOT NULL,
 
    PRIMARY KEY(idcategory,question),
 
    FOREIGN KEY(idcategory) REFERENCES lr_group.Category(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(question) REFERENCES lr_group.Question(id)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE lr_group.Answer (
    id SERIAL,
    isFixed BOOLEAN DEFAULT FALSE,
    body VARCHAR,
    ts TIMESTAMP,
    idUser VARCHAR(20),
    parentId INTEGER,
 
    PRIMARY KEY(id),
 
    FOREIGN KEY(idUser) REFERENCES lr_group.Utente(username)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(parentId) REFERENCES lr_group.Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE lr_group.VoteAnswer(
    answer INTEGER NOT NULL,
    idUser VARCHAR(20) NOT NULL,
    vote INTEGER
      DEFAULT 0
      CHECK (vote=1 OR vote=-1), --can be -1,1
 
    PRIMARY KEY(answer,idUser),
 
    FOREIGN KEY(answer) REFERENCES lr_group.Answer(id)
    ON DELETE NO ACTION ON UPDATE CASCADE,
 
    FOREIGN KEY(idUser) REFERENCES lr_group.Utente(username)
    ON DELETE NO ACTION ON UPDATE CASCADE
);
 
CREATE TABLE lr_group.Have(
    idQuestion INTEGER NOT NULL,
    idAnswer INTEGER NOT NULL,
 
    PRIMARY KEY(idQuestion,idAnswer),
 
    FOREIGN KEY(idQuestion) REFERENCES lr_group.Question(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
 
    FOREIGN KEY(idAnswer) REFERENCES lr_group.Answer(id)
    ON DELETE CASCADE ON UPDATE CASCADE
);
 
CREATE TABLE lr_group.Website(
    address VARCHAR(50) NOT NULL,
    addrType  lr_group.webSiteType NOT NULL,
    PRIMARY KEY(address)
);

CREATE TABLE lr_group.Own(
    address VARCHAR(50) NOT NULL,
    username  VARCHAR(50) NOT NULL,

    PRIMARY KEY(address, username),

    FOREIGN KEY(username) REFERENCES lr_group.Utente(username)
    ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY(address) REFERENCES lr_group.Website(address)
    ON DELETE CASCADE ON UPDATE CASCADE

);
 
-- CREATE TABLE lr_group.HaveWebsite(
--     website VARCHAR(50) NOT NULL,
--     link VARCHAR(50) NOT NULL,
--  	type lr_group.webSiteType NOT NULL,
--     PRIMARY KEY(website,link),
--
--     FOREIGN KEY(website) REFERENCES lr_group.Website(name)
--     ON DELETE NO ACTION ON UPDATE CASCADE
-- );

CREATE TABLE lr_group.Certificate(

    name VARCHAR(50) NOT NULL,
    organization VARCHAR(50) NOT NULL,

    PRIMARY KEY(name,organization)
);

CREATE TABLE lr_group.HaveCertificate(
    username VARCHAR(20) NOT NULL,
    name VARCHAR(50) NOT NULL ,
    organization VARCHAR(50) NOT NULL,
    achievmentDate DATE,

    PRIMARY KEY(username,organization,name),

    FOREIGN KEY(name,organization) REFERENCES lr_group.Certificate(name,organization)

     ON DELETE NO ACTION ON UPDATE CASCADE

);