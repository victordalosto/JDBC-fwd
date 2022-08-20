package dnit.fwd;

import dnit.fwd.dao.CreateTable;
import dnit.fwd.dao.CreateView;
import dnit.fwd.jdbc.JDBC;
import dnit.fwd.push.ReadFromCSV;

public class Main {
    public static void main(String[] args) throws Exception {
        
        // Instantiate a DB Controller
        JDBC jdbc = new JDBC("dnit_fwd");

        // Commands to create the tables and views in the database. Used for dump 
        CreateTable.fwd(jdbc);
        CreateView.fwd(jdbc);

        // Commands to push datas into the database from .csv files
        ReadFromCSV.readAndpush("AC", jdbc);

    }
}