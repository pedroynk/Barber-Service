package com.ndbarbearia.barberservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import com.ndbarbearia.barberservice.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByPerfil(String perfil);

    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findAll(Pageable pageable);
}
