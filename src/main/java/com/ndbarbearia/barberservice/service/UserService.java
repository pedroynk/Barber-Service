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

import com.ndbarbearia.barberservice.model.User;
import com.ndbarbearia.barberservice.repository.UserRepository;
import com.ndbarbearia.barberservice.utils.PasswordUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${dir.users}")
    private String directory;

    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User searchById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User register(User user) {
        user.setPassword(PasswordUtils.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User edit(User user, Long id) {
        user.setPassword(this.searchById(id).getPassword());
        return userRepository.save(user);
    }

    public void searchAndDeleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> searchByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public Page<User> listAllUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public List<String> listUsersTypes() {
        return Arrays.asList("Administrador", "Barbeiro", "Cliente");
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

    public String getPathProfilePicture(Long id) {
        User user = this.searchById(id);
        return user.getProfilePicture();
    }

    public Boolean activeOrDisable(Long id) {
        User user = this.searchById(id);
        user.setActive(!user.getActive());
        this.userRepository.save(user);
        return user.getActive();
    }
}
