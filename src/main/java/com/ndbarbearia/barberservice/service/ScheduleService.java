package com.ndbarbearia.barberservice.service;

import com.ndbarbearia.barberservice.model.Profile;
import com.ndbarbearia.barberservice.model.Schedule;
import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.repository.ScheduleRepository;
import com.ndbarbearia.barberservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Long clientId, Long barberId, String serviceDescription, LocalDateTime date) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        User barber = userRepository.findById(barberId)
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado"));

        if (client.getProfile() != Profile.CLIENTE) {
            throw new IllegalArgumentException("O usuário selecionado não é um cliente.");
        }
        if (barber.getProfile() != Profile.BARBEIRO) {
            throw new IllegalArgumentException("O usuário selecionado não é um barbeiro.");
        }

        Schedule schedule = Schedule.builder()
                .client(client)
                .barber(barber)
                .serviceDescription(serviceDescription)
                .date(date)
                .build();

        return scheduleRepository.save(schedule);
    }
}
