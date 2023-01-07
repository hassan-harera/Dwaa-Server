package com.harera.hayat.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.harera.hayat.repository.UserRepository;
import com.harera.hayat.service.FileManager;
import com.harera.hayat.service.user.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class S3FileService implements CloudFileService {

    private final UserRepository userRepository;
    private final String endpointUrl;
    private final String bucketName;
    private final String accessKeyId;
    private final String secretKey;
    private final AmazonS3 amazonS3;
    private final FileManager fileManager;

    @Autowired
    public S3FileService(UserService userService, UserRepository userRepository,
                    @Value("${s3.endpoint.url}") String endpointUrl,
                    @Value("${s3.bucket.name}") String bucketName,
                    @Value("${s3.access.key.id}") String accessKeyId,
                    @Value("${s3.secret.key}") String secretKey,
                    FileManager fileManager) {
        this.userRepository = userRepository;
        this.endpointUrl = endpointUrl;
        this.bucketName = bucketName;
        this.accessKeyId = accessKeyId;
        this.secretKey = secretKey;
        this.fileManager = fileManager;
        AWSCredentials credentials =
                        new BasicAWSCredentials(this.accessKeyId, this.secretKey);
        this.amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .build();
    }

    public String uploadFile(@NotNull File file) {
        String filename = generateFileName();
        String fileUrl = endpointUrl + "/" + filename;
        amazonS3.putObject(new PutObjectRequest(bucketName, filename, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        deleteLocalFile(file);
        return fileUrl;
    }

    private void deleteLocalFile(File file) {
        try {
            Files.delete(file.getAbsoluteFile().toPath());
        } catch (IOException e) {
            log.error(String.format("File [%s] is not deleted", file.getName()));
        }
    }

    public void deleteFile(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        amazonS3.deleteObject(bucketName, fileName);
    }

    private String generateFileName() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String replace = localDateTime.format(DateTimeFormatter.ISO_DATE_TIME)
                        .replace("-", "").replace("T", "").replace(":", "")
                        .replace("+", "");
        replace += localDateTime.getNano();
        return replace;
    }
}
