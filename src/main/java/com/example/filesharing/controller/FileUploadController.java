package com.example.filesharing.controller;

import com.example.filesharing.domain.User;
import com.example.filesharing.domain.File;
import com.example.filesharing.repository.FileRepository;
import com.example.filesharing.repository.UserRepository;
import com.example.filesharing.storage.StorageFileNotFoundException;
import com.example.filesharing.storage.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> handleFileUpload(@RequestParam("file") MultipartFile uploadedFile) {
        String storageName = makeStorageName(uploadedFile);

        storageService.store(uploadedFile, storageName);

        File file = new File();
        file.setOriginalName(uploadedFile.getOriginalFilename());
        file.setStorageName(storageName);

        Optional<User> uploader = getUploader();
        uploader.ifPresent(file::setUser);

        fileRepository.save(file);

        HashMap<String, String> response = new HashMap<>();

        response.put("success", "success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Optional<User> getUploader() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.of(userRepository.findByUsername(authentication.getName()));
    }

    private String makeStorageName(MultipartFile file) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return timestamp.getTime() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
