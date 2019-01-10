DROP DATABASE IF EXISTS youlearndb;
CREATE DATABASE youlearndb;
USE youlearndb;

/* SCRIPT DI CREAZIONE*/

DROP TABLE IF EXISTS cartadicredito;
CREATE TABLE IF NOT EXISTS cartadicredito(
	numeroAccount INTEGER(4) NOT NULL,
	numeroCarta VARCHAR(16) NOT NULL,
	meseScadenza VARCHAR(20) NOT NULL,
	annoScadenza VARCHAR(20) NOT NULL,
	tipo INTEGER(10) NOT NULL,
	nomeIntestatario VARCHAR(50) NOT NULL,
	PRIMARY KEY (numeroCarta)
);

DROP TABLE IF EXISTS account;
CREATE TABLE IF NOT EXISTS account (
  nome    VARCHAR(25) NOT NULL,
  cognome  VARCHAR(25) NOT NULL,
  password   VARCHAR(45) NOT NULL,
  email        VARCHAR(255) NOT NULL ,
  tipo INTEGER(10) NOT NULL,
  verificato TINYINT(1) NOT NULL,
  numeroCarta VARCHAR(16) NOT NULL, 
  PRIMARY KEY (email),
  FOREIGN KEY (numeroCarta) REFERENCES cartadicredito(Numerocarta) ON UPDATE CASCADE ON DELETE CASCADE
  );

DROP TABLE IF EXISTS corso;
CREATE TABLE IF NOT EXISTS corso(
   idCorso INT NOT NULL AUTO_INCREMENT,
   accountCreatore VARCHAR(255) NOT NULL,
   accountSupervisore VARCHAR(255) NOT NULL,
   nome VARCHAR(255) NOT NULL,
   descrizione VARCHAR(1048) NOT NULL,
   dataCreazione DATE NOT NULL,
   dataFine DATE NOT NULL,
   copertina VARCHAR(255) NOT NULL,
   prezzo VARCHAR(10) NOT NULL,
   stato INTEGER(10) NOT NULL,
   categoria VARCHAR(30) NOT NULL,
   nLezioni INTEGER(10) NOT NULL,
   nIscritti INTEGER(10) DEFAULT 0,
   PRIMARY KEY(idCorso),
   FOREIGN KEY(accountCreatore) REFERENCES account(email),
   FOREIGN KEY(accountSupervisore) REFERENCES account(email)
);

DROP TABLE IF EXISTS pagamento;
CREATE TABLE IF NOT EXISTS pagamento(
	accountMail VARCHAR(255) NOT NULL,
	corsoIdCorso INT NOT NULL,
    dataPagamento DATE NOT NULL,
    importo DOUBLE NOT NULL,
    fattura INTEGER(10) NOT NULL,
    UNIQUE(fattura),
    PRIMARY KEY (accountMail, corsoIdCorso),
    FOREIGN KEY (accountMail) REFERENCES account(email),
    FOREIGN KEY (corsoIdCorso) REFERENCES corso(idCorso)
);

DROP TABLE IF EXISTS lezione;
CREATE TABLE IF NOT EXISTS lezione(
	corsoIdCorso INT NOT NULL,
	nome VARCHAR(255) NOT NULL,
	visualizzazione INTEGER(32) NOT NULL,
	numeroLezione INTEGER(10) NOT NULL,
	PRIMARY KEY(corsoIdCorso, numeroLezione),
	FOREIGN KEY(corsoIdCorso) REFERENCES corso(idCorso)
);

DROP TABLE IF EXISTS commento;
CREATE TABLE IF NOT EXISTS commento(
	idCommento INTEGER(10) NOT NULL,
	testo VARCHAR(255) NOT NULL,
	accountMail VARCHAR(255) NOT NULL,
	corsoIdCorso INT NOT NULL,
	lezioneNumeroLezione INTEGER(10) NOT NULL,
	PRIMARY KEY(idCommento),
	FOREIGN KEY(corsoIdCorso) REFERENCES lezione(corsoIdCorso),
	FOREIGN KEY(lezioneNumeroLezione) REFERENCES lezione(numeroLezione),
	FOREIGN KEY (accountMail) REFERENCES account(email)
);
