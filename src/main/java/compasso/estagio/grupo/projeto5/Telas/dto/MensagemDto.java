package compasso.estagio.grupo.projeto5.Telas.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import compasso.estagio.grupo.projeto5.Telas.model.Mensagem;

public class MensagemDto {
    @NotBlank
    private String corpoDaMensagem;
    private LocalDateTime datetime = LocalDateTime.now();

    public Mensagem toMensagem(){
        Mensagem mensagem = new Mensagem();
        mensagem.setDataEHorario(datetime);
        mensagem.setTexto(this.corpoDaMensagem);
        System.out.println(this.corpoDaMensagem);
        return mensagem;
    }

    public String getCorpoDaMensagem() {
        return corpoDaMensagem;
    }

    public void setCorpoDaMensagem(String corpoDaMensagem) {
        this.corpoDaMensagem = corpoDaMensagem;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    
}
