
package leiloes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    // Método para cadastrar produtos
    public void cadastrarProduto(ProdutosDTO produto) throws SQLException {

        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        Connection conn = new ConectaDAO().connectDB();

        if (conn == null) {
            throw new SQLException("Não foi possível conectar ao banco de dados.");
        }

        try (PreparedStatement prep = conn.prepareStatement(sql)) {

            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

        } finally {
            conn.close();
        }
    }

    // Método para listar todos os produtos cadastrados
    public ArrayList<ProdutosDTO> listarProdutos() throws SQLException {

        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        String sql = "SELECT id, nome, valor, status FROM produtos";

        Connection conn = new ConectaDAO().connectDB();

        if (conn == null) {
            throw new SQLException("Não foi possível conectar ao banco de dados.");
        }

        try (PreparedStatement prep = conn.prepareStatement(sql);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {

                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));

                lista.add(produto);
            }

        } finally {
            conn.close();
        }

        return lista;
    }
}
