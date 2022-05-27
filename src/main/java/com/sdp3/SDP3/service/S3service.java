package com.sdp3.SDP3.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class S3service {

    String bucketName="woodyarn";
    String region="ap-south-1";
    String foldername = "images";
    private AmazonS3 s3Client;

//    @Autowired
//    private Repository repository;

    @PostConstruct
    private void initialiseS3Bucket() {
        try {

            AWSCredentials credentials = new BasicAWSCredentials("AKIA2AK7N2V7WE6ZPUO4","89HTlnFZkfYjYE7O+xatWvvhQh8x6g4FoycVw7a1");
            this.s3Client = new AmazonS3Client(credentials);
        }catch (AmazonS3Exception ex){
            ex.printStackTrace();
        }
    }
    public String uploadFile(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = foldername+ "/" + generateFileName(multipartFile);
            String s3fileUrl = getEndpointUrl() + "/" + bucketName + "/" + fileName;
            if(uploadFileTos3bucket(fileName, file)){
//                Image image=new Image();
//                image.setName(fileName);
//                repository.save(image);
                return fileName;
            }
            file.delete();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return multiPart.getOriginalFilename().replace(" ", "_")+new Date().getTime() + "-" ;
    }

    private boolean uploadFileTos3bucket(String fileName, File file) {
        try {
            PutObjectResult result = s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String deleteFileFromS3Bucket(String fileName) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
//            S3File s3File = s3FileRepository.findByFileName(fileName);
//            s3FileRepository.delete(s3File);
            return "Successfully deleted";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public byte[] getFile(String filename) {
        byte[] bytearray = null;
        try {
            S3Object img = s3Client.getObject(new GetObjectRequest(bucketName,filename));
            bytearray= IOUtils.toByteArray(img.getObjectContent());
        }catch (Exception e){
            System.out.println("File fetch Error");
            e.printStackTrace();}
        return bytearray;
    }

    public String getEndpointUrl(){
        String endpointUrl= "https://"+bucketName+".s3."+region+".amazonaws.com";
        return endpointUrl;
    }

}
