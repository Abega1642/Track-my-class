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
        "cor_date" TIMESTAMP NOT NULL,
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
        is_justified   BOOL NOT NULL,
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

CREATE TABLE group_level_matcher(
    level_year VARCHAR(2) NOT NULL,
    "group" VARCHAR(2) NOT NULL
);



INSERT INTO Student (std_ref, last_name, first_name, email, phone_number, level_year, "group") VALUES
('STD23001', 'RAKOTONIRINA', 'Mirana Ny Aina', 'hei.mirana@gmail.com', '+261341234567', 'L1', 'J1'),
('STD23002', 'RANDRIA', 'Mirana', 'hei.mirana.2@gmail.com', '+261341234568', 'L1', 'J2'),
('STD23003', 'RABE', 'Faly', 'hei.faly@gmail.com', '+261341234569', 'L1', 'J1'),
('STD23004', 'RAKOTO', 'Tiana', 'hei.tiana@gmail.com', '+261341234570', 'L1', 'J2'),
('STD23005', 'ANDRIANINA', 'Rivo', 'hei.rivo@gmail.com', '+261341234571', 'L1', 'J1'),
('STD23006', 'RAKOTOMALALA', 'Miora', 'hei.miora@gmail.com', '+261341234572', 'L1', 'J2'),
('STD23007', 'RAVAO', 'Nirina', 'hei.nirina@gmail.com', '+261341234573', 'L1', 'J1'),
('STD23008', 'RANDRIANASOLO', 'Tahina', 'hei.tahina@gmail.com', '+261341234574', 'L1', 'J2'),
('STD23009', 'RAVELO', 'Antsa', 'hei.antsa@gmail.com', '+261341234575', 'L1', 'J1'),
('STD23010', 'RAZAFINDRABE', 'Nomena', 'hei.nomena@gmail.com', '+261341234576', 'L1', 'J2'),

('STD22001', 'ANDRIANARISON', 'Hasina', 'hei.hasina@gmail.com', '+261341234577', 'L2', 'H1'),
('STD22002', 'RAKOTOMANGA', 'Fanilo', 'hei.fanilo@gmail.com', '+261341234578', 'L2', 'H2'),
('STD22003', 'RAZAFIMAHARO', 'Solofo', 'hei.solofo@gmail.com', '+261341234579', 'L2', 'H1'),
('STD22004', 'ANDRIATSITOHAINA', 'Hery', 'hei.hery@gmail.com', '+261341234580', 'L2', 'H2'),
('STD22005', 'RAZAKANDRAINY', 'Njara', 'hei.njara@gmail.com', '+261341234581', 'L2', 'H1'),
('STD22006', 'ANDRIAMBOLOLONA', 'Tantely', 'hei.tantely@gmail.com', '+261341234582', 'L2', 'H2'),
('STD22007', 'RABARY', 'Zo', 'hei.zo@gmail.com', '+261341234583', 'L2', 'H1'),
('STD22008', 'RAZAFINDRAKOTO', 'Ando', 'hei.ando@gmail.com', '+261341234584', 'L2', 'H2'),
('STD22009', 'RANDRIAMANANTENA', 'Fano', 'hei.fano@gmail.com', '+261341234585', 'L2', 'H1'),
('STD22010', 'RAZAFINDRABE', 'Vola', 'hei.vola@gmail.com', '+261341234586', 'L2', 'H2'),

('STD21001', 'RAKOTOARIMANANA', 'Lala', 'hei.lala@gmail.com', '+261341234587', 'L3', 'G1'),
('STD21002', 'RAKOTOARISOA', 'Malala', 'hei.malala@gmail.com', '+261341234588', 'L3', 'G1'),
('STD21003', 'ANDRIAMAMPIANINA', 'Haja', 'hei.haja@gmail.com', '+261341234589', 'L3', 'G1'),
('STD21004', 'RAZANAMASINA', 'Jao', 'hei.jao@gmail.com', '+261341234590', 'L3', 'G1'),
('STD21005', 'RAKOTOMANGA', 'Zara', 'hei.zara@gmail.com', '+261341234591', 'L3', 'G1'),
('STD21006', 'ANDRIAMBOAVONJY', 'Niry', 'hei.niry@gmail.com', '+261341234592', 'L3', 'G1'),
('STD21007', 'ANDRIANIVO', 'Harisoa', 'hei.harisoa@gmail.com', '+261341234593', 'L3', 'G1'),
('STD21008', 'RANDRIANJAFY', 'Rindra', 'hei.rindra@gmail.com', '+261341234594', 'L3', 'G1'),
('STD21009', 'RAKOTONDRAZAKA', 'Fanja', 'hei.fanja@gmail.com', '+261341234595', 'L3', 'G1'),
('STD21010', 'RAZAFINDRAKOTO', 'Zo', 'hei.zo2@gmail.com', '+261341234596', 'L3', 'G1'),

