package leiloes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConectaDAO {

    public Connection connectDB() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost:3306/leiloes?useSSL=false&serverTimezone=UTC";
            String usuario = "root";
            String senha = ""; // Coloque a sua senha do MySQL, se houver

            conn = DriverManager.getConnection(url, usuario, senha);
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL na Conexão: " + e.getMessage());
        }
        
        return conn;
    }
}