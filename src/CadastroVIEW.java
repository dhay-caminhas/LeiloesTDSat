     package leiloes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroVIEW extends javax.swing.JFrame {

    // Declaração dos componentes da tela
    private JTextField txtNome;
    private JTextField txtValor;
    private JButton btnCadastrar;
    private JLabel lblNome;
    private JLabel lblValor;

    public CadastroVIEW() {
        // Configurações básicas da janela
        setTitle("Cadastro de Produtos - LeilõesTDSat");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Rótulo e Campo de Texto para o Nome
        lblNome = new JLabel("Nome:");
        lblNome.setBounds(40, 40, 80, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(110, 40, 230, 25);
        add(txtNome);

        // Rótulo e Campo de Texto para o Valor
        lblValor = new JLabel("Valor:");
        lblValor.setBounds(40, 90, 80, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(110, 90, 230, 25);
        add(txtValor);

        // Botão de Cadastrar
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(140, 150, 120, 30);
        add(btnCadastrar);

        // Evento de clique do botão Cadastrar
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });
    }

    private void btnCadastrarActionPerformed(ActionEvent evt) {
        // 1. Criar o objeto DTO
        ProdutosDTO produto = new ProdutosDTO();
        
        // 2. Recolher os dados dos campos de texto
        produto.setNome(txtNome.getText());
        
        try {
            produto.setValor(Double.parseDouble(txtValor.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um valor numérico válido.");
            return; 
        }
        
        produto.setStatus("À Venda");
        
        // 3. Chamar a DAO para gravar no banco de dados
        ProdutosDAO produtodao = new ProdutosDAO();
        produtodao.cadastrarProduto(produto);
        
        // 4. Limpar os campos após o sucesso
        txtNome.setText("");
        txtValor.setText("");
        txtNome.requestFocus();
    }

    // Método principal para permitir testar a tela isoladamente se necessário
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroVIEW().setVisible(true);
            }
        });
    }
}