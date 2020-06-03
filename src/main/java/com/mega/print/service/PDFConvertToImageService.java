package com.mega.print.service;

import com.mega.print.model.ReadFileByPixel;
import com.mega.print.model.ResponsePDFAndPercentageColor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Service
public class PDFConvertToImageService {
    @Autowired
    private UploadService uploadService;
    private ReadFileByPixel readFileByPixel;
    public void conv(String pdfPath, ResponsePDFAndPercentageColor responsePDFAndPercentageColor, int [] arrayNumberPage){
        readFileByPixel = new ReadFileByPixel(arrayNumberPage);
        try {

            String destinationDir = uploadService.getNameDirectory(); // converted images from pdf document are saved here
            File sourceFile = new File(pdfPath);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
            }
            if (sourceFile.exists()) {
                PDDocument document = PDDocument.load(pdfPath);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                String fileName = sourceFile.getName().replace(".pdf", "");
                //int count
                int pageNumber = 1;
                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage();
                    File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
                    ImageIO.write(image, "png", outputfile);

                    readFileByPixel.readFileByPixel(outputfile.getAbsolutePath(),
                            responsePDFAndPercentageColor,pageNumber);

                    outputfile.delete();
                    pageNumber++;
                }
                readFileByPixel.getPercentageByAllPage(responsePDFAndPercentageColor);
                document.close();
            } else {
                System.err.println(sourceFile.getName() +" File not exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
