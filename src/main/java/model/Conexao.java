package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.postgresql.jdbc2.optional.PoolingDataSource;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author caiot
 */
public class Conexao {

    private static Connection connection = null;
    private static final PoolingDataSource source = new PoolingDataSource();
    
    static{
        try {
            /*
            String usuario = "postgres";
            String senha = "1602";
            
            Class.forName("org.postgresql.Driver");
            String urlconexao = "jdbc:postgresql://localhost/Chat";

            setConnection(DriverManager.getConnection(urlconexao, usuario, senha));
            
            getConnection().setAutoCommit(true);
            */
  
            source.setServerName(System.getenv("DATABASE_HOST"));
            source.setDatabaseName(System.getenv("DATABASE_NAME"));
            source.setUser(System.getenv("DATABASE_USER"));
            source.setPassword(System.getenv("DATABASE_PASSWORD"));
            source.setMaxConnections(5);
            
            new InitialContext().rebind("DataSource", source);
            
            
            
        } catch (NamingException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the connection
     */
    public static Connection getConnection() {
        
        try {
            DataSource dsource = (DataSource)new InitialContext().lookup("DataSource");
            connection = dsource.getConnection();
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }

    /**
     * @param aConnection the connection to set
     */
    public static void setConnection(Connection aConnection) {
        connection = aConnection;
    }

    
}
