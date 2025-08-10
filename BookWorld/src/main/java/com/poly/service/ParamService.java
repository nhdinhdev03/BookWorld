package com.poly.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ParamService {
    @Autowired
    HttpServletRequest request;
   
    public String getString(String name, String defaultValue) {
        String value = request.getParameter(name);
        return value != null ? value : defaultValue;
    }
    public int getInt(String name, int defaultValue) {
        Integer value = Integer.parseInt(request.getParameter(name));
        return value != null ? value : defaultValue;
    }
    public double getDouble(String name, double defaultValue) {
        Double value = Double.parseDouble(request.getParameter(name));
        return value != null ? value : defaultValue;
    }
    public boolean getBoolean(String name, boolean defaultValue) {
        String value = request.getParameter(name);
        return value.equals("on") ? true : defaultValue;
    }
    public Date getDate(String name, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse( request.getParameter(name));
        return date != null ? date : date;
    }
    public File save(MultipartFile file, String path) {
       File dir = new File(request.getServletContext().getRealPath(path));
       if(!dir.exists()) dir.mkdirs();
       try{
        File saveFile = new File(dir, file.getOriginalFilename());
        file.transferTo(saveFile);
        return saveFile;
       } catch (Exception e) {
            throw new RuntimeException(e);
       }
    }
}
