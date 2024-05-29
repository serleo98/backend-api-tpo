package com.uade.tpo.demo.repository.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.tpo.demo.repository.cloudinary.CloudinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

@Service
public class CloudinaryRepositoryImpl implements CloudinaryRepository {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String savePhoto(String fileName, MultipartFile file) {
        Map params = ObjectUtils.asMap(
                "public_id", "myfolder/mysubfolder/my_dog",
                "overwrite", true,
                "resource_type", "image"
        );

        Map uploadResult = null;

        try {
            uploadResult = cloudinary.uploader().upload(convertMultipartFileToBase64(file), ObjectUtils.emptyMap());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadResult.get("url").toString();
    }

    @Override
    public void delete() {

    }
    public String convertMultipartFileToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

}
