package compasso.estagio.grupo.projeto5.Telas.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import compasso.estagio.grupo.projeto5.Telas.dto.MensagemDto;
import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;
import compasso.estagio.grupo.projeto5.Telas.model.Perfil;
import compasso.estagio.grupo.projeto5.Telas.repository.MensagemRepository;
import compasso.estagio.grupo.projeto5.Telas.repository.PerfilRepository;

@Controller
@RequestMapping("mensagem")
public class MensagemController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @GetMapping()
    public String mensagem(MensagemDto mensagemDto, Model modelo, Principal principal) {

        System.out.println("eu chego aqui");

        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        Pageable page = PageRequest.of(0, 10, Sort.by("dataEHorario").descending());
		Page<Mensagem> mensagens = mensagemRepository.findByPerfilId(perfil.getId(), page);
		modelo.addAttribute("mensagens", mensagens);
        
        return "redirect:/aulas";
    }

    @PostMapping("nova")
    public String novaMensagemAluno(@Valid MensagemDto mensagemDto, Model modelo, BindingResult result, Principal principal){
        if (result.hasErrors()) {
			return "redirect:/aulas";
		}

        Mensagem mensagem = mensagemDto.toMensagem(); 
        Perfil perfil = perfilRepository.findByEmail(principal.getName());
        mensagem.setPerfil(perfil);
        perfil.setMensagens(mensagem);
        mensagemRepository.save(mensagem);

        return "redirect:/aulas";
    }

}
