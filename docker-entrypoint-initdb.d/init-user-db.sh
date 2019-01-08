#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL	
	CREATE USER adv_user WITH password 'adv_pass';
	CREATE DATABASE adv_works OWNER adv_user;		
	GRANT ALL PRIVILEGES ON DATABASE adv_works TO adv_user;
	ALTER SCHEMA public OWNER TO adv_user;
	\q
EOSQL

# Create Database Data
psql -U adv_user -d adv_works -a -f postgres-db.sql