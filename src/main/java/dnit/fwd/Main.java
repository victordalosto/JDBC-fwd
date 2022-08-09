
package dnit.fwd;

import dnit.fwd.JDBC.JDBC;

public class Main {
    public static void main(String[] args) throws Exception {
        
        JDBC jdbc = new JDBC("dnit-fwd");

        jdbc.useTable("estado");
        jdbc.createTable("ID INTEGER AUTO_INCREMENT PRIMARY KEY", "UF CHAR(2) UNIQUE NOT NULL");
        jdbc.insert("1, 'AC'", "2, 'AL'", "3, 'AM'", "4, 'AP'", "5, 'BA'", "6, 'CE'", "7, 'DF'", "8, 'ES'", "9, 'GO'", "10, 'MA'", "11, 'MG'", "12, 'MS'", "13, 'MT'", "14, 'PA'", "15, 'PB'", "16, 'PE'", "17, 'PI'", "18, 'PR'", "19, 'RJ'", "20, 'RN'", "21, 'RO'", "22, 'RR'", "23, 'RS'", "24, 'SC'", "25, 'SE'", "26, 'SP'", "27, 'TO'");
        jdbc.commit();

        jdbc.useTable("snv");
        // jdbc.createTable("");
        // jdbc.commit();

        jdbc.useTable("dados");

        
        jdbc.close();

    }
}