package Visao;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class PanelCadastroCliente extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JButton btCadastrar;
	private JTextField tfNomeCompleto;
	private JFormattedTextField tfCpf;
	private JFormattedTextField tfDataNasc;
	private JTextField tfEmail;
	private JTextField tfLimCredito;
	private JCheckBox cbxLimCredPadrao;
	private JButton btEditarCliente;
	private JLabel lblAlerta;

	public PanelCadastroCliente() {
		this.setBounds(10, 86, 1060, 683);
		setLayout(null);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		setLayout(null);

		add(getLblAlerta());
		add(getBtEditarCliente());
		add(getCbxLimCredPadrao());
		add(getTfLimCredito());
		add(getTfEmail());
		add(getTfDataNasc());
		add(getTfCpf());
		add(getTfNomeCompleto());
		add(getBtCadastrar());
		add(getLblBackground());
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(
					new ImageIcon(PanelCadastroCliente.class.getResource("/Imagens/BackgroundPanelCliente.png")));
			lblBackground.setBounds(0, 0, 1060, 683);
		}
		return lblBackground;
	}

	public JButton getBtCadastrar() {
		if (btCadastrar == null) {
			btCadastrar = new JButton("");
			btCadastrar.setBounds(728, 317, 270, 72);
			btCadastrar.setBorder(null);
			btCadastrar.setContentAreaFilled(false);
			btCadastrar.setFocusPainted(false);
		}
		return btCadastrar;
	}

	public JTextField getTfNomeCompleto() {
		if (tfNomeCompleto == null) {
			tfNomeCompleto = new JTextField();
			tfNomeCompleto.setFont(new Font("DialogInput", Font.BOLD, 25));
			tfNomeCompleto.setBounds(65, 157, 515, 41);
			tfNomeCompleto.setColumns(10);
		}
		return tfNomeCompleto;
	}

	public JFormattedTextField getTfCpf() {
	    if (tfCpf == null) {
	        tfCpf = new JFormattedTextField();
	        tfCpf.setBounds(65, 252, 394, 41);
	        tfCpf.setFont(new Font("DialogInput", Font.BOLD, 25));
	        tfCpf.setForeground(Color.BLACK);
	        tfCpf.setText("");
	        tfCpf.setHorizontalAlignment(SwingConstants.LEFT);
	        tfCpf.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            private void verificaTextoVazio() {
	                SwingUtilities.invokeLater(() -> {
	                    if (tfCpf.getText().length() <= 12 && tfCpf.getText().length() != 7 && tfCpf.getText().trim().compareTo("")!=0) {
	                        try {
	                            tfCpf.setFormatterFactory(new DefaultFormatterFactory(getMascaraCpf()));
	                        } catch (ParseException e1) {
	                        	tfCpf.setText("");
	                        }
	                    }
	                });
	            }

	            private MaskFormatter getMascaraCpf() throws ParseException {
	                MaskFormatter mf = new MaskFormatter("###.###.###-##");
	                mf.setPlaceholderCharacter('_');
	                return mf;
	            }
	        });
	    }
	    return tfCpf;
	}

	public JFormattedTextField getTfDataNasc() {
		if (tfDataNasc == null) {
			tfDataNasc = new JFormattedTextField();
			tfDataNasc.setBounds(65, 356, 205, 41);
			tfDataNasc.setFont(new Font("DialogInput", Font.BOLD, 25));
			tfDataNasc.setColumns(10);
	        tfDataNasc.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            private void verificaTextoVazio() {
	                SwingUtilities.invokeLater(() -> {
	                    String text = tfDataNasc.getText().trim();
	                    if (text.compareTo("") != 0 && text.length() < 10) {
	                        try {
	                            tfDataNasc.setFormatterFactory(new DefaultFormatterFactory(getMascaraDataNasc()));
	                        } catch (ParseException e1) {
	                            e1.printStackTrace();
	                        }
	                    }
	                });
	            }

	            private MaskFormatter getMascaraDataNasc() throws ParseException {
	                MaskFormatter mf = new MaskFormatter("##/##/####");
	                mf.setPlaceholderCharacter('_');
	                return mf;
	            }
	        });
		}
		return tfDataNasc;
	}

	public JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setFont(new Font("DialogInput", Font.BOLD, 25));
			tfEmail.setBounds(65, 454, 449, 41);
			tfEmail.setColumns(10);
		}
		return tfEmail;
	}

	public JTextField getTfLimCredito() {
		if (tfLimCredito == null) {
			tfLimCredito = new JTextField();
			tfLimCredito.setFont(new Font("DialogInput", Font.BOLD, 20));
			tfLimCredito.setBounds(65, 553, 145, 41);
			tfLimCredito.setColumns(10);
		}
		return tfLimCredito;
	}

	public JCheckBox getCbxLimCredPadrao() {
		if (cbxLimCredPadrao == null) {
			cbxLimCredPadrao = new JCheckBox("Padrão");
			cbxLimCredPadrao.setFont(new Font("Tahoma", Font.PLAIN, 15));
			cbxLimCredPadrao.setBounds(216, 556, 76, 29);
		}
		return cbxLimCredPadrao;
	}

	public JButton getBtEditarCliente() {
		if (btEditarCliente == null) {
			btEditarCliente = new JButton("");
			btEditarCliente
					.setIcon(new ImageIcon(PanelCadastroCliente.class.getResource("/Imagens/iconEditar (1).png")));
			btEditarCliente.setBounds(978, 24, 50, 50);
			btEditarCliente.setBorder(null);
			btEditarCliente.setContentAreaFilled(false);
			btEditarCliente.setFocusPainted(false);
		}
		return btEditarCliente;
	}

	public JLabel getLblAlerta() {
		if (lblAlerta == null) {
			lblAlerta = new JLabel("O cpf já está cadastrado!");
			lblAlerta.setVerticalAlignment(SwingConstants.TOP);
			lblAlerta.setBounds(694, 400, 323, 55);
			lblAlerta.setVisible(false);
			lblAlerta.setHorizontalAlignment(SwingConstants.CENTER);
			lblAlerta.setFont(new Font("Dialog", Font.BOLD, 12));
			lblAlerta.setForeground(new Color(255, 0, 0));
		}
		return lblAlerta;
	}
}
