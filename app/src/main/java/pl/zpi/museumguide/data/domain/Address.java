package pl.zpi.museumguide.data.domain;

/**
 * Created by Jakub Licznerski on 19.03.2017.
 */

public class Address {

    private String country;
    private String postalCode;
    private String city;
    private String street;
    private String number;

    public Address() {

    }

    public Address(String country, String postalCode, String city, String street, String number) {
        this.country = country;
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
