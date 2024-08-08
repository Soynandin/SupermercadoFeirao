package Recursos;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import Aplicacao.App;
import Controle.ControllerTelaAbas;
import Modelo.BalancoGeral;
import Modelo.Funcionario;
import Monitoramento.DAOBalancoGeral;
import Visao.TelaAbas;

public class PopupEscolhaCaixa extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton btCaixaAberto;
	private JButton btCaixaFechado;
	private JButton btCaixaAdministrador;
	private JButton btFechar;
	private JLabel lblInformes;
	private JLabel lblBackground;

	public static Funcionario FuncAtivo;
	public static BalancoGeral BalancoAtivo;
	
	public PopupEscolhaCaixa(Funcionario FuncLogado) {
		FuncAtivo = FuncLogado;
		this.setUndecorated(true);
		this.setSize(433, 250);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		addItens();
		DAOBalancoGeral daoBalancoGeral = new DAOBalancoGeral();
		BalancoAtivo = daoBalancoGeral.obterOuCriarBalancoGeralParaHoje(FuncLogado);
		DAOBalancoGeral.getEmBalancoGeral().close();;
	}

	private void addItens() {
		getContentPane().add(getBtFechar());
		getContentPane().add(getBtCaixaFechado());
		getContentPane().add(getBtCaixaAberto());
		getContentPane().add(getBtCaixaAdministrador());
		getContentPane().add(getLblInformes());
		getContentPane().add(getLblBackground());

	}

	public JButton getBtCaixaAberto() {
		if (btCaixaAberto == null) {
			btCaixaAberto = new JButton();
			btCaixaAberto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (App.controleTela != 1 || App.controleTela == 1 && !TelaAbas.getEstd().equals("ABERTO")) {
						App.statusTela = "ABERTO";
						dispose();
						App.janela.getContentPane().removeAll();
						TelaAbas telaAbas = new TelaAbas("ABERTO");
						ControllerTelaAbas ctrlTelaAbas = new ControllerTelaAbas(telaAbas);
						App.janela.getContentPane().add(telaAbas.getBtAba1());
						App.janela.getContentPane().add(telaAbas.getBtAba2());
						App.janela.getContentPane().add(ctrlTelaAbas.getTela());
						App.janela.repaint();
						App.janela.revalidate();
						App.janela.setVisible(true);
					} else {

					}
				}
			});
			btCaixaAberto.setIcon(new ImageIcon(PopupEscolhaCaixa.class.getResource("/Imagens/btAbrir.png")));
			btCaixaAberto.setBounds(30, 157, 102, 50);
			btCaixaAberto.setBorder(null);
			btCaixaAberto.setContentAreaFilled(false);
			btCaixaAberto.setFocusPainted(false);

		}
		return btCaixaAberto;
	}

	public JButton getBtCaixaFechado() {
		if (btCaixaFechado == null) {
			btCaixaFechado = new JButton();
			btCaixaFechado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (App.controleTela != 1 || App.controleTela == 1 && !TelaAbas.getEstd().equals("FECHADO")) {
						App.statusTela = "FECHADO";
						DAOBalancoGeral daoBalancoGeral = new DAOBalancoGeral();
						BalancoGeral atualizarBalanco = daoBalancoGeral.obterOuCriarBalancoGeralParaHoje(FuncAtivo);
						ZoneId fusoHorarioBahia = ZoneId.of("America/Bahia");
				        LocalDateTime agora = LocalDateTime.now(fusoHorarioBahia);
						atualizarBalanco.setDataHoraCaixaFechado(agora);
						try {
							DAOBalancoGeral daoBG = new DAOBalancoGeral();
							daoBG.atualizarBalancoGeral(atualizarBalanco);
						} catch (Exception ex) {
						    System.err.println("Erro ao atualizar Balanco Geral:");
						    ex.printStackTrace();
						}
						dispose();
						App.janela.getContentPane().removeAll();
						TelaAbas telaAbas = new TelaAbas("FECHADO");
						ControllerTelaAbas ctrlTelaAbas = new ControllerTelaAbas(telaAbas);
						App.janela.getContentPane().add(telaAbas.getBtAba1());
						App.janela.getContentPane().add(telaAbas.getBtAba2());
						App.janela.getContentPane().add(ctrlTelaAbas.getTela());
						App.janela.repaint();
						App.janela.revalidate();
						App.janela.setVisible(true);
					} else {

					}
				}
			});
			btCaixaFechado.setIcon(new ImageIcon(PopupEscolhaCaixa.class.getResource("/Imagens/btFechar.png")));
			btCaixaFechado.setBounds(168, 157, 102, 50);
			btCaixaFechado.setBorder(null);
			btCaixaFechado.setContentAreaFilled(false);
			btCaixaFechado.setFocusPainted(false);
		}
		return btCaixaFechado;
	}

	public JButton getBtCaixaAdministrador() {
		if (btCaixaAdministrador == null) {
			btCaixaAdministrador = new JButton();
			btCaixaAdministrador.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ((App.controleTela != 1 || App.controleTela == 1 && !TelaAbas.getEstd().equals("ADMINISTRADOR")) && FuncAtivo.getCategoriaFunc()==1 ) {
						App.statusTela = "ADMINISTRADOR";
						dispose();
						App.janela.getContentPane().removeAll();
						TelaAbas telaAbas = new TelaAbas("ADMINISTRADOR");
						ControllerTelaAbas ctrlTelaAbas = new ControllerTelaAbas(telaAbas);
						App.janela.getContentPane().add(telaAbas.getBtAba1());
						App.janela.getContentPane().add(telaAbas.getBtAba2());
						App.janela.getContentPane().add(telaAbas.getBtAba3());
						App.janela.getContentPane().add(telaAbas.getBtAba4());
						App.janela.getContentPane().add(telaAbas.getBtAba5());
						App.janela.getContentPane().add(ctrlTelaAbas.getTela());
						App.janela.repaint();
						App.janela.revalidate();
						App.janela.setVisible(true);
					} else {

					}
				}
			});
			btCaixaAdministrador.setIcon(new ImageIcon(PopupEscolhaCaixa.class.getResource("/Imagens/btModoAdm.png")));
			btCaixaAdministrador.setBounds(303, 157, 102, 50);
			btCaixaAdministrador.setBorder(null);
			btCaixaAdministrador.setContentAreaFilled(false);
			btCaixaAdministrador.setFocusPainted(false);
		}
		return btCaixaAdministrador;
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

	public JLabel getLblInformes() {
		if (lblInformes == null) {
			lblInformes = new JLabel();
			lblInformes.setText("Informe em qual estado você quer que o Caixa abra:");
			lblInformes.setFont(new Font("Dialog", Font.BOLD, 15));
			lblInformes.setBounds(20, 64, 403, 81);
		}
		return lblInformes;
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(PopupEscolhaCaixa.class.getResource("/Imagens/Estado do Caixa.png")));
			lblBackground.setBounds(0, 0, 433, 250);
		}
		return lblBackground;
	}
}
