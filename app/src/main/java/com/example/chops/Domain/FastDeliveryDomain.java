package com.example.chops.Domain;

public class FastDeliveryDomain {
    private String title;
    private String pic;
    private double star;
    private int time;

    public FastDeliveryDomain(String title, String pic, double star, int time) {
        this.title = title;
        this.pic = pic;
        this.star = star;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
