package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import Aplicacao.App;
import Modelo.Produto;
import Monitoramento.DAOProduto;
import Recursos.BarraListaProdutos;
import Visao.PanelGerenciamentoEstoque;

public class ControllerPanelGerenciamentoEstoque implements ActionListener {

	PanelGerenciamentoEstoque panelGerenciamentoEstoque;
	int controle = 0;

	public ControllerPanelGerenciamentoEstoque(PanelGerenciamentoEstoque panelGerenciamentoEstoque) {
		this.panelGerenciamentoEstoque = panelGerenciamentoEstoque;
		addEventos();
	}

	public PanelGerenciamentoEstoque getTela() {
		return panelGerenciamentoEstoque;
	}

	private void addEventos() {
		panelGerenciamentoEstoque.getBtPesquisar().addActionListener(this);
		panelGerenciamentoEstoque.getBtCancelarProduto().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == panelGerenciamentoEstoque.getBtPesquisar()) {
			if (panelGerenciamentoEstoque.getCbxCategoriaEstoque().getSelectedItem().equals("")) {
				somClique();
				panelGerenciamentoEstoque.getLblBackground().setIcon(
						new ImageIcon(PanelGerenciamentoEstoque.class.getResource("/Imagens/panelEstoque.png")));
				panelGerenciamentoEstoque.getLblCategoria().setVisible(false);
				panelGerenciamentoEstoque.getBtCancelarProduto().setVisible(false);
				panelGerenciamentoEstoque.getBloquearVisao().setVisible(true);
				panelGerenciamentoEstoque.modelListaProdutos.clear();
				panelGerenciamentoEstoque.modelListaVencimento.clear();
				panelGerenciamentoEstoque.modelListaAlertas.clear();
				panelGerenciamentoEstoque.escondeListas();
				controle = 0;
			} else {
				somClique();
				String categoria = (String) panelGerenciamentoEstoque.getCbxCategoriaEstoque().getSelectedItem();
				if (buscarProdutosPorCategoria(categoria)) {
					panelGerenciamentoEstoque.getLblBackground().setIcon(new ImageIcon(
							PanelGerenciamentoEstoque.class.getResource("/Imagens/ControleDeEstoque.png")));
					panelGerenciamentoEstoque.getLblCategoria().setText(categoria);
					panelGerenciamentoEstoque.getLblCategoria().setVisible(true);
					panelGerenciamentoEstoque.getBtCancelarProduto().setVisible(true);
					panelGerenciamentoEstoque.getBloquearVisao().setVisible(false);
					panelGerenciamentoEstoque.mostraListas();
					if (controle == 0 || categoria.equals(panelGerenciamentoEstoque.getLblCategoria().getText())) {
						panelGerenciamentoEstoque.modelListaProdutos.clear();
						panelGerenciamentoEstoque.modelListaVencimento.clear();
						panelGerenciamentoEstoque.modelListaAlertas.clear();
						carregaInformacoesPorCategoria(categoria);
					}
				} else {
					panelGerenciamentoEstoque.getLblBackground().setIcon(
							new ImageIcon(PanelGerenciamentoEstoque.class.getResource("/Imagens/panelEstoque.png")));
					panelGerenciamentoEstoque.getLblCategoria().setVisible(false);
					panelGerenciamentoEstoque.getBtCancelarProduto().setVisible(false);
					panelGerenciamentoEstoque.getBloquearVisao().setVisible(true);
					panelGerenciamentoEstoque.modelListaProdutos.clear();
					panelGerenciamentoEstoque.modelListaVencimento.clear();
					panelGerenciamentoEstoque.modelListaAlertas.clear();
					panelGerenciamentoEstoque.escondeListas();
					controle = 0;
					mostraAlerta(categoria);
				}
			}
		}

