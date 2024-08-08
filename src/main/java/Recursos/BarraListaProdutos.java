package Recursos;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BarraListaProdutos extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblCodigoProduto;
	private JLabel lblNomeProduto;
	private JLabel lblQtdProduto;
	private JLabel lblValorProduto;
	
	public BarraListaProdutos() {
		GridBagLayout layoutPrincipal = new GridBagLayout();
		layoutPrincipal.columnWidths = new int[]{69, 240, 73, 88};
		layoutPrincipal.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        setLayout(layoutPrincipal);
		setSize(471, 30);
		addItens();
	}

	private void addItens() {
		
        GridBagConstraints gbcCodigo = new GridBagConstraints();
        gbcCodigo.weighty = 1.0;
        gbcCodigo.gridx = 0;
        gbcCodigo.gridy = 0;
        gbcCodigo.fill = GridBagConstraints.HORIZONTAL;
        gbcCodigo.weightx = 10.0;

        GridBagConstraints gbcNome = new GridBagConstraints();
        gbcNome.weighty = 1.0;
        gbcNome.gridx = 1;
        gbcNome.gridy = 0;
        gbcNome.fill = GridBagConstraints.HORIZONTAL;
        gbcNome.weightx = 0.5;

        GridBagConstraints gbcQtd = new GridBagConstraints();
        gbcQtd.weighty = 1.0;
        gbcQtd.gridx = 2;
        gbcQtd.gridy = 0;
        gbcQtd.fill = GridBagConstraints.HORIZONTAL;
        gbcQtd.weightx = 10.0;

        GridBagConstraints gbcValor = new GridBagConstraints();
        gbcValor.weighty = 1.0;
        gbcValor.gridx = 3;
        gbcValor.gridy = 0;
        gbcValor.fill = GridBagConstraints.HORIZONTAL;
        gbcValor.weightx = 15.0;

        
        add(getLblCodigoProduto(), gbcCodigo);
        add(getLblNomeProduto(), gbcNome);
        add(getLblQtdProduto(), gbcQtd);
        add(getLblValorProduto(), gbcValor);
	}

	public JLabel getLblCodigoProduto() {
		if(lblCodigoProduto==null) {
			lblCodigoProduto = new JLabel();
			lblCodigoProduto.setText("c1f5");
			lblCodigoProduto.setFont(new Font("Dialog", Font.BOLD, 15));
		}
		return lblCodigoProduto;
	}

	public JLabel getLblNomeProduto() {
		if(lblNomeProduto==null) {
			lblNomeProduto = new JLabel();
			lblNomeProduto.setText("anskasnasasasas");
			lblNomeProduto.setFont(new Font("Dialog", Font.BOLD, 15));
		}
		return lblNomeProduto;
	}

	public JLabel getLblQtdProduto() {
		if(lblQtdProduto==null) {
			lblQtdProduto = new JLabel();
			lblQtdProduto.setText("12");
			lblQtdProduto.setFont(new Font("Dialog", Font.BOLD, 15));
		}
		return lblQtdProduto;
	}

	public JLabel getLblValorProduto() {
		if(lblValorProduto==null) {
			lblValorProduto = new JLabel();
			lblValorProduto.setText("12.35");
			lblValorProduto.setFont(new Font("Dialog", Font.BOLD, 15));
		}
		return lblValorProduto;
	}
	
	public String getCodigo() {
	    return lblCodigoProduto.getText();
	}

	public String getNome() {
	    return lblNomeProduto.getText();
	}

	public String getQuantidade() {
	    return lblQtdProduto.getText();
	}

	public String getValor() {
	    return lblValorProduto.getText();
	}

}