('STD23011', 'RAKOTOARISON', 'Ando', 'hei.ando2@gmail.com', '+261341234597', 'L1', 'J1'),
('STD23012', 'ANDRIAMANANA', 'Faniry', 'hei.faniry@gmail.com', '+261341234598', 'L1', 'J2'),
('STD23013', 'RANDRIANAIVO', 'Lova', 'hei.lova@gmail.com', '+261341234599', 'L1', 'J1'),
('STD23014', 'ANDRIATSIMBAZAFY', 'Tsiry', 'hei.tsiry@gmail.com', '+261341234600', 'L1', 'J2'),
('STD23015', 'RAZAFIMAHATRATRA', 'Anja', 'hei.anja@gmail.com', '+261341234601', 'L1', 'J1'),
('STD23016', 'RAZAFINARIVO', 'Tojo', 'hei.tojo@gmail.com', '+261341234602', 'L1', 'J2'),
('STD23017', 'ANDRIAMITANTSOA', 'Fanja', 'hei.fanja2@gmail.com', '+261341234603', 'L1', 'J1'),
('STD23018', 'RAZAFINDRALAMBO', 'Njaka', 'hei.njaka@gmail.com', '+261341234604', 'L1', 'J2'),
('STD23019', 'ANDRIANASOLO', 'Rindra', 'hei.rindra2@gmail.com', '+261341234605', 'L1', 'J1'),
('STD23020', 'RANDRIAMAMPIONONA', 'Zara', 'hei.zara2@gmail.com', '+261341234606', 'L1', 'J2'),

('STD22011', 'RAKOTONIAINA', 'Tojo', 'hei.tojo2@gmail.com', '+261341234607', 'L2', 'H1'),
('STD22012', 'RANDRIAMALALA', 'Mamy', 'hei.mamy@gmail.com', '+261341234608', 'L2', 'H2'),
('STD22013', 'ANDRIATSITOHAINA', 'Noro', 'hei.noro@gmail.com', '+261341234609', 'L2', 'H1'),
('STD22014', 'RAZAFI', 'Lanto', 'hei.lanto@gmail.com', '+261341234610', 'L2', 'H2'),
('STD22015', 'RAKOTOMANANA', 'Tovo', 'hei.tovo@gmail.com', '+261341234611', 'L2', 'H1'),
('STD22016', 'RAKOTONIRINA', 'Miora', 'hei.miora2@gmail.com', '+261341234612', 'L2', 'H2');


