package com.ndbarbearia.barberservice.controller;

import com.ndbarbearia.barberservice.model.Schedule;
import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/create")
    public Schedule createSchedule(
            @RequestParam Long clientId,
            @RequestParam Long barberId,
            @RequestParam String serviceDescription,
            @RequestParam String date) {
        
        // Convertendo a data do tipo String para LocalDateTime
        LocalDateTime scheduleDate = LocalDateTime.parse(date);

        // Chama o serviço para criar o agendamento
        return scheduleService.createSchedule(clientId, barberId, serviceDescription, scheduleDate);
    }
}
