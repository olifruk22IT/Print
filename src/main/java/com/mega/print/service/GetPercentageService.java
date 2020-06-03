package com.mega.print.service;

import com.mega.print.model.ReadFileByPixel;
import com.mega.print.model.ResponsePDFAndPercentageColor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GetPercentageService {
    @Autowired
    private ConvertToPDF convertToPDF;
    @Autowired
    private PDFConvertToImageService pdfConvertToImageService;
    private ReadFileByPixel readFile;
    private ResponsePDFAndPercentageColor responsePDFAndPercentageColor;

    @Autowired
    private HttpServletRequest request;


    private ResponsePDFAndPercentageColor checkFormate(File [] files, int [] arrayNumberPage, boolean increase_image) throws IOException {
        String format = Optional.ofNullable(files[0].getName())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(files[0].getName().lastIndexOf(".") + 1)).get();


        if(format.equals("pdf")){
            responsePDFAndPercentageColor = new ResponsePDFAndPercentageColor(files[0]);
            pdfConvertToImageService.conv(files[0].getAbsolutePath(),responsePDFAndPercentageColor,arrayNumberPage);
            return responsePDFAndPercentageColor;
        }
        else if(format.equals("png") || format.equals("tiff") || format.equals("jpg")){
            File file = convertToPDF.image(files,increase_image);
            responsePDFAndPercentageColor = new ResponsePDFAndPercentageColor(file);
            System.out.println(file.toString());
            pdfConvertToImageService.conv(file.getAbsolutePath(),responsePDFAndPercentageColor,arrayNumberPage);
            return responsePDFAndPercentageColor;
        }
        else if(format.equals("docx")){
            File file = convertToPDF.docx(files[0]);
            responsePDFAndPercentageColor = new ResponsePDFAndPercentageColor(file);
            pdfConvertToImageService.conv(file.getAbsolutePath(),responsePDFAndPercentageColor,arrayNumberPage);
            return responsePDFAndPercentageColor;
        }
        else if(format.equals("pptx") || format.equals("ppt")){
            try {
                File file = convertToPDF.pptx(files[0]);
                responsePDFAndPercentageColor = new ResponsePDFAndPercentageColor(file);
                pdfConvertToImageService.conv(file.getAbsolutePath(),responsePDFAndPercentageColor,arrayNumberPage);
                return responsePDFAndPercentageColor;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responsePDFAndPercentageColor;
        }
        return new ResponsePDFAndPercentageColor(null);
    }
    public ResponsePDFAndPercentageColor getPercentage(File [] files,int [] arrayNumberPage,boolean increase_image) throws IOException {
        return checkFormate(files,arrayNumberPage,increase_image);
    }
}