INSERT INTO Teacher (tch_ref, last_name, first_name, is_assistant, email, phone_number) VALUES
('TCH21001', 'RAKOTOMALALA', 'Faniry', TRUE, 'hei.faniry@gmail.com', '+261341234611'),
('TCH21002', 'RANDRIANASOLO', 'Rivo', FALSE, 'hei.rivo@gmail.com', '+261341234612'),
('TCH21003', 'RAZAFINDRAKOTO', 'Tiana', TRUE, 'hei.tiana@gmail.com', '+261341234613'),
('TCH21004', 'ANDRIAMAMPIANINA', 'Hasina', FALSE, 'hei.hasina@gmail.com', '+261341234614'),
('TCH21005', 'RABARY', 'Nirina', TRUE, 'hei.nirina@gmail.com', '+261341234615'),
('TCH22001', 'RAKOTONIRINA', 'Faly', FALSE, 'hei.faly@gmail.com', '+261341234616'),
('TCH22002', 'RAZAKANDRAINY', 'Antsa', TRUE, 'hei.antsa@gmail.com', '+261341234617'),
('TCH22003', 'ANDRIANTSITOHAINA', 'Tantely', FALSE, 'hei.tantely@gmail.com', '+261341234618'),
('TCH22004', 'RANDRIANIRINA', 'Zo', TRUE, 'hei.zo@gmail.com', '+261341234619'),
('TCH22005', 'RAKOTONDRABE', 'Miora', FALSE, 'hei.miora@gmail.com', '+261341234620'),
('TCH23001', 'ANDRIANIVO', 'Tahina', TRUE, 'hei.tahina@gmail.com', '+261341234621'),
('TCH23002', 'RAZAFINDRAZAKA', 'Lala', FALSE, 'hei.lala@gmail.com', '+261341234622'),
('TCH23003', 'RANDRIAMAMPIONONA', 'Njaka', TRUE, 'hei.njaka@gmail.com', '+261341234623'),
('TCH23004', 'RAKOTOARISON', 'Rindra', FALSE, 'hei.rindra@gmail.com', '+261341234624'),
('TCH23005', 'RAKOTOMANGA', 'Zo', TRUE, 'hei.zo2@gmail.com', '+261341234625'),
('TCH23006', 'RAZAFIMANANTSOA', 'Ando', FALSE, 'hei.ando@gmail.com', '+261341234626'),
('TCH23007', 'RANDRIA', 'Lova', TRUE, 'hei.lova@gmail.com', '+261341234627'),
('TCH23008', 'RAKOTOMALALA', 'Harisoa', FALSE, 'hei.harisoa@gmail.com', '+261341234628'),
('TCH23009', 'ANDRIANARISOA', 'Fanja', TRUE, 'hei.fanja@gmail.com', '+261341234629'),
('TCH23010', 'RAZAFINJAKA', 'Tojo', FALSE, 'hei.tojo@gmail.com', '+261341234630');

INSERT INTO Course (crs_ref, name, credit) VALUES
('L1-001', 'PROG1', 6),
('L1-002', 'SYS1', 6),
('L1-003', 'MGT1', 4),
('L1-004', 'LV1', 4),
('L1-005', 'WEB1', 6),
('L1-006', 'PROG2', 6),
('L1-007', 'SYS2', 6),
('L1-008', 'WEB2', 6),
('L1-009', 'API', 4),
('L1-010', 'THEORIE1P1', 4),
('L1-011', 'THEORIE1P2', 4),
('L1-012', 'DONNEES1', 4);


