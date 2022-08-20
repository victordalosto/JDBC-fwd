package dnit.fwd.dao;

import dnit.fwd.jdbc.JDBC;

public class CreateTable {

    private CreateTable() {}


    /* Creates all the tables from the dnit_fwd database */
    public static void fwd (JDBC jdbc) throws Exception {
        CreateTable.dropAll(jdbc);
        CreateTable.state(jdbc);
        CreateTable.snv(jdbc);
        CreateTable.result(jdbc);
        CreateTable.observation(jdbc);
        CreateTable.result_observation(jdbc);
        CreateTable.deflection(jdbc);
        CreateTable.temperature(jdbc);
        CreateTable.data(jdbc);
        CreateTable.forca(jdbc);
        CreateTable.gps(jdbc);
    }



    /* Method to drop all tables from this schema */
    public static void dropAll(JDBC jdbc) throws Exception {
        try {
            jdbc.runCommand("DROP TABLE IF EXISTS `gps`");
            jdbc.runCommand("DROP TABLE IF EXISTS `forca`");
            jdbc.runCommand("DROP TABLE IF EXISTS `data`");
            jdbc.runCommand("DROP TABLE IF EXISTS `temperature`");
            jdbc.runCommand("DROP TABLE IF EXISTS `deflection`");
            jdbc.runCommand("DROP TABLE IF EXISTS `result_observation`");
            jdbc.runCommand("DROP TABLE IF EXISTS `observation`");
            jdbc.runCommand("DROP TABLE IF EXISTS `result`");
            jdbc.runCommand("DROP TABLE IF EXISTS `snv`");
            jdbc.runCommand("DROP TABLE IF EXISTS `state`");
            jdbc.runCommand("DROP VIEW IF EXISTS `fwd`");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the state table in the database*/
    public static void state(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("state");
            jdbc.createTable("id_state TINYINT AUTO_INCREMENT PRIMARY KEY", 
                             "uf CHAR(2) UNIQUE NOT NULL");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        }
    }



    /* Creates the snv table in the database */
    public static void snv(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("snv");
            jdbc.createTable("id_snv INT AUTO_INCREMENT PRIMARY KEY", 
                             "id_state TINYINT NOT NULL",
                             "br smallint(3) UNSIGNED ZEROFILL NOT NULL", 
                             "snv char(10) UNIQUE NOT NULL", 
                             "periodo DATE NOT NULL",
                             "FOREIGN KEY (id_state) REFERENCES state(id_state)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        }
    }



    /* Creates the snv table in the database */
    public static void result(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("result");
            jdbc.createTable("id_result INT AUTO_INCREMENT PRIMARY KEY", 
                             "id_snv int NOT NULL",
                             "km FLOAT NOT NULL",
                             "FOREIGN KEY (id_snv) REFERENCES snv(id_snv)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        }
    }



    /* Creates the temperature table in the database */
    public static void temperature(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("temperature");
            jdbc.createTable("id_result INT PRIMARY KEY", 
                             "ar FLOAT NOT NULL", 
                             "pav FLOAT NOT NULL",
                             "FOREIGN KEY (id_result) REFERENCES result(id_result)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the observation table in the database */
    public static void observation(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("observation");
            jdbc.createTable("id_obs INT AUTO_INCREMENT PRIMARY KEY", 
                             "obs VARCHAR(255)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the snv_observation table in the database */
    public static void result_observation(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("result_observation");
            jdbc.createTable("id_result INT NOT NULL", 
                             "id_obs INT NOT NULL ",
                             "PRIMARY KEY (id_result, id_obs)",
                             "FOREIGN KEY (id_result) REFERENCES result(id_result)",
                             "FOREIGN KEY (id_obs) REFERENCES observation(id_obs)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the deflection table in the database */
    public static void deflection(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("deflection");
            jdbc.createTable("id_result INT PRIMARY KEY", 
                             "d0 FLOAT NOT NULL", 
                             "d20 FLOAT NOT NULL",
                             "d30 FLOAT NOT NULL", 
                             "d45 FLOAT NOT NULL", 
                             "d65 FLOAT NOT NULL", 
                             "d90 FLOAT NOT NULL", 
                             "d120 FLOAT NOT NULL",
                             "FOREIGN KEY (id_result) REFERENCES result(id_result)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the data table in the database */
    public static void data(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("data");
            jdbc.createTable("id_result INT PRIMARY KEY", 
                             "date date NOT NULL", 
                             "time time NOT NULL",
                             "FOREIGN KEY (id_result) REFERENCES result(id_result)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the forca table in the database */
    public static void forca(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("forca");
            jdbc.createTable("id_result INT PRIMARY KEY", 
                             "forca float NOT NULL", 
                             "pression float NOT NULL", 
                             "raio float NOT NULL",
                             "FOREIGN KEY (id_result) REFERENCES result(id_result)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }



    /* Creates the gps table in the database */
    public static void gps(JDBC jdbc) throws Exception {
        try {
            jdbc.useTable("gps");
            jdbc.createTable("id_result INT PRIMARY KEY", 
                             "latitude double NOT NULL", 
                             "longitude double NOT NULL", 
                             "altitude double NOT NULL",
                             "FOREIGN KEY (id_result) REFERENCES result(id_result)");
            jdbc.commit();
        } catch (Exception e) {
            e.printStackTrace();
            jdbc.rollback();
        } 
    }

}
