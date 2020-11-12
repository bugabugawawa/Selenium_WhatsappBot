import java.sql.*;
import java.util.concurrent.RunnableScheduledFuture;

public class Mysql {
	
	public static int registro(String mensagem, String nome, String data, int prioridade) {

		/**\connect root@localhost:3308 conecta no mysql pelo shell*/
        String url = "jdbc:mysql://localhost:3308/wpp?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "bugabuga11";
        String[] datacomp = data.split(":");
        
        try {
            Connection myConn = DriverManager.getConnection(url,user,password);
            
            Statement myStmt = myConn.createStatement();
            
            String sql = "INSERT INTO vobys VALUES (NULL,'"+nome+"', '"+mensagem+"', '"+data+"', NULL)";
            
            myStmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            Connection myConn = DriverManager.getConnection(url,user,password);
            
            Statement myStmt = myConn.createStatement();
            
            String sql = "select * from wpp.func";
            
            ResultSet rs = myStmt.executeQuery(sql);
           
            while (rs.next())
            {
            	if(nome.equals(rs.getString("nome"))) {
            		String date = rs.getString("data");
            		String[] values = date.split(":");
            		if((Integer.parseInt(values[0])>Integer.parseInt(datacomp[0])) || (Integer.parseInt(values[0])==Integer.parseInt(datacomp[0]) && Integer.parseInt(values[1])>Integer.parseInt(datacomp[1]))) {
            			sql = "REPLACE INTO func VALUES (NULL,'"+nome+"', '"+mensagem+"', '"+data+"', now(), '"+prioridade+"', '"+rs.getString("email")+"')";
            			
            			myStmt.executeUpdate(sql);
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
        	sql = "REPLACE INTO func VALUES (NULL,'"+rs.getString("nome")+"', '"+"ausente"+"', '"+"00:00"+"', now(), '"+0+"', '"+rs.getString("email")+"')";
        	
        	myStmt2.executeUpdate(sql);
        }
		return new Runnable() {public void run() {}};
	}
	
	public static int id(String nome, String data, String atividade) {

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
            	if(nome.equals(rs.getString("nome")) && data.equals(rs.getString("data")) && atividade.equals(rs.getString("atividade"))) {return 1;}
            }        

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
        
    }

}
