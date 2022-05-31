package JMX;

import lombok.Getter;
import lombok.Setter;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

@Getter
@Setter
public class PointsStat extends NotificationBroadcasterSupport implements PointsStatMBean {

    private static final PointsStat pointsStat = new PointsStat();
    private int sequenceNumber = 0;

    private int pointsCount = 0;
    private int missesCount = 0;

    public static PointsStat getInstance() {
        return pointsStat;
    }

    @Override
    public void addPoint(boolean isHit) {
        if (!isHit) missesCount++;
        pointsCount++;
        if (pointsCount % 15 == 0) {
            sendNotification(new Notification("PointsStat", this, sequenceNumber++,
                    System.currentTimeMillis(), "The number of points is multiple of 15"));
        }
    }
}
