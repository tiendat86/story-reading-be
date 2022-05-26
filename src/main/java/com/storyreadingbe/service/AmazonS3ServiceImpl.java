package com.storyreadingbe.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Objects;

@Service
@Transactional
public class AmazonS3ServiceImpl {
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${amazon.aws.url-endpoint}")
    private String endPointUrl;
    @Value("${amazon.s3.default-bucket}")
    private String bucketName;
    
    public String uploadFileUrl(MultipartFile multipartFile) {
        String filename = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            filename = generateFilename(multipartFile);
            this.uploadFileTos3Bucket(filename, file);
            file.delete();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String fileUrl = endPointUrl + "/"  + filename;
        return fileUrl;
    }
    
    private File convertMultiPartToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return file;
    }
    
    private String generateFilename(MultipartFile multipartFile) {
        try {
            return new Date().getTime() + "-" + Objects.requireNonNull(multipartFile.getOriginalFilename())
                    .replace(" ", "_");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    private void uploadFileTos3Bucket(String filename, File file) {
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, filename, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
