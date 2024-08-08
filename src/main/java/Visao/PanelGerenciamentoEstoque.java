package Visao;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Monitoramento.DAOProduto;
import Recursos.BarraListaProdutos;
import Recursos.BarraListaProdutosRenderer;

public class PanelGerenciamentoEstoque extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> cbxCategoriaEstoque;
	
	private JButton btCancelarProduto;
	private JButton btPesquisar;
	
	private JLabel lblAlerta;
	private JLabel lblCategoria;
	private JLabel lblBackground;
	private JLabel bloquearVisao;
	
	private JList<BarraListaProdutos> listaProdutos;
	private JList<BarraListaProdutos> listaVencimento;
	private JList<BarraListaProdutos> listaAlertas;

	private JScrollPane scrollPaneProdutos;
	private JScrollPane scrollPaneVencimento;
	private JScrollPane scrollPaneAlertas;

	public DefaultListModel<BarraListaProdutos> modelListaProdutos = new DefaultListModel<>();
	public DefaultListModel<BarraListaProdutos> modelListaVencimento = new DefaultListModel<>();
	public DefaultListModel<BarraListaProdutos> modelListaAlertas = new DefaultListModel<>();
	
	public PanelGerenciamentoEstoque() {
		this.setBounds(10, 86, 1060, 683);
		setLayout(null);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getBtCancelarProduto());
		add(getListaAlertas());
		add(getListaVencimento());
		add(getListaProdutos());
		add(getLblAlerta());
		add(getBloquearVisao());
		add(getLblCategoria());
		add(getBtPesquisar());
		add(getCbxCategoriaEstoque());
		add(getLblBackground());
	}

	private void addItensCategoriaEstoque() {
		cbxCategoriaEstoque.addItem("");
		DAOProduto daoProduto = new DAOProduto();
		List<String> categorias = daoProduto.buscarCategorias();
		try {
			for(String categoria : categorias) {
				cbxCategoriaEstoque.addItem(categoria);
			}
		} catch(Exception e) {
			cbxCategoriaEstoque.addItem("Erro");
		}
	}

	public JComboBox<String> getCbxCategoriaEstoque() {
		if (cbxCategoriaEstoque == null) {
			cbxCategoriaEstoque = new JComboBox<String>();
			cbxCategoriaEstoque.setBounds(690, 21, 265, 31);
			addItensCategoriaEstoque();
		}
		return cbxCategoriaEstoque;
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel("");
			lblBackground.setIcon(new ImageIcon(PanelGerenciamentoEstoque.class.getResource("/Imagens/panelEstoque.png")));
			lblBackground.setBounds(0, 0, 1060, 683);
		}
		return lblBackground;
	}
	
	public JButton getBtPesquisar() {
		if (btPesquisar == null) {
			btPesquisar = new JButton("");
			btPesquisar.setIcon(new ImageIcon(PanelGerenciamentoEstoque.class.getResource("/Imagens/btBuscar.png")));
			btPesquisar.setBorder(null);
			btPesquisar.setContentAreaFilled(false);
			btPesquisar.setFocusPainted(false);
			btPesquisar.setBounds(965, 11, 45, 45);
		}
		return btPesquisar;
	}
	
	public JButton getBtCancelarProduto() {
		if (btCancelarProduto == null) {
			btCancelarProduto = new JButton();
			btCancelarProduto.setIcon(new ImageIcon(TelaVenda.class.getResource("/Imagens/btCancelar.png")));
			btCancelarProduto.setBorder(null);
			btCancelarProduto.setContentAreaFilled(false);
			btCancelarProduto.setFocusPainted(false);
			btCancelarProduto.setBounds(482, 167, 52, 40);
			btCancelarProduto.setVisible(false);
		}
		return btCancelarProduto;
	}

	public JLabel getBloquearVisao() {
		if (bloquearVisao == null) {
			bloquearVisao = new JLabel();
			bloquearVisao.setIcon(new ImageIcon(PanelGerenciamentoEstoque.class.getResource("/Imagens/bloqVisao1.png")));
			bloquearVisao.setBounds(4, 71, 1046, 608);
			bloquearVisao.setVisible(true);
		}
		return bloquearVisao;
	}
	
	public JLabel getLblAlerta() {
		if (lblAlerta == null) {
			lblAlerta = new JLabel("");
			lblAlerta.setHorizontalAlignment(SwingConstants.RIGHT);
			lblAlerta.setForeground(new Color(255, 0, 0));
			lblAlerta.setFont(new Font("DialogInput", Font.BOLD, 15));
			lblAlerta.setVisible(false);
			lblAlerta.setBounds(436, 71, 593, 24);
		}
		return lblAlerta;
	}

	public JLabel getLblCategoria() {
		if (lblCategoria == null) {
			lblCategoria = new JLabel("");
			lblCategoria.setVisible(false);
			lblCategoria.setFont(new Font("DialogInput", Font.BOLD, 25));
			lblCategoria.setBounds(201, 109, 384, 24);
		}
		return lblCategoria;
	}

	public JScrollPane getListaProdutos() {
        if (scrollPaneProdutos == null) {
            listaProdutos = new JList<>(modelListaProdutos);
            listaProdutos.setCellRenderer(new BarraListaProdutosRenderer());
            scrollPaneProdutos = new JScrollPane(listaProdutos);
            scrollPaneProdutos.setBounds(50, 218, 484, 411);
            scrollPaneProdutos.setVisible(false);
        }
        return scrollPaneProdutos;
    }

    public JScrollPane getListaVencimento() {
        if (scrollPaneVencimento == null) {
            listaVencimento = new JList<>(modelListaVencimento);
            listaVencimento.setCellRenderer(new BarraListaProdutosRenderer());
            scrollPaneVencimento = new JScrollPane(listaVencimento);
            scrollPaneVencimento.setBounds(576, 218, 466, 142);
            scrollPaneVencimento.setVisible(false);
        }
        return scrollPaneVencimento;
    }

    public JScrollPane getListaAlertas() {
        if (scrollPaneAlertas == null) {
            listaAlertas = new JList<>(modelListaAlertas);
            listaAlertas.setCellRenderer(new BarraListaProdutosRenderer());
            scrollPaneAlertas = new JScrollPane(listaAlertas);
            scrollPaneAlertas.setBounds(576, 422, 466, 207);
            scrollPaneAlertas.setVisible(false);
        }
        return scrollPaneAlertas;
    }

    public void escondeListas() {
        JScrollPane vencimentoScrollPane = getListaVencimento();
        if (vencimentoScrollPane != null) {
            vencimentoScrollPane.setVisible(false);
        }

        JScrollPane alertasScrollPane = getListaAlertas();
        if (alertasScrollPane != null) {
            alertasScrollPane.setVisible(false);
        }

        JScrollPane produtosScrollPane = getListaProdutos();
        if (produtosScrollPane != null) {
            produtosScrollPane.setVisible(false);
        }

        revalidate();
        repaint();
    }

    public void mostraListas() {
        JScrollPane vencimentoScrollPane = getListaVencimento();
        if (vencimentoScrollPane != null) {
            vencimentoScrollPane.setVisible(true);
        }

        JScrollPane alertasScrollPane = getListaAlertas();
        if (alertasScrollPane != null) {
            alertasScrollPane.setVisible(true);
        }

        JScrollPane produtosScrollPane = getListaProdutos();
        if (produtosScrollPane != null) {
            produtosScrollPane.setVisible(true);
        }

        revalidate();
        repaint();
    }
    
    public JList<BarraListaProdutos> pegaListaProdutos(){
    	return listaProdutos;
    }
    public JList<BarraListaProdutos> pegaListaVencimento(){
    	return listaVencimento;
    }
    public JList<BarraListaProdutos> pegaListaAlertas(){
    	return listaAlertas;
    }
	
}