		if (e.getSource() == panelGerenciamentoEstoque.getBtCancelarProduto()) {
			removerProduto();
		}
	}

	private void removerProduto() {
		JList<BarraListaProdutos> listaProdutos = panelGerenciamentoEstoque.pegaListaProdutos();
		JList<BarraListaProdutos> listaVencimento = panelGerenciamentoEstoque.pegaListaVencimento();
		JList<BarraListaProdutos> listaAlertas = panelGerenciamentoEstoque.pegaListaAlertas();

		if (listaProdutos != null && listaProdutos.getSelectedIndex() != -1) {
			DefaultListModel<BarraListaProdutos> modelProdutos = (DefaultListModel<BarraListaProdutos>) listaProdutos
					.getModel();
			DefaultListModel<BarraListaProdutos> modelVencimento = (DefaultListModel<BarraListaProdutos>) listaVencimento
					.getModel();
			DefaultListModel<BarraListaProdutos> modelAlertas = (DefaultListModel<BarraListaProdutos>) listaAlertas
					.getModel();

			int selectedIndex = listaProdutos.getSelectedIndex();
			BarraListaProdutos barraSelecionada = modelProdutos.getElementAt(selectedIndex);
			String codigo = barraSelecionada.getCodigo();

			// Remove produto das listas de vencimento e alertas se presente
			removerProdutoDeLista(modelVencimento, codigo);
			removerProdutoDeLista(modelAlertas, codigo);

			// Remove produto da lista principal e do arquivo
			modelProdutos.remove(selectedIndex);
			removerProdutoDaLista(codigo);
			removerProduto(codigo);

			// Atualiza interface gráfica
			String categoria = (String) panelGerenciamentoEstoque.getCbxCategoriaEstoque().getSelectedItem();
			if (!buscarProdutosPorCategoria(categoria)) {
				panelGerenciamentoEstoque.getBtPesquisar().setEnabled(false);
				new Thread(() -> {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					SwingUtilities.invokeLater(() -> {
						panelGerenciamentoEstoque.getLblBackground().setIcon(new ImageIcon(
								PanelGerenciamentoEstoque.class.getResource("/Imagens/panelEstoque.png")));
						panelGerenciamentoEstoque.getLblCategoria().setVisible(false);
						panelGerenciamentoEstoque.getBtCancelarProduto().setVisible(false);
						panelGerenciamentoEstoque.getBloquearVisao().setVisible(true);
						panelGerenciamentoEstoque.modelListaProdutos.clear();
						panelGerenciamentoEstoque.modelListaVencimento.clear();
						panelGerenciamentoEstoque.modelListaAlertas.clear();
						panelGerenciamentoEstoque.escondeListas();
						panelGerenciamentoEstoque.getBtPesquisar().setEnabled(true);
					});
				}).start();
			}
		} else {
			System.out.println("Nenhum produto selecionado.");
			somErro();
		}
	}

	private void removerProdutoDeLista(DefaultListModel<BarraListaProdutos> model, String codigo) {
		for (int i = 0; i < model.getSize(); i++) {
			if (model.getElementAt(i).getCodigo().equals(codigo)) {
				model.remove(i);
				break;
			}
		}
	}

	private void removerProdutoDaLista(String codigo) {
		Iterator<Produto> iterator = App.listaProdutos.iterator();
		while (iterator.hasNext()) {
			Produto produto = iterator.next();
			if (produto.getCodProduto().equals(codigo)) {
				iterator.remove();
				break;
			}
		}
	}

	private void removerProduto(String codigo) {
		try {
			DAOProduto daoProduto = new DAOProduto();
			Produto produto = daoProduto.buscarProdutoPorCodigo(codigo);
			if (produto != null) {
				DAOProduto daop = new DAOProduto();
				daop.removerProduto(produto);
			}
		} catch (Exception e) {
			System.err.println("Erro ao remover produto: " + e.getMessage());
			somErro();
		}
	}

	// Método para buscar produtos por categoria
	private boolean buscarProdutosPorCategoria(String categoria) {
		DAOProduto daoProduto = new DAOProduto();
		try {
			List<Produto> produtos = daoProduto.listaProdutosPorCategoria(categoria);
			if (produtos != null && !produtos.isEmpty()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	// Método para buscar produtos por categoria
	private void carregaInformacoesPorCategoria(String categoria) {
		DAOProduto daoProduto = new DAOProduto();
		try {
			List<Produto> produtos = daoProduto.listaProdutosPorCategoria(categoria);
			for (Produto produto : produtos) {
				BarraListaProdutos produtoInserido = new BarraListaProdutos();
				produtoInserido.getLblCodigoProduto().setText(produto.getCodProduto());
				produtoInserido.getLblNomeProduto().setText(produto.getNomeProduto());
				produtoInserido.getLblQtdProduto().setText(String.valueOf(produto.getQuantidadeAtual()));
				produtoInserido.getLblValorProduto().setText(String.valueOf(produto.getPrecoUnidProduto()));
				panelGerenciamentoEstoque.modelListaProdutos.addElement(produtoInserido);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				if (dataFutura(produto.getDtValProduto().format(formatter)) == false) {
					panelGerenciamentoEstoque.modelListaVencimento.addElement(produtoInserido);
				}
				try {
					int qtdAtual = produto.getQuantidadeAtual();
					int qtdMinima = produto.getQtdMinEstoqueProduto() * produto.getQtdCaixaProduto();
					if (qtdAtual <= qtdMinima) {
						panelGerenciamentoEstoque.modelListaAlertas.addElement(produtoInserido);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			controle = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean dataFutura(String dataString) {
		try {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dataAtual = LocalDate.now();
			LocalDate data = LocalDate.parse(dataString, formatoData);
			// Verifica se a data fornecida é após a data atual
			return data.isAfter(dataAtual);
		} catch (DateTimeParseException e) {
			// Se houver erro de parse, a data é inválida
			return false;
		}
	}

	private void mostraAlerta(String categoria) {
		panelGerenciamentoEstoque.getLblAlerta()
				.setText("<html><body><p>A Lista de Produtos <b>" + categoria + "</b> está vazia !!</p></body></html>");
		panelGerenciamentoEstoque.getLblAlerta().setVisible(true);
		new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SwingUtilities.invokeLater(() -> {
				panelGerenciamentoEstoque.getLblAlerta().setVisible(false);
				panelGerenciamentoEstoque.repaint();
			});
		}).start();
	}

	private void somClique() {
		try {
			URL url = App.class.getResource("/Audio/cliquei.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void somErro() {
		try {
			URL url = App.class.getResource("/Audio/erro.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
