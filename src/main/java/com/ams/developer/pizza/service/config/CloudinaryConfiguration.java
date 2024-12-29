package com.ams.developer.pizza.service.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dfjdmcvsr",
                "api_key", "751958635517357",
                "api_secret", "m3DYJukEwXBEEBOvMj0DoszUSZw"
        ));
    }
}
