package dnit.fwd;

import dnit.fwd.jdbc.JDBC;

public class Test {
   
    /* Check if the main functionality of a database are working, like acessing, adding, modifing, deleting and fetching.*/
    @org.junit.Test
    public void HandlingDatabase() throws Exception {

        /* Creates a connection with a database. @arg (String) = schema name in localhost. */
        JDBC jdbc = new JDBC("dnit_fwd");

        /* Select an existing database in the SQL schema */
        jdbc.useTable("estado_teste");

        /* DROP and create a table using multiple parameters */
        jdbc.createTable("ID INTEGER AUTO_INCREMENT PRIMARY KEY", "UF CHAR(2) UNIQUE NOT NULL");

        /* Inserts values in the managed table */
        jdbc.insert("1, 'AC'", "2, 'AL'", "3, 'AM'", "4, 'AP'", "5, 'BA'", "6, 'CE'", "7, 'DF'", "8, 'ES'", "9, 'GO'", "10, 'MA'", "11, 'MG'", "12, 'MS'", "13, 'MT'", "14, 'PA'", "15, 'PB'", "16, 'PE'", "17, 'PI'", "18, 'PR'", "19, 'RJ'", "20, 'RN'", "21, 'RO'", "22, 'RR'", "23, 'RS'", "24, 'SC'", "25, 'SE'", "26, 'SP'", "27, 'TO'", "28, 'ZZ'");
        // jdbc.insertUsingParameters("UF", (new String[] {"'AC'", "'AL'", "'AP'", "'AM'", "'BA'", "'CE'", "'DF'", "'ES'", "'GO'", "'MA'", "'MT'", "'MS'", "'MG'", "'PA'", "'PB'", "'PR'", "'PE'", "'PI'", "'RJ'", "'RN'", "'RS'", "'RO'", "'RR'", "'SC'", "'SP'", "'SE'", "'TO'"}));
        // jdbc.insertUsingParameters(("ID, UF"), "1, 'AC'", "2, 'AL'", "3, 'AM'", "4, 'AP'", "5, 'BA'", "6, 'CE'", "7, 'DF'", "8, 'ES'", "9, 'GO'", "10, 'MA'", "11, 'MG'", "12, 'MS'", "13, 'MT'", "14, 'PA'", "15, 'PB'", "16, 'PE'", "17, 'PI'", "18, 'PR'", "19, 'RJ'", "20, 'RN'", "21, 'RO'", "22, 'RR'", "23, 'RS'", "24, 'SC'", "25, 'SE'", "26, 'SP'", "27, 'TO'");

        /* Updates a value from Database */
        jdbc.update("UF", "'ZZ'", "'WW'");
        
        /* Delete a value from Database */
        jdbc.delete("UF", "'WW'");

        /* Commits the changes to the database. Default behaviour is autommict = 0, simulating a transaction */
        jdbc.commit();
        
        /* Query values from Database */
        jdbc.queryAll(); 
        // jdbc.query(2);
        
        jdbc.runCommand("DROP TABLE estado_teste");

        /* Closes the connection */
        jdbc.close();
    }
}