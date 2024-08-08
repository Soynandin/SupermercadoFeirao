package Recursos;

import javax.swing.*;

import java.awt.*;
public class BarraListaProdutosRenderer extends JPanel implements ListCellRenderer<BarraListaProdutos> {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<? extends BarraListaProdutos> list, BarraListaProdutos value, int index, boolean isSelected, boolean cellHasFocus) {
        // Define o valor dos campos do componente BarraListaProdutos com base nos parâmetros do método
        value.getLblCodigoProduto().setText(value.getCodigo());
        value.getLblNomeProduto().setText(value.getNome());
        value.getLblQtdProduto().setText(value.getQuantidade());
        value.getLblValorProduto().setText(value.getValor());

        // Aplica a cor de fundo e de texto com base na seleção do item
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        
        // Retorna o componente BarraListaProdutos para ser renderizado na lista
        return value;
    }
}

