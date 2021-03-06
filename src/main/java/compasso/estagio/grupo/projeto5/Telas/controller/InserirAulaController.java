package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import compasso.estagio.grupo.projeto5.Telas.AmazonS3.FileSaverService;
import compasso.estagio.grupo.projeto5.Telas.dto.AulaDto;
import compasso.estagio.grupo.projeto5.Telas.model.Aula;
import compasso.estagio.grupo.projeto5.Telas.model.Tipo;
import compasso.estagio.grupo.projeto5.Telas.repository.AulaRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("inseriraula")
public class InserirAulaController {

	private int cont;

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	AulaRepository aulaRepository;

	@Autowired
	private FileSaverService service;

	@GetMapping
	public String inserir(Model modelo, AulaDto aulaDto, Principal principal) {

		modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));

		if (cont > 0) {
			modelo.addAttribute("cadastrado", "Aula cadastrada com sucesso!");
			cont = 0;
		}
		return "inseriraula";
	}

	@PostMapping
	public String novaAula(@Valid AulaDto aulaDto, BindingResult result,
			@RequestParam(value = "file") MultipartFile file, Model modelo, Principal principal) {
		if (result.hasErrors()) {
			modelo.addAttribute("perfil", perfilRepository.findByEmail(principal.getName()));
			return "inseriraula";
		}

		Aula aula = aulaDto.toAula();
		aula.setPdf(file.getOriginalFilename());
		aula.setVideo(aula.getVideo().replace("watch?v=", "embed/"));
		service.uploadFile(file);

		switch (aulaDto.getTipo()) {
		case "Gl??teo":
			aula.setTipo(Tipo.GLUTEO);
			break;
		case "Abd??men":
			aula.setTipo(Tipo.ABDOMEN);
			break;
		case "Pernas":
			aula.setTipo(Tipo.PERNAS);
			break;
		case "Bra??os":
			aula.setTipo(Tipo.BRACOS);
			break;
		case "Peito":
			aula.setTipo(Tipo.PEITO);
			break;
		}

		aulaRepository.save(aula);
		cont++;
		aulaDto = null;
		return "redirect:inseriraula";
	}
}
