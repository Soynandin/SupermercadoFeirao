package Visao;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

public class PanelEditarProduto extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JTextField txtNomeProduto;
	private JTextField txtQtdEstoque;
	private JTextField txtQtdMinimoEstoque;
	private JTextField txtPrecoVenda;
	private JTextField txtCategoria;
	private JButton btSalvarEdicao;
	
	public PanelEditarProduto() {
		setBounds(0, 0, 449, 470);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		setLayout(null);
		add(getBtSalvarEdicao());
		add(getTxtCategoria());
		add(getTxtPrecoVenda());
		add(getTxtQtdMinimoEstoque());
		add(getTxtQtdEstoque());
		add(getTxtNomeProduto());
		add(getLblBackground());
	}
	
	public JLabel getLblBackground() {
		if(lblBackground==null) {
			lblBackground = new JLabel();
			lblBackground.setBounds(0, 0, 450, 470);
			lblBackground.setIcon(new ImageIcon(PanelEditarProduto.class.getResource("/Imagens/BackgroundEdicaoProduto (1).png")));
		}
		return lblBackground;
	}
	public JTextField getTxtNomeProduto() {
		if (txtNomeProduto == null) {
			txtNomeProduto = new JTextField();
			txtNomeProduto.setBounds(22, 51, 400, 30);
			txtNomeProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			txtNomeProduto.setColumns(10);
		}
		return txtNomeProduto;
	}
	public JTextField getTxtQtdEstoque() {
		if (txtQtdEstoque == null) {
			txtQtdEstoque = new JTextField();
			txtQtdEstoque.setBounds(22, 136, 400, 30);
			txtQtdEstoque.setFont(new Font("Dialog", Font.BOLD, 15));
			txtQtdEstoque.setColumns(10);
		}
		return txtQtdEstoque;
	}
	public JTextField getTxtQtdMinimoEstoque() {
		if (txtQtdMinimoEstoque == null) {
			txtQtdMinimoEstoque = new JTextField();
			txtQtdMinimoEstoque.setBounds(22, 223, 400, 30);
			txtQtdMinimoEstoque.setFont(new Font("Dialog", Font.BOLD, 15));
			txtQtdMinimoEstoque.setColumns(10);
		}
		return txtQtdMinimoEstoque;
	}
	public JTextField getTxtPrecoVenda() {
		if (txtPrecoVenda == null) {
			txtPrecoVenda = new JTextField();
			txtPrecoVenda.setBounds(22, 308, 400, 30);
			txtPrecoVenda.setFont(new Font("Dialog", Font.BOLD, 15));
			txtPrecoVenda.setColumns(10);
		}
		return txtPrecoVenda;
	}
	public JTextField getTxtCategoria() {
		if (txtCategoria == null) {
			txtCategoria = new JTextField();
			txtCategoria.setBounds(22, 395, 400, 30);
			txtCategoria.setFont(new Font("Dialog", Font.BOLD, 15));
			txtCategoria.setColumns(10);
		}
		return txtCategoria;
	}
	public JButton getBtSalvarEdicao() {
		if (btSalvarEdicao == null) {
			btSalvarEdicao = new JButton("");
			btSalvarEdicao.setBounds(169, 427, 100, 43);
			btSalvarEdicao.setIcon(new ImageIcon(PanelEditarProduto.class.getResource("/Imagens/Salvar.png")));
			btSalvarEdicao.setContentAreaFilled(false);
			btSalvarEdicao.setFocusPainted(false);
			btSalvarEdicao.setBorder(null);
		}
		return btSalvarEdicao;
	}
}
