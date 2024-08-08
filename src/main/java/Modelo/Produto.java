package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduto")
    private int id;

    @Column(name = "codProduto")
    @Size(max = 100)
    @NotNull
    private String codProduto;

    @Column(name = "nomeProduto")
    @Size(max = 150)
    @NotNull
    private String nomeProduto;

    @Column(name = "categoriaProduto")
    @Size(max = 45)
    @NotNull
    private String categoriaProduto;

    @Column(name = "dtFabProduto")
    @NotNull
    private LocalDate dtFabProduto;

    @Column(name = "dtValProduto")
    @NotNull
    private LocalDate dtValProduto;

    @Column(name = "precoCaixaProduto")
    @NotNull
    private double precoCaixaProduto;

    @Column(name = "precoUnidProduto")
    @NotNull
    private double precoUnidProduto;

    @Column(name = "qtdCaixaProduto")
    @NotNull
    private int qtdCaixaProduto;

    @Column(name = "qtdEstoqueProduto")
    @NotNull
    private int qtdEstoqueProduto;

    @Column(name = "qtdMinEstoqueProduto")
    @NotNull
    private int qtdMinEstoqueProduto;

    @Column(name = "alertaMinEstoque")
    @NotNull
    private boolean alertaMinEstoque;

    @Column(name = "quantidadeAtual")
    @NotNull
    private int quantidadeAtual;
    
	public Produto() {
	}

	public Produto(String codProduto, String nomeProduto, String categoriaProduto, LocalDate dtFabProduto,
			LocalDate dtValProduto, double precoCaixaProduto, double precoUnidProduto, int qtdCaixaProduto,
			int qtdEstoqueProduto, int qtdMinEstoqueProduto) {
		super();
		this.codProduto = codProduto;
		this.nomeProduto = nomeProduto;
		this.categoriaProduto = categoriaProduto;
		this.dtFabProduto = dtFabProduto;
		this.dtValProduto = dtValProduto;
		this.precoCaixaProduto = precoCaixaProduto;
		this.precoUnidProduto = precoUnidProduto;
		this.qtdCaixaProduto = qtdCaixaProduto;
		this.qtdEstoqueProduto = qtdEstoqueProduto;
		this.qtdMinEstoqueProduto = qtdMinEstoqueProduto;
		quantidadeAtual = qtdCaixaProduto * qtdEstoqueProduto;
	}

	public int getId() {
		return this.id;
	}
	public String getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(String categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public LocalDate getDtFabProduto() {
		return dtFabProduto;
	}

	public void setDtFabProduto(String dtFabProduto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.dtFabProduto = LocalDate.parse(dtFabProduto, formatter);
	}

	public LocalDate getDtValProduto() {
		return dtValProduto;
	}

	public void setDtValProduto(String dtValProduto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.dtValProduto = LocalDate.parse(dtValProduto, formatter);
	}

	public double getPrecoCaixaProduto() {
		return precoCaixaProduto;
	}

	public void setPrecoCaixaProduto(double precoCaixaProduto) {
		this.precoCaixaProduto = precoCaixaProduto;
	}

	public double getPrecoUnidProduto() {
		return precoUnidProduto;
	}

	public void setPrecoUnidProduto(double precoUnidProduto) {
		this.precoUnidProduto = precoUnidProduto;
	}

	public int getQtdCaixaProduto() {
		return qtdCaixaProduto;
	}

	public void setQtdCaixaProduto(int qtdCaixaProduto) {
		this.qtdCaixaProduto = qtdCaixaProduto;
	}

	public int getQtdEstoqueProduto() {
		return qtdEstoqueProduto;
	}

	public void setQtdEstoqueProduto(int qtdEstoqueProduto) {
		this.qtdEstoqueProduto = qtdEstoqueProduto;
	}

	public int getQtdMinEstoqueProduto() {
		return qtdMinEstoqueProduto;
	}

	public void setQtdMinEstoqueProduto(int qtdMinEstoqueProduto) {
		this.qtdMinEstoqueProduto = qtdMinEstoqueProduto;
	}

	public int getQuantidadeAtual() {
		quantidadeAtual = qtdCaixaProduto * qtdEstoqueProduto;
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(int quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

	public boolean isAlertaMinEstoque() {
		return alertaMinEstoque;
	}

	public void setAlertaMinEstoque(boolean alertaMinEstoque) {
		this.alertaMinEstoque = alertaMinEstoque;
	}

}
