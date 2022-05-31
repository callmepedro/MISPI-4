package Beans;

import javax.faces.bean.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import Entity.Hit;
import JMX.AvgIntervalStat;
import JMX.PointsStat;
import Utils.DatabaseManager;
import lombok.*;


@Getter
@Setter
@ManagedBean(name = "hitsData", eager = true)
@SessionScoped
public class HitsData implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private DatabaseManager dbManager = new DatabaseManager();
    private List<Hit> hitsList = dbManager.getHitList();

    private double x;
    private double r = 2;

    private String curTime;
    private double execTime;
    private boolean result;

    private double yVal1 = -3, yVal2 = -2, yVal3 = -1,
            yVal4 = 0, yVal5 = 1, yVal6 = 2, yVal7 = 3;
    private boolean yState1, yState2, yState3, yState4, yState5, yState6, yState7;


    @Getter
    @Setter
    static class YPair{
        private boolean state;
        private double value;

        public YPair(boolean state, double value) {
            this.state = state;
            this.value = value;
        }
    }

    synchronized public void addHit() {
        long startTime = System.nanoTime();

        YPair y1 = new YPair(yState1, yVal1);
        YPair y2 = new YPair(yState2, yVal2);
        YPair y3 = new YPair(yState3, yVal3);
        YPair y4 = new YPair(yState4, yVal4);
        YPair y5 = new YPair(yState5, yVal5);
        YPair y6 = new YPair(yState6, yVal6);
        YPair y7 = new YPair(yState7, yVal7);

        List<YPair> yPairs = Arrays.asList(y1, y2, y3, y4, y5, y6, y7);
        for (YPair yPair : yPairs) {
            if (yPair.isState()) {
                curTime = dateFormat.format(new Date());
                result = checkHitResult(svgX, svgY, svgR);
                execTime = (double) (System.nanoTime() - startTime) / 1000000;

                Hit newHit = new Hit(x, yPair.getValue(), r, curTime, execTime, result);
                if(dbManager.addHit(newHit)) {
                    hitsList.add(newHit);
                    PointsStat.getInstance().addPoint(result);
                }
            }
        }
    }

    private double svgX;
    private double svgY;
    private double svgR;

    public void svgAddHit() {
        long startTime = System.nanoTime();

        curTime = dateFormat.format(new Date());
        result = checkHitResult(svgX, svgY, svgR);
        execTime = (double) (System.nanoTime() - startTime) / 1000000;

        Hit newHit = new Hit(svgX, svgY, svgR, curTime, execTime, result);
        if (dbManager.addHit(newHit)){
            hitsList.add(newHit);
            PointsStat.getInstance().addPoint(result);
            AvgIntervalStat.getInstance().commit(hitsList.size());
        }
    }

    public void clear() {
        if (dbManager.clearList()) {
            hitsList.clear();
        }
    }


    public List<Hit> getHitsList() {
        return hitsList;
    }


    private boolean checkHitResult(double x, double y, double r) {
        return checkTriangle(x, y, r) || checkRectangle(x, y, r) || checkCircle(x, y, r);
    }

    private boolean checkTriangle(double x, double y, double r){
        return x >= 0 && y <= 0 && 2*(x-y) <= r;
    }

    private boolean checkRectangle(double x, double y, double r){
        return x >= -r && x <= 0 && y >= 0 && y <= r;
    }

    private boolean checkCircle(double x, double y, double r){
        return x >= 0 && y >= 0 && x*x + y*y <= r*r;
    }

    public static boolean methodForTest(int x) {
        return x > 0;
    }
}
