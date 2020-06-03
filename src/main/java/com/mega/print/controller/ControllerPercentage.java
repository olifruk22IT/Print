package com.mega.print.controller;

import com.itextpdf.text.DocumentException;
import com.mega.print.model.ResponsePDFAndPercentageColor;
import com.mega.print.service.ConvertToPDF;
import com.mega.print.service.GetPercentageService;
import com.mega.print.service.PDFConvertToImageService;
import com.mega.print.service.UploadService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class ControllerPercentage {

    @Autowired
    GetPercentageService getPercentageService;
    @Autowired
    PDFConvertToImageService pdfConvertToImageService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UploadService uploadService;

    private ResponsePDFAndPercentageColor responsePDFAndPercentageColor;

    @Autowired
    private ConvertToPDF convertToPDF;

    @GetMapping("/")
    public String index() {
        return "uploadform.html";
    }



    @PostMapping("/")
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile[] file, @RequestParam("from") String arrayNumberPage,
                                      @RequestParam(value = "1", required = false) String checkboxValue, Model model) throws IOException, InvalidFormatException, DocumentException {

        if(file != null){
           uploadService.createDirectory(request);

           File [] files = new File[file.length];

           for(int i =0;i< file.length;i++){
               String orgName = file[i].getOriginalFilename();
               String filePath = uploadService.getNameDirectory() + orgName;
               File dest = new File(filePath);
               file[i].transferTo(dest);
               files[i] = dest;
           }

            int [] array = {2};

            boolean increase_image = false;
            if(checkboxValue != null){
                increase_image = true;
            }
            responsePDFAndPercentageColor = getPercentageService.getPercentage( files,
                    array,increase_image );
            model.addAttribute("blackColor","Black color  = " + responsePDFAndPercentageColor.getGetAllPercentage()
                    .getBlack_percentage());
            model.addAttribute("whiteColor","White color = " + responsePDFAndPercentageColor.getGetAllPercentage()
                    .getWite_percentage());
            model.addAttribute("coloredColor", "ColoredColor =" + responsePDFAndPercentageColor.getGetAllPercentage()
                    .getColored_percentage());
        }
        return "uploadform.html";
    }

//    @GetMapping("/upload")
//    public ResponsePDFAndPercentageColor  uploadMultipartFile(@RequestParam("file") MultipartFile file,@RequestParam(value = "1", required = false) String checkboxValue) throws IOException, InvalidFormatException, DocumentException {
//
//        if(file != null){
//            uploadService.createDirectory(request);
//
//            String orgName = file.getOriginalFilename();
//            String filePath = uploadService.getNameDirectory() + orgName;
//            File dest = new File(filePath);
//            file.transferTo(dest);
//
//            System.out.println(dest.toString());
//            int [] array = {2};
//
//            boolean increase_image = false;
//            if(checkboxValue != null){
//                increase_image = true;
//            }
//            responsePDFAndPercentageColor = getPercentageService.getPercentage(dest.toString(),
//                    array,increase_image );
//           responsePDFAndPercentageColor.setInputStreamResource();
//
//           //for()
//            System.out.println(responsePDFAndPercentageColor.getDictionary().size());
//
//
//        }
//        return responsePDFAndPercentageColor;
//    }

    @GetMapping(value = "/pdf")
    public void showPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        InputStream inputStream = new FileInputStream(responsePDFAndPercentageColor.getFile());
        int nRead;
        while ((nRead = inputStream.read()) != -1) {
            response.getWriter().write(nRead);
        }
    }

}
