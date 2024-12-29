package com.ams.developer.pizza.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    
    String uploadFile(MultipartFile file) throws IOException;

    Map deleteImage(String publicId) throws IOException;

    
}
