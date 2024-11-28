package com.ndbarbearia.barberservice.controller;

import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class AgendamentoController {
    @Autowired
    private AgendamentoService agendamentoService;

    /**
     * Exibe o formulário de agendamento para o barbeiro selecionado.
     */
    @GetMapping("/agendamento/formulario")
    public String mostrarFormularioAgendamento(@RequestParam Long barbeiroId, Model model) {
        model.addAttribute("barbeiroId", barbeiroId);
        return "agendamento/agendamentos";
    }

    /**
     * Busca horários disponíveis para o barbeiro e a data selecionados.
     */
    @GetMapping("/agendamento/horarios")
    public String buscarHorarios(@RequestParam Long barbeiroId, @RequestParam String data, Model model) {
        try {
            LocalDate dataSelecionada = LocalDate.parse(data);
            LocalDateTime dataHoraInicio = dataSelecionada.atStartOfDay(); // Adiciona horário 00:00
            List<LocalDateTime> horariosDisponiveis = agendamentoService.buscarHorariosDisponiveis(barbeiroId, dataHoraInicio);
            // Busca horários disponíveis no serviço
            model.addAttribute("barbeiroId", barbeiroId);
            model.addAttribute("data", dataHoraInicio);
            model.addAttribute("horarios", horariosDisponiveis);

            return "agendamento/agendamentos";
        } catch (Exception e) {
            // Log para debug
            System.err.println("Erro ao buscar horários: " + e.getMessage());
            model.addAttribute("errorMessage", "Erro ao buscar horários disponíveis.");
            return "error/custom-error"; // Página de erro personalizada
        }
    }

    /**
     * Confirma o agendamento com base nos dados fornecidos.
     */
    @PostMapping("/agendamento/confirmar")
    public String confirmarAgendamento(
            @RequestParam Long clienteId,
            @RequestParam Long barbeiroId,
            @RequestParam String servicoDescricao,
            @RequestParam String horario,
            @RequestParam String data,
            Model model) {
        try {
            // Converte data e horário para LocalDateTime
            LocalDate dataFormatada = LocalDate.parse(data);
            LocalTime horarioFormatado = LocalTime.parse(horario);
            LocalDateTime dataHora = LocalDateTime.of(dataFormatada, horarioFormatado);

            // Cria o agendamento usando o serviço
            Agendamento agendamento = agendamentoService.criarAgendamento(clienteId, barbeiroId, servicoDescricao, dataHora);

            // Adiciona o agendamento ao modelo
            model.addAttribute("agendamento", agendamento);

            return "agendamento/agendamento-confirmado"; // Página de confirmação
        } catch (Exception e) {
            // Log para debug
            System.err.println("Erro ao confirmar agendamento: " + e.getMessage());
            model.addAttribute("errorMessage", "Erro ao confirmar agendamento.");
            return "error/custom-error"; // Página de erro personalizada
        }
    }
}
