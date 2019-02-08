// Ziyi Zhuang
// 1531016
// uwn73d8g
import java.util.*;

public class version1 {
    public static void main(String args[]) 
    { 
        List<Point> col = new ArrayList<Point>();
        Point[] arr = new Point[2];
        // Using Scanner for Getting Input from User 
        Scanner in = new Scanner(System.in); 
        while (in.hasNext()) {
            double x = in.nextDouble();
            double y = in.nextDouble();
            Point p = new Point(x, y);
            col.add(p);
        }
        in.close();
        double min = Double.MAX_VALUE;
        long begin = System.currentTimeMillis();

        for (int i = 0; i < col.size(); i++) {
            for (int j = 0; j < col.size(); j++) {
                if (i != j) {
                    double dis = col.get(i).distance(col.get(j));
                    if (dis < min) {
                        min = dis;
                        arr[0] = col.get(i);
                        arr[1] = col.get(j);
                    }
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("version: 1");
        System.out.println("number of points: " + col.size());
        System.out.println("x1: " + arr[0].getX() + "\ty1: " + arr[0].getY());
        System.out.println("x2: " + arr[1].getX() + "\ty2: " + arr[1].getY());
        System.out.println("distance: " + min);
        System.out.println("time: " + (end-begin) + "ms");
    }
    
}


class Point{
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance (Point other) {
        double deltaX = y - other.y;
        double deltaY = x - other.x;
        double result = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        return result; 
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double compareX(Point other) { 
        return this.x - other.x; 
    } 

    public double compareY(Point other) { 
        return this.y - other.y; 
    } 
}