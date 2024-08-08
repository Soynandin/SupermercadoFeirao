package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Aplicacao.App;
import Modelo.Venda;
import Recursos.BarraListaProdutos;
import Visao.PanelRelatorioCaixaDiario;

public class ControllerPanelRelatorio implements ActionListener {
	
	PanelRelatorioCaixaDiario panelRelatorioCaixaDiario;
	private int indiceAtual=0;
	
	public ControllerPanelRelatorio(PanelRelatorioCaixaDiario panelRelatorioCaixaDiario) {
		this.panelRelatorioCaixaDiario = panelRelatorioCaixaDiario;
		addEventos();
		App.atualizaListaVendas();
	}

	private void addEventos() {
		panelRelatorioCaixaDiario.getBtVoltarTudo().addActionListener(this);
		panelRelatorioCaixaDiario.getBtVoltarUm().addActionListener(this);
		panelRelatorioCaixaDiario.getBtPassarUm().addActionListener(this);
		panelRelatorioCaixaDiario.getBtPassarTudo().addActionListener(this);
		panelRelatorioCaixaDiario.getBtAtualizar().addActionListener(this);
	}

	public PanelRelatorioCaixaDiario getTela() {
		return panelRelatorioCaixaDiario;
	}

	public void actionPerformed(ActionEvent e) {
		somBotao();
		if (e.getSource() == panelRelatorioCaixaDiario.getBtVoltarTudo()) {
			atualizarDadosRelatorio("PRIMEIRO");
		}
		if (e.getSource() == panelRelatorioCaixaDiario.getBtVoltarUm()) {
			atualizarDadosRelatorio("-1");
		}
		if (e.getSource() == panelRelatorioCaixaDiario.getBtPassarUm()) {
			atualizarDadosRelatorio("+1");
		}
		if (e.getSource() == panelRelatorioCaixaDiario.getBtPassarTudo()) {
			atualizarDadosRelatorio("ULTIMO");
		}
		if(e.getSource() == panelRelatorioCaixaDiario.getBtAtualizar()) {
			atualizarDadosRelatorio("PRIMEIRO");
		}
	}
	
	private void atualizarDadosRelatorio(String posicao) {
		App.atualizaListaVendas();
	    if (!App.listaVendas.isEmpty()) {
	        if (posicao.equals("PRIMEIRO")) {
	            indiceAtual = 0;
	        } else if (posicao.equals("-1") && indiceAtual > 0) {
	            indiceAtual = Math.max(0, indiceAtual - 1);
	        } else if (posicao.equals("+1") && indiceAtual <App.listaVendas.size()) {
	            indiceAtual = Math.min(App.listaVendas.size() - 1, indiceAtual + 1);
	        } else { // Último
	            indiceAtual = App.listaVendas.size() - 1;
	        }
	        	Venda venda = App.listaVendas.get(indiceAtual);
		        panelRelatorioCaixaDiario.getLblCodVenda().setText("Codigo: " + "VENDA"+String.valueOf(indiceAtual));
		        panelRelatorioCaixaDiario.getLblDataHora().setText("Data/Hora: " + venda.getDataHoraVendaFormatada());
		        panelRelatorioCaixaDiario.getLblDesconto().setText("Desconto: " + venda.getDescontoVenda());
		        panelRelatorioCaixaDiario.getLblFormaPagamento().setText("Pagamento: " +venda.getFormaPagVenda());
		        panelRelatorioCaixaDiario.getLblImposto().setText("Imposto: " + venda.getImposto());
		        panelRelatorioCaixaDiario.getLblStatusVenda().setText("Status: " + venda.getStatusVenda());
		        panelRelatorioCaixaDiario.getLblSubTotal().setText("Subtotal: R$" + venda.getSubTotalVenda());
		        panelRelatorioCaixaDiario.getLblValorTotal().setText("Total: R$" + venda.getValorTotalVenda());
		        panelRelatorioCaixaDiario.getModel().clear();
		        String[] dados = App.listaVendas.get(indiceAtual).getListaProdutos().split(";");
		        for(int i=0; i<dados.length; i++) {
		        	String dado1Parte1 = dados[0].trim();
		        	String[] partes1 = dado1Parte1.split("\\s{2,}");
		        	BarraListaProdutos produtoInserido = new BarraListaProdutos();
	    			produtoInserido.getLblCodigoProduto().setText(partes1[0].trim());
	    			produtoInserido.getLblNomeProduto().setText(partes1[1].trim());
	    			produtoInserido.getLblQtdProduto().setText(partes1[2].trim());
	    			produtoInserido.getLblValorProduto().setText(partes1[3].trim());
	    			panelRelatorioCaixaDiario.getModel().addElement(produtoInserido);
		        }
		        panelRelatorioCaixaDiario.getJList();
	    } else {
	    		panelRelatorioCaixaDiario.getLblCodVenda().setText("Codigo: " + "XXX");
	 			panelRelatorioCaixaDiario.getLblDataHora().setText("<html><p><strong>Não há vendas na lista!</strong> Verifique as compras no Arquivo TXT.</p></html>");
	 			panelRelatorioCaixaDiario.getLblDesconto().setText("");
	 			panelRelatorioCaixaDiario.getLblFormaPagamento().setText("");
	 			panelRelatorioCaixaDiario.getLblImposto().setText("");
	 			panelRelatorioCaixaDiario.getLblStatusVenda().setText("");
	 			panelRelatorioCaixaDiario.getLblSubTotal().setText("");
	 			panelRelatorioCaixaDiario.getLblValorTotal().setText("");
	 			panelRelatorioCaixaDiario.getModel().clear();
	    }
	}
	
	private void somBotao() {
		try {
			URL url = App.class.getResource("/Audio/bt.wav");
	        AudioInputStream stream = AudioSystem.getAudioInputStream(url);
	        Clip clip = AudioSystem.getClip();
	        clip.open(stream);
	        clip.start();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}
