-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
--

DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS ARTICLES_VENDUS;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS RETRAITS;
DROP TABLE IF EXISTS CATEGORIES;


CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)


CREATE TABLE RETRAITS (
	no_retrait       INTEGER IDENTITY(1,1) NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_retrait)  

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL UNIQUE,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(50) NOT NULL UNIQUE,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(30) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL,
    activer			 bit NOT NULL
)

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)


CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    photo_nom 					  VARCHAR(255),
    photo_data 					  IMAGE,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL,
	no_retrait					  INTEGER NULL
)



ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)


CREATE TABLE ENCHERES(	
	no_enchere  INTEGER IDENTITY(1,1) NOT NULL,
	date_enchere datetime NOT NULL,
	montant_enchere INTEGER NOT NULL,
	no_article INTEGER NOT NULL,
	no_utilisateur INTEGER NOT NULL
 )

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY ( no_enchere)
 
ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_no_article_fk FOREIGN KEY ( no_article ) REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 
	

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_retrait_fk FOREIGN KEY ( no_retrait )
        REFERENCES RETRAITS ( no_retrait )
ON DELETE no action 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action
    
-- Ajout de l'utilisateur inconnu pour le fonctionnement du site :
INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES('inconnu','','','','','','','','',0,1);