package com.ndbarbearia.barberservice.service;

import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.repository.AgendamentoRepository;
import com.ndbarbearia.barberservice.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Agendamento criarAgendamento(Long clienteId, Long barbeiroId, String servicoDescricao, LocalDateTime data) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        Usuario barbeiro = usuarioRepository.findById(barbeiroId)
                .orElseThrow(() -> new EntityNotFoundException("Barbeiro não encontrado"));

        if (data.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data do agendamento não pode ser no passado.");
        }

        if (!isBarbeiroDisponivel(barbeiro, data)) {
            throw new IllegalArgumentException("O barbeiro não está disponível na data escolhida.");
        }

        Agendamento agendamento = Agendamento.builder()
                .cliente(cliente)
                .barbeiro(barbeiro)
                .servicoDescricao(servicoDescricao)
                .data(data)
                .build();

        return agendamentoRepository.save(agendamento);
    }

    private boolean isBarbeiroDisponivel(Usuario barbeiro, LocalDateTime data) {
        List<Agendamento> agendamentosNoHorario = agendamentoRepository.findByBarbeiroAndData(barbeiro, data);
        return agendamentosNoHorario.isEmpty();
    }

    public List<Agendamento> listarAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public Page<Agendamento> listarAgendamentosPaginados(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return agendamentoRepository.findAll(pageable);
    }

}
