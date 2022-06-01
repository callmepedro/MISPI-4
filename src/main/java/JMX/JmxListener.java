package JMX;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

public class JmxListener implements ServletContextListener {

    public static final String pointsStatName = "JMX:type=mbean,name=PointsStat";
    public static final String avgIntervalStatName = "JMX:type=mbean,name=AvgIntervalStat";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName pointsStatObjectName = new ObjectName(pointsStatName);
            ObjectName avgIntervalStatObjectName = new ObjectName(avgIntervalStatName);

            mBeanServer.registerMBean(PointsStat.getInstance(), pointsStatObjectName);
            mBeanServer.registerMBean(AvgIntervalStat.getInstance(), avgIntervalStatObjectName);

            JmxNotificationListener jmxNotificationListener = new JmxNotificationListener();
            mBeanServer.addNotificationListener(pointsStatObjectName, jmxNotificationListener, null, null);
            mBeanServer.addNotificationListener(avgIntervalStatObjectName, jmxNotificationListener, null, null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName pointsStatObjectName = new ObjectName(pointsStatName);
            ObjectName avgIntervalStatObjectName = new ObjectName(avgIntervalStatName);

            mBeanServer.unregisterMBean(pointsStatObjectName);
            mBeanServer.unregisterMBean(avgIntervalStatObjectName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