INSERT INTO is_present (crs_ref, std_ref, tch_ref, commencement, termination) VALUES
('L1-001', 'STD23001', 'TCH21001', '2024-09-01 08:00:00', '2024-09-01 10:00:00'),
('L1-001', 'STD23002', 'TCH21002', '2024-09-02 10:00:00', '2024-09-02 12:00:00'),
('L1-002', 'STD23003', 'TCH21003', '2024-09-03 08:00:00', '2024-09-03 10:00:00'),
('L1-002', 'STD23004', 'TCH21004', '2024-09-04 09:00:00', '2024-09-04 11:00:00'),
('L1-003', 'STD23005', 'TCH21005', '2024-09-05 08:00:00', '2024-09-05 10:00:00'),
('L1-003', 'STD23006', 'TCH22001', '2024-09-06 10:00:00', '2024-09-06 12:00:00'),
('L1-004', 'STD23007', 'TCH22002', '2024-09-07 08:00:00', '2024-09-07 10:00:00'),
('L1-004', 'STD23008', 'TCH22003', '2024-09-08 09:00:00', '2024-09-08 11:00:00'),
('L1-005', 'STD23009', 'TCH22004', '2024-09-09 08:00:00', '2024-09-09 10:00:00'),
('L1-005', 'STD23010', 'TCH22005', '2024-09-10 10:00:00', '2024-09-10 12:00:00'),
('L1-006', 'STD22001', 'TCH23001', '2024-09-11 08:00:00', '2024-09-11 10:00:00'),
('L1-006', 'STD22002', 'TCH23002', '2024-09-12 09:00:00', '2024-09-12 11:00:00'),
('L1-007', 'STD23001', 'TCH21001', '2024-09-13 08:00:00', '2024-09-13 10:00:00'),
('L1-007', 'STD23002', 'TCH21002', '2024-09-14 10:00:00', '2024-09-14 12:00:00'),
('L1-008', 'STD23003', 'TCH21003', '2024-09-15 08:00:00', '2024-09-15 10:00:00'),
('L1-008', 'STD23004', 'TCH21004', '2024-09-16 09:00:00', '2024-09-16 11:00:00'),
('L1-009', 'STD23005', 'TCH21005', '2024-09-17 08:00:00', '2024-09-17 10:00:00'),
('L1-009', 'STD23006', 'TCH22001', '2024-09-18 10:00:00', '2024-09-18 12:00:00'),
('L1-010', 'STD23007', 'TCH22002', '2024-09-19 08:00:00', '2024-09-19 10:00:00'),
('L1-010', 'STD23008', 'TCH22003', '2024-09-20 09:00:00', '2024-09-20 11:00:00'),
('L1-011', 'STD23009', 'TCH22004', '2024-09-21 08:00:00', '2024-09-21 10:00:00'),
('L1-011', 'STD23010', 'TCH22005', '2024-09-22 10:00:00', '2024-09-22 12:00:00'),
('L1-012', 'STD22001', 'TCH23001', '2024-09-23 08:00:00', '2024-09-23 10:00:00'),
('L1-012', 'STD22002', 'TCH23002', '2024-09-24 09:00:00', '2024-09-24 11:00:00'),
('L1-001', 'STD23003', 'TCH21001', '2024-09-25 08:00:00', '2024-09-25 10:00:00'),
('L1-001', 'STD23004', 'TCH21002', '2024-09-26 10:00:00', '2024-09-26 12:00:00'),
('L1-002', 'STD23005', 'TCH21003', '2024-09-27 08:00:00', '2024-09-27 10:00:00'),
('L1-002', 'STD23006', 'TCH21004', '2024-09-28 09:00:00', '2024-09-28 11:00:00'),
('L1-003', 'STD23007', 'TCH21005', '2024-09-29 08:00:00', '2024-09-29 10:00:00'),
('L1-003', 'STD23008', 'TCH22001', '2024-09-30 10:00:00', '2024-09-30 12:00:00'),
('L1-004', 'STD23009', 'TCH22002', '2024-10-01 08:00:00', '2024-10-01 10:00:00'),
('L1-004', 'STD23010', 'TCH22003', '2024-10-02 09:00:00', '2024-10-02 11:00:00'),
('L1-005', 'STD22001', 'TCH22004', '2024-10-03 08:00:00', '2024-10-03 10:00:00'),
('L1-005', 'STD22002', 'TCH22005', '2024-10-04 10:00:00', '2024-10-04 12:00:00'),
('L1-006', 'STD23001', 'TCH21001', '2024-10-05 08:00:00', '2024-10-05 10:00:00'),
('L1-006', 'STD23002', 'TCH21002', '2024-10-06 10:00:00', '2024-10-06 12:00:00'),
('L1-007', 'STD23003', 'TCH21003', '2024-10-07 08:00:00', '2024-10-07 10:00:00'),
('L1-007', 'STD23004', 'TCH21004', '2024-10-08 09:00:00', '2024-10-08 11:00:00'),
('L1-008', 'STD23005', 'TCH21005', '2024-10-09 08:00:00', '2024-10-09 10:00:00'),
('L1-008', 'STD23006', 'TCH22001', '2024-10-10 10:00:00', '2024-10-10 12:00:00'),
('L1-009', 'STD23007', 'TCH22002', '2024-10-11 08:00:00', '2024-10-11 10:00:00'),
('L1-009', 'STD23008', 'TCH22003', '2024-10-12 09:00:00', '2024-10-12 11:00:00'),
('L1-010', 'STD23009', 'TCH22004', '2024-10-13 08:00:00', '2024-10-13 10:00:00'),
('L1-010', 'STD23010', 'TCH22005', '2024-10-14 10:00:00', '2024-10-14 12:00:00'),
('L1-011', 'STD22001', 'TCH23001', '2024-10-15 08:00:00', '2024-10-15 10:00:00'),
('L1-011', 'STD22002', 'TCH23002', '2024-10-16 09:00:00', '2024-10-16 11:00:00'),
('L1-012', 'STD23001', 'TCH21001', '2024-10-17 08:00:00', '2024-10-17 10:00:00'),
('L1-012', 'STD23002', 'TCH21002', '2024-10-18 10:00:00', '2024-10-18 12:00:00');



