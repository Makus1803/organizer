package com.luv2code.springdemo.mvc;

import java.util.LinkedHashMap;

public class Student {
    private String firstName;
    private String lastName;
    private String country;
    private String favouriteLanguage;
    private String favouriteSystems;

    private LinkedHashMap<String, String> countryOption;
    private LinkedHashMap<String, String> systemOption;


    public Student(){

        //populate country options: used ISO country code
        countryOption = new LinkedHashMap<>();

        countryOption.put("BR", "Brazil");
        countryOption.put("FR", "France");
        countryOption.put("DE", "Germany");
        countryOption.put("IN", "India");
        countryOption.put("PL", "Poland");

        systemOption = new LinkedHashMap<>();
        systemOption.put("Windows", "Windows");
        systemOption.put("Linux", "Linux");
        systemOption.put("AppleShit", "AppleShit");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LinkedHashMap<String, String> getCountryOption() {
        return countryOption;
    }

    public LinkedHashMap<String, String> getSystemOption() {
        return systemOption;
    }

    public String getFavouriteLanguage() {
        return favouriteLanguage;
    }

    public void setFavouriteLanguage(String favouriteLanguage) {
        this.favouriteLanguage = favouriteLanguage;
    }

    public String getFavouriteSystems() {
        return favouriteSystems;
    }

    public void setFavouriteSystems(String favouriteSystems) {
        this.favouriteSystems = favouriteSystems;
    }
}
