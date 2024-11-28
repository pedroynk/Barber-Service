package com.ndbarbearia.barberservice.service;

import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarbeiroService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario buscarPorId(Long barbeiroId) {
        Usuario barbeiro = usuarioRepository.findById(barbeiroId)
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro não encontrado"));

        return barbeiro;
    }

}
