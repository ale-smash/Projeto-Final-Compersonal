package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import compasso.estagio.grupo.projeto5.Telas.dto.InformacaoAdicionalDto;
import compasso.estagio.grupo.projeto5.Telas.dto.UsuarioDto;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("/alunos")
public class AlunosController {

	private int cont;

	private int numeroDePags;

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private AulaRepository aulaRepository;

	@GetMapping("/{page}")
	public String alunosAll(@PathVariable(name = "page") int pagina, Model modelo, Principal principal) {

		Page<Perfil> estudantes = getListaDeAlunos(principal, pagina);
		
		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
		modelo.addAttribute("estudantes", estudantes);
		modelo.addAttribute("pagina", pagina);
		modelo.addAttribute("numeroPagina", numeroDePags);

		return "alunos";
	}

	@GetMapping("/perfil/{email}")
	public String uuniPerfil(@PathVariable("email") String email, Model modelo, Principal principal) {

		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
		List<Aula> aulas = aulaRepository.findAll();
		List<Aula> aulasCadastradas = aulaRepository.getAulaCadastrada(email);
		aulas.removeAll(aulasCadastradas);

		Perfil perfil = perfilRepository.findByEmail(email);
		UsuarioDto u = UsuarioDto.converte(perfil);

		if (perfil.getInformacao() != null) {
			InformacaoAdicionalDto info = new InformacaoAdicionalDto();
			info.toInformacaoAdicionalDto(perfilRepository.findByEmail(email).getInformacao());
			modelo.addAttribute("info", info);
		} else {
			modelo.addAttribute("info", null);
		}

		if (aulas.isEmpty()) {
			modelo.addAttribute("SemAula", "Sem mais aulas!");
			modelo.addAttribute("aulas", null);
		} else {
			modelo.addAttribute("aulas", aulas);
		}

		modelo.addAttribute("aluno", u);
		if (!(perfil.getPlano().getStatus().equals("8"))) {
			modelo.addAttribute("situacao", perfil.getPlano().getStatus());
		}else {
			modelo.addAttribute("situacao", null);
		}

		if (cont == 1) {
			modelo.addAttribute("cadastrado", "Aula foi adicionada com sucesso!");
			cont = 0;
		} else if(cont == 2) {
			modelo.addAttribute("cadastrado", "C??digo foi adicionado com sucesso!");
			cont=0;
		}
		
		return "uniPerfil";
	}

	@PostMapping("/adicionarAula")
	@CacheEvict(value = "listaDeAulas", allEntries = true)
	public String adicionarAula(String titulo, String email, Model modelo) {
		if (!(titulo == null)) {
			Perfil perfil = perfilRepository.findByEmail(email);
			Aula aula = aulaRepository.findByTitulo(titulo);
			perfil.setAulas(aula);
			perfilRepository.save(perfil);
			cont=1;
		}
		return "redirect:/alunos/perfil/" + email;
	}

	@PostMapping("/adicionarTransacao")
	public String adicionarTransacao(String codigo, String email, Model modelo) {

		Perfil perfil = perfilRepository.findByEmail(email);
		perfil.setPlano(perfil.getPlano().getPlanoPreenchido(codigo));
		perfilRepository.save(perfil);
		cont=2;
		
		return "redirect:/alunos/perfil/" + email;
	}

	private Page<Perfil> getListaDeAlunos(Principal principal, int pagina) {

		List<Perfil> perfis = perfilRepository.findByPermissao_Permissao("Usuario");
		numeroDePags = perfis.size() / 6;
		if (perfis.size() % 6 != 0) {
			numeroDePags++;
		}

		Pageable paginacao = PageRequest.of(pagina, 6, Sort.by("primeiroNome").ascending());
		Page<Perfil> usuarios = perfilRepository.findByPermissaoPermissao("Usuario", paginacao);
		return usuarios;
	}

}
