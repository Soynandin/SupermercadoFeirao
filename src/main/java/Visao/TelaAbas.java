package Visao;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import Aplicacao.App;
import Controle.ControllerTelaCadastroProdutos;
import Controle.ControllerTelaCadastroUsuario;
import Controle.ControllerTelaGerenciamentoEstoque;
import Controle.ControllerTelaRelatorio;
import Controle.ControllerTelaVenda;

public class TelaAbas extends JTabbedPane{

	private static final long serialVersionUID = 1L;

	private TelaVenda telaVenda;
	private TelaRelatorio telaRelatorio;
	private TelaCadastroProdutos telaCadastroProdutos;
	private TelaGerenciamentoEstoque telaGerenciamentoEstoque;
	private TelaCadastroUsuario telaCadastroUsuario;
	private JButton btAba1;
	private JButton btAba2;
	private JButton btAba3;
	private JButton btAba4;
	private JButton btAba5;
	
	private static String estd;
	public TelaAbas(String estado) {
		App.controleTela=1;
		this.setLayout(new BorderLayout());
		this.setBounds(0, 0, 1080, 780);
		estd=estado;
		addAbas(estd);
		App.janela.getContentPane().add(Frame.getBtFecharJanela());
		this.setVisible(true);
	}

	private void addAbas(String e) {
		addTab("Vendas", getTelaVenda());
		addTab("Relatorios", getTelaRelatorio());
		addTab("GerenciamentoProdutos", getTelaCadastroProdutos());
		addTab("GerenciamentoEstoque", getTelaGerenciamentoEstoque());
		addTab("Cadastros", getTelaCadastro());
		if(e.equals("ABERTO") || e.equals("FECHADO")) {
			getBtAba3().setEnabled(false);
			getBtAba4().setEnabled(false);
			getBtAba5().setEnabled(false);
		} else {
			getBtAba3().setEnabled(true);
			getBtAba4().setEnabled(true);
			getBtAba5().setEnabled(true);
		}
	}
	
	public TelaVenda getTelaVenda() {
		if(telaVenda==null){
			telaVenda = new TelaVenda(estd);
			if(estd.equals("ADMINISTRADOR")) {
				telaVenda.getLblEstadoCaixa(estd).setText(estd);
				telaVenda.getLblEstadoCaixa(estd).setBounds(604, 11, 300, 67);
			}
			
			@SuppressWarnings("unused")
			ControllerTelaVenda ctrlTelaVenda = new ControllerTelaVenda(telaVenda);
			
		}
		return telaVenda;
	}
	
	public TelaRelatorio getTelaRelatorio() {
		if(telaRelatorio==null){
			telaRelatorio = new TelaRelatorio(estd);
			@SuppressWarnings("unused")
			ControllerTelaRelatorio ctrlTelaRelatorio = new ControllerTelaRelatorio(telaRelatorio);
		}
		return telaRelatorio;
	}
	
	public TelaCadastroProdutos getTelaCadastroProdutos() {
		if(telaCadastroProdutos==null){
			telaCadastroProdutos = new TelaCadastroProdutos();
			@SuppressWarnings("unused")
			ControllerTelaCadastroProdutos ctrlTelaCadastroProdutos = new ControllerTelaCadastroProdutos(telaCadastroProdutos, 1);
		}
		return telaCadastroProdutos;
	}
	
	public TelaGerenciamentoEstoque getTelaGerenciamentoEstoque() {
		if(telaGerenciamentoEstoque==null){
			telaGerenciamentoEstoque = new TelaGerenciamentoEstoque();
			@SuppressWarnings("unused")
			ControllerTelaGerenciamentoEstoque ctrlTelaGerenciamentoEstoque = new ControllerTelaGerenciamentoEstoque(telaGerenciamentoEstoque);
		}
		return telaGerenciamentoEstoque;
	}
	
	public TelaCadastroUsuario getTelaCadastro() {
		if(telaCadastroUsuario==null){
			telaCadastroUsuario = new TelaCadastroUsuario();
			@SuppressWarnings("unused")
			ControllerTelaCadastroUsuario ctrlTelaCadastroUsuario = new ControllerTelaCadastroUsuario(telaCadastroUsuario);
		}
		return telaCadastroUsuario;
	}
	
	

	public JButton getBtAba1() {
		if(btAba1==null) {
			btAba1 =new JButton();
			btAba1 = new JButton();
			btAba1.setBounds(10, 11, 50, 50);
			btAba1.setBorder(null);
			btAba1.setContentAreaFilled(false);
			btAba1.setFocusPainted(false);
		}
		return btAba1;
	}

	public JButton getBtAba2() {
		if(btAba2==null) {
			btAba2 =new JButton();
			btAba2 = new JButton();
			btAba2.setBounds(129, 11, 50, 50);
			btAba2.setBorder(null);
			btAba2.setContentAreaFilled(false);
			btAba2.setFocusPainted(false);
		}
		return btAba2;
	}
	
	public JButton getBtAba3() {
		if(btAba3==null) {
			btAba3 =new JButton();
			btAba3 = new JButton();
			btAba3.setBounds(250, 11, 50, 50);
			btAba3.setBorder(null);
			btAba3.setContentAreaFilled(false);
			btAba3.setFocusPainted(false);
		}
		return btAba3;
	}
	
	public JButton getBtAba4() {
		if(btAba4==null) {
			btAba4 =new JButton();
			btAba4 = new JButton();
			btAba4.setBounds(370, 11, 50, 50);
			btAba4.setBorder(null);
			btAba4.setContentAreaFilled(false);
			btAba4.setFocusPainted(false);
		}
		return btAba4;
	}
	
	public JButton getBtAba5() {
		if(btAba5==null) {
			btAba5 =new JButton();
			btAba5 = new JButton();
			btAba5.setBounds(485, 11, 50, 50);
			btAba5.setBorder(null);
			btAba5.setContentAreaFilled(false);
			btAba5.setFocusPainted(false);
		}
		return btAba5;
	}
	
	public static String getEstd() {
		return estd;
	}

}
