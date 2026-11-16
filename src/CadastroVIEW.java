     
package leiloes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroVIEW extends javax.swing.JFrame {

    private JTextField txtNome;
    private JTextField txtValor;
    private JButton btnCadastrar;
    private JLabel lblNome;
    private JLabel lblValor;
    private JButton btnVerProdutos;

  
public CadastroVIEW() {

    setTitle("Cadastro de Produtos - LeilõesTDSat");
    setSize(400, 280);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);

    lblNome = new JLabel("Nome:");
    lblNome.setBounds(40, 40, 80, 25);
    add(lblNome);

    txtNome = new JTextField();
    txtNome.setBounds(110, 40, 230, 25);
    add(txtNome);

    lblValor = new JLabel("Valor:");
    lblValor.setBounds(40, 90, 80, 25);
    add(lblValor);

    txtValor = new JTextField();
    txtValor.setBounds(110, 90, 230, 25);
    add(txtValor);

    btnCadastrar = new JButton("Cadastrar");
    btnCadastrar.setBounds(140, 130, 120, 30);
    add(btnCadastrar);

    btnCadastrar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            btnCadastrarActionPerformed(evt);
        }
    });

    // Botão para abrir a listagem de produtos
    btnVerProdutos = new JButton("Ver produtos");
    btnVerProdutos.setBounds(140, 175, 120, 30);
    add(btnVerProdutos);

    btnVerProdutos.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            new vendasVIEW().setVisible(true);
        }
    });
}


    private void btnCadastrarActionPerformed(ActionEvent evt) {

        String nome = txtNome.getText().trim();
        String valorTexto = txtValor.getText().trim().replace(",", ".");

        if (nome.isEmpty() || valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try {

            double valor = Double.parseDouble(valorTexto);

            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "O valor deve ser maior que zero.");
                return;
            }

            ProdutosDTO produto = new ProdutosDTO();
            produto.setNome(nome);
            produto.setValor(valor);
            produto.setStatus("À Venda");

            ProdutosDAO produtodao = new ProdutosDAO();
            produtodao.cadastrarProduto(produto);

            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");

            txtNome.setText("");
            txtValor.setText("");
            txtNome.requestFocus();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "Digite um valor numérico válido.");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroVIEW().setVisible(true);
            }
        });
    }
}
