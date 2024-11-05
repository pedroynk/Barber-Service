package com.ndbarbearia.barberservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private User barber;

    @Column(name = "service_description", nullable = false, length = 255)
    private String serviceDescription;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public Schedule(User client, User barber, String serviceDescription, LocalDateTime date) {
        if (client.getProfile() != Profile.CLIENTE) {
            throw new IllegalArgumentException("O usuário associado como cliente deve ter o perfil CLIENTE.");
        }
        if (barber.getProfile() != Profile.BARBEIRO) {
            throw new IllegalArgumentException("O usuário associado como barbeiro deve ter o perfil BARBEIRO.");
        }
        this.client = client;
        this.barber = barber;
        this.serviceDescription = serviceDescription;
        this.date = date;
    }
}
