\c track_my_class;

CREATE TABLE Student(
        std_ref      VARCHAR (8) NOT NULL PRIMARY KEY,
        last_name    VARCHAR (200) NOT NULL,
        first_name   VARCHAR (200),
        email        VARCHAR (250) NOT NULL,
        phone_number VARCHAR (15) NOT NULL,
        level_year   VARCHAR (2) NOT NULL,
        "group"      VARCHAR (2) NOT NULL
);

CREATE TABLE Teacher(
        tch_ref      VARCHAR (8) NOT NULL PRIMARY KEY,
        last_name    VARCHAR (200) NOT NULL,
        first_name   VARCHAR (200),
        is_assistant BOOL NOT NULL,
        email        VARCHAR (250) NOT NULL,
        phone_number VARCHAR (15) NOT NULL
);

CREATE TABLE Course(
        crs_ref VARCHAR (8) NOT NULL PRIMARY KEY,
        name    VARCHAR (10) NOT NULL,
        credit  INT NOT NULL
);

CREATE TABLE COR(
        cor_ref VARCHAR (8) NOT NULL PRIMARY KEY,
        std_ref VARCHAR (8) NOT NULL,
	FOREIGN KEY (std_ref) REFERENCES Student(std_ref)
);

CREATE TABLE Teaches(
        crs_ref VARCHAR (8) NOT NULL,
        tch_ref VARCHAR (8) NOT NULL,
	FOREIGN KEY (crs_ref) REFERENCES Course(crs_ref),
	FOREIGN KEY (tch_ref) REFERENCES Teacher(tch_ref)
);

CREATE TABLE has_missed(
        std_ref      VARCHAR (8) NOT NULL ,
        crs_ref      VARCHAR (8) NOT NULL ,
        tch_ref      VARCHAR (8) NOT NULL ,
        commencement TIMESTAMP NOT NULL ,
        termination  TIMESTAMP NOT NULL ,
        is_jusfied   BOOL NOT NULL,
	 FOREIGN KEY (std_ref) REFERENCES Student(std_ref),
	 FOREIGN KEY (crs_ref) REFERENCES Course(crs_ref),
	 FOREIGN KEY (tch_ref) REFERENCES Teacher(tch_ref)
);

CREATE TABLE is_delayed(
        std_ref      VARCHAR (8) NOT NULL ,
        crs_ref      VARCHAR (8) NOT NULL ,
        tch_ref      VARCHAR (8) NOT NULL ,
        commencement TIMESTAMP NOT NULL ,
        termination  TIMESTAMP NOT NULL ,
        lateness     TIMESTAMP NOT NULL,
	FOREIGN KEY (std_ref) REFERENCES Student(std_ref),
	FOREIGN KEY (crs_ref) REFERENCES Course(crs_ref),
	FOREIGN KEY (tch_ref) REFERENCES Teacher(tch_ref)
);

CREATE TABLE is_present(
        crs_ref      VARCHAR (8) NOT NULL ,
        std_ref      VARCHAR (8) NOT NULL ,
        tch_ref      VARCHAR (8) NOT NULL ,
        commencement TIMESTAMP NOT NULL ,
        termination  TIMESTAMP NOT NULL,
	FOREIGN KEY (crs_ref) REFERENCES Course(crs_ref),
	FOREIGN KEY (std_ref) REFERENCES Student(std_ref),
	FOREIGN KEY (tch_ref) REFERENCES Teacher(tch_ref)
);
