package com.ndbarbearia.barberservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barber_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarberService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barber_id", nullable = false)
    private Long barberId;

    @Column(name = "service_description", nullable = false, length = 255)
    private String serviceDescription;
}
