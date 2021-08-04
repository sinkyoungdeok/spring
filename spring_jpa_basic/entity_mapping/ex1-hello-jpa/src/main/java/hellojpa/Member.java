package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id
    private Long id;
    @Column(name = "name")
    private String username;

    public Member() {
    }
}
