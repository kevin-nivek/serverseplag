package com.seplag.serverseplag.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService {
  
  private final MinioClient minioClient;  // Adicione 'final' aqui

  @Value("${minio.bucket:albuns-capas}")  // Valor padrão adicionado
  private String bucketName;

  @Value("${minio.presigned-expiry:30}")  // Valor padrão adicionado
  private Integer presignedExpiryMinutes;

  public String uploadImage(MultipartFile file, String folder) {
        try {
            ensureBucketExists();
            
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            String objectKey = folder + "/" + UUID.randomUUID().toString() + extension;

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );

            log.info("Upload realizado: {}", objectKey);
            return objectKey;

        } catch (Exception e) {
            log.error("Erro no upload", e);
            throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage(), e);
        }
    }

    public String generatePresignedUrl(String objectKey) {
        try {
            Date expiryDate = Date.from(
                LocalDateTime.now()
                    .plusMinutes(presignedExpiryMinutes)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
            );

            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .method(Method.GET)
                    .expiry((int) (expiryDate.toInstant().getEpochSecond() / 60), TimeUnit.MINUTES)
                    .build()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar URL", e);
            throw new RuntimeException("Erro ao gerar URL da imagem: " + e.getMessage(), e);
        }
    }

    public void deleteImage(String objectKey) {
        try {
            minioClient.removeObject(
                RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .build()
            );
            log.info("Imagem removida: {}", objectKey);
        } catch (Exception e) {
            log.error("Erro ao remover imagem", e);
            throw new RuntimeException("Erro ao remover imagem: " + e.getMessage(), e);
        }
    }

    private void ensureBucketExists() {
        try {
            boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!found) {
                minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
                );
                log.info("Bucket criado: {}", bucketName);
            }
        } catch (Exception e) {
            log.error("Erro ao verificar bucket", e);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}