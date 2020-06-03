package com.mega.print.service;

import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

@Service
public class ConvertToPDF {

    @Autowired
    private UploadService uploadService;
    public File image(File[] files,boolean increase_image) {
        String destinationDir = uploadService.getNameDirectory();
        String fileName = files[0].getName().replace("." +
                Optional.ofNullable(files[0].getName())
                        .filter(f -> f.contains("."))
                        .map(f -> f.substring(files[0].getName().lastIndexOf(".") + 1)).get(), "");
        File file1 = new File(destinationDir + fileName + ".pdf");
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file1.getAbsolutePath()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
       for(File file:files){

           document.setPageSize(PageSize.A4);

           try {



               Image image = Image.getInstance(file.getAbsolutePath());


               int with =Float.valueOf(image.getWidth()).intValue();
               int height = Float.valueOf(image.getHeight()).intValue();

               int withPage = Float.valueOf(PageSize.A4.getWidth()).intValue();
               int heightPage = Float.valueOf(PageSize.A4.getHeight()).intValue();

               if(with > height){
                   if(increase_image){

                       image.setRotationDegrees(90);
                       float x = (PageSize.A4.getHeight()/2) / (image.getScaledHeight()/2);
                       float y = (PageSize.A4.getWidth() / 2) / (image.getScaledWidth() / 2);
                       if(x < y){
                           image.scalePercent(y*100,y*100);
                           image.setAbsolutePosition(0,
                                   (PageSize.A4.getHeight() - image.getScaledHeight()) / 2);
                       }else {
                           image.scalePercent(x * 100, x * 100);
                           image.setAbsolutePosition((PageSize.A4.getWidth() - image.getScaledWidth())/2,0);
                       }

                   }else {
                       int y = withPage/2 - height/2;
                       int x =  heightPage/2 - with/2;
                       image.setRotationDegrees(90);
                       image.setAbsolutePosition(y,x);
                   }

               }
               else if(with < height){
                   if(increase_image){
                       float x = (PageSize.A4.getHeight()/2) / (image.getScaledHeight()/2);
                       float y = (PageSize.A4.getWidth() / 2) / (image.getScaledWidth() / 2);
                       if (x < y) {
                           image.scalePercent(y*100,y*100);
                           image.setAbsolutePosition(0,
                                   (PageSize.A4.getHeight() - image.getScaledHeight()) / 2);
                       }else {
                           image.scalePercent(x * 100, x * 100);
                           image.setAbsolutePosition((PageSize.A4.getWidth() - image.getScaledWidth())/2,0);
                       }
                   }
                   else {
                       int x = withPage/2 - with/2;
                       int y =  heightPage/2 - height/2;
                       image.setAbsolutePosition(x,y);
                   }
               }
               else if(with == height){
                   if(increase_image){
                       image.scaleToFit(withPage,withPage);
                       int x = 0;
                       int y =  heightPage/2 - withPage/2;
                       image.setAbsolutePosition(x,y);
                   }
                   else {
                       int x = withPage/2 - with/2;
                       int y =  heightPage/2 - height/2;
                       image.setAbsolutePosition(x,y);
                   }
               }

               document.newPage();
               document.add(image);

               file.delete();

           } catch (IOException i) {

           } catch (BadElementException e) {
               e.printStackTrace();
           } catch (DocumentException e) {
               e.printStackTrace();
           }
       }

        document.close();
        return file1;
    }

    public File pptx(File file) throws IOException {

        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
        String destinationDir = uploadService.getNameDirectory();

        String fileName = file.getName().replace("." +
                Optional.ofNullable(file.getName())
                        .filter(f -> f.contains("."))
                        .map(f -> f.substring(file.getName().lastIndexOf(".") + 1)).get(), "");

        Dimension pgsize = ppt.getPageSize();
        XSLFSlide[] slide = ppt.getSlides();
        BufferedImage img = null;
        File file1 = new File(destinationDir + fileName + ".pdf");
        if (!file1.exists()) {
            file1.createNewFile();
        }
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(file1.getAbsolutePath()));

            doc.open();

            for (int i = 0; i < slide.length; i++) {
                img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();

                //clear the drawing area
                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                //render
                slide[i].draw(graphics);
                File file2 = new File(destinationDir + "images" + i + ".png");
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(file2.getAbsolutePath());
                javax.imageio.ImageIO.write(img, "png", out);
                ppt.write(out);
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(file2.getAbsolutePath());
                doc.setPageSize(new com.itextpdf.text.Rectangle(image.getScaledWidth(), image.getScaledHeight()));
                doc.newPage();
                image.setAbsolutePosition(0, 0);
                doc.add(image);

                file2.delete();
                System.out.println("Image successfully created");
                out.close();
            }
            doc.close();
            file.delete();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file1;
    }

    public File docx(File file_docx) {
        String destinationDir = uploadService.getNameDirectory();

        String fileName = file_docx.getName().replace(".docx", "");
        File file_pdf = new File(destinationDir + fileName + ".pdf");
        try {
            long start = System.currentTimeMillis();

            // 1) Load DOCX into XWPFDocument
            InputStream is = new FileInputStream(new File(file_docx.getAbsolutePath()));
            XWPFDocument document = new XWPFDocument(is);

            // 2) Prepare Pdf options
            PdfOptions options = PdfOptions.create();

            if (file_docx.exists()) {
                // 3)  Convert XWPFDocument to Pdf

                OutputStream out = new FileOutputStream(file_pdf);
                PdfConverter.getInstance().convert(document, out, options);

                System.err.println("Generate pdf/HelloWorld.pdf with "
                        + (System.currentTimeMillis() - start) + "ms");
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        file_docx.delete();
        return file_pdf;
    }
}
