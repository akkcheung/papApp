package hk.pnp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	 private static Connection instance = null;
	    
	    private DBConnection()
	    {
	    }
	    
	    public static Connection getInstance()
	        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	    {
	        if (instance == null)
	        {
	            instance = getConn();
	        }
	        return instance;
	    }
	    
	    public static Connection getConn()
	        throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	    {
	        
	        String driver = "com.mysql.jdbc.Driver";
	        String url = "jdbc:mysql://localhost/pnpdb";
	        String userName = "pnpAdmin";
	        
	        String passWord = "nopass";
	            
	        Connection conn = null;
	        Class.forName(driver).newInstance();
	        conn = DriverManager.getConnection(url, userName, passWord);
	        
	        return conn;
	    }
}
