package com.ndbarbearia.barberservice.controller;

import com.ndbarbearia.barberservice.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/cadastrar")
public String criarAgendamento(
        @RequestParam Long clienteId,
        @RequestParam Long barbeiroId,
        @RequestParam String servicoDescricao,
        @RequestParam String data) {

    LocalDateTime dataAgendamento = LocalDateTime.parse(data);
    agendamentoService.criarAgendamento(clienteId, barbeiroId, servicoDescricao, dataAgendamento);

    return "agendamento/formulario";
    }
}