--\i /home/walter/Documentos/sdtp1/bdsd.sql;

create database bdsd;
drop user rol;

create user rol with password 'admin';
alter role rol with SUPERUSER;
alter role rol with LOGIN;
grant all privileges on database bdsd to rol;

\c bdsd;

/*create table persona(
	idpersona int(3) primary key,
	nombre varchar(30),
	estado boolean
);*/

create table mensaje(
	idmensaje SERIAL primary key,
	emisor varchar(16),
	receptor varchar(16),
	ipenvio varchar(16),
	mensaje varchar(120),
	fecha varchar(30),
	error varchar(120),
	estado integer--,
	--constraint fkemisor foreign key (idemisor) references persona (idpersona),
	--constraint fkreceptor foreign key (idreceptor) references persona (idpersona)
);
