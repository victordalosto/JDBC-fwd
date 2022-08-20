
package dnit.fwd.jdbc;

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
    private String DB_NAME;

    private PreparedStatement prepResultStatement;


    /* Controller Constructor that makes a connection with a local database. */
    public JDBC(String DB_NAME) throws SQLException {
        this.DB_NAME = DB_NAME;
        CONNECTION = "jdbc:mysql://localhost/" + DB_NAME + "?useTimezone=true&serverTimezone=UTC"; 
        connection = DriverManager.getConnection(CONNECTION, "root", "212329");
        connection.setAutoCommit(false);
        statement = connection.createStatement();
        prepResultStatement = connection.prepareStatement("INSERT INTO result (id_snv, km) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        System.out.println("Connected to database: "  + DB_NAME);
    }



    /* USE statement is used to select any existing database in the SQL schema. */
    public void useTable(String TABLE_NAME) throws SQLException {
        System.out.println("From database: " + DB_NAME + ". Using table: " + TABLE_NAME);
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
        runCommand("DROP TABLE IF EXISTS " + TABLE_NAME);
        runCommand("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ALL_STATEMENT + ");");
    }


    
    /* INSERT INTO database VALUES () */
    public int insert(String... inserts) throws Exception {
        return insertUsingParameters("", inserts);
    }



    /* INSERT INTO database (parametersTypes) VALUES (inserts) */
    public int insertUsingParameters(String parametersTypes, String... inserts) throws Exception {
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
            parametersTypes = " (" + convertString(parametersTypes) + ")";
        if (!TABLE_NAME.equals("result")) {
            String sql = "INSERT INTO " + TABLE_NAME +  parametersTypes + " VALUES " + ALL_INSERTS;
            runCommand(sql);
            return 0;
        } else {
            System.out.println("INSERT INTO result (id_snv, km) VALUES (?, ?)");
            prepResultStatement.setString(1, inserts[0].split(",")[0]);
            prepResultStatement.setString(2, inserts[0].split(",")[1]);
            prepResultStatement.executeUpdate();
            ResultSet generatedKey= prepResultStatement.getGeneratedKeys();
            generatedKey.next();
            return generatedKey.getInt(1);
        }
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



    /* Returns the first value of a query, or null */
    public String queryValue(String colunName, String paramWHERE) throws Exception {
        String sql = "SELECT " + colunName + " FROM " + TABLE_NAME + " WHERE " + paramWHERE;
        System.out.println(sql);
        ResultSet results = statement.executeQuery(sql);
        if (results.next())
            return results.getString(1);
        else 
            return null;
    }



    /* DELETE FROM TABLENAME WHERE colunmName = value (NOT INJECTION SAFE)*/
    public void delete(String collunmName, String value) throws Exception {
        runUpdate("DELETE FROM " + TABLE_NAME + " WHERE " + collunmName + " = " + value);
    }

    

    /* update FROM TABLENAME WHERE colunmName = value (NOT INJECTION SAFE)*/
    public void update(String collunmName, String oldValue, String newValue) throws Exception {
        runUpdate("UPDATE " + TABLE_NAME + " SET " + collunmName + " = " + newValue + " WHERE " + collunmName + " = " + oldValue);
    }



    // Simple function that replaces ' with " to allow the use of ' as string in java.
    private String convertString(String text) {
        text = text.replaceAll("['\"`]+", "\"");
        return text;
    }



    /* Commits a transaction */
    public void commit() throws Exception {
        System.out.println("Commiting values into: " + DB_NAME + "\n");
        connection.commit();
    }



    /* Rollsback a transaction */
    public void rollback() throws Exception {
        System.out.println("Rolling back transaction from : " + DB_NAME + "\n");
        connection.rollback();
    }



    /* Execute a command in the DB */
    public void runCommand(String sql) throws SQLException {
        System.out.println(sql);
        statement.execute(sql);
    }



    /* Execute a command in the DB */
    private void runUpdate(String sql) throws SQLException {
        System.out.println(sql);
        statement.executeUpdate(sql);
    }



    /* Closes the connection of the current database instance */
    public void close() {
        TABLE_NAME = null;
        try {prepResultStatement.close();} catch (Exception e) {}
        try {statement.close();} catch (Exception e) {}
        try {connection.close();} catch (Exception e) {}
    }
}
