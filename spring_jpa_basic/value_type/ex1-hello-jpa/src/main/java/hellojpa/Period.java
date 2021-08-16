package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    public Period() {
    }

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
