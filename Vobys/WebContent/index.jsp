<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import = "java.sql.*"%>    

<%
	String url = "jdbc:mysql://localhost:3308/wpp?useTimezone=true&serverTimezone=UTC";
    String user = "root";
    String password = "bugabuga11";
    String lista = "";
    String func = "";
	int i;    
	int prioridade = 0;
	
        try {
            Connection myConn = DriverManager.getConnection(url,user,password);
            
            Statement myStmt = myConn.createStatement();
            
            String sql = "select * from wpp.func";
                        
            ResultSet rs = myStmt.executeQuery(sql);
                        
            int size = 0;
            if (rs != null) 
            {
              rs.last();    // moves cursor to the last row
              size = rs.getRow(); // get row id 
            }

            rs.first();
            
            do
            {   
            	for(i=0;i<5;++i){
            		String nome = rs.getString("nome");
                    String atividade = rs.getString("atividade_atual");
                    String data = rs.getString("data");
                    prioridade = rs.getInt("prioridade");
                    func = nome + "\n" + atividade + "\n" + data;
                    
                    if(size==1){i=4;}
                    
                    switch(prioridade){
           			case 1:
           				if(i==0){lista = lista.concat("<tr><td class=verde>" + func + "</td>");}
           				else if(i==4){lista = lista.concat("<td class=verde>" + func + "</td></tr>");}
    	           		else {lista = lista.concat("<td class=verde>" + func + "</td>");}
           				break;
           			case 42:
           				if(i==0){lista = lista.concat("<tr><td class=amarelo>" + func + "</td>");}
           				else if(i==4){lista = lista.concat("<td class=amarelo>" + func + "</td></tr>");}
    	           		else {lista = lista.concat("<td class=amarelo>" + func + "</td>");}
           				break;
           			default:
           				if(i==0){lista = lista.concat("<tr><td class=branco>" + func + "</td>");}
           				else if(i==4){lista = lista.concat("<td class=branco>" + func + "</td></tr>");}
    	           		else {lista = lista.concat("<td class=branco>" + func + "</td>");}
           				break;
           			} 
                    
            		rs.next();
            		--size;
            	}
            }while (size>1);   
			
        } catch (SQLException e) {
            e.printStackTrace();
        }

	session.setAttribute( "lista", lista );
%>



<!DOCTYPE html>



<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Vobys</title>
    <script src="sweetalert2.all.min.js"></script>
 	<script type="text/javascript">
 	
		//codigo da tabela:
		var table = document.getElementById('table');
		var rowCount = table.row.lenght;
		var cellCount = table.row[0].cells.lenght;
		var row = table.insertRow(rowCount);
		
		for(var i =0; i <= cellCount; i++){
			var cell = 'cell'+i;
			cell = row.insertCell(i);
			var copycel = document.getElementById('col'+i).innerHTML;
			cell.innerHTML=copycel;
		}
		
	</script>
</head>
<body min-height="100vh">
		<table id="table" cellpadding=0 cellspacing=0 border=0>${lista}</table>
</body>
<link href="StyleSheet1.css" rel="stylesheet" type="text/css" />
</html>
