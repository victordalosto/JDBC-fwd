
package dnit.fwd.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Personal Script made to Handle SQL database using JDBC.
 */
public class JDBC {

    private final String CONNECTION;
    private final Connection connection;
    private final Statement statement;
    private String TABLE_NAME;

    private PreparedStatement preparedQueryStatement; // removed, not implemented


    /* Constructor that makes a connection with a local database. */
    public JDBC(String DB_NAME) throws SQLException {
        CONNECTION = "jdbc:mysql://localhost/" + DB_NAME + "?useTimezone=true&serverTimezone=UTC"; 
        connection = DriverManager.getConnection(CONNECTION, "root", "212329");
        connection.setAutoCommit(false);
        statement = connection.createStatement();
    }



    /* USE statement is used to select any existing database in the SQL schema. */
    public void useTable(String TABLE_NAME) throws SQLException {
        this.TABLE_NAME = TABLE_NAME;
    }



    /* DROP the current table and CREATE a new TABLE given the name and data-type as stament */
    public void createTable(String... statements) throws Exception {
        if (this.TABLE_NAME == null)
            throw new Exception("A table must be defined to be managed");
        if (statements.length == 0) 
            throw new Exception("Can't create a table using no statement.");
        String ALL_STATEMENT = "";
        for (int i = 0; i < statements.length - 1; i++)
            ALL_STATEMENT += statements[i] + ", ";
        ALL_STATEMENT += statements[statements.length - 1];
        statement.execute("DROP TABLE IF EXISTS " + TABLE_NAME);
        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ALL_STATEMENT + ");");
    }


    
    /* INSERT INTO database VALUES () */
    public void insert(String... inserts) throws Exception {
        insertUsingParameters("", inserts);
    }



    /* INSERT INTO database (parametersTypes) VALUES (inserts) */
    public void insertUsingParameters(String parametersTypes, String... inserts) throws Exception {
        if (this.TABLE_NAME == null)
            throw new Exception("A table must be defined to be managed");
        if (parametersTypes == null) 
            throw new Exception("You must give valid parameters to INSERT");
        if (inserts.length == 0)
            throw new Exception("Can't create a table using no statement.");
        String ALL_INSERTS = "";
        for (int i = 0; i < inserts.length - 1; i++)
            ALL_INSERTS += "(" + convertString(inserts[i]) + "), ";
        ALL_INSERTS += "(" + convertString(inserts[inserts.length - 1]) + ")";
        if (parametersTypes != "") 
            parametersTypes = " (" + parametersTypes + ")";
        String sql = "INSERT INTO " + TABLE_NAME +  parametersTypes + " VALUES " + ALL_INSERTS;
        statement.execute(sql);
    }



    /** Print in the prompt a SELECT * from database.
     * @param numCollumns Is the num of columns that will be printed */
    public void queryAll() throws Exception {
        query(0);
    }



    /** Print in the prompt a SELECT * from database. */
    public void query(int numCollumns) throws Exception {
        Statement state = connection.createStatement();
        ResultSet results = state.executeQuery("SELECT * FROM " + TABLE_NAME);
        ResultSetMetaData metaData = results.getMetaData();
        if (numCollumns <= 0)
            numCollumns = metaData.getColumnCount();
        String queryHeader = "";
        for (int i = 1; i < numCollumns && i < metaData.getColumnCount(); i++)
            queryHeader += metaData.getColumnName(i) + "  |  ";
        System.out.println(queryHeader + metaData.getColumnName(numCollumns));
        while (results.next()) {
            String SELECT_QUERY = "";
            for (int i = 1; i <= numCollumns - 1; i++) {
                SELECT_QUERY += results.getString(i) + "  -  ";
            }
            SELECT_QUERY += results.getString(numCollumns);
            System.out.println(SELECT_QUERY);
        }
    }



    /* DELETE FROM TABLENAME WHERE colunmName = value (NOT INJECTION SAFE)*/
    public void delete(String collunmName, String value) throws Exception {
        statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE " + collunmName + " = " + value);
    }

    

    /* update FROM TABLENAME WHERE colunmName = value (NOT INJECTION SAFE)*/
    public void update(String collunmName, String oldValue, String newValue) throws Exception {
        statement.executeUpdate("UPDATE " + TABLE_NAME + " SET " + collunmName + " = " + newValue + " WHERE " + collunmName + " = " + oldValue);
        System.out.println(statement.getUpdateCount());
    }



    // Simple function that replaces ' with " to be used as string in java.
    private String convertString(String text) {
        text = text.replaceAll("['\"`]+", "\"");
        return text;
    }
    


    public void commit() throws Exception {
        connection.commit();
    }
    


    public void rollback() throws Exception {
        connection.rollback();
    }



    /* Closes the connection of the current database instance */
    public void close() {
        TABLE_NAME = null;
        try {preparedQueryStatement.close();} catch (Exception e) {}
        try {statement.close();} catch (Exception e) {}
        try {connection.close();} catch (Exception e) {}
    }
}
