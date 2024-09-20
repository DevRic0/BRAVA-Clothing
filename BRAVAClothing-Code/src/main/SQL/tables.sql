drop database avabase;
create database avabase;
GRANT ALL PRIVILEGES ON avabase.* TO 'root'@'localhost';
use avabase;

create table categoria
(
    Nome        varchar(30)  not null,
    Descrizione varchar(500) null,
    idCategoria int          not null
        primary key,
    Genere      varchar(10)  null
);

create table cliente
(
    nome         varchar(30) not null,
    cognome      varchar(30) not null,
    email        varchar(40) not null,
    username     varchar(30) not null,
    password     varchar(40) not null,
    indirizzo    varchar(40) not null,
    isAdmin      tinyint(1)  null,
    telefono     varchar(15) not null,
    idCliente    int         not null
        primary key
);

create table magazzino
(
    Nome        varchar(30) not null,
    Indirizzo   varchar(50) not null,
    idMagazzino int         not null
        primary key
);

create table ordine
(
    iva             double not null,
    dataInserimento date   not null,
    idOrdine        int    not null
        primary key,
    cli_fk          int    null,
    constraint ordine_cliente_idCliente_fk
        foreign key (cli_fk) references cliente (idCliente)
            on update cascade on delete cascade
);

create table prodotto
(
    Nome                   varchar(30)  not null,
    Prezzo                 decimal(10, 2) not null,
    DescrizioneBreve       varchar(100) not null,
    DescrizioneDettagliata varchar(200) not null,
    inOfferta              tinyint(1)   not null,
    idProdotto             int          not null
        primary key,
    mag_fk                 int          null,
    cat_fk                 int          null,
    quantita               int          null,
    image                  longblob         null,
    constraint prodotto_categoria_idCategoria_fk
        foreign key (cat_fk) references categoria (idCategoria)
            on update cascade on delete cascade,
    constraint prodotto_magazzino_idMagazzino_fk
        foreign key (mag_fk) references magazzino (idMagazzino)
            on update cascade on delete cascade
);

create table prodotto_ordine
(
    ord_fk   int not null,
    pro_fk   int not null,
    quantita int not null,
    primary key (pro_fk, ord_fk)

);

create table spedizione
(
    dataSpedizione   date        not null,
    statusSpedizione varchar(20) not null,
    spese            double      not null,
    idSpedizione     int         not null
        primary key,
    ord_fk           int         not null,
    constraint spedizione_ordine_idOrdine_fk
        foreign key (ord_fk) references ordine (idOrdine)
            on update cascade on delete cascade
);