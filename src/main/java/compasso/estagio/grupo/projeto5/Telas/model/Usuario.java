package compasso.estagio.grupo.projeto5.Telas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String email;

	private String senha;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Permissao> permissao = new ArrayList<Permissao>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return permissao;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		senha = encoder.encode(senha);
		this.senha = senha;
	}

	public List<Permissao> getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao.add(permissao);
	}

}