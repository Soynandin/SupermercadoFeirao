package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

import Aplicacao.App;
import Modelo.Produto;
import Monitoramento.DAOProduto;
import Visao.PanelEditarProduto;

public class ControllerPanelEditarProduto implements ActionListener {
	PanelEditarProduto panelEditarProduto;
	Produto produtoBuscado;

	public ControllerPanelEditarProduto(PanelEditarProduto panelEditarProduto, Produto produto) {
		this.panelEditarProduto = panelEditarProduto;
		this.produtoBuscado = produto;
		inicializaDados();
		addEventos();
	}

	private void addEventos() {
		panelEditarProduto.getBtSalvarEdicao().addActionListener(this);
	}

	public PanelEditarProduto getTela() {
		return panelEditarProduto;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == panelEditarProduto.getBtSalvarEdicao()) {
			salvaEdicao();
		}
	}

	private void inicializaDados() {
		SwingUtilities.invokeLater(() -> {
			panelEditarProduto.getTxtNomeProduto().setText(produtoBuscado.getNomeProduto());
			panelEditarProduto.getTxtQtdEstoque().setText(String.valueOf(produtoBuscado.getQtdEstoqueProduto()));
			panelEditarProduto.getTxtQtdMinimoEstoque().setText(String.valueOf(produtoBuscado.getQtdMinEstoqueProduto()));
			double precoVenda = produtoBuscado.getPrecoUnidProduto();
            String precoFormatado = String.format("%.2f", precoVenda);
			panelEditarProduto.getTxtPrecoVenda().setText(precoFormatado);
			panelEditarProduto.getTxtCategoria().setText(produtoBuscado.getCategoriaProduto());
			panelEditarProduto.repaint();
		});
	}

	private void salvaEdicao() {
	    if (verificarCamposVazios()) { // Verifica se há campos vazios
	        somErro();
	    } else {
	        if (verificarAlteracao()) { // Verifica se houve alterações
	            somCadastrar(); // Toca som de cadastro

	            // Obtém instância do DAOProduto
	            DAOProduto daoProduto = new DAOProduto();

	            // Cria uma cópia do produto buscado
	            Produto produtoAlterado = produtoBuscado;

	            // Atualiza os atributos do produto alterado com os valores dos campos editados
	            produtoAlterado.setNomeProduto(panelEditarProduto.getTxtNomeProduto().getText());
	            produtoAlterado.setQtdEstoqueProduto(Integer.parseInt(panelEditarProduto.getTxtQtdEstoque().getText()));
	            produtoAlterado.setQtdMinEstoqueProduto(Integer.parseInt(panelEditarProduto.getTxtQtdMinimoEstoque().getText()));
	            produtoAlterado.setPrecoUnidProduto(Double.parseDouble(formatarNumero(panelEditarProduto.getTxtPrecoVenda().getText().trim())));
	            produtoAlterado.setCategoriaProduto(panelEditarProduto.getTxtCategoria().getText());
	            produtoAlterado.setQuantidadeAtual(produtoBuscado.getQtdEstoqueProduto() * produtoBuscado.getQtdCaixaProduto());

	            // Atualiza o produto no banco de dados
	            daoProduto.atualizarProduto(produtoAlterado);

	            // Toca som de cadastro novamente
	            somCadastrar();

	            // Atualiza a lista de produtos na interface
	            App.atualizaListaProdutos();
	        } else {
	            // Se não houver alteração, apenas toca um som de clique
	            somClique();
	        }
	    }
	}
	public static String formatarNumero(String numero) {
		// Remove os pontos
		String semPontos = numero.replace(".", "");
		// Substitui as vírgulas por pontos
		String numeroFormatado = semPontos.replace(",", ".");
		return numeroFormatado;
	}

	private boolean verificarAlteracao() {
		if (panelEditarProduto.getTxtNomeProduto().getText().equals(produtoBuscado.getNomeProduto())
				&& panelEditarProduto.getTxtQtdEstoque().getText()
						.equals(String.valueOf(produtoBuscado.getQtdEstoqueProduto()))
				&& panelEditarProduto.getTxtQtdMinimoEstoque().getText()
						.equals(String.valueOf(produtoBuscado.getQtdMinEstoqueProduto()))
				&& panelEditarProduto.getTxtPrecoVenda().getText()
						.equals(String.valueOf(produtoBuscado.getPrecoUnidProduto()))
				&& panelEditarProduto.getTxtCategoria().getText().equals(produtoBuscado.getCategoriaProduto())) {
			return false;
		}
		return true;
	}

	private boolean verificarCamposVazios() {
		if (panelEditarProduto.getTxtNomeProduto().getText().trim().isEmpty()
				|| panelEditarProduto.getTxtQtdEstoque().getText().trim().isEmpty()
				|| panelEditarProduto.getTxtQtdMinimoEstoque().getText().trim().isEmpty()
				|| panelEditarProduto.getTxtPrecoVenda().getText().trim().isEmpty()
				|| panelEditarProduto.getTxtCategoria().getText().trim().isEmpty()) {
			return true;
		}
		return false;
	}

	private void somCadastrar() {
		try {
			URL url = getClass().getResource("/Audio/cadastrar.wav");
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
			URL url = getClass().getResource("/Audio/erro.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void somClique() {
		try {
			URL url = getClass().getResource("/Audio/cliquei.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
