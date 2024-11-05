package com.ndbarbearia.barberservice.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ndbarbearia.barberservice.service.UserService;

public class PictureController {
    @Autowired
    private UserService userService;

    @Value("${sem.imagem}")
    private String defaultPicture;

    @GetMapping("/files/{idUsuario}")
    public ResponseEntity<Resource> serveFile(@PathVariable Long idUser) {
        String path = this.userService.getPathProfilePicture(idUser);
        Path file = null;

        if (path == null) {
            path = this.defaultPicture;
            file = Paths.get(path);
        } else {
            file = Paths.get(this.userService.getPathProfilePicture(idUser));
        }

        Resource resource;
        try {
            resource = new UrlResource(file.toUri());

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resource);
    }
}
