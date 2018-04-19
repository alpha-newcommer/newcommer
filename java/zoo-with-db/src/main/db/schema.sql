-- t_ribbon_animal テーブルの削除
DROP TABLE IF EXISTS t_ribbon_animal;

-- t_cage_animal テーブルの削除
DROP TABLE IF EXISTS t_cage_animal;
DROP SEQUENCE IF EXISTS t_cage_animal_id_seq;

-- m_cage テーブルの削除
DROP TABLE IF EXISTS m_cage;
DROP SEQUENCE IF EXISTS m_cage_cd_seq;

-- m_animal テーブルの削除
DROP TABLE IF EXISTS m_animal;
DROP SEQUENCE IF EXISTS m_animal_cd_seq;

-- m_ribbon テーブルの削除
DROP TABLE IF EXISTS m_ribbon;
DROP SEQUENCE IF EXISTS m_ribbon_cd_seq;




-- m_cage テーブルの作成
CREATE TABLE m_cage
(
  cd serial NOT NULL,
  name character varying(50) NOT NULL,
  type character varying(100) NOT NULL,
  CONSTRAINT m_cage_pkey PRIMARY KEY (cd)
);

-- m_animal テーブルの作成
CREATE TABLE m_animal
(
  cd serial NOT NULL,
  name character varying(50),
  type character varying(100) NOT NULL,
  CONSTRAINT m_animal_pkey PRIMARY KEY (cd)
);

-- m_ribbon テーブルの作成
CREATE TABLE m_ribbon
(
  cd serial NOT NULL,
  name character varying(50) NOT NULL,
  CONSTRAINT m_ribbon_pkey PRIMARY KEY (cd)
);

-- t_cage_animal テーブルの作成
CREATE TABLE t_cage_animal
(
  id serial NOT NULL,
  cage_cd integer NOT NULL,
  animal_cd integer NOT NULL,
  weight integer NOT NULL,
  CONSTRAINT t_cage_animal_pkey PRIMARY KEY (id),
  CONSTRAINT t_cage_animal_animal_cd_fkey FOREIGN KEY (animal_cd)
      REFERENCES m_animal (cd) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT t_cage_animal_cage_cd_fkey FOREIGN KEY (cage_cd)
      REFERENCES m_cage (cd) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- t_ribbon_animal テーブルの作成
CREATE TABLE t_ribbon_animal
(
  ribbon_cd integer NOT NULL,
  animal_id integer NOT NULL,
  CONSTRAINT t_ribbon_animal_animal_id_fkey FOREIGN KEY (animal_id)
      REFERENCES t_cage_animal (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT t_ribbon_animal_ribbon_cd_fkey FOREIGN KEY (ribbon_cd)
      REFERENCES m_ribbon (cd) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

