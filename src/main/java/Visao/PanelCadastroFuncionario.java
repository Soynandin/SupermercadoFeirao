package Visao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class PanelCadastroFuncionario extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblBackground;
	private JButton btCadastrar;
	private JTextField tfNomeCompleto;
	private JFormattedTextField tfCpf;
	private JFormattedTextField tfDataNasc;
	private JTextField tfEmail;
	private JComboBox<String> cbxCategoriaFunc;
	private JButton btEditarFuncionario;
	private JLabel lblAlerta;
	
	
	public PanelCadastroFuncionario() {
		this.setBounds(10, 86, 1060, 683);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		setLayout(null);
		setLayout(null);
		add(getLblAlerta());
		add(getBtEditarFuncionario());
		add(getCbxCategoriaFunc());
		add(getTfEmail());
		add(getTfDataNasc());
		add(getTfCpf());
		add(getTfNomeCompleto());
		add(getBtCadastrar());
		add(getLblBackground());
	}

	public JLabel getLblBackground() {
		if(lblBackground==null) {
			lblBackground = new JLabel();
			lblBackground.setBounds(10, 0, 1060, 683);
			lblBackground.setAlignmentY(Component.BOTTOM_ALIGNMENT);
			lblBackground.setIcon(new ImageIcon(PanelCadastroFuncionario.class.getResource("/Imagens/BackgroundPanelFuncionario.png")));
		}
		return lblBackground;
	}
	public JButton getBtCadastrar() {
		if (btCadastrar == null) {
			btCadastrar = new JButton("");
			btCadastrar.setBounds(738, 321, 268, 68);
			btCadastrar.setBorder(null);
			btCadastrar.setContentAreaFilled(false);
			btCadastrar.setFocusPainted(false);
		}
		return btCadastrar;
	}
	public JTextField getTfNomeCompleto() {
		if (tfNomeCompleto == null) {
			tfNomeCompleto = new JTextField();
			tfNomeCompleto.setBounds(65, 155, 535, 41);
			tfNomeCompleto.setFont(new Font("DialogInput", Font.BOLD, 25));
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

	        // Adiciona um DocumentListener ao documento do TextField
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
			
			// Adiciona um DocumentListener ao documento do TextField
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
			tfEmail.setBounds(65, 455, 462, 41);
			tfEmail.setFont(new Font("DialogInput", Font.BOLD, 25));
			tfEmail.setColumns(10);
		}
		return tfEmail;
	}
	public JComboBox<String> getCbxCategoriaFunc() {
		if (cbxCategoriaFunc == null) {
			cbxCategoriaFunc = new JComboBox<String>();
			cbxCategoriaFunc.setFont(new Font("Dialog", Font.PLAIN, 18));
			cbxCategoriaFunc.setBounds(65, 553, 162, 41);
			cbxCategoriaFunc.addItem("Atendente");
			cbxCategoriaFunc.addItem("Gerente");
		}
		return cbxCategoriaFunc;
	}
	public JButton getBtEditarFuncionario() {
		if (btEditarFuncionario == null) {
			btEditarFuncionario = new JButton("");
			btEditarFuncionario.setBounds(983, 23, 50, 50);
			btEditarFuncionario.setIcon(new ImageIcon(PanelCadastroFuncionario.class.getResource("/Imagens/iconEditar (1).png")));
			btEditarFuncionario.setBorder(null);
			btEditarFuncionario.setContentAreaFilled(false);
			btEditarFuncionario.setFocusPainted(false);
		}
		return btEditarFuncionario;
	}
	public JLabel getLblAlerta() {
		if (lblAlerta == null) {
			lblAlerta = new JLabel("O cpf já está cadastrado!");
			lblAlerta.setVerticalAlignment(SwingConstants.TOP);
			lblAlerta.setLocation(733, 400);
			lblAlerta.setSize(300, 50);
			lblAlerta.setVisible(false);
			lblAlerta.setHorizontalAlignment(SwingConstants.CENTER);
			lblAlerta.setFont(new Font("Dialog", Font.BOLD, 11));
			lblAlerta.setForeground(new Color(255, 0, 0));
		}
		return lblAlerta;
	}
}
