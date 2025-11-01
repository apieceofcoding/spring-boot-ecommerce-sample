package com.sample.ecommerce.storage;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MinioStorage {

    private static final String PATH_SEPARATOR = "/";

    private final MinioClient minioClient;
    private final MinioProperty minioProperty;

    @PostConstruct
    public void init() {
        try {
            if (isBucketExists()) {
                log.info("MinIO bucket '{}' already exists", getBucketName());
                setBucketPublicReadPolicy();
                return;
            }
            createBucket();
            setBucketPublicReadPolicy();
            log.info("MinIO bucket '{}' created successfully with public read access", getBucketName());
        } catch (Exception e) {
            log.error("Failed to initialize MinIO bucket: {}", e.getMessage(), e);
            throw new StorageException("Failed to initialize MinIO bucket", e);
        }
    }

    public String uploadFile(MultipartFile file, String folder) {
        validateFile(file);

        try {
            String objectName = generateObjectName(folder, file.getOriginalFilename());
            putObject(file, objectName);

            String fileUrl = buildFileUrl(objectName);
            log.info("File uploaded successfully: {}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("Failed to upload file: {}", e.getMessage(), e);
            throw new StorageException("Failed to upload file", e);
        }
    }

    public void deleteFile(String fileUrl) {
        String objectName = extractObjectName(fileUrl);
        if (objectName == null) {
            log.warn("Invalid file URL, skipping delete: {}", fileUrl);
            return;
        }

        try {
            removeObject(objectName);
            log.info("File deleted successfully: {}", fileUrl);
        } catch (Exception e) {
            log.error("Failed to delete file '{}': {}", fileUrl, e.getMessage(), e);
        }
    }

    private void validateFile(MultipartFile file) {
        Objects.requireNonNull(file, "File cannot be null");
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
    }

    private boolean isBucketExists() throws Exception {
        return minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(getBucketName())
                        .build()
        );
    }

    private void createBucket() throws Exception {
        minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(getBucketName())
                        .build()
        );
    }

    private void setBucketPublicReadPolicy() throws Exception {
        String policy = """
                {
                  "Version": "2012-10-17",
                  "Statement": [
                    {
                      "Effect": "Allow",
                      "Principal": {"AWS": ["*"]},
                      "Action": ["s3:GetObject"],
                      "Resource": ["arn:aws:s3:::%s/*"]
                    }
                  ]
                }
                """.formatted(getBucketName());

        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                        .bucket(getBucketName())
                        .config(policy)
                        .build()
        );
        log.info("Bucket '{}' policy set to public read", getBucketName());
    }

    private void putObject(MultipartFile file, String objectName) throws Exception {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(getBucketName())
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    private void removeObject(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(getBucketName())
                        .object(objectName)
                        .build()
        );
    }

    private String generateObjectName(String folder, String originalFilename) {
        String extension = extractFileExtension(originalFilename);
        return folder + PATH_SEPARATOR + UUID.randomUUID() + extension;
    }

    private String extractFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private String buildFileUrl(String objectName) {
        return getUrlPrefix() + objectName;
    }

    private String extractObjectName(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return null;
        }
        String urlPrefix = getUrlPrefix();
        return fileUrl.startsWith(urlPrefix) ? fileUrl.substring(urlPrefix.length()) : null;
    }

    private String getUrlPrefix() {
        return minioProperty.endpoint() + PATH_SEPARATOR + getBucketName() + PATH_SEPARATOR;
    }

    private String getBucketName() {
        return minioProperty.bucket();
    }

    private static class StorageException extends RuntimeException {
        public StorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
