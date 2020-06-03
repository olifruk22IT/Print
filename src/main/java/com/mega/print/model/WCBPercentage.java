package com.mega.print.model;

public class WCBPercentage {
    private String wite_percentage;
    private String black_percentage;
    private String colored_percentage;
    public WCBPercentage(double wite_percentage,double black_percentage, double colored_percentage){
        this.black_percentage =String.format("%.2f",black_percentage);
        this.colored_percentage = String.format("%.2f",colored_percentage);
        this.wite_percentage = String.format("%.2f",wite_percentage);
    }

    public String getWite_percentage() {
        return wite_percentage;
    }

    public String getBlack_percentage() {
        return black_percentage;
    }

    public String getColored_percentage() {
        return colored_percentage;
    }
}
