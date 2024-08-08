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
@Table(name = "Funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFuncionario")
    private int id;

    @Column(name = "nomeCompleto")
    @Size(max = 100)
    @NotNull
    private String nomeCompleto;

    @Column(name = "cpf", unique = true)
    @Size (max = 14)
    @NotNull
    private String cpf;

    @Column(name = "email")
    @Email
    @Size(max = 150)
    private String email;

    @Column(name = "dataNasc")
    @NotNull
    private Date dataNasc;

    @Column(name = "categoriaFunc")
    @NotNull
    private int categoriaFunc;

    @Column(name = "senha")
    @Size(max = 30)
    @NotNull
    private String senha;
	
	public Funcionario() {}
	
	
	public Funcionario(String nomeCompleto, String cpf, String email, Date dataNasc, int categoriaFunc,
			String senha) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.email = email;
		this.dataNasc = dataNasc;
		this.categoriaFunc = categoriaFunc;
		this.senha = senha;
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
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(this.dataNasc);
	}
	public void setDataNasc(String str) throws ParseException {
		SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy");
		this.dataNasc = formatter.parse(str);
	}
	public int getCategoriaFunc() {
		return categoriaFunc;
	}
	public void setCategoriaFunc(int categoriaFunc) {
		this.categoriaFunc = categoriaFunc;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String string) {
		this.senha = string;
	}
	
	
}
