package hellojpa;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Delivery  {

    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;


    public Delivery(Long id, Address address) {
        this.id = id;
        this.address = address;
    }

    public Delivery() {

    }
}
