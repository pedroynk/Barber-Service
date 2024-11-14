package com.ndbarbearia.barberservice.service;

import com.ndbarbearia.barberservice.model.Perfil;
import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.repository.AgendamentoRepository;
import com.ndbarbearia.barberservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendamentoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento criarAgendamento(Long clienteId, Long barbeiroId, String servicoDescricao, LocalDateTime data) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        Usuario barbeiro = usuarioRepository.findById(barbeiroId)
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado"));

        if (cliente.getPerfil() != Perfil.CLIENTE) {
            throw new IllegalArgumentException("O usuário selecionado não é um cliente.");
        }
        if (barbeiro.getPerfil() != Perfil.BARBEIRO) {
            throw new IllegalArgumentException("O usuário selecionado não é um barbeiro.");
        }

        Agendamento agendamento = Agendamento.builder()
                .cliente(cliente)
                .barbeiro(barbeiro)
                .servicoDescricao(servicoDescricao)
                .data(data)
                .build();

        return agendamentoRepository.save(agendamento);
    }
}
