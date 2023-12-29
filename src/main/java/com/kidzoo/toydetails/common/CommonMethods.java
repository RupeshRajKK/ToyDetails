package com.kidzoo.toydetails.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class CommonMethods {

    public static byte[] readFile(String filename) throws IOException {
        String fileData = null;
        Resource resource = new ClassPathResource(filename);
        InputStream is = resource.getInputStream();
        byte[] fileDataBytes = new byte[is.available()];
        is.read(fileDataBytes);
        fileData = new String(fileDataBytes);
        return fileData.getBytes();
    }
}
