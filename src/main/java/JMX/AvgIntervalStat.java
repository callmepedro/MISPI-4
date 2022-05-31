package JMX;


import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Getter
@Setter
public class AvgIntervalStat implements AvgIntervalStatMBean {

    private static final AvgIntervalStat avgIntervalStat = new AvgIntervalStat();

    private long lastHit = 0;
    private long sumIntervals = 0;

    public static AvgIntervalStat getInstance() {
        return avgIntervalStat;
    }

    @Override
    public void commit(long pointsCount) {
        if (lastHit != 0) {
            long curInterval = System.currentTimeMillis() - lastHit;

            long averageInterval = (sumIntervals + curInterval) / (pointsCount);
            sumIntervals += curInterval;

            System.out.println(averageInterval);
        }
        lastHit = System.currentTimeMillis();
    }
}
