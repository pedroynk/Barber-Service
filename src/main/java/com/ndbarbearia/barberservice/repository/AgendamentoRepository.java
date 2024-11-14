package com.ndbarbearia.barberservice.repository;

import com.ndbarbearia.barberservice.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByBarbeiroIdAndData(Long barbeiroId, LocalDateTime data);
}