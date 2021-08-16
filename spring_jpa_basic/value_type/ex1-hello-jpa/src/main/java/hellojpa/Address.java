package hellojpa;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    public Address() {
    }

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
