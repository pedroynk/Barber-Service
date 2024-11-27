package com.ndbarbearia.barberservice.service;

import com.ndbarbearia.barberservice.model.Perfil;
import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.repository.AgendamentoRepository;
import com.ndbarbearia.barberservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<LocalDateTime> buscarHorariosDisponiveis(Long barbeiroId, LocalDateTime data) {
        List<LocalDateTime> horarios = new ArrayList<>();
        // Ajusta o início da data para o começo do dia
        LocalDateTime inicio = data.withHour(9).withMinute(0).withSecond(0).withNano(0);
        
        for (int i = 0; i < 8; i++) {
            LocalDateTime horario = inicio.plusHours(i);
            if (!agendamentoRepository.existsByBarbeiroIdAndData(barbeiroId, horario)) {
                horarios.add(horario);
            }
        }
        return horarios;
    }

}
