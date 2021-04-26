UPDATE ARTICLES_VENDUS SET prix_vente = tmp.montant FROM ARTICLES_VENDUS AS a INNER JOIN (SELECT e.no_article, max(montant_enchere) AS montant FROM ENCHERES AS e LEFT JOIN ARTICLES_VENDUS AS a ON e.no_article = a.no_article GROUP BY e.no_article) AS tmp ON a.no_article = tmp.no_article WHERE a.date_fin_encheres <= GETDATE() AND a.prix_vente IS NULL;


USE msdb ;  
GO

EXEC dbo.sp_start_job N'VentesJob' ; 
GO