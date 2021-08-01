package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BancoService {

	private static final String myDriver = "com.mysql.jdbc.Driver";
	private static final String DATABASE_NAME= "indicadordeanalisetecnicacomia";
    private static final String myUrl = "jdbc:mysql://localhost/" + DATABASE_NAME;
    private static final String user = "root";
    private static final String pass = "projetoia";
    public static Connection conn = null;
    
	
	public static void setup() {
			try {
				Class.forName(myDriver);
				if (conn == null)
					conn = DriverManager.getConnection(myUrl, user , pass);	
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	static final String deleteInfoCandle = "delete from  " + "INFO_CANDLE" ;

	public static void dropaDatabase() {		
		executaQuery(deleteInfoCandle);
	}
	
	public static void executaQuery(String query) {
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.execute();
		    st.close();
		} catch ( SQLException e) {
			System.out.println("excessão mysql - não pode executar query " + e.getLocalizedMessage());
		}
	     
	}
	
}