INSERT INTO has_missed (std_ref, crs_ref, tch_ref, commencement, termination, is_justified) VALUES
('STD23001', 'L1-001', 'TCH21001', '2024-09-01 08:00:00', '2024-09-01 10:00:00', false),
('STD23002', 'L1-002', 'TCH21002', '2024-09-02 10:00:00', '2024-09-02 12:00:00', true),
('STD23003', 'L1-003', 'TCH21003', '2024-09-03 08:00:00', '2024-09-03 10:00:00', false),
('STD23004', 'L1-004', 'TCH21004', '2024-09-04 09:00:00', '2024-09-04 11:00:00', true),
('STD23005', 'L1-005', 'TCH21005', '2024-09-05 08:00:00', '2024-09-05 10:00:00', false),
('STD23006', 'L1-006', 'TCH22001', '2024-09-06 10:00:00', '2024-09-06 12:00:00', true),
('STD23007', 'L1-007', 'TCH22002', '2024-09-07 08:00:00', '2024-09-07 10:00:00', false),
('STD23008', 'L1-008', 'TCH22003', '2024-09-08 09:00:00', '2024-09-08 11:00:00', true),
('STD23009', 'L1-009', 'TCH22004', '2024-09-09 08:00:00', '2024-09-09 10:00:00', false),
('STD23010', 'L1-010', 'TCH22005', '2024-09-10 10:00:00', '2024-09-10 12:00:00', true),
('STD22001', 'L1-011', 'TCH23001', '2024-09-11 08:00:00', '2024-09-11 10:00:00', true),
('STD22002', 'L1-012', 'TCH23002', '2024-09-12 09:00:00', '2024-09-12 11:00:00', false),
('STD23001', 'L1-001', 'TCH21001', '2024-09-13 08:00:00', '2024-09-13 10:00:00', true),
('STD23002', 'L1-002', 'TCH21002', '2024-09-14 10:00:00', '2024-09-14 12:00:00', false),
('STD23003', 'L1-003', 'TCH21003', '2024-09-15 08:00:00', '2024-09-15 10:00:00', true),
('STD23004', 'L1-004', 'TCH21004', '2024-09-16 09:00:00', '2024-09-16 11:00:00', false),
('STD23005', 'L1-005', 'TCH21005', '2024-09-17 08:00:00', '2024-09-17 10:00:00', true),
('STD23006', 'L1-006', 'TCH22001', '2024-09-18 10:00:00', '2024-09-18 12:00:00', false),
('STD23007', 'L1-007', 'TCH22002', '2024-09-19 08:00:00', '2024-09-19 10:00:00', true),
('STD23008', 'L1-008', 'TCH22003', '2024-09-20 09:00:00', '2024-09-20 11:00:00', false),
('STD23009', 'L1-009', 'TCH22004', '2024-09-21 08:00:00', '2024-09-21 10:00:00', true),
('STD23010', 'L1-010', 'TCH22005', '2024-09-22 10:00:00', '2024-09-22 12:00:00', false),
('STD22001', 'L1-011', 'TCH23001', '2024-09-23 08:00:00', '2024-09-23 10:00:00', true),
('STD22002', 'L1-012', 'TCH23002', '2024-09-24 09:00:00', '2024-09-24 11:00:00', false),
('STD23001', 'L1-001', 'TCH21001', '2024-09-25 08:00:00', '2024-09-25 10:00:00', true),
('STD23002', 'L1-002', 'TCH21002', '2024-09-26 10:00:00', '2024-09-26 12:00:00', false),
('STD23003', 'L1-003', 'TCH21003', '2024-09-27 08:00:00', '2024-09-27 10:00:00', true),
('STD23004', 'L1-004', 'TCH21004', '2024-09-28 09:00:00', '2024-09-28 11:00:00', false),
('STD23005', 'L1-005', 'TCH21005', '2024-09-29 08:00:00', '2024-09-29 10:00:00', true),
('STD23006', 'L1-006', 'TCH22001', '2024-09-30 10:00:00', '2024-09-30 12:00:00', false),
('STD23007', 'L1-007', 'TCH22002', '2024-10-01 08:00:00', '2024-10-01 10:00:00', true),
('STD23008', 'L1-008', 'TCH22003', '2024-10-02 09:00:00', '2024-10-02 11:00:00', false),
('STD23009', 'L1-009', 'TCH22004', '2024-10-03 08:00:00', '2024-10-03 10:00:00', true),
('STD23010', 'L1-010', 'TCH22005', '2024-10-04 10:00:00', '2024-10-04 12:00:00', false),
('STD22001', 'L1-011', 'TCH23001', '2024-10-05 08:00:00', '2024-10-05 10:00:00', true),
('STD22002', 'L1-012', 'TCH23002', '2024-10-06 09:00:00', '2024-10-06 11:00:00', false),
('STD23001', 'L1-001', 'TCH21001', '2024-10-07 08:00:00', '2024-10-07 10:00:00', true),
('STD23002', 'L1-002', 'TCH21002', '2024-10-08 10:00:00', '2024-10-08 12:00:00', false),
('STD23003', 'L1-003', 'TCH21003', '2024-10-09 08:00:00', '2024-10-09 10:00:00', true),
('STD23004', 'L1-004', 'TCH21004', '2024-10-10 09:00:00', '2024-10-10 11:00:00', false),
('STD23005', 'L1-005', 'TCH21005', '2024-10-11 08:00:00', '2024-10-11 10:00:00', true),
('STD23006', 'L1-006', 'TCH22001', '2024-10-12 10:00:00', '2024-10-12 12:00:00', false),
('STD23007', 'L1-007', 'TCH22002', '2024-10-13 08:00:00', '2024-10-13 10:00:00', true),
('STD23008', 'L1-008', 'TCH22003', '2024-10-14 09:00:00', '2024-10-14 11:00:00', false),
('STD23009', 'L1-009', 'TCH22004', '2024-10-15 08:00:00', '2024-10-15 10:00:00', true),
('STD23010', 'L1-010', 'TCH22005', '2024-10-16 10:00:00', '2024-10-16 12:00:00', false),
('STD22001', 'L1-011', 'TCH23001', '2024-10-17 08:00:00', '2024-10-17 10:00:00', true),
('STD22002', 'L1-012', 'TCH23002', '2024-10-18 09:00:00', '2024-10-18 11:00:00', false),
('STD23001', 'L1-001', 'TCH21001', '2024-10-19 08:00:00', '2024-10-19 10:00:00', true),
('STD23002', 'L1-002', 'TCH21002', '2024-10-20 10:00:00', '2024-10-20 12:00:00', false),
('STD23003', 'L1-003', 'TCH21003', '2024-10-21 08:00:00', '2024-10-21 10:00:00', true),
('STD23004', 'L1-004', 'TCH21004', '2024-10-22 09:00:00', '2024-10-22 11:00:00', false),
('STD23005', 'L1-005', 'TCH21005', '2024-10-23 08:00:00', '2024-10-23 10:00:00', true),
('STD23006', 'L1-006', 'TCH22001', '2024-10-24 10:00:00', '2024-10-24 12:00:00', false),
('STD23007', 'L1-007', 'TCH22002', '2024-10-25 08:00:00', '2024-10-25 10:00:00', true),
('STD23008', 'L1-008', 'TCH22003', '2024-10-26 09:00:00', '2024-10-26 11:00:00', false),
('STD23009', 'L1-009', 'TCH22004', '2024-10-27 08:00:00', '2024-10-27 10:00:00', true),
('STD23010', 'L1-010', 'TCH22005', '2024-10-28 10:00:00', '2024-10-28 12:00:00', false),
('STD22001', 'L1-011', 'TCH23001', '2024-10-29 08:00:00', '2024-10-29 10:00:00', true),
('STD22002', 'L1-012', 'TCH23002', '2024-10-30 09:00:00', '2024-10-30 11:00:00', false),
('STD23001', 'L1-001', 'TCH21001', '2024-10-31 08:00:00', '2024-10-31 10:00:00', true);


