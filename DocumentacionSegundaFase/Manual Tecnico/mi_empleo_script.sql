--Creacion del usuario para la base de datos--

    CREATE USER 'rootdba'@'localhost'
IDENTIFIED WITH mysql_native_password BY '1234';


--creando la base de datos--

CREATE SCHEMA IF NOT EXISTS 'Mi_Empleo' ;

GRANT ALL PRIVILEGES 
ON 'Mi_Empleo'*
TO 'rootdba'@'localhost';

--seleccionamos la base de datos--
USE 'Mi_Empleo' ;

-- categoria: table
CREATE TABLE 'categoria' (
  'codigo' int NOT NULL AUTO_INCREMENT,
  'nombre' varchar(20) NOT NULL,
  'descripcion' text NOT NULL,
  PRIMARY KEY ('codigo'),
  UNIQUE KEY 'categoria_pk' ('nombre')
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- usuario: table
CREATE TABLE 'usuario' (
  'codigo' int NOT NULL AUTO_INCREMENT,
  'email' varchar(40) NOT NULL,
  'cui' varchar(15) NOT NULL,
  'nombre' varchar(45) NOT NULL,
  'username' varchar(20) NOT NULL,
  'direccion' varchar(60) NOT NULL,
  'tipo' varchar(15) DEFAULT NULL,
  'fecha_dob' date NOT NULL,
  'password' varchar(36) DEFAULT NULL,
  PRIMARY KEY ('codigo'),
  UNIQUE KEY 'username_key' ('username')
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- parametros: table
CREATE TABLE 'parametros' (
  'nombre' varchar(30) NOT NULL,
  'valor' varchar(30) NOT NULL,
  PRIMARY KEY ('nombre')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- empleador: table
CREATE TABLE 'empleador' (
  'codigo_usuario' int NOT NULL,
  'vision' varchar(250) DEFAULT NULL,
  'mision' varchar(250) DEFAULT NULL,
  PRIMARY KEY ('codigo_usuario'),
  CONSTRAINT 'empleador_usuario_codigo_fk' FOREIGN KEY ('codigo_usuario') REFERENCES 'usuario' ('codigo')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- tarjeta: table
CREATE TABLE 'tarjeta' (
  'id' int NOT NULL AUTO_INCREMENT,
  'titular' varchar(45) NOT NULL,
  'numero' varchar(18) NOT NULL,
  'cvv' int NOT NULL,
  'id_empleador' int NOT NULL,
  PRIMARY KEY ('id'),
  KEY 'tarjeta_usuario_codigo_fk' ('id_empleador'),
  CONSTRAINT 'tarjeta_usuario_codigo_fk' FOREIGN KEY ('id_empleador') REFERENCES 'usuario' ('codigo')
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- solicitante: table
CREATE TABLE 'solicitante' (
  'codigo_usuario' int NOT NULL,
  'cv_path' tinytext,
  PRIMARY KEY ('codigo_usuario'),
  CONSTRAINT 'solicitante_usuario_codigo_fk' FOREIGN KEY ('codigo_usuario') REFERENCES 'usuario' ('codigo')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- telefono: table
CREATE TABLE 'telefono' (
  'id' int NOT NULL AUTO_INCREMENT,
  'numero' int NOT NULL,
  'id_usuario' int NOT NULL,
  PRIMARY KEY ('id','id_usuario'),
  KEY 'telefono_usuario_codigo_fk' ('id_usuario'),
  CONSTRAINT 'telefono_usuario_codigo_fk' FOREIGN KEY ('id_usuario') REFERENCES 'usuario' ('codigo')
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- comision: table
CREATE TABLE 'comision' (
  'valor' float NOT NULL,
  'codigo' int NOT NULL AUTO_INCREMENT,
  'codigo_empleador' int NOT NULL,
  'fecha' date NOT NULL,
  PRIMARY KEY ('codigo'),
  KEY 'comision_usuario_codigo_fk' ('codigo_empleador'),
  CONSTRAINT 'comision_usuario_codigo_fk' FOREIGN KEY ('codigo_empleador') REFERENCES 'usuario' ('codigo')
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- oferta: table
CREATE TABLE 'oferta' (
  'empresa' int DEFAULT NULL,
  'categoria' int DEFAULT NULL,
  'ubicacion' varchar(60) NOT NULL,
  'estado' varchar(20) NOT NULL,
  'fecha_publicacion' date NOT NULL,
  'fecha_limite' date NOT NULL,
  'detalles' text NOT NULL,
  'ganador' int DEFAULT NULL,
  'modalidad' varchar(15) NOT NULL,
  'salario' float NOT NULL,
  'codigo' int NOT NULL AUTO_INCREMENT,
  'nombre' varchar(60) NOT NULL,
  'descripcion' tinytext NOT NULL,
  PRIMARY KEY ('codigo'),
  KEY 'oferta_categoria_codigo_fk' ('categoria'),
  KEY 'oferta_empleador_codigo_usuario_fk' ('empresa'),
  KEY 'oferta_solicitante_codigo_usuario_fk' ('ganador'),
  CONSTRAINT 'oferta_categoria_codigo_fk' FOREIGN KEY ('categoria') REFERENCES 'categoria' ('codigo'),
  CONSTRAINT 'oferta_empleador_codigo_usuario_fk' FOREIGN KEY ('empresa') REFERENCES 'empleador' ('codigo_usuario'),
  CONSTRAINT 'oferta_solicitante_codigo_usuario_fk' FOREIGN KEY ('ganador') REFERENCES 'solicitante' ('codigo_usuario')
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- entrevista: table
CREATE TABLE 'entrevista' (
  'codigo' int NOT NULL AUTO_INCREMENT,
  'solicitante' int NOT NULL,
  'fecha' date NOT NULL,
  'hora' time NOT NULL,
  'estado' varchar(20) NOT NULL,
  'ubicacion' varchar(60) NOT NULL,
  'notas' text,
  'codigo_oferta' int DEFAULT NULL,
  PRIMARY KEY ('codigo'),
  KEY 'entrevista_oferta_codigo_fk' ('codigo_oferta'),
  KEY 'entrevista_solicitante_codigo_usuario_fk' ('solicitante'),
  CONSTRAINT 'entrevista_oferta_codigo_fk' FOREIGN KEY ('codigo_oferta') REFERENCES 'oferta' ('codigo'),
  CONSTRAINT 'entrevista_solicitante_codigo_usuario_fk' FOREIGN KEY ('solicitante') REFERENCES 'solicitante' ('codigo_usuario')
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- preferencia: table
CREATE TABLE 'preferencia' (
  'id_usuario' int NOT NULL,
  'id_categoria' int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY ('id_categoria','id_usuario'),
  KEY 'preferencia_usuario_codigo_fk' ('id_usuario'),
  CONSTRAINT 'preferencia_categoria_codigo_fk' FOREIGN KEY ('id_categoria') REFERENCES 'categoria' ('codigo'),
  CONSTRAINT 'preferencia_usuario_codigo_fk' FOREIGN KEY ('id_usuario') REFERENCES 'usuario' ('codigo')
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- solicitud: table
CREATE TABLE 'solicitud' (
  'codigo_oferta' int NOT NULL,
  'codigo' int NOT NULL AUTO_INCREMENT,
  'usuario' int NOT NULL,
  'mensaje' tinytext NOT NULL,
  PRIMARY KEY ('codigo'),
  KEY 'solicitud_oferta_codigo_fk' ('codigo_oferta'),
  KEY 'solicitud_solicitante_codigo_usuario_fk' ('usuario'),
  CONSTRAINT 'solicitud_oferta_codigo_fk' FOREIGN KEY ('codigo_oferta') REFERENCES 'oferta' ('codigo'),
  CONSTRAINT 'solicitud_solicitante_codigo_usuario_fk' FOREIGN KEY ('usuario') REFERENCES 'solicitante' ('codigo_usuario')
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO 'parametros' (nombre, valor) VALUES ('fecha', '2023-11-15');

INSERT INTO 'parametros' (nombre, valor) VALUES ('comision', '150');




