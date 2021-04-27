USE msdb ;  
GO

IF ('VentesJob' IN (SELECT name FROM dbo.sysjobs))
BEGIN
	EXEC sp_delete_job  
		@job_name = N'VentesJob' ;
END

EXEC sp_add_job
    @job_name = N'VentesJob';
GO

EXEC sp_add_jobstep
    @job_name = N'VentesJob',
    @step_name = N'MajArticles',
    @subsystem = N'TSQL',
    @command = N'UPDATE ARTICLES_VENDUS SET prix_vente = tmp.montant FROM ARTICLES_VENDUS AS a INNER JOIN (SELECT e.no_article, max(montant_enchere) AS montant FROM ENCHERES AS e LEFT JOIN ARTICLES_VENDUS AS a ON e.no_article = a.no_article GROUP BY e.no_article) AS tmp ON a.no_article = tmp.no_article WHERE a.date_fin_encheres <= GETDATE() AND a.prix_vente IS NULL',
	@database_name = N'TROCENCHERES',
	@retry_attempts = 5,  
    @retry_interval = 5 ;
GO

EXEC sp_add_schedule  
    @schedule_name = N'MinuitCron' ,  
    @freq_type = 4,  
    @freq_interval = 1,  
    @active_start_time = 000000 ;
GO

EXEC sp_attach_schedule  
   @job_name = N'VentesJob',  
   @schedule_name = N'MinuitCron' ;  
GO  

EXEC dbo.sp_add_jobserver  
    @job_name = N'VentesJob' ;  
GO  

SELECT * FROM dbo.sysschedules;

SELECT * FROM dbo.sysjobs;

SELECT * FROM dbo.sysjobsteps;

SELECT * FROM dbo.sysjobservers;