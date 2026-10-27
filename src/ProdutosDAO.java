    package leiloes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;

    public void cadastrarProduto(ProdutosDTO produto) {
        // Chama a classe correta com C maiúsculo
        conn = new ConectaDAO().connectDB();
        
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar ao banco de dados. Verifique o MySQL.");
            return;
        }
        
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Registo efetuado com sucesso!");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}