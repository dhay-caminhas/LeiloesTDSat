
package leiloes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class produtosVIEW extends JFrame {

    private JTable tabelaProdutos;
    private DefaultTableModel modelo;
    private JButton btnVender;
    private JButton btnConsultarVendas;
    private JButton btnVoltar;

    public produtosVIEW() {

        setTitle("Consulta de Produtos - LeilõesTDSat");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Produtos Cadastrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Valor", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProdutos = new JTable(modelo);
        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnVender = new JButton("Vender");
        btnConsultarVendas = new JButton("Consultar Vendas");
        btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnVender);
        painelBotoes.add(btnConsultarVendas);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnVender.addActionListener(e -> venderProdutoSelecionado());

        btnConsultarVendas.addActionListener(e -> {
            new vendasVIEW().setVisible(true);
        });

        btnVoltar.addActionListener(e -> dispose());

        listarProdutos();
    }

    private void listarProdutos() {

        try {
            ProdutosDAO dao = new ProdutosDAO();

            ArrayList<ProdutosDTO> lista = dao.listarProdutos();

            modelo.setRowCount(0);

            for (ProdutosDTO produto : lista) {

                modelo.addRow(new Object[]{
                    produto.getId(),
                    produto.getNome(),
                    produto.getValor(),
                    produto.getStatus()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao listar produtos: " + e.getMessage()
            );
        }
    }

    private void venderProdutoSelecionado() {

        int linhaSelecionada = tabelaProdutos.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um produto para vender."
            );
            return;
        }

        int id = (int) modelo.getValueAt(linhaSelecionada, 0);

        String status = modelo.getValueAt(linhaSelecionada, 3).toString();

        if (status.equalsIgnoreCase("Vendido")) {
            JOptionPane.showMessageDialog(
                this,
                "Este produto já foi vendido."
            );
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente vender este produto?",
            "Confirmar venda",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {

            try {
                ProdutosDAO dao = new ProdutosDAO();

                dao.venderProduto(id);

                JOptionPane.showMessageDialog(
                    this,
                    "Produto vendido com sucesso!"
                );

                listarProdutos();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao vender produto: " + e.getMessage()
                );
            }
        }
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new produtosVIEW().setVisible(true);
        });
    }
}

