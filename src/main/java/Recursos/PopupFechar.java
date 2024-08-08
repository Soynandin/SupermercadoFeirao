package Recursos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import Aplicacao.App;
import Controle.ControllerTelaLogin;
import Modelo.BalancoGeral;
import Monitoramento.DAOBalancoFinanceiro;
import Monitoramento.DAOBalancoGeral;
import Monitoramento.DAOCliente;
import Monitoramento.DAOFuncionario;
import Monitoramento.DAOProduto;
import Monitoramento.DAOVenda;
import Visao.Frame;
import Visao.TelaLogin;

public class PopupFechar extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton btSairConta;
	private JButton btSairAplicativo;
	private JButton btFechar;
	private JLabel lblBackground;

	public PopupFechar() {
		getContentPane().setLayout(null);
		this.setUndecorated(true);
		this.setSize(433, 250);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		addItens();
	}

	private void addItens() {
		getContentPane().add(getBtSairConta());
		getContentPane().add(getBtSairAplicativo());
		getContentPane().add(getBtFechar());
		getContentPane().add(getLblBackground());
	}

	public JButton getBtSairConta() {
		if (btSairConta == null) {
			btSairConta = new JButton();
			btSairConta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (App.controleTela != 0) {
						App.controleTela = 0;
						dispose();
						App.janela.getContentPane().removeAll();
						TelaLogin telaLogin = new TelaLogin();
						ControllerTelaLogin ctrlTelaLogin = new ControllerTelaLogin(telaLogin);
						App.janela.getContentPane().add(ctrlTelaLogin.getTela());
						App.janela.getContentPane().add(Frame.getBtFecharJanela());
						App.janela.repaint();
						App.janela.revalidate();
						App.janela.setVisible(true);
					}
				}
			});
			btSairConta.setBounds(33, 110, 159, 81);
			btSairConta.setBorder(null);
			btSairConta.setContentAreaFilled(false);
			btSairConta.setFocusPainted(false);
		}
		return btSairConta;
	}

	public JButton getBtSairAplicativo() {
		if (btSairAplicativo == null) {
			btSairAplicativo = new JButton();
			btSairAplicativo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(App.controleBalancoDiario==1) {
						DAOBalancoGeral daoBalancoGeral = new DAOBalancoGeral();
						BalancoGeral atualizarBalanco = daoBalancoGeral.obterOuCriarBalancoGeralParaHoje(PopupEscolhaCaixa.FuncAtivo);
						ZoneId fusoHorarioBahia = ZoneId.of("America/Bahia");
				        LocalDateTime agora = LocalDateTime.now(fusoHorarioBahia);
						atualizarBalanco.setDataHoraCaixaFechado(agora);
						try {
						    daoBalancoGeral.atualizarBalancoGeral(atualizarBalanco);
						} catch (Exception ex) {
						    System.err.println("Erro ao atualizar Balanco Geral:");
						    ex.printStackTrace();
						}
					}
					dispose();
					App.janela.removeAll();
					DAOFuncionario.fecharEmfFuncionario();
					DAOCliente.fecharEmfCliente();
					DAOProduto.fecharEmfProduto();
					DAOVenda.fecharEmfVenda();
					DAOBalancoGeral.fecharEmfBalancoGeral();
					DAOBalancoFinanceiro.fecharEmfBalancoFinanceiro();
					App.getEMF().close();
					System.exit(0);
				}
			});
			btSairAplicativo.setBounds(238, 110, 159, 81);
			btSairAplicativo.setBorder(null);
			btSairAplicativo.setContentAreaFilled(false);
			btSairAplicativo.setFocusPainted(false);
		}
		return btSairAplicativo;
	}

	public JButton getBtFechar() {
		if (btFechar == null) {
			btFechar = new JButton();
			btFechar.setBounds(395, 11, 30, 30);
			btFechar.setBorder(null);
			btFechar.setContentAreaFilled(false);
			btFechar.setFocusPainted(false);
			btFechar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == btFechar) {
						dispose();
					}
				}
			});
		}
		return btFechar;
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(PopupEscolhaCaixa.class.getResource("/Imagens/popupFechar.png")));
			lblBackground.setBounds(0, 0, 433, 250);
		}
		return lblBackground;
	}
	
}
