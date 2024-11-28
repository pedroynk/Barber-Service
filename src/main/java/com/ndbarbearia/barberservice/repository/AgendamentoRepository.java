package com.ndbarbearia.barberservice.repository;

import com.ndbarbearia.barberservice.model.Agendamento;
import com.ndbarbearia.barberservice.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByBarbeiroAndData(Usuario barbeiro, LocalDateTime data);
}
