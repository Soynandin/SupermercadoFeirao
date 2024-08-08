package Modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="BalancoFinanceiro")
public class BalancoFinanceiro {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idBalancoFinanceiro")
    private int id;

    @Column(name="dataHoraBalanco")
    private LocalDateTime dataHoraBalanco;

    @Column(name="movimentoMensal")
    private double movimentoMensal;

    @Column(name="lucro")
    private double lucro;

    @Column(name="divida")
    private double divida;

	public BalancoFinanceiro() {
	}

	public BalancoFinanceiro(LocalDateTime dataHoraBalanco, double movimentoMensal, double lucro, double divida) {
		super();
		this.dataHoraBalanco = dataHoraBalanco;
		this.movimentoMensal = movimentoMensal;
		this.lucro = lucro;
		this.divida = divida;
	}

	public int getId() {
		return this.id;
	}
	public LocalDateTime getDataHoraBalanco() {
		return dataHoraBalanco;
	}

	public double getMovimentoMensal() {
		return movimentoMensal;
	}

	public double getLucro() {
		return lucro;
	}

	public double getDivida() {
		return divida;
	}

	public void setDataHoraBalanco(LocalDateTime dataHoraBalanco) {
		this.dataHoraBalanco = dataHoraBalanco;
	}

	public void setMovimentoMensal(double movimentoMensal) {
		this.movimentoMensal = movimentoMensal;
	}

	public void setLucro(double lucro) {
		this.lucro = lucro;
	}

	public void setDivida(double divida) {
		this.divida = divida;
	}

}
