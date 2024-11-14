package com.ndbarbearia.barberservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "u_nome", nullable = false)
    private String nome;

    @Column(name = "u_email", nullable = false, unique = true)
    private String email;

    @Column(name = "u_senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "u_perfil", nullable = false)
    private Perfil perfil;

    @Column(name = "u_ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "u_foto_perfil")
    private String fotoPerfilPath;
}
