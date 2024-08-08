package Aplicacao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Controle.ControllerFrame;
import Controle.ControllerTelaInicial;
import Modelo.Cliente;
import Modelo.Funcionario;
import Modelo.Produto;
import Modelo.Venda;
import Monitoramento.DAOCliente;
import Monitoramento.DAOFuncionario;
import Monitoramento.DAOProduto;
import Monitoramento.DAOVenda;
import Recursos.PopupEscolhaCaixa;
import Visao.Frame;
import Visao.TelaInicial;

public class App {
	public static Frame janela;
	public static EntityManagerFactory emf;

	public static List<Funcionario> listaFuncionarios;
	public static List<Cliente> listaClientes;
	public static List<Produto> listaProdutos;
	public static List<Venda> listaVendas;

	public static int controleTela;
	public static int controleBalancoDiario;

	public static String statusTela = "";
	public static String dataHoraAbertura = ""; 
	public static String dataHoraFechamento = "";

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			emf = getEMF();

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					inicializaListaFuncionarios();
					inicializaListaClientes();
					inicializaListaProdutos();
					inicializaListaVendas();
				}
			});

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					janela = new Frame();
					@SuppressWarnings("unused")
					ControllerFrame ctrlJanela = new ControllerFrame(janela);
					inicializaTela();
					janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					janela.setLocationRelativeTo(null);
					janela.setVisible(true);
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static EntityManagerFactory getEMF() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("supermercadoFeirao");
		}
		return emf;
	}

	private static void inicializaTela() {
		controleTela = 0;
		controleBalancoDiario = 0;
		TelaInicial telaInicial = new TelaInicial();
		ControllerTelaInicial ctrlTelaInicial = new ControllerTelaInicial(telaInicial);
		janela.getContentPane().add(ctrlTelaInicial.getTela());
		janela.getContentPane().add(Frame.getBtFecharJanela());
		janela.repaint();
		janela.revalidate();
		janela.setVisible(true);
	}

	private static void inicializaListaFuncionarios() {
		// listaFuncionarios = new ArrayList<>();
		DAOFuncionario daoFuncionario = new DAOFuncionario();
		listaFuncionarios = daoFuncionario.ListaFuncionarios();
	}

	private static void inicializaListaClientes() {
		listaClientes = new ArrayList<>();
		DAOCliente daoCliente = new DAOCliente();
		listaClientes = daoCliente.listaClientes();
	}

	private static void inicializaListaProdutos() {
		listaProdutos = new ArrayList<>();
		DAOProduto daoProduto = new DAOProduto();
		listaProdutos = daoProduto.listaProdutos();
	}

	private static void inicializaListaVendas() {
		listaVendas = new ArrayList<>();
	}

	public static void atualizaListaVendas() {
		DAOVenda daoVenda = new DAOVenda();
		listaVendas.clear();
		listaVendas.addAll(daoVenda.listarVendasPorBalancoGeral(PopupEscolhaCaixa.BalancoAtivo));
	}

	public static void atualizaListaProdutos() {
		DAOProduto daoProduto = new DAOProduto();
		listaProdutos.clear();
		listaProdutos.addAll(daoProduto.listaProdutos());
	}

}
