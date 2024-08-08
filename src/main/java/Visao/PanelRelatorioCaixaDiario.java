package Visao;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Recursos.BarraListaProdutos;
import Recursos.BarraListaProdutosRenderer;

public class PanelRelatorioCaixaDiario extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JList<BarraListaProdutos> historicoCompras;
	public DefaultListModel<BarraListaProdutos> model;
	private JLabel lblCodVenda;
	private JLabel lblDataHora;
	private JLabel lblFormaPagamento;
	private JLabel lblImposto;
	private JLabel lblDesconto;
	private JLabel lblTroco;
	private JLabel lblStatusVenda;
	private JLabel lblSubTotal;
	private JLabel lblValorTotal;
	private JLabel lblBackground;
	private JButton btVoltarTudo;
	private JButton btVoltarUm;
	private JButton btPassarUm;
	private JButton btPassarTudo;
	private JButton btAtualizar;

	public PanelRelatorioCaixaDiario() {
		this.setBounds(10, 86, 1060, 683);
		setLayout(null);
		model = new DefaultListModel<>();
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getBtAtualizar());
		add(getBtPassarTudo());
		add(getBtPassarUm());
		add(getBtVoltarUm());
		add(getBtVoltarTudo());
		add(getHistoricoCompras());
		add(getLblCodVenda());
		add(getLblDataHora());
		add(getLblFormaPagamento());
		add(getLblImposto());
		add(getLblDesconto());
		add(getLblTroco());
		add(getLblStatusVenda());
		add(getLblSubTotal());
		add(getLblValorTotal());
		add(getLblBackground());
	}

	public JScrollPane getHistoricoCompras() {
	    // Verifica se a lista de produtos já foi inicializada
	    if (historicoCompras == null) {
	        // Inicializa o modelo da lista de produtos
	        model = new DefaultListModel<>();

	        // Inicializa a lista de produtos e configurações adicionais
	        historicoCompras = new JList<>(model);
	        historicoCompras.setCellRenderer(new BarraListaProdutosRenderer());
	        
	        // Não é necessário chamar setVisible(true) para a JList ou o JScrollPane
	        
	        // Cria o JScrollPane e define seu tamanho preferido
	        JScrollPane scrollPane = new JScrollPane(historicoCompras);
	     // Define o tamanho preferido do JScrollPane
	     	scrollPane.setBounds(25, 105, 492, 546);
	        return scrollPane;
	    }
	    return null; // Retorna null se a lista já foi inicializada anteriormente
	}
	
	public void getJList() {
		historicoCompras.revalidate();
		historicoCompras.repaint();
	}
	
	public DefaultListModel<BarraListaProdutos> getModel() {
	    return model;
	}
	public JLabel getLblCodVenda() {
		if (lblCodVenda == null) {
			lblCodVenda = new JLabel();
			lblCodVenda.setFont(new Font("Dialog", Font.BOLD, 40));
			lblCodVenda.setBounds(35, 11, 991, 51);
		}
		return lblCodVenda;
	}

	public JLabel getLblDataHora() {
		if (lblDataHora == null) {
			lblDataHora = new JLabel();
			lblDataHora.setFont(new Font("Dialog", Font.BOLD, 25));
			lblDataHora.setBounds(539, 148, 487, 56);
		}
		return lblDataHora;
	}

	public JLabel getLblFormaPagamento() {
		if (lblFormaPagamento == null) {
			lblFormaPagamento = new JLabel();
			lblFormaPagamento.setFont(new Font("Dialog", Font.BOLD, 25));
			lblFormaPagamento.setBounds(539, 215, 487, 56);
		}
		return lblFormaPagamento;
	}

	public JLabel getLblImposto() {
		if (lblImposto == null) {
			lblImposto = new JLabel();
			lblImposto.setFont(new Font("Dialog", Font.BOLD, 20));
			lblImposto.setBounds(539, 282, 244, 56);
		}
		return lblImposto;
	}

	public JLabel getLblDesconto() {
		if (lblDesconto == null) {
			lblDesconto = new JLabel();
			lblDesconto.setFont(new Font("Dialog", Font.BOLD, 20));
			lblDesconto.setBounds(793, 282, 233, 56);
		}
		return lblDesconto;
	}

	public JLabel getLblTroco() {
		if (lblTroco == null) {
			lblTroco = new JLabel();
			lblTroco.setFont(new Font("Dialog", Font.BOLD, 20));
			lblTroco.setBounds(539, 349, 487, 56);
		}
		return lblTroco;
	}

	public JLabel getLblStatusVenda() {
		if (lblStatusVenda == null) {
			lblStatusVenda = new JLabel();
			lblStatusVenda.setFont(new Font("Dialog", Font.BOLD, 20));
			lblStatusVenda.setBounds(539, 416, 487, 56);
		}
		return lblStatusVenda;
	}

	public JLabel getLblSubTotal() {
		if (lblSubTotal == null) {
			lblSubTotal = new JLabel();
			lblSubTotal.setFont(new Font("Dialog", Font.BOLD, 20));
			lblSubTotal.setBounds(539, 483, 487, 56);
		}
		return lblSubTotal;
	}

	public JLabel getLblValorTotal() {
		if (lblValorTotal == null) {
			lblValorTotal = new JLabel();
			lblValorTotal.setFont(new Font("Dialog", Font.BOLD, 30));
			lblValorTotal.setBounds(539, 550, 487, 56);
		}
		return lblValorTotal;
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(PanelRelatorioCaixaDiario.class.getResource("/Imagens/Informações de venda.png")));
			lblBackground.setBounds(0, 0, 1060, 683);
		}
		return lblBackground;
	}

	public JButton getBtVoltarTudo() {
		if (btVoltarTudo == null) {
			btVoltarTudo = new JButton("");
			btVoltarTudo.setIcon(new ImageIcon(PanelRelatorioCaixaDiario.class.getResource("/Imagens/btVoltarTudo.png")));
			btVoltarTudo.setBounds(700, 617, 40, 40);
			btVoltarTudo.setBorder(null);
			btVoltarTudo.setContentAreaFilled(false);
			btVoltarTudo.setFocusPainted(false);
		}
		return btVoltarTudo;
	}

	public JButton getBtVoltarUm() {
		if (btVoltarUm == null) {
			btVoltarUm = new JButton("");
			btVoltarUm.setIcon(new ImageIcon(PanelRelatorioCaixaDiario.class.getResource("/Imagens/btVoltarUm.png")));
			btVoltarUm.setBounds(750, 617, 40, 40);
			btVoltarUm.setBorder(null);
			btVoltarUm.setContentAreaFilled(false);
			btVoltarUm.setFocusPainted(false);
		}
		return btVoltarUm;
	}

	public JButton getBtPassarUm() {
		if (btPassarUm == null) {
			btPassarUm = new JButton("");
			btPassarUm.setIcon(new ImageIcon(PanelRelatorioCaixaDiario.class.getResource("/Imagens/btPassarUm.png")));
			btPassarUm.setBounds(800, 617, 40, 40);
			btPassarUm.setBorder(null);
			btPassarUm.setContentAreaFilled(false);
			btPassarUm.setFocusPainted(false);
		}
		return btPassarUm;
	}

	public JButton getBtPassarTudo() {
		if (btPassarTudo == null) {
			btPassarTudo = new JButton("");
			btPassarTudo.setIcon(new ImageIcon(PanelRelatorioCaixaDiario.class.getResource("/Imagens/btPassarTudo.png")));
			btPassarTudo.setBounds(850, 617, 40, 40);
			btPassarTudo.setBorder(null);
			btPassarTudo.setContentAreaFilled(false);
			btPassarTudo.setFocusPainted(false);
		}
		return btPassarTudo;
	}
	public JButton getBtAtualizar() {
		if (btAtualizar == null) {
			btAtualizar = new JButton("");
			btAtualizar.setIcon(new ImageIcon(PanelRelatorioCaixaDiario.class.getResource("/Imagens/btAtualizar.png")));
			btAtualizar.setBounds(1005, 11, 40, 40);
			btAtualizar.setBorder(null);
			btAtualizar.setContentAreaFilled(false);
			btAtualizar.setFocusPainted(false);
		}
		return btAtualizar;
	}
}
