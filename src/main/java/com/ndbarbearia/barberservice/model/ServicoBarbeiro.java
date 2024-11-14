package com.ndbarbearia.barberservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barber_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicoBarbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barbeiro_id", nullable = false)
    private Long barbeiroId;

    @Column(name = "servico_descricao", nullable = false, length = 255)
    private String servicoDescricao;
}
