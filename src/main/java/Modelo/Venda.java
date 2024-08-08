package Modelo;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenda")
    private int id;

    @Column(name = "listaProdutos")
    @NotNull
    private String listaProdutos;

    @Column(name = "dataHoraVenda")
    @NotNull
    private LocalDateTime dataHoraVenda;

    @Column(name = "formaPagVenda")
    @Size(max = 45)
    @NotNull
    private String formaPagVenda;

    @Column(name = "descontoVenda")
    @Size(max = 45)
    @NotNull
    private String descontoVenda;

    @Column(name = "statusVenda")
    @Size(max = 45)
    @NotNull
    private String statusVenda;

    @Column(name = "subTotalVenda")
    @Size(max = 45)
    @NotNull
    private String subTotalVenda;

    @Column(name = "valorTotalVenda")
    @Size(max = 45)
    @NotNull
    private String valorTotalVenda;

    @Column(name = "imposto")
    @Size(max = 45)
    @NotNull
    private String imposto;
    
    @Column(name = "cpfCliente")
    @Size(max = 14)
    private String cpfCliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BalancoGeral_balancoGeralId")
    @NotNull
    private BalancoGeral BalancoGeral_balancoGeralId;
	
	public Venda() {
	}
	
	public int getId() {
		return this.id;
	}
	public String getListaProdutos() {
		return listaProdutos;
	}
	public void setListaProdutos(String listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	public String getDataHoraVendaFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
	    return dataHoraVenda.format(formatter);
	}
	public void setDataHoraVendaFormatada(String dataHoraString) {
	    // Remove os segundos da string
	    String dataHoraSemSegundos = dataHoraString.substring(0, 16);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    this.dataHoraVenda = LocalDateTime.parse(dataHoraSemSegundos, formatter);
	}
	
	public String getFormaPagVenda() {
		return formaPagVenda;
	}


	public void setFormaPagVenda(String formaPagVenda) {
		this.formaPagVenda = formaPagVenda;
	}


	public String getDescontoVenda() {
		return descontoVenda;
	}


	public void setDescontoVenda(String descontoVenda) {
		this.descontoVenda = descontoVenda;
	}


	public String getStatusVenda() {
		return statusVenda;
	}


	public void setStatusVenda(String statusVenda) {
		this.statusVenda = statusVenda;
	}


	public String getSubTotalVenda() {
		return subTotalVenda;
	}


	public void setSubTotalVenda(String subTotalVenda) {
		this.subTotalVenda = subTotalVenda;
	}


	public String getValorTotalVenda() {
		return valorTotalVenda;
	}


	public void setValorTotalVenda(String valorTotalVenda) {
		this.valorTotalVenda = valorTotalVenda;
	}


	public String getImposto() {
		double d = Double.parseDouble(imposto);
		DecimalFormat df = new DecimalFormat("0.00");
		return String.valueOf(df.format(d));
	}

	public void setImposto(String imposto) {
		this.imposto = imposto;
	}

	public BalancoGeral getBalancoGeral() {
		return BalancoGeral_balancoGeralId;
	}

	public void setBalancoGeral(BalancoGeral balancoGeral) {
		this.BalancoGeral_balancoGeralId = balancoGeral;
	}

	

}
