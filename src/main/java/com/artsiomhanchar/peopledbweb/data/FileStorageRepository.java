package com.artsiomhanchar.peopledbweb.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class FileStorageRepository {

    @Value("${STORAGE_FOLDER}")
    private String storageFolder;

    public void save(String originalFilename, InputStream inputStream) {
        try {
            Path filePath = Path.of(storageFolder).resolve(originalFilename).normalize();

            System.out.println(filePath.toString());

            Files.copy(inputStream, filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
