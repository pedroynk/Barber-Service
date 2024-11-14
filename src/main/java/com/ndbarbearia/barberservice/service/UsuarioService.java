package com.ndbarbearia.barberservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ndbarbearia.barberservice.model.Usuario;
import com.ndbarbearia.barberservice.repository.UsuarioRepository;
import com.ndbarbearia.barberservice.utils.SenhaUtils;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${diretorio.usuarios}")
    private String directory;

    public List<Usuario> searchAll() {
        return usuarioRepository.findAll();
    }

    public Usuario searchById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario register(Usuario usuario) {
        usuario.setSenha(SenhaUtils.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario edit(Usuario usuario, Long id) {
        usuario.setSenha(this.searchById(id).getSenha());
        return usuarioRepository.save(usuario);
    }

    public void searchAndDeleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> searchByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

    public Page<Usuario> listAllUsers(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable);
    }

    public List<String> listUsersTypes() {
        return Arrays.asList("ADMINISTRADOR", "Barbeiro", "Cliente");
    }

    public String saveProfilePicture(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        Path directoryPath = Paths.get(directory);

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String originalFilename = file.getOriginalFilename();
        String filenameWithoutExtension = FilenameUtils.removeExtension(originalFilename);
        String extension = FilenameUtils.getExtension(originalFilename);

        String newFileName = filenameWithoutExtension + "_" + System.currentTimeMillis() + "." + extension;

        Path filePath = Paths.get(directoryPath.toString(), newFileName);

        Files.write(filePath, file.getBytes());

        return filePath.toString();
    }

    public String getFotoPerfilPath(Long id) {
        Usuario usuario = this.searchById(id);
        return usuario.getFotoPerfilPath();
    }

    public Boolean ativarOuDesativar(Long id) {
        Usuario usuario = this.searchById(id);
        usuario.setAtivo(!usuario.getAtivo());
        this.usuarioRepository.save(usuario);
        return usuario.getAtivo();
    }
}
