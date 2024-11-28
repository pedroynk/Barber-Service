package com.ndbarbearia.barberservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agendamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agendamento_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Usuario barbeiro;

    @Column(name = "servico_descricao", nullable = false, length = 255)
    private String servicoDescricao;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    public Agendamento(Usuario cliente, Usuario barbeiro, String servicoDescricao, LocalDateTime data) {
        if (cliente.getPerfil() != Perfil.CLIENTE) {
            throw new IllegalArgumentException("O usuário associado como cliente deve ter o perfil Cliente.");
        }
        if (barbeiro.getPerfil() != Perfil.BARBEIRO) {
            throw new IllegalArgumentException("O usuário associado como barbeiro deve ter o perfil Barbeiro.");
        }
        this.cliente = cliente;
        this.barbeiro = barbeiro;
        this.servicoDescricao = servicoDescricao;
        this.data = data;
    }
}