package util;
import java.sql.*;

public class Connect {

    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "seruput_teh";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    private Connection con;
    private Statement st;
    private static Connect connect;
    public ResultSet rs;
    public ResultSetMetaData rsmd;
    public static Connect getInstance() {
        if (connect == null) return new Connect();
        return connect;
    }

    private Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con; 
    }
    public ResultSet execQuery(String query) {
    	try {
			rs=  st.executeQuery(query);
			rsmd = rs.getMetaData();
		} catch (Exception e) {
			
		}
    	return rs;
    }
    
    public boolean execUpdate(String query) {
    	boolean success;
    	try {
			st.executeUpdate(query);
			System.out.println(query+"\n^ run successfuly");
			success=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("execUpdate failed");
			success=false;
		}
    	return success;
    }
}