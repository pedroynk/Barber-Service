package com.ndbarbearia.barberservice.controller;

import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/agendamento/formulario")
    public String mostrarFormularioAgendamento(@RequestParam Long barbeiroId, Model model) {
        model.addAttribute("barbeiroId", barbeiroId);
        return "agendamento/agendamento-formulario";
    }

    @PostMapping("/agendamento/horarios")
    public String buscarHorarios(@RequestParam Long barbeiroId, @RequestParam String data, Model model) {
        // Converte a string da data para LocalDateTime
        LocalDateTime dataSelecionada = LocalDateTime.parse(data);
        
        // Busca os horários disponíveis para o barbeiro e a data
        model.addAttribute("barbeiroId", barbeiroId);
        model.addAttribute("data", dataSelecionada);
        model.addAttribute("horarios", agendamentoService.buscarHorariosDisponiveis(barbeiroId, dataSelecionada));
        
        return "agendamento/agendamento-formulario";  // Recarrega a mesma página com os horários disponíveis
    }



    // Método POST para criar o agendamento
    @PostMapping("/agendamento/confirmar")
    public String confirmarAgendamento(
            @RequestParam Long clienteId,
            @RequestParam Long barbeiroId,
            @RequestParam String servicoDescricao,
            @RequestParam String horario,
            @RequestParam String data, 
            Model model) {

        // Converte o horário e a data para LocalDateTime
        LocalDateTime dataFormatada = LocalDateTime.parse(data);
        LocalDateTime horarioFormatado = LocalDateTime.parse(horario);

        // Criar o agendamento usando o serviço
        Agendamento agendamento = agendamentoService.criarAgendamento(clienteId, barbeiroId, servicoDescricao, horarioFormatado);

        // Adiciona o agendamento ao modelo para exibição na tela de confirmação
        model.addAttribute("agendamento", agendamento);

        // Redireciona para a página de confirmação ou visualização do agendamento
        return "agendamento/agendamento-confirmado";  // Refira-se a uma página de confirmação
    }
}
