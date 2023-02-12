create table LIVRE
(
   CODE integer not null,
   TITRE varchar(100) not null,
   AUTEUR varchar(100) not null,
   ISBN bigint not null,
   CATEGORIE varchar(100) not null,
   constraint PK_LIVRE primary key (CODE)
);

create table LECTEUR
(
   CIN bigint not null,
   NOM varchar(100) not null,
   PRENOM varchar(100) not null,
   NUMTEL bigint not null,
   CREDIT float null,
   date_abonnement date not null,
   FRAIS integer not null,
	EMAIL varchar(100) null,
   PREFERENCE varchar(100) null,
   constraint PK_LECTEUR primary key (CIN)
   
);

create table EMPRUNTER
(
   CIN bigint not null,
   CODE integer not null,
   DATE_EMPRUNT date not null,
   constraint PK_EMPRUNTER primary key (CODE)
);
