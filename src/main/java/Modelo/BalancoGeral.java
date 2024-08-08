package Modelo;

import java.time.LocalDateTime;

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

@Entity
@Table(name = "BalancoGeral")
public class BalancoGeral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBalancoGeral")
    private int id;

    @Column(name = "dataHoraBalanco")
    @NotNull
    private LocalDateTime dataHoraBalanco;

    @Column(name = "dataHoraCaixaAberto")
    private LocalDateTime dataHoraCaixaAberto;

    @Column(name = "dataHoraCaixaFechado")
    private LocalDateTime dataHoraCaixaFechado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Funcionario_idFuncionario")
    @NotNull
    private Funcionario usuarioCaixa;
    
    @Column(name = "movDinheiro")
    private double movDinheiro;
    
    @Column(name = "movCredito")
    private double movCredito;
    
    @Column(name = "movDebito")
    private double movDebito;
    
    @Column(name = "movFiado")
    private double movFiado;
    
    @Column(name = "valorTotal")
    private double valorTotal;
    
    @Column(name = "valorSaldo")
    private double valorSaldo;
    
    @Column(name = "valorDividas")
    private double valorDividas;

	public BalancoGeral() {
	}

	public BalancoGeral(@NotNull LocalDateTime dataHoraBalanco, LocalDateTime dataHoraCaixaAberto,
			LocalDateTime dataHoraCaixaFechado, @NotNull Funcionario usuarioCaixa, double movDinheiro,
			double movCredito, double movDebito, double movFiado, double valorTotal, double valorSaldo,
			double valorDividas) {
		super();
		this.dataHoraBalanco = dataHoraBalanco;
		this.dataHoraCaixaAberto = dataHoraCaixaAberto;
		this.dataHoraCaixaFechado = dataHoraCaixaFechado;
		this.usuarioCaixa = usuarioCaixa;
		this.movDinheiro = movDinheiro;
		this.movCredito = movCredito;
		this.movDebito = movDebito;
		this.movFiado = movFiado;
		this.valorTotal = valorTotal;
		this.valorSaldo = valorSaldo;
		this.valorDividas = valorDividas;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDataHoraBalanco() {
		return dataHoraBalanco;
	}

	public LocalDateTime getDataHoraCaixaAberto() {
		return dataHoraCaixaAberto;
	}

	public LocalDateTime getDataHoraCaixaFechado() {
		return dataHoraCaixaFechado;
	}

	public Funcionario getUsuarioCaixa() {
		return usuarioCaixa;
	}

	public double getMovDinheiro() {
		return movDinheiro;
	}

	public double getMovCredito() {
		return movCredito;
	}

	public double getMovDebito() {
		return movDebito;
	}

	public double getMovFiado() {
		return movFiado;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public double getValorSaldo() {
		return valorSaldo;
	}

	public double getValorDividas() {
		return valorDividas;
	}

	public void setDataHoraBalanco(LocalDateTime dataHoraBalanco) {
		this.dataHoraBalanco = dataHoraBalanco;
	}

	public void setDataHoraCaixaAberto(LocalDateTime dataHoraCaixaAberto) {
		this.dataHoraCaixaAberto = dataHoraCaixaAberto;
	}

	public void setDataHoraCaixaFechado(LocalDateTime dataHoraCaixaFechado) {
		this.dataHoraCaixaFechado = dataHoraCaixaFechado;
	}

	public void setUsuarioCaixa(Funcionario usuarioCaixa) {
		this.usuarioCaixa = usuarioCaixa;
	}

	public void setMovDinheiro(double movDinheiro) {
		this.movDinheiro = movDinheiro;
	}

	public void setMovCredito(double movCredito) {
		this.movCredito = movCredito;
	}

	public void setMovDebito(double movDebito) {
		this.movDebito = movDebito;
	}

	public void setMovFiado(double movFiado) {
		this.movFiado = movFiado;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setValorSaldo(double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public void setValorDividas(double valorDividas) {
		this.valorDividas = valorDividas;
	}

	
}
