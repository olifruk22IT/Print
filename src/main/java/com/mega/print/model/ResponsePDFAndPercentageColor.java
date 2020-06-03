package com.mega.print.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ResponsePDFAndPercentageColor {
    private Map<Integer,WCBPercentage> percentage_by_page;
    private  ByteArrayResource inputStreamResource;

    @JsonIgnore
    private File file;
    private WCBPercentage getAllPercentage;
    public ResponsePDFAndPercentageColor(){}
    public ResponsePDFAndPercentageColor(File file){
        this.file = file;
        percentage_by_page = new HashMap<Integer,WCBPercentage>();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setInputStreamResource(){
        try {
            this.inputStreamResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, WCBPercentage> getDictionary(){
        return percentage_by_page;
    }

    public WCBPercentage getGetAllPercentage() {
        return getAllPercentage;
    }

    public void setGetAllPercentage(WCBPercentage getAllPercentage) {
        this.getAllPercentage = getAllPercentage;
    }

    public void addValue(int number_page, double black_percentage, double wite_percentage, double colored_percentage){
        percentage_by_page.put(number_page,new WCBPercentage(wite_percentage,black_percentage,colored_percentage));
    }
}
