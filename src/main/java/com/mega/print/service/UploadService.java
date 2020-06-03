package com.mega.print.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@Service
public class UploadService {

    private String realPathtoUploads;
    public void createDirectory(HttpServletRequest request){
        String uploadsDir = "/uploads/";
        realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
        if(! new File(realPathtoUploads).exists())
        {
            new File(realPathtoUploads).mkdir();
        }
    }

    public String getNameDirectory(){
        return realPathtoUploads;
    }


}
