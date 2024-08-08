package Controle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Aplicacao.App;
import Modelo.BalancoGeral;
import Modelo.Cliente;
import Modelo.Produto;
import Modelo.Venda;
import Monitoramento.DAOBalancoGeral;
import Monitoramento.DAOCliente;
import Monitoramento.DAOProduto;
import Monitoramento.DAOVenda;
import Recursos.BarraListaProdutos;
import Recursos.PopupEscolhaCaixa;
import Visao.TelaVenda;

public class ControllerTelaVenda implements ActionListener {

	DAOBalancoGeral daoBalanco;
	private TelaVenda telaVenda;
	FocusListener adaptUsuario;
	DocumentListener documentUsuario;

	public ControllerTelaVenda(TelaVenda TelaVenda) {
		this.telaVenda = TelaVenda;
		daoBalanco = new DAOBalancoGeral();
		addEventos();
	}

	private void addEventos() {
		telaVenda.getBtFinalizarVenda().addActionListener(this);
		telaVenda.getBtFecharCaixa().addActionListener(this);
		telaVenda.getBtInsereProduto().addActionListener(this);
		telaVenda.getBtBuscaProduto().addActionListener(this);
		telaVenda.getBtCancelarProduto().addActionListener(this);
		telaVenda.getCbxFormaPagamento().addActionListener(this);
		telaVenda.getCbxParcelamento().addActionListener(this);
		telaVenda.getTxtCpfCliente().addFocusListener(addAcaoTfCpf());
		telaVenda.getTxtCpfCliente().getDocument().addDocumentListener(addDocumentTfCpf());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaVenda.getBtFinalizarVenda()) {
			if (!telaVenda.getEstado().equals("FECHADO") && !telaVenda.model.isEmpty()) {
				if (telaVenda.getTxtCpfCliente().getText().isEmpty()) {
					if (telaVenda.getCbxFormaPagamento().getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(telaVenda, "Selecione a forma de pagamento!");
					} else if (telaVenda.getCbxFormaPagamento().getSelectedIndex() == 5) {
						JOptionPane.showMessageDialog(telaVenda, "Não é possível comprar fiado!");
					} else {
						selecionarFormaPagamento();
						salvarDetalhesCompra();
					}
				} else {
					if (telaVenda.getCbxFormaPagamento().getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(telaVenda, "Selecione a forma de pagamento!");
					} else if (telaVenda.getCbxFormaPagamento().getSelectedIndex() == 5) {
						if (buscaCliente()) {
							selecionarFormaPagamento();
							salvarDetalhesCompra();
						} else {
							JOptionPane.showMessageDialog(telaVenda,
									"Não há quantidade suficiente de produtos para a venda!");
						}
					} else {
						selecionarFormaPagamento();
						salvarDetalhesCompra();
					}
				}
			}
		}
		if (e.getSource() == telaVenda.getBtFecharCaixa()) {
			PopupEscolhaCaixa escolhaCaixa = new PopupEscolhaCaixa(PopupEscolhaCaixa.FuncAtivo);
			escolhaCaixa.setLocationRelativeTo(telaVenda);
		}
		if (e.getSource() == telaVenda.getBtInsereProduto()) {
			somClique();
			inserirProduto();
		}
		if (e.getSource() == telaVenda.getBtBuscaProduto()) {
			somClique();
			buscarProduto();
		}
		if (e.getSource() == telaVenda.getBtCancelarProduto()) {
			somClique();
			removerItemSelecionado();
		}
		if (e.getSource() == telaVenda.getCbxFormaPagamento()) {
			selecionarFormaPagamento();
		}
		if (e.getSource() == telaVenda.getCbxParcelamento()) {
			if (telaVenda.getCbxParcelamento().isEnabled() == true) {
				selecionarParcelamento(Double.parseDouble(telaVenda.getLblSubtotalProduto().getText()));
			}
		}
	}

	private void salvarDetalhesCompra() {
	    try {
	    	DAOVenda daoVenda = new DAOVenda();
	        Venda venda = new Venda();
	        LocalDateTime agora = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	        String dataHoraString = agora.format(formatter);
	        DefaultListModel<BarraListaProdutos> listaProdutos = telaVenda.model;

	        String listaProdutosVenda = "";
	        for (int i = 0; i < listaProdutos.getSize(); i++) {
	            BarraListaProdutos produto = listaProdutos.getElementAt(i);
	            
	            String detalhesProduto = String.format("    %s  %s  %s  %s", 
	                produto.getLblCodigoProduto().getText(),
	                produto.getLblNomeProduto().getText(), 
	                produto.getLblQtdProduto().getText(),
	                produto.getLblValorProduto().getText());

	            listaProdutosVenda+=detalhesProduto+";";
	            atualizarProdutoAoVender(Integer.parseInt(produto.getLblQtdProduto().getText().replace("Un.", "")), produto.getLblCodigoProduto().getText());
	        }
	        venda.setListaProdutos(listaProdutosVenda);
	        venda.setDataHoraVendaFormatada(dataHoraString);
	        venda.setFormaPagVenda((String) telaVenda.getCbxFormaPagamento().getSelectedItem());
	        venda.setSubTotalVenda(telaVenda.getLblSubtotalProduto().getText());
	        venda.setValorTotalVenda(telaVenda.getLblValorTotal().getText());
	        
	        // Usar cliente padrão se não encontrado
	        if (telaVenda.getTxtCpfCliente().getText().trim().isEmpty() || telaVenda.getTxtCpfCliente().getText().equals("Cliente")) {
	            venda.setCpfCliente(null); // CPF padrão ou ajuste conforme necessário
	        } else {
	        	venda.setCpfCliente(telaVenda.getTxtCpfCliente().getText());
	        }

	        // Configurar detalhes da venda conforme a forma de pagamento
	        if (telaVenda.getCbxFormaPagamento().getSelectedIndex() == 1
	                || telaVenda.getCbxFormaPagamento().getSelectedIndex() == 2
	                || telaVenda.getCbxFormaPagamento().getSelectedIndex() == 3) {
	            venda.setImposto("0.00");
	            venda.setDescontoVenda(telaVenda.getLblDesconto().getText());
	            venda.setStatusVenda("FINALIZADO");
	        } else if (telaVenda.getCbxFormaPagamento().getSelectedIndex() == 4) {
	            double aux = Double.parseDouble(telaVenda.getLblValorTotal().getText())
	                    - Double.parseDouble(telaVenda.getLblSubtotalProduto().getText());
	            venda.setImposto(String.valueOf(aux));
	            venda.setDescontoVenda(telaVenda.getLblDesconto().getText());
	            venda.setStatusVenda("FINALIZADO(Parcelamento)");
	        } else {
	            venda.setImposto("0.00");
	            venda.setDescontoVenda(telaVenda.getLblDesconto().getText());
	            venda.setStatusVenda("ABERTO");
	        }
	        BalancoGeral b = daoBalanco.obterOuCriarBalancoGeralParaHoje(PopupEscolhaCaixa.FuncAtivo);
	        venda.setBalancoGeral(b);
	        // Salvar venda
	        daoVenda.salvarVenda(venda);
	        App.listaVendas.add(venda);
	        
	        somVenda();
	        reiniciaTelaVenda();
	    } catch (Exception e) {
	        e.printStackTrace();
	        somErro();
	    }
	}

	private void inserirProduto() {

		String codigo = converterParaMaiusculas(telaVenda.getTxtCodigoProduto().getText());
		String quantidade = telaVenda.getTxtQtdProduto().getText();

		if (telaVenda.getTxtCodigoProduto().getText().trim().isEmpty()) {
			telaVenda.getTxtCodigoProduto().setBorder(BorderFactory.createLineBorder(Color.RED));
			new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				SwingUtilities.invokeLater(() -> {
					telaVenda.getTxtCodigoProduto().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				});
			}).start();
		} else {
			if (quantidade.isEmpty()) {
				telaVenda.getTxtQtdProduto().setBorder(BorderFactory.createLineBorder(Color.RED));
				new Thread(() -> {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					SwingUtilities.invokeLater(() -> {
						telaVenda.getTxtQtdProduto().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					});
				}).start();

			} else {
				DAOProduto daoProduto = new DAOProduto();
				Produto produto = daoProduto.buscarProdutoPorCodigo(codigo);
				if (produto != null) {
					if (produto.getQuantidadeAtual() > 0
							&& produto.getQuantidadeAtual() >= Integer.parseInt(telaVenda.getTxtQtdProduto().getText())
							&& testaListaProdutos(produto)) {
						if (Integer.parseInt(telaVenda.getTxtQtdProduto().getText()) > 0) {
							BarraListaProdutos produtoInserido = new BarraListaProdutos();
							produtoInserido.getLblCodigoProduto().setText(produto.getCodProduto());
							produtoInserido.getLblNomeProduto().setText(produto.getNomeProduto());
							produtoInserido.getLblQtdProduto().setText(telaVenda.getTxtQtdProduto().getText() + "Un.");
							produtoInserido.getLblValorProduto()
									.setText("R$" + String.valueOf(produto.getPrecoUnidProduto()));
							telaVenda.model.addElement(produtoInserido);
							atualizarValorSubtotalAoInserir(Integer.parseInt(telaVenda.getTxtQtdProduto().getText()),
									produto.getPrecoUnidProduto());
							atualizarValorTotal(
									Double.parseDouble(telaVenda.getLblSubtotalProduto().getText().replace(",", ".")));
							buscarProduto();
							selecionarFormaPagamento();
						} else {
							telaVenda.getTxtQtdProduto().setBorder(BorderFactory.createLineBorder(Color.RED));
							new Thread(() -> {
								try {
									Thread.sleep(5000);
								} catch (InterruptedException ex) {
									ex.printStackTrace();
								}
								SwingUtilities.invokeLater(() -> {
									telaVenda.getTxtQtdProduto().setBorder(BorderFactory.createLineBorder(Color.BLACK));
								});
							}).start();
						}
					} else {
						JOptionPane.showMessageDialog(telaVenda,
								"Não há quantidade suficiente de produtos para a venda!");
					}
				} else {
					JOptionPane.showMessageDialog(telaVenda, "Produto não encontrado!");
				}
			}
		}
	}

	private boolean testaListaProdutos(Produto p) {
		int qtd = 0;
		DefaultListModel<BarraListaProdutos> listaProdutos = telaVenda.model;
		for (int i = 0; i < listaProdutos.getSize(); i++) {
			BarraListaProdutos produto = listaProdutos.getElementAt(i);
			if (produto.getCodigo().equals(p.getCodProduto())) {
				qtd += Integer.parseInt(produto.getQuantidade().replace("Un.", ""));
			}
		}
		if (p.getQuantidadeAtual() >= qtd + Integer.parseInt(telaVenda.getTxtQtdProduto().getText())) {
			return true;
		} else {
			return false;
		}
	}

	private void buscarProduto() {
		String codigo = converterParaMaiusculas(telaVenda.getTxtCodigoProduto().getText());
		String quantidade = telaVenda.getTxtQtdProduto().getText();
		if (telaVenda.getTxtCodigoProduto().getText().trim().isEmpty()) {
			telaVenda.getTxtCodigoProduto().setBorder(BorderFactory.createLineBorder(Color.RED));
			new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				SwingUtilities.invokeLater(() -> {
					telaVenda.getTxtCodigoProduto().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				});
			}).start();

		} else {
			if (quantidade.isEmpty()) {
				telaVenda.getTxtQtdProduto().setBorder(BorderFactory.createLineBorder(Color.RED));
				new Thread(() -> {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					SwingUtilities.invokeLater(() -> {
						telaVenda.getTxtQtdProduto().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					});
				}).start();

			} else {
				DAOProduto daoProduto = new DAOProduto();
				Produto produto = daoProduto.buscarProdutoPorCodigo(codigo);
				if (produto != null) {
					SwingUtilities.invokeLater(() -> {
						telaVenda.getPanelPrecoUnitProduto().getLblCodigo().setText(produto.getCodProduto());
						telaVenda.getPanelPrecoUnitProduto().getLblNomeProduto().setText(produto.getNomeProduto());
						telaVenda.getPanelPrecoUnitProduto().getLblPrecoU()
								.setText(String.valueOf(produto.getPrecoUnidProduto()));
					});
				} else {
					SwingUtilities.invokeLater(() -> {
						telaVenda.getPanelPrecoUnitProduto().getLblCodigo().setText("");
						telaVenda.getPanelPrecoUnitProduto().getLblNomeProduto().setText("");
						telaVenda.getPanelPrecoUnitProduto().getLblPrecoU().setText("");
					});
					JOptionPane.showMessageDialog(telaVenda, "Produto não encontrado!");
				}
			}
		}
	}

	public void removerItemSelecionado() {
		// Obtém a lista de produtos da telaVenda
		JList<BarraListaProdutos> listaProdutos = telaVenda.pegaLista();

		// Verifica se a lista de produtos está inicializada e se há algum item
		// selecionado
		if (listaProdutos != null && listaProdutos.getSelectedIndex() != -1) {
			// Remove o item selecionado do modelo
			DefaultListModel<BarraListaProdutos> model = (DefaultListModel<BarraListaProdutos>) listaProdutos
					.getModel();
			double valorProduto = Double.parseDouble(
					listaProdutos.getSelectedValue().getLblValorProduto().getText().replace("R", "").replace("$", ""));
			int qtdProduto = Integer.parseInt(listaProdutos.getSelectedValue().getLblQtdProduto().getText()
					.replace("U", "").replace("n", "").replace(".", ""));
			atualizarValorSubtotalAoRetirar(qtdProduto, valorProduto);
			atualizarValorTotal(Double.parseDouble(telaVenda.getLblSubtotalProduto().getText().replace(",", ".")));
			model.remove(listaProdutos.getSelectedIndex());
			selecionarFormaPagamento();
		}
	}

	private void selecionarFormaPagamento() {
		String formaPagamentoSelecionada = String.valueOf(telaVenda.getCbxFormaPagamento().getSelectedItem());
		String cpfCliente = telaVenda.getTxtCpfCliente().getText().trim();

		if (!telaVenda.getLblSubtotalProduto().getText().trim().isEmpty() && !telaVenda.model.isEmpty()) {
			switch (formaPagamentoSelecionada) {
			case "Dinheiro":
				if (cpfCliente.isEmpty() || cpfCliente.equals("Cliente")) {
					atualizarFormaPagamento("Dinheiro", false);
				} else {
					if (buscaCliente() || validarCPF(cpfCliente)) {
						atualizarFormaPagamento("Dinheiro", true);
					} else {
						exibirMensagemDeErroNoCampo();
					}
				}
				break;
			case "Pix":
				if (cpfCliente.isEmpty() || cpfCliente.equals("Cliente")) {
					atualizarFormaPagamento("Pix", false);
				} else {
					if (buscaCliente() || validarCPF(cpfCliente)) {
						atualizarFormaPagamento("Pix", true);
					} else {
						exibirMensagemDeErroNoCampo();
					}
				}
				break;
			case "Cartão Débito":
				if (cpfCliente.isEmpty() || cpfCliente.equals("Cliente")) {
					atualizarFormaPagamento("Cartão Débito", false);
				} else {
					if (buscaCliente() || validarCPF(cpfCliente)) {
						atualizarFormaPagamento("Cartão Débito", true);
					} else {
						exibirMensagemDeErroNoCampo();
					}
				}
				break;
			case "Cartão Crédito":
				if (cpfCliente.isEmpty() || cpfCliente.equals("Cliente")) {
					atualizarFormaPagamento("Cartão Crédito", false);
				} else {
					if (buscaCliente() || validarCPF(cpfCliente)) {
						atualizarFormaPagamento("Cartão Crédito", true);
					} else {
						exibirMensagemDeErroNoCampo();
					}
				}
				break;
			case "Fiado":
				if (cpfCliente.isEmpty() || cpfCliente.equals("Cliente")) {
					atualizarFormaPagamento("Fiado", false);
				} else {
					if (buscaCliente() || validarCPF(cpfCliente)) {
						atualizarFormaPagamento("Fiado", true);
					} else {
						exibirMensagemDeErroNoCampo();
					}
				}
				break;
			default:
				atualizarFormaPagamento("", false);
				break;
			}
		} else {
			if (formaPagamentoSelecionada.isEmpty() || formaPagamentoSelecionada.equals("")) {
				atualizarFormaPagamento("", false);
			}
		}
	}

	private void selecionarParcelamento(double valor) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (telaVenda.getCbxParcelamento().getSelectedItem().equals("")) {
			double total = valor;
			total = valor + (valor * 0.05);
			telaVenda.getLblValorTotal().setText(String.valueOf(df.format(total)).replace(",", "."));
		} else if (telaVenda.getCbxParcelamento().getSelectedItem().equals("2x")) {
			double total = valor;
			total = valor + (valor * 0.10);
			telaVenda.getLblValorTotal().setText(String.valueOf(df.format(total)).replace(",", "."));
		} else if (telaVenda.getCbxParcelamento().getSelectedItem().equals("4x")) {
			double total = valor;
			total = valor + 2 * (valor * 0.10);
			telaVenda.getLblValorTotal().setText(String.valueOf(df.format(total)).replace(",", "."));
		} else if (telaVenda.getCbxParcelamento().getSelectedItem().equals("6x")) {
			double total = valor;
			total = valor + 4 * (valor * 0.07);
			telaVenda.getLblValorTotal().setText(String.valueOf(df.format(total)).replace(",", "."));
		} else { // 8x
			double total = valor;
			total = valor + 6 * (valor * 0.05);
			telaVenda.getLblValorTotal().setText(String.valueOf(df.format(total)).replace(",", "."));
		}
	}

	private void atualizarProdutoAoVender(int qtdProduto, String codigo) {
		DAOProduto daoProduto = new DAOProduto();
		Produto p = daoProduto.buscarProdutoPorCodigo(codigo);
		int estoque = p.getQtdEstoqueProduto();
		int qtdAtual = p.getQuantidadeAtual();

		estoque -= (qtdProduto / p.getQtdCaixaProduto());
		qtdAtual -= qtdProduto;

		p.setQtdEstoqueProduto(estoque);
		p.setQuantidadeAtual(qtdAtual);
		DAOProduto daoP = new DAOProduto();
		daoP.atualizarProduto(p);
	}

	private void atualizarFormaPagamento(String formaPagamento, boolean clienteValido) {
		if (clienteValido) {
			if (formaPagamento.equals("0%")) {
				telaVenda.getLblDesconto().setText("0%");
				telaVenda.getLblDesconto().setBackground(Color.WHITE);
				telaVenda.getLblDesconto().setOpaque(false);
				telaVenda.getCbxParcelamento().setSelectedItem("");
				telaVenda.getCbxParcelamento().setEnabled(false);
			} else if (formaPagamento.equals("Dinheiro") || formaPagamento.equals("Pix")
					|| formaPagamento.equals("Cartão Débito")) {
				telaVenda.getLblDesconto().setOpaque(true);
				telaVenda.getLblDesconto().setBackground(new Color(168, 223, 191));
				telaVenda.getCbxParcelamento().setSelectedItem("");
				telaVenda.getCbxParcelamento().setEnabled(false);
				telaVenda.getLblDesconto().setText("5%");
			} else if (formaPagamento.equals("Fiado")) {
				telaVenda.getCbxParcelamento().setSelectedItem("");
				telaVenda.getCbxParcelamento().setEnabled(false);
				telaVenda.getLblDesconto().setOpaque(true);
				if (buscaCliente()) {
					telaVenda.getLblDesconto().setBackground(new Color(255, 255, 153));
					telaVenda.getLblDesconto().setText("À Prazo");
				} else {
					telaVenda.getLblDesconto().setBackground(Color.WHITE);
					telaVenda.getLblDesconto().setText("0%");
				}
			} else if (formaPagamento.equals("Cartão Crédito")) {
				telaVenda.getLblDesconto().setOpaque(true);
				telaVenda.getLblDesconto().setBackground(Color.PINK);
				telaVenda.getCbxParcelamento().setEnabled(true);
				telaVenda.getLblDesconto().setText("Parcelamento");
			} else {
				telaVenda.getLblDesconto().setText("0%");
				telaVenda.getLblDesconto().setBackground(Color.WHITE);
				telaVenda.getLblDesconto().setOpaque(false);
				telaVenda.getCbxParcelamento().setSelectedItem("");
				telaVenda.getCbxParcelamento().setEnabled(false);

			}
			atualizarValorTotal(Double.parseDouble(telaVenda.getLblSubtotalProduto().getText().replace(",", ".")));
		} else {
			if (formaPagamento.equals("Cartão Crédito")) {
				telaVenda.getLblDesconto().setOpaque(true);
				telaVenda.getLblDesconto().setBackground(Color.PINK);
				telaVenda.getCbxParcelamento().setEnabled(true);
				telaVenda.getLblDesconto().setText("Parcelamento");
				if (!telaVenda.getLblSubtotalProduto().getText().trim().isEmpty()) {
					atualizarValorTotal(
							Double.parseDouble(telaVenda.getLblSubtotalProduto().getText().replace(",", ".")));
				}
			} else {
				telaVenda.getLblDesconto().setText("0%");
				telaVenda.getLblDesconto().setBackground(Color.WHITE);
				telaVenda.getLblDesconto().setOpaque(false);
				telaVenda.getCbxParcelamento().setSelectedItem("");
				telaVenda.getCbxParcelamento().setEnabled(false);
				if (!telaVenda.getLblSubtotalProduto().getText().trim().isEmpty()) {
					atualizarValorTotal(
							Double.parseDouble(telaVenda.getLblSubtotalProduto().getText().replace(",", ".")));
				}
			}
		}
	}

	private void atualizarValorTotal(double valor) {
		DecimalFormat df = new DecimalFormat("0.00");
		String formaPagamentoSelecionada = String.valueOf(telaVenda.getCbxFormaPagamento().getSelectedItem());
		String parcelamentoSelecionado = String.valueOf(telaVenda.getCbxParcelamento().getSelectedItem());

		if (telaVenda.getLblValorTotal().getText().trim().isEmpty()) {
			telaVenda.getLblValorTotal().setText("0.00");
		}

		if (telaVenda.getLblDesconto().getText().trim().isEmpty()) {
			double total = valor;
			telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
		} else {
			if (formaPagamentoSelecionada.equals("Dinheiro") || formaPagamentoSelecionada.equals("Pix")
					|| formaPagamentoSelecionada.equals("Cartão Débito")) {
				if (!telaVenda.getTxtCpfCliente().getText().trim().isEmpty()) {
					if (buscaCliente() || validarCPF(telaVenda.getTxtCpfCliente().getText())) {
						double total = valor - (valor * 0.05);
						telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					} else {
						double total = valor;
						telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					}
				} else {
					double total = valor;
					telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
				}
			} else if (formaPagamentoSelecionada.equals("Cartão Crédito")) {
				double total = valor;
				switch (parcelamentoSelecionado) {
				case "":
					total += valor * 0.05;
					telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					break;
				case "2x":
					total += valor * 0.10;
					telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					break;
				case "4x":
					total += 2 * (valor * 0.10);
					telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					break;
				case "6x":
					total += 4 * (valor * 0.07);
					telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					break;
				default:
					total += 6 * (valor * 0.05);
					telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
					break;
				}
			} else { // "Fiado"
				double total = valor;
				telaVenda.getLblValorTotal().setText(df.format(total).replace(",", "."));
			}
		}
	}

	private void atualizarValorSubtotalAoInserir(int qtd, double valor) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (telaVenda.getLblSubtotalProduto().getText().trim().isEmpty()) {
			telaVenda.getLblSubtotalProduto().setText("0.00");
		}
		double subtotal = Double.parseDouble(telaVenda.getLblSubtotalProduto().getText());
		subtotal += qtd * valor;
		telaVenda.getLblSubtotalProduto().setText(String.valueOf(df.format(subtotal)).replace(",", "."));
	}

	private void atualizarValorSubtotalAoRetirar(int qtd, double valor) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (telaVenda.getLblSubtotalProduto().getText().trim().isEmpty()) {
			telaVenda.getLblSubtotalProduto().setText("0.00");
		}
		double subtotal = Double.parseDouble(telaVenda.getLblSubtotalProduto().getText());
		subtotal -= qtd * valor;
		telaVenda.getLblSubtotalProduto().setText(String.valueOf(df.format(subtotal)).replace(",", "."));
	}

	private boolean buscaCliente() {
	    DAOCliente daoCliente = new DAOCliente();
	    Cliente cliente = daoCliente.buscarCliente(telaVenda.getTxtCpfCliente().getText());
	    return cliente != null;
	}


	private void exibirMensagemDeErroNoCampo() {
		telaVenda.getTxtCpfCliente().setBorder(BorderFactory.createLineBorder(Color.RED));
		new Thread(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			SwingUtilities.invokeLater(() -> {
				telaVenda.getTxtCpfCliente().setBorder(BorderFactory.createLineBorder(Color.BLACK));
			});
		}).start();
	}

	private void reiniciaTelaVenda() {
		telaVenda.getCbxFormaPagamento().setSelectedIndex(0);
		;
		telaVenda.getCbxParcelamento().setSelectedIndex(0);
		telaVenda.getCbxParcelamento().setEnabled(false);
		telaVenda.getTxtCodigoProduto().setText("");
		telaVenda.getTxtQtdProduto().setText("0");
		telaVenda.getTxtCpfCliente().setText("Cliente");
		telaVenda.model.clear();
		telaVenda.getLblSubtotalProduto().setText("");
		telaVenda.getLblValorTotal().setText("");
		telaVenda.getLblDesconto().setText("");
		telaVenda.getPanelPrecoUnitProduto().getLblCodigo().setText("");
		telaVenda.getPanelPrecoUnitProduto().getLblNomeProduto().setText("");
		telaVenda.getPanelPrecoUnitProduto().getLblPrecoU().setText("");
		telaVenda.getTxtCpfCliente().setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	private String converterParaMaiusculas(String codigo) {
		StringBuilder codigoMaiusculo = new StringBuilder();

		for (int i = 0; i < codigo.length(); i++) {
			char caractere = codigo.charAt(i);
			if (Character.isLetter(caractere)) {
				codigoMaiusculo.append(Character.toUpperCase(caractere));
			} else {
				codigoMaiusculo.append(caractere);
			}
		}
		return codigoMaiusculo.toString();
	}

	private boolean validarCPF(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			return false;
		}

		boolean todosDigitosIguais = true;
		for (int i = 1; i < 11; i++) {
			if (cpf.charAt(i) != cpf.charAt(0)) {
				todosDigitosIguais = false;
				break;
			}
		}
		if (todosDigitosIguais) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
		}
		int resto = soma % 11;
		int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

		if (Character.getNumericValue(cpf.charAt(9)) != digitoVerificador1) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
		}
		resto = soma % 11;
		int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);
		if (Character.getNumericValue(cpf.charAt(10)) != digitoVerificador2) {
			return false;
		}
		return true;
	}

	private DocumentListener addDocumentTfCpf() {
		if (documentUsuario == null) {
			documentUsuario = new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					if (telaVenda.getTxtCpfCliente().getText().length() == 14) {
						selecionarFormaPagamento();
					}
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					// Método chamado quando o texto é removido
					verificaTextoVazio();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// Método chamado quando há uma alteração que afeta o atributo do documento
					verificaTextoVazio();
				}

				private void verificaTextoVazio() {
					SwingUtilities.invokeLater(() -> {
						// Verifica se o texto do TextField está vazio ou não contém o caracter '-'
						if (telaVenda.getTxtCpfCliente().getText().isEmpty()
								|| telaVenda.getTxtCpfCliente().getText().length() <= 12
										&& telaVenda.getTxtCpfCliente().getText().length() != 7) {
							try {
								telaVenda.getTxtCpfCliente()
										.setFormatterFactory(new DefaultFormatterFactory(getMascaraCpf()));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
						}

					});
				}

				private MaskFormatter getMascaraCpf() throws ParseException {
					MaskFormatter mf = new MaskFormatter("###.###.###-##");
					mf.setPlaceholderCharacter('_');
					return mf;
				}
			};
		}
		return documentUsuario;
	}

	public FocusListener addAcaoTfCpf() {
		if (adaptUsuario == null) {
			adaptUsuario = new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if (telaVenda.getTxtCpfCliente().getText().trim().compareTo("Cliente") == 0) {
						telaVenda.getTxtCpfCliente().setText("");
						telaVenda.getTxtCpfCliente().setForeground(new Color(0, 0, 0));
						telaVenda.getTxtCpfCliente().setCaretPosition(0);
						try {
							telaVenda.getTxtCpfCliente()
									.setFormatterFactory(new DefaultFormatterFactory(getMascaraCpf()));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}

				public void focusLost(FocusEvent e) {
					telaVenda.getTxtCpfCliente().setFormatterFactory(null);
					if (telaVenda.getTxtCpfCliente().getText().trim().isEmpty()
							|| telaVenda.getTxtCpfCliente().getText().trim().compareTo("   .   .   -  ") == 63) {
						telaVenda.getTxtCpfCliente().setFormatterFactory(null);
						telaVenda.getTxtCpfCliente().setText("Cliente");
						telaVenda.getTxtCpfCliente().setForeground(new Color(114, 114, 114));

					}
				}
			};
		}
		return adaptUsuario;
	}

	private MaskFormatter getMascaraCpf() throws ParseException {
		MaskFormatter mf = new MaskFormatter("###.###.###-##");
		mf.setPlaceholderCharacter('_');
		return mf;
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

	private void somVenda() {
		try {
			URL url = App.class.getResource("/Audio/comprei.wav");
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
}
