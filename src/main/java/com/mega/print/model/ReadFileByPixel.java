package com.mega.print.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReadFileByPixel {
    private int countBlackPixel = 0;
    private int countWhitePixel = 0;
    private int countColorPixel = 0;
    private int countAllPixel = 0;
    private int [] arraynumberPage ;


    public ReadFileByPixel(int [] numberPage){
        this.arraynumberPage = numberPage;
        countBlackPixel = 0;
        countWhitePixel = 0;
        countColorPixel = 0;
        countAllPixel = 0;
    }

    private double percentageBlackColor(int countBlackPixel2,int countAllPixel2){
        return ((double) countBlackPixel2/countAllPixel2) * 100;
    }

    private double percentageWhiteColor(int countWhitePixel2,int countAllPixel2 ){
        return ((double) countWhitePixel2/countAllPixel2) * 100;
    }

    private double percentageСoloredСolor(int countColorPixel2,int countAllPixel2){
        return ((double) countColorPixel2/countAllPixel2) * 100;
    }

    public void readFileByPixel(String path,ResponsePDFAndPercentageColor responsePDFAndPercentageColor,int numberPage) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        int countWhitePixel1 = 0;
        int countBlackPixel1 = 0;
        int countColorPixel1 =0;
        int  countAllPixel1 =0;
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {

                Color color = new Color(image.getRGB(col, row));
                if (color.getBlue() == color.getRed() && color.getRed() == color.getGreen() && color.getBlue() == 255) {
                    countWhitePixel++;
                    countWhitePixel1++;
                } else if (color.getBlue() == color.getRed() && color.getRed() == color.getGreen() && color.getBlue() != 255) {
                    countBlackPixel++;
                    countBlackPixel1++;
                } else {
                    countColorPixel++;
                    countColorPixel1++;
                }
                countAllPixel++;
                countAllPixel1++;
            }
        }


                responsePDFAndPercentageColor.addValue(numberPage,percentageBlackColor(countBlackPixel1,countAllPixel1),
                        percentageWhiteColor(countWhitePixel1,countAllPixel1),
                        percentageСoloredСolor(countColorPixel1,countAllPixel1));


    }

    public void getPercentageByAllPage(ResponsePDFAndPercentageColor responsePDFAndPercentageColor){
        responsePDFAndPercentageColor.setGetAllPercentage(new WCBPercentage(percentageWhiteColor(countWhitePixel,countAllPixel),
                percentageBlackColor(countBlackPixel,countAllPixel),percentageСoloredСolor(countColorPixel,countAllPixel)));
    }
}
