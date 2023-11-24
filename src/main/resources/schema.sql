CREATE TABLE USUARIOS_LOGIN (
                                       ID_USUARIO INTEGER NOT NULL AUTO_INCREMENT,
                                       USUARIO VARCHAR(100) NOT NULL,
                                       CONTRASENA VARCHAR(100) NOT NULL,
                                       CONSTRAINT USUARIOS_LOGIN_PK PRIMARY KEY (ID_USUARIO)
);

CREATE TABLE CANCION (
                                ID_CANCION INTEGER NOT NULL AUTO_INCREMENT,
                                TITULO VARCHAR(100) NOT NULL,
                                ARTISTA VARCHAR(100) NOT NULL,
                                ALBUM VARCHAR(100),
                                ANNO INTEGER,
                                GENERO VARCHAR(100),
                                CONSTRAINT CANCION_PK PRIMARY KEY (ID_CANCION)
);

CREATE TABLE LISTA (
                              ID_LISTA integer NOT NULL AUTO_INCREMENT,
                              NOMBRE VARCHAR(100) NOT NULL,
                              DESCRIPCION VARCHAR(100),
                              CONSTRAINT LISTA_PK PRIMARY KEY (ID_LISTA)
);

CREATE TABLE LISTA_CANCION (
                                      ID_LISTA_CANCION INTEGER NOT NULL AUTO_INCREMENT,
                                      ID_LISTA INTEGER NOT NULL AUTO_INCREMENT,
                                      ID_CANCION INTEGER NOT NULL,
                                      CONSTRAINT LISTA_CANCION_PK PRIMARY KEY (ID_LISTA_CANCION),
                                      CONSTRAINT LISTA_CANCION_FK FOREIGN KEY (ID_LISTA) REFERENCES PUBLIC.LISTA(ID_LISTA),
                                      CONSTRAINT LISTA_CANCION_FK_1 FOREIGN KEY (ID_CANCION) REFERENCES PUBLIC.CANCION(ID_CANCION)
);
