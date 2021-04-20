package compasso.estagio.grupo.projeto5.Telas.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String primeiroNome;

	private String ultimoNome;

	private String senha;

	private String confirmacaoSenha;

	private String email;

	private String telefone;

	@OneToMany(mappedBy = "perfil", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Mensagem> mensagens = new ArrayList<Mensagem>();

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(Mensagem mensagem) {
        this.mensagens.add(mensagem);
    }

}
