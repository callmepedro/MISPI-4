package Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Hit {

    @Id
    @GeneratedValue
    private Long Id;

    private double x;
    private double y;
    private double r;
    private String curTime;
    private double execTime;
    private boolean result;

    public Hit(double x, double y, double r, String curTime, double execTime, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.curTime = curTime;
        this.execTime = execTime;
        this.result = result;
    }

}
