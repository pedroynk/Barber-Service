package com.ndbarbearia.barberservice.repository;

import com.ndbarbearia.barberservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    boolean existsByBarberIdAndDate(Long barberId, LocalDateTime date);
}
