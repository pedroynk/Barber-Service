package com.ndbarbearia.barberservice.controller;

import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService scheduleService;

    @PostMapping("/criar")
    public Agendamento criarAgendamento(
            @RequestParam Long clienteId,
            @RequestParam Long barbeiroId,
            @RequestParam String servicoDescricao,
            @RequestParam String data) {
        
        LocalDateTime dataAgendamento = LocalDateTime.parse(data);

        return scheduleService.criarAgendamento(clienteId, barbeiroId, servicoDescricao, dataAgendamento);
    }
}