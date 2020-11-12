import java.sql.*;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Mysql {
	
	public static int registro(String nome, String mensagem, String data, int prioridade, String num) {

		/**\connect root@localhost:3308 conecta no mysql pelo shell*/
        String url = "jdbc:mysql://localhost:3308/wpp?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "bugabuga11";
        String[] datacomp = data.split(":");

        try {
        	Connection myConn = DriverManager.getConnection(url,user,password);
            Statement myStmt = myConn.createStatement();
            String sql = "INSERT INTO vobys VALUES (NULL,'"+nome+"', '"+mensagem+"', '"+data+"', now(), '"+num+"')";
			myStmt.executeUpdate(sql);
        	
            Connection myConn2 = DriverManager.getConnection(url,user,password);
            
            Statement myStmt2 = myConn2.createStatement();
            
            String sql2 = "select * from wpp.func";
            
            ResultSet rs = myStmt2.executeQuery(sql2);
                       
            while (rs.next())
            {
            	if(num.equals(rs.getString("numero"))) {
            		String date = rs.getString("data");
            		String[] values = date.split(":");
            		
            		if((Integer.parseInt(values[0])<Integer.parseInt(datacomp[0])) || (Integer.parseInt(values[0])==Integer.parseInt(datacomp[0]) && Integer.parseInt(values[1])<Integer.parseInt(datacomp[1]))) {
            			sql2 = "REPLACE INTO func VALUES (NULL,'"+rs.getString("nome")+"', '"+mensagem+"', '"+data+"', now(), '"+prioridade+"', '"+rs.getString("email")+"', '"+rs.getString("numero")+"')";
            			myStmt2.executeUpdate(sql2);
            			return 0;
	            		}
	            	}
            }   
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
        
    }
	
	public static Runnable reset() throws SQLException{
		String url = "jdbc:mysql://localhost:3308/wpp?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "bugabuga11";
        
		Connection myConn = DriverManager.getConnection(url,user,password);
        
        Statement myStmt = myConn.createStatement();
        
        Statement myStmt2 = myConn.createStatement();
        
        String sql = "select * from wpp.func";
        
        ResultSet rs = myStmt.executeQuery(sql);
		
		while (rs.next())
        {
        	sql = "REPLACE INTO func VALUES (NULL,'"+rs.getString("nome")+"', '"+"ausente"+"', '"+"02:30"+"', now(), '"+0+"', '"+rs.getString("email")+"', '"+rs.getString("numero")+"')";
        	
        	myStmt2.executeUpdate(sql);
        }
		System.out.println("Reset");
		return new Runnable() {public void run() {}};
	}
	
	public static int id(String data, String atividade, String num) {

		/**\connect root@localhost:3308 conecta no mysql pelo shell*/
        String url = "jdbc:mysql://localhost:3308/wpp?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "bugabuga11";
        
        try {
            Connection myConn = DriverManager.getConnection(url,user,password);
            
            Statement myStmt = myConn.createStatement();
            
            String sql = "select * from wpp.vobys";
            
             ResultSet rs = myStmt.executeQuery(sql);
            
            while (rs.next())
            {
            	if(num.equals(rs.getString("numero")) && data.equals(rs.getString("data")) && atividade.equals(rs.getString("atividade"))) {return 1;}
            }        

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
        
    }
	
	public static String nome(String num) {

		/**\connect root@localhost:3308 conecta no mysql pelo shell*/
        String url = "jdbc:mysql://localhost:3308/wpp?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "bugabuga11";
        
        try {
            Connection myConn = DriverManager.getConnection(url,user,password);
            
            Statement myStmt = myConn.createStatement();
            
            String sql = "select * from wpp.func";
            
             ResultSet rs = myStmt.executeQuery(sql);
            
            while (rs.next())
            {
            	if(num.equals(rs.getString("numero"))) {return rs.getString("nome");}
            }        

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
        
    }

}
