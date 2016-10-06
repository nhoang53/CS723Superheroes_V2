package edu.orangecoastcollege.cs273.nhoang53.cs723superheroes_v2;

/**
 * Nguyen Hoang C02288487
 * Project 2: CS273 Superheroes
 */

public class Superheroes {
    private String userName;
    private String name;
    private String superpower;
    private String oneThing;
    private String imageName;

    public Superheroes() {
        this.userName = "";
        this.name = "";
        this.superpower = "";
        this.oneThing = "";
        this.imageName = "";
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getSuperpower() {
        return superpower;
    }

    public String getOneThing() {
        return oneThing;
    }

    public String getImageName() {
        return imageName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public void setOneThing(String oneThing) {
        this.oneThing = oneThing;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