INSERT INTO is_delayed (std_ref, crs_ref, tch_ref, commencement, termination, lateness) VALUES
('STD23005', 'L1-005', 'TCH21005', '2024-10-11 08:00:00', '2024-10-11 10:00:00', '2024-10-11 08:18:00'),
('STD23006', 'L1-006', 'TCH22001', '2024-10-12 10:00:00', '2024-10-12 12:00:00', '2024-10-12 10:40:00'),
('STD23007', 'L1-007', 'TCH22002', '2024-10-13 08:00:00', '2024-10-13 10:00:00', '2024-10-13 08:22:00'),
('STD23008', 'L1-008', 'TCH22003', '2024-10-14 09:00:00', '2024-10-14 11:00:00', '2024-10-14 09:50:00'),
('STD23009', 'L1-009', 'TCH22004', '2024-10-15 08:00:00', '2024-10-15 10:00:00', '2024-10-15 08:30:00'),
('STD23010', 'L1-010', 'TCH22005', '2024-10-16 10:00:00', '2024-10-16 12:00:00', '2024-10-16 10:25:00'),
('STD22001', 'L1-011', 'TCH23001', '2024-10-17 08:00:00', '2024-10-17 10:00:00', '2024-10-17 08:20:00'),
('STD22002', 'L1-012', 'TCH23002', '2024-10-18 09:00:00', '2024-10-18 11:00:00', '2024-10-18 09:40:00'),
('STD23001', 'L1-001', 'TCH21001', '2024-10-19 08:00:00', '2024-10-19 10:00:00', '2024-10-19 08:45:00'),
('STD23002', 'L1-002', 'TCH21002', '2024-10-20 10:00:00', '2024-10-20 12:00:00', '2024-10-20 10:15:00'),
('STD23003', 'L1-003', 'TCH21003', '2024-10-21 08:00:00', '2024-10-21 10:00:00', '2024-10-21 08:10:00'),
('STD23004', 'L1-004', 'TCH21004', '2024-10-22 09:00:00', '2024-10-22 11:00:00', '2024-10-22 09:30:00'),
('STD23005', 'L1-005', 'TCH21005', '2024-10-23 08:00:00', '2024-10-23 10:00:00', '2024-10-23 08:35:00'),
('STD23006', 'L1-006', 'TCH22001', '2024-10-24 10:00:00', '2024-10-24 12:00:00', '2024-10-24 10:45:00'),
('STD23007', 'L1-007', 'TCH22002', '2024-10-25 08:00:00', '2024-10-25 10:00:00', '2024-10-25 08:50:00'),
('STD23008', 'L1-008', 'TCH22003', '2024-10-26 09:00:00', '2024-10-26 11:00:00', '2024-10-26 09:20:00'),
('STD23009', 'L1-009', 'TCH22004', '2024-10-27 08:00:00', '2024-10-27 10:00:00', '2024-10-27 08:05:00'),
('STD23010', 'L1-010', 'TCH22005', '2024-10-28 10:00:00', '2024-10-28 12:00:00', '2024-10-28 10:30:00'),
('STD22001', 'L1-011', 'TCH23001', '2024-10-29 08:00:00', '2024-10-29 10:00:00', '2024-10-29 08:15:00'),
('STD22002', 'L1-012', 'TCH23002', '2024-10-30 09:00:00', '2024-10-30 11:00:00', '2024-10-30 09:35:00'),
('STD23001', 'L1-001', 'TCH21001', '2024-10-31 08:00:00', '2024-10-31 10:00:00', '2024-10-31 08:22:00'),
('STD23002', 'L1-002', 'TCH21002', '2024-11-01 10:00:00', '2024-11-01 12:00:00', '2024-11-01 10:05:00'),
('STD23003', 'L1-003', 'TCH21003', '2024-11-02 08:00:00', '2024-11-02 10:00:00', '2024-11-02 08:50:00'),
('STD23004', 'L1-004', 'TCH21004', '2024-11-03 09:00:00', '2024-11-03 11:00:00', '2024-11-03 09:40:00'),
('STD23005', 'L1-005', 'TCH21005', '2024-11-04 08:00:00', '2024-11-04 10:00:00', '2024-11-04 08:12:00'),
('STD23006', 'L1-006', 'TCH22001', '2024-11-05 10:00:00', '2024-11-05 12:00:00', '2024-11-05 10:20:00'),
('STD23007', 'L1-007', 'TCH22002', '2024-11-06 08:00:00', '2024-11-06 10:00:00', '2024-11-06 08:40:00'),
('STD23008', 'L1-008', 'TCH22003', '2024-11-07 09:00:00', '2024-11-07 11:00:00', '2024-11-07 09:50:00'),
('STD23009', 'L1-009', 'TCH22004', '2024-11-08 08:00:00', '2024-11-08 10:00:00', '2024-11-08 08:10:00'),
('STD23010', 'L1-010', 'TCH22005', '2024-11-09 10:00:00', '2024-11-09 12:00:00', '2024-11-09 10:45:00');

INSERT INTO group_level_matcher (level_year, "group") VALUES
('L1', 'J1'),
('L1', 'J2'),
('L2', 'H1'),
('L2', 'H2'),
('L3', 'G1');