package Visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Recursos.BarraListaProdutos;
import Recursos.BarraListaProdutosRenderer;
import Recursos.ExibirProduto;

public class TelaVenda extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btInsereProduto;
	private JButton btBuscaProduto;
	private JButton btCancelarProduto;
	private JButton btFinalizarVenda;
	private JComboBox<String> cbxFormaPagamento;
	private JComboBox<String> cbxParcelamento;
	private JList<BarraListaProdutos> listaProdutos;
	private JTextField txtCodigoProduto;
	private JTextField txtQtdProduto;
	private JLabel lblEstadoCaixa;
	private JLabel lblListaProdutos;
	private JLabel lblTituloProduto;
	private JLabel lblTituloValorTotal;
	private JLabel lblTituloSubtotal;
	private JLabel lblTituloDesconto;
	private JLabel lblCodigoProduto;
	private JLabel lblQtdProduto;
	private ExibirProduto panelPrecoUnitProduto;
	private JLabel lblSubtotalProduto;
	private JLabel lblValorTotal;
	private JLabel lblDesconto;
	private JLabel lblBackground;
	private JButton btFecharCaixa;

	private String estd;
	private JLabel lblCpf;
	private JFormattedTextField txtCpfCliente;
	public DefaultListModel<BarraListaProdutos> model;

	public TelaVenda(String estadoCaixa) {
		this.setBounds(0, 0, 1080, 780);
		estd = estadoCaixa;
		setLayout(null);
		addItens(estadoCaixa);
		this.setVisible(true);
	}

	private void addItens(String e) {
		add(getLblCpf());
		add(getTxtCpfCliente());
		add(getBtFecharCaixa());
		add(getBtInsereProduto());
		add(getBtBuscaProduto());
		add(getBtCancelarProduto());
		add(getBtFinalizarVenda());
		add(getCbxFormaPagamento());
		add(getCbxParcelamento());
		add(getListaProdutos());
		add(getTxtCodigoProduto());
		add(getTxtQtdProduto());
		add(getLblListaProdutos());
		add(getLblTituloProduto());
		add(getLblTituloValorTotal());
		add(getLblTituloSubtotal());
		add(getLblTituloDesconto());
		add(getLblEstadoCaixa(e));
		add(getLblCodigoProduto());
		add(getLblQtdProduto());
		add(getPanelPrecoUnitProduto());
		add(getLblSubtotalProduto());
		add(getLblValorTotal());
		add(getLblDesconto());
		add(getLblBackground(e));

	}

	public String getEstado() {
		return estd;
	}

	public JButton getBtInsereProduto() {
		if (btInsereProduto == null) {
			btInsereProduto = new JButton();
			btInsereProduto.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/btInserir.png")));
			btInsereProduto.setBorder(null);
			btInsereProduto.setContentAreaFilled(false);
			btInsereProduto.setFocusPainted(false);
			btInsereProduto.setBounds(66, 558, 244, 53);
		}
		return btInsereProduto;
	}

	public JButton getBtBuscaProduto() {
		if (btBuscaProduto == null) {
			btBuscaProduto = new JButton();

			btBuscaProduto.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/btPesquisar.png")));
			btBuscaProduto.setBorder(null);
			btBuscaProduto.setContentAreaFilled(false);
			btBuscaProduto.setFocusPainted(false);
			btBuscaProduto.setBounds(328, 561, 50, 50);
		}
		return btBuscaProduto;
	}

	public JButton getBtCancelarProduto() {
		if (btCancelarProduto == null) {
			btCancelarProduto = new JButton();
			btCancelarProduto.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/btCancelar.png")));
			btCancelarProduto.setBorder(null);
			btCancelarProduto.setContentAreaFilled(false);
			btCancelarProduto.setFocusPainted(false);
			btCancelarProduto.setBounds(393, 566, 40, 40);
			
			// Adiciona um MouseListener ao botão
			btCancelarProduto.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mousePressed(MouseEvent e) {
	                // Verifica se o clique foi feito com o botão auxiliar (direito)
	                if (SwingUtilities.isRightMouseButton(e)) {
	                	cbxFormaPagamento.setSelectedIndex(0);;
	                	cbxParcelamento.setSelectedIndex(0);
	                	cbxParcelamento.setEnabled(false);
	                	txtCodigoProduto.setText("");
	                    txtQtdProduto.setText("0");
	                    txtCpfCliente.setText("Cliente");
	                    model.clear();
	                    lblSubtotalProduto.setText("");
	                    lblValorTotal.setText("");
	                    lblDesconto.setText("");
	                    panelPrecoUnitProduto.getLblCodigo().setText("");
	                    panelPrecoUnitProduto.getLblNomeProduto().setText("");
	                    panelPrecoUnitProduto.getLblPrecoU().setText("");
	                    txtCpfCliente.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	                }
	            }
	        });
		}
		return btCancelarProduto;
	}

	public JButton getBtFinalizarVenda() {
		if (btFinalizarVenda == null) {
			btFinalizarVenda = new JButton();
			btFinalizarVenda.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/btFinalizarCompra.png")));
			btFinalizarVenda.setBorder(null);
			btFinalizarVenda.setContentAreaFilled(false);
			btFinalizarVenda.setFocusPainted(false);
			btFinalizarVenda.setBounds(735, 694, 291, 62);
		}
		return btFinalizarVenda;
	}

	public JComboBox<String> getCbxFormaPagamento() {
		if (cbxFormaPagamento == null) {
			cbxFormaPagamento = new JComboBox<String>();
			cbxFormaPagamento.setFont(new Font("Dialog", Font.BOLD, 15));
			cbxFormaPagamento.setBounds(25, 698, 273, 35);
			cbxFormaPagamento.addItem("");
			cbxFormaPagamento.addItem("Dinheiro");
			cbxFormaPagamento.addItem("Pix");
			cbxFormaPagamento.addItem("Cartão Débito");
			cbxFormaPagamento.addItem("Cartão Crédito");
			cbxFormaPagamento.addItem("Fiado");
		}

		// ação da seleção
		return cbxFormaPagamento;
	}

	public JComboBox<String> getCbxParcelamento() {
		if (cbxParcelamento == null) {
			cbxParcelamento = new JComboBox<String>();
			cbxParcelamento.setFont(new Font("Dialog", Font.PLAIN, 18));
			cbxParcelamento.setBounds(308, 698, 136, 35);
			cbxParcelamento.setEnabled(false);
			cbxParcelamento.addItem("");
			cbxParcelamento.addItem("2x");
			cbxParcelamento.addItem("4x");
			cbxParcelamento.addItem("6x");
			cbxParcelamento.addItem("8x");
		}
		return cbxParcelamento;
	}

	public JScrollPane getListaProdutos() {
		// Verifica se a lista de produtos já foi inicializada
		if (listaProdutos == null) {
			// Inicializa a lista de produtos e configura conforme necessário
			model = new DefaultListModel<>();

			listaProdutos = new JList<BarraListaProdutos>(model);
			listaProdutos.setCellRenderer(new BarraListaProdutosRenderer());
			listaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// Define a cor da seleção do item
			listaProdutos.setSelectionBackground(Color.ORANGE); // Cor de fundo
			listaProdutos.setSelectionForeground(Color.WHITE);
			// Cria um JScrollPane e adiciona a lista de produtos a ele
			JScrollPane scrollPane = new JScrollPane(listaProdutos);

			// Define o tamanho preferido do JScrollPane
			scrollPane.setBounds(471, 163, 584, 365);

			return scrollPane;
		}
		return null; // Retorno de segurança caso a lista já tenha sido inicializada anteriormente
	}

	public JList<BarraListaProdutos> pegaLista() {
		return listaProdutos;
	}

	public JTextField getTxtCodigoProduto() {
		if (txtCodigoProduto == null) {
			txtCodigoProduto = new JTextField();
			txtCodigoProduto.setFont(new Font("Dialog", Font.BOLD, 25));
			txtCodigoProduto.setHorizontalAlignment(SwingConstants.CENTER);
			txtCodigoProduto.setBounds(25, 471, 222, 64);
		}
		return txtCodigoProduto;
	}

	public JTextField getTxtQtdProduto() {
	    if (txtQtdProduto == null) {
	        txtQtdProduto = new JTextField("0");
	        txtQtdProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        txtQtdProduto.setFont(new Font("Dialog", Font.BOLD, 25));
	        txtQtdProduto.setBounds(260, 471, 181, 64);

	     // Adiciona um FocusListener para limpar o texto ao ganhar foco se for "0"
	        txtQtdProduto.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (txtQtdProduto.getText().equals("0")) {
	                    txtQtdProduto.setText("");
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                if (txtQtdProduto.getText().trim().isEmpty()) {
	                    txtQtdProduto.setText("0");
	                }
	            }
	        });
	        
	     // Adiciona um KeyListener para permitir apenas números inteiros
	        txtQtdProduto.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                char c = e.getKeyChar();
	                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
	                    e.consume();  // Ignora o caractere não numérico
	                }
	            }
	        });

	    }
	    return txtQtdProduto;
	}

	public JLabel getLblEstadoCaixa(String e) {
		if (lblEstadoCaixa == null) {
			lblEstadoCaixa = new JLabel();
			lblEstadoCaixa.setForeground(new Color(0, 0, 0));
			lblEstadoCaixa.setFont(new Font("DialogInput", Font.BOLD, 30));
			lblEstadoCaixa.setText("<html>CAIXA<b> " + e + "</b></html>");
			lblEstadoCaixa.setHorizontalAlignment(SwingConstants.CENTER);
			lblEstadoCaixa.setBounds(123, 11, 845, 67);
		}
		return lblEstadoCaixa;
	}

	public JLabel getLblListaProdutos() {
		if (lblListaProdutos == null) {
			lblListaProdutos = new JLabel();
			lblListaProdutos.setForeground(new Color(0, 0, 0));
			lblListaProdutos.setFont(new Font("DialogInput", Font.BOLD, 30));
			lblListaProdutos.setText("<html>LISTA DE COMPRAS</html>");
			lblListaProdutos.setHorizontalAlignment(SwingConstants.CENTER);
			lblListaProdutos.setBounds(471, 109, 584, 50);
		}
		return lblListaProdutos;
	}

	public JLabel getLblTituloProduto() {
		if (lblTituloProduto == null) {
			lblTituloProduto = new JLabel();
			lblTituloProduto.setForeground(new Color(0, 0, 0));
			lblTituloProduto.setFont(new Font("DialogInput", Font.BOLD, 30));
			lblTituloProduto.setText("<html>PRODUTO</html>");
			lblTituloProduto.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloProduto.setBounds(24, 115, 419, 43);
		}
		return lblTituloProduto;
	}

	public JLabel getLblTituloValorTotal() {
		if (lblTituloValorTotal == null) {
			lblTituloValorTotal = new JLabel();
			lblTituloValorTotal.setForeground(new Color(0, 0, 0));
			lblTituloValorTotal.setFont(new Font("DialogInput", Font.BOLD, 25));
			lblTituloValorTotal.setText("<html>VALOR TOTAL</html>");
			lblTituloValorTotal.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloValorTotal.setBounds(689, 552, 366, 35);
		}
		return lblTituloValorTotal;
	}

	public JLabel getLblTituloSubtotal() {
		if (lblTituloSubtotal == null) {
			lblTituloSubtotal = new JLabel();
			lblTituloSubtotal.setForeground(new Color(0, 0, 0));
			lblTituloSubtotal.setFont(new Font("DialogInput", Font.BOLD, 20));
			lblTituloSubtotal.setText("<html>SUBTOTAL</html>");
			lblTituloSubtotal.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloSubtotal.setBounds(470, 551, 194, 37);
		}
		return lblTituloSubtotal;
	}

	public JLabel getLblTituloDesconto() {
		if (lblTituloDesconto == null) {
			lblTituloDesconto = new JLabel();
			lblTituloDesconto.setForeground(new Color(0, 0, 0));
			lblTituloDesconto.setFont(new Font("DialogInput", Font.BOLD, 20));
			lblTituloDesconto.setText("<html>DESCONTO</html>");
			lblTituloDesconto.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloDesconto.setBounds(470, 695, 193, 25);
		}
		return lblTituloDesconto;
	}

	public JLabel getLblCodigoProduto() {
		if (lblCodigoProduto == null) {
			lblCodigoProduto = new JLabel();
			lblCodigoProduto.setForeground(new Color(0, 0, 0));
			lblCodigoProduto.setFont(new Font("DialogInput", Font.BOLD, 20));
			lblCodigoProduto.setText("<html>CÓDIGO</html>");
			lblCodigoProduto.setHorizontalAlignment(SwingConstants.CENTER);
			lblCodigoProduto.setBounds(24, 436, 223, 35);
		}
		return lblCodigoProduto;
	}

	public JLabel getLblQtdProduto() {
		if (lblQtdProduto == null) {
			lblQtdProduto = new JLabel();
			lblQtdProduto.setForeground(new Color(0, 0, 0));
			lblQtdProduto.setFont(new Font("DialogInput", Font.BOLD, 20));
			lblQtdProduto.setText("<html>QUANTIDADE</html>");
			lblQtdProduto.setHorizontalAlignment(SwingConstants.CENTER);
			lblQtdProduto.setBounds(260, 436, 181, 35);
		}
		return lblQtdProduto;
	}

	public ExibirProduto getPanelPrecoUnitProduto() {
		if (panelPrecoUnitProduto == null) {
			panelPrecoUnitProduto = new ExibirProduto();
			panelPrecoUnitProduto.setBounds(29, 163, 407, 244);
		}
		return panelPrecoUnitProduto;
	}

	public JLabel getLblSubtotalProduto() {
		if (lblSubtotalProduto == null) {
			lblSubtotalProduto = new JLabel();
			lblSubtotalProduto.setFont(new Font("Dialog", Font.BOLD, 25));
			lblSubtotalProduto.setHorizontalAlignment(SwingConstants.CENTER);
			lblSubtotalProduto.setBounds(471, 591, 191, 80);
		}
		return lblSubtotalProduto;
	}

	public JLabel getLblValorTotal() {
		if (lblValorTotal == null) {
			lblValorTotal = new JLabel();
			lblValorTotal.setFont(new Font("Dialog", Font.BOLD, 70));
			lblValorTotal.setHorizontalAlignment(SwingConstants.CENTER);
			lblValorTotal.setBounds(691, 591, 364, 80);
		}
		return lblValorTotal;
	}

	public JLabel getLblDesconto() {
		if (lblDesconto == null) {
			lblDesconto = new JLabel("0%");
			lblDesconto.setHorizontalAlignment(SwingConstants.CENTER);
			lblDesconto.setFont(new Font("Dialog", Font.BOLD, 20));
			lblDesconto.setBounds(469, 723, 194, 32);
		}
		return lblDesconto;
	}

	public JLabel getLblBackground(String e) {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setBounds(0, 0, 1080, 780);
			if (e.equals("ADMINISTRADOR")) {
				lblBackground
						.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/BackgroundTelaVendaAdm.png")));
				getLblEstadoCaixa(e).setBounds(250, 11, 845, 67);
			} else {
				lblBackground
						.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/BackgroundTelaVendaAtd.png")));
			}
		}
		return lblBackground;
	}

	public JButton getBtFecharCaixa() {
		if (btFecharCaixa == null) {
			btFecharCaixa = new JButton("");
			btFecharCaixa.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/fecharCaixaa.png")));
			btFecharCaixa.setBounds(921, 11, 50, 50);
			btFecharCaixa.setBorder(null);
			btFecharCaixa.setContentAreaFilled(false);
			btFecharCaixa.setFocusPainted(false);
		}
		return btFecharCaixa;
	}

	public JLabel getLblCpf() {
		if (lblCpf == null) {
			lblCpf = new JLabel();
			lblCpf.setText("<html>CPF CLIENTE</html>");
			lblCpf.setHorizontalAlignment(SwingConstants.CENTER);
			lblCpf.setForeground(Color.BLACK);
			lblCpf.setFont(new Font("DialogInput", Font.BOLD, 17));
			lblCpf.setBounds(39, 630, 86, 41);
		}
		return lblCpf;
	}

	public JFormattedTextField getTxtCpfCliente() {
		if (txtCpfCliente == null) {
			txtCpfCliente = new JFormattedTextField();
			txtCpfCliente.setForeground(new Color(114, 114, 114));
			txtCpfCliente.setText("Cliente");
			txtCpfCliente.setHorizontalAlignment(SwingConstants.LEFT);
			txtCpfCliente.setFont(new Font("Dialog", Font.BOLD, 25));
			txtCpfCliente.setBounds(132, 630, 311, 40);
		}
		return txtCpfCliente;
	}
}
