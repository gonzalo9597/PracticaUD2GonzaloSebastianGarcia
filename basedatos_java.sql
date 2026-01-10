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
tipocalzado varchar(30),
idmarca int not null,
precio float not null,
fechadelanzamiento date);
--
alter table calzados
add foreign key (idtienda) references tiendas(idtienda),
add foreign key (idmarca) references marcas(idmarca);
--
CREATE TABLE IF NOT EXISTS pedidos (
idpedido int auto_increment primary key,
codigoseguimiento varchar(50) not null UNIQUE,
idcalzado int not null,
idtienda int not null,
idmarca int not null,
cantidad int not null,
nombredestinatario varchar(100) not null,
tipoenvio varchar(50) not null,
direccion varchar(100));
--
alter table pedidos
add foreign key (idcalzado) references calzados(idcalzado),
add foreign key (idtienda) references tiendas(idtienda),
add foreign key (idmarca) references marcas(idmarca);
--
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
end; 
--
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
end; 
--
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
end;
--
create function existeCodigoSeguimiento(f_codigoseguimiento varchar(50))
    returns bit
begin
    declare i int;
    set i=0;
    while (i<(select max(idpedido) from pedidos)) do
            if ((select codigoseguimiento from pedidos
                 where idpedido=(i+1)) like f_codigoseguimiento)
            then return 1;
            end if;
            set i=i+1;
        end while;
    return 0;
end;
--


