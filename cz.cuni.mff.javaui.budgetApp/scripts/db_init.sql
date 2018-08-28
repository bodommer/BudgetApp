ALTER TABLE budget_db.periods DROP CONSTRAINT parent_user;
ALTER TABLE budget_db.periods ADD CONSTRAINT parent_user FOREIGN KEY (iduser) REFERENCES budget_db.users(iduser) ON DELETE CASCADE;
