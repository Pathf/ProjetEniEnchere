-- data table CATEGORIES
INSERT INTO CATEGORIES(libelle) VALUES ('Informatique');
INSERT INTO CATEGORIES (libelle) VALUES ('Ameublement');
INSERT INTO CATEGORIES (libelle) VALUES ('Vétement');
INSERT INTO CATEGORIES (libelle) VALUES ('Sport&Loisirs');

-- data table RETRAITS
INSERT INTO RETRAITS (rue, code_postal, ville) VALUES ('rue 1', '44000', 'ville 1');
INSERT INTO RETRAITS (rue, code_postal, ville) VALUES ('rue 2', '44001', 'ville 2');
INSERT INTO RETRAITS (rue, code_postal, ville) VALUES ('rue 3', '44002', 'ville 3');
INSERT INTO RETRAITS (rue, code_postal, ville) VALUES ('rue 4', '44003', 'ville 4');
INSERT INTO RETRAITS (rue, code_postal, ville) VALUES ('rue 5', '44004', 'ville 5');

-- data table UTILISATEURS
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES ('admin', 'admin', 'admin', 'admin@test.com', '0600000000', 'rue admin', '44000', 'ville 1', 'password', '99999', 1);
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES ('utilisateur1', 'un', 'utilisateur1', 'un@test.com', '0606060601', 'rue utilisateur 1', '44000', 'ville 1', 'password', '100', 0);
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES ('utilisateur2', 'deux', 'utilisateur2', 'deux@test.com', '0606060602', 'rue utilisateur 2', '44001', 'ville 2', 'password', '65', 0);
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES ('utilisateur3', 'trois', 'utilisateur3', 'trois@test.com', '0606060603', 'rue utilisateur 3', '44002', 'ville 3', 'password', '10', 0);
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES ('utilisateur4', 'quatre', 'utilisateur4', 'quatre@test.com', '0606060604', 'rue utilisateur 4', '44003', 'ville 4', 'password', '10000', 0);
INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES ('utilisateur5', 'cinq', 'utilisateur5', 'cinq@test.com', '0606060605', 'rue utilisateur 5', '44004', 'ville 5', 'password', '1', 0);

-- data table ARTICLES_VENDUS
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES ('article 1', 'super article 1', GETDATE(), GETDATE()+1, 10, null, 2, 1, 1);
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES ('article 2', 'super article 2', GETDATE()+1, GETDATE()+4, 16, null, 2, 2, 1);
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES ('article 3', 'super article 3', GETDATE()+4, GETDATE()+8, 100, null, 2, 3, 1);
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES ('article 4', 'super article 4', GETDATE()+2, GETDATE()+4, 24, null, 3, 4, 2);
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES ('article 5', 'super article 5', GETDATE()+6, GETDATE()+12, 36, null, 4, 1, 3);
INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, no_retrait) VALUES ('article 6', 'super article 6', GETDATE(), GETDATE()+3, 9, null, 5, 2, 4);

-- data table ENCHERES
INSERT INTO ENCHERES ( date_enchere, montant_enchere, no_article, no_utilisateur ) VALUES ( GETDATE(), 10, 1, 3 );
INSERT INTO ENCHERES ( date_enchere, montant_enchere, no_article, no_utilisateur ) VALUES ( GETDATE(), 11, 1, 4 );
INSERT INTO ENCHERES ( date_enchere, montant_enchere, no_article, no_utilisateur ) VALUES ( GETDATE(), 12, 1, 3 );
INSERT INTO ENCHERES ( date_enchere, montant_enchere, no_article, no_utilisateur ) VALUES ( GETDATE(), 13, 1, 5 );

INSERT INTO ENCHERES ( date_enchere, montant_enchere, no_article, no_utilisateur ) VALUES (GETDATE(), 150, 2, 3);
INSERT INTO ENCHERES ( date_enchere, montant_enchere, no_article, no_utilisateur ) VALUES (GETDATE()+1, 20, 3, 4);