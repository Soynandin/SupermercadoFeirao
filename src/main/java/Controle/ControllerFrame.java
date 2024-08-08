package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Aplicacao.App;
import Monitoramento.DAOCliente;
import Monitoramento.DAOFuncionario;
import Monitoramento.DAOProduto;
import Recursos.PopupFechar;
import Visao.Frame;

public class ControllerFrame implements ActionListener{

	Frame janela;
	
	public ControllerFrame(Frame Janela) {
		janela = Janela;
		janela.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				fecharSistema();
			}
		});
		addEventos();
	}

	private void addEventos() {
		Frame.getBtFecharJanela().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Frame.getBtFecharJanela()) {
			PopupFechar popupFechar = new PopupFechar();
			popupFechar.setLocationRelativeTo(janela);
		}
	}
	
	public static void fecharSistema() {
		DAOFuncionario.fecharEmfFuncionario();
		DAOCliente.fecharEmfCliente();
		DAOProduto.fecharEmfProduto();
		App.emf.close();
	}
	
}
