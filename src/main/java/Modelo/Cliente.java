package Modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCliente")
	private int id;

	@Column(name = "nomeCompleto")
	@Size(max = 100)
	@NotNull
	private String nomeCompleto;

	@Column(name = "cpf")
	@Size(max = 11)
	@NotNull
	private String cpf;

	@Column(name = "email")
	@Email
	@Size(max = 150)
	private String email;

	@Column(name = "dataNasc")
	@NotNull
	private Date dataNasc;

	@Column(name = "limCredito")
	@NotNull
	private double limCredito;

	public Cliente() {
	}

	public Cliente(String nomeCompleto, String cpf, String email, Date dataNasc, double limCredito) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.email = email;
		this.dataNasc = dataNasc;
		this.limCredito = limCredito;
	}

	public int getId() {
		return this.id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataNasc() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(this.dataNasc);
	}

	public void setDataNasc(String str) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		this.dataNasc = formatter.parse(str);
	}

	public double getLimCredito() {
		return limCredito;
	}

	public void setLimCredito(double limCredito) {
		this.limCredito = limCredito;
	}

}
