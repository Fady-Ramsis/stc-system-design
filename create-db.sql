 IF NOT EXISTS (
    SELECT name
    FROM master.dbo.sysdatabases
    WHERE name = 'stc-assessment'
)
BEGIN
    CREATE DATABASE stc-assessment;
END
GO
