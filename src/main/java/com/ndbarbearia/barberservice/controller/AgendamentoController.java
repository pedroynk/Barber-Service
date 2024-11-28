package com.ndbarbearia.barberservice.controller;

import com.ndbarbearia.barberservice.service.AgendamentoService;
import com.ndbarbearia.barberservice.service.BarbeiroService;
import com.ndbarbearia.barberservice.service.UsuarioService;
import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private BarbeiroService barbeiroService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrar/{barbeiroId}")
    public String mostrarFormularioDeCadastro(@PathVariable Long barbeiroId, Model model) {
        Usuario barbeiro = barbeiroService.buscarPorId(barbeiroId);
        List<Usuario> clientes = usuarioService.buscarClientes();
        model.addAttribute("barbeiro", barbeiro);
        model.addAttribute("clientes", clientes);
        model.addAttribute("servicos", List.of("Corte de Cabelo", "Corte de Barba", "Corte de Cabelo e Barba"));

        return "agendamento/formulario";
    }

    @PostMapping("/cadastrar")
    public String criarAgendamento(
            @RequestParam Long clienteId,
            @RequestParam Long barbeiroId,
            @RequestParam String servicoDescricao,
            @RequestParam String data) {

        LocalDateTime dataAgendamento = LocalDateTime.parse(data);
        agendamentoService.criarAgendamento(clienteId, barbeiroId, servicoDescricao, dataAgendamento);

        return "redirect:/agendamento/listar";
    }

    @GetMapping("/listar")
    public String listarAgendamentos(Model model) {
        List<Agendamento> agendamentos = agendamentoService.listarAgendamentos();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/lista";
    }

    @GetMapping("/listar/paginado")
    public String listarAgendamentosPaginados(@RequestParam int pagina, @RequestParam int tamanho, Model model) {
        var agendamentos = agendamentoService.listarAgendamentosPaginados(pagina, tamanho);
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/lista";
    }
}
