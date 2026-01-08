CREATE DATABASE if not exists mibasegonzalo;
--
USE mibasegonzalo;
--
CREATE TABLE IF NOT EXISTS marcas (
idmarca int auto_increment primary key,
nombremarca varchar(50) not null,
nombrelegalempresa varchar(150) not null,
fechafundacion date,
paisfundacion varchar(50));
--
CREATE TABLE IF NOT EXISTS tiendas (
idtienda int auto_increment primary key,
nombretienda varchar(50)  not null,
email varchar(100) not null,
telefono varchar(9),
tipotienda varchar(50),
web varchar(500));
--
CREATE TABLE IF NOT EXISTS calzados (
idcalzado int auto_increment primary key,
modelo varchar(50) not null,
codigosku varchar(40) not null UNIQUE,
idtienda int not null,
tipotienda varchar(30),
idmarca int not null,
precio float not null,
fechadelanzamiento date);
--
alter table calzados
add foreign key (idtienda) references tiendas(idtienda),
add foreign key (idmarca) references marcas(idmarca);
--
delimiter ||
create function existeCodigoSKU(f_codigosku varchar(40))
returns bit
begin
	declare i int;
	set i=0;
	while (i<(select max(idcalzado) from calzados)) do
	if ((select codigosku from calzados
		 where idcalzado=(i+1)) like f_codigosku)
	then return 1;
	end if;
	set i=i+1;
	end while;
	return 0;
end; ||
delimiter ;
--
delimiter ||
create function existeNombreTienda (f_name varchar(50))
returns bit
begin
	declare i int;
	set i=0;
	while (i<(select max(idtienda) from tiendas)) do
	if ((select nombretienda from tiendas
	     where idtienda = (i+1)) like f_name)
	then return 1;
	end if;
	set i=i+1;
	end while;
	return 0;
end; ||
delimiter ;
--
delimiter ||
create function existeNombreMarca (f_name varchar(50))
returns bit
begin
	declare i int;
	set i=0;
	while (i<(select max(idmarca) from marcas)) do
	if ((select concat(nombrelegalempresa,', ',nombremarca) from marcas
		where idmarca = (i+1)) like f_name)
	then return 1;
	end if;
	set i=i+1;
	end while;
	return 0;
end; ||
delimiter ;


