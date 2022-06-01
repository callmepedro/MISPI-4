package JMX;


import lombok.Getter;
import lombok.Setter;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

@Getter
@Setter
public class AvgIntervalStat extends NotificationBroadcasterSupport implements AvgIntervalStatMBean {

    private static final AvgIntervalStat avgIntervalStat = new AvgIntervalStat();
    private int sequenceNumber = 0;

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

            sendNotification(new Notification("AvgIntervalStat", this, sequenceNumber++,
                    System.currentTimeMillis(), String.format("Current average interval is %d", averageInterval)));
        }
        lastHit = System.currentTimeMillis();
    }
}
