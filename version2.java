// Ziyi Zhuang
// 1531016
// uwn73d8g
import java.util.*;
public class version2 {
    public static void main(String args[]) {
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
        Point[] array = col.toArray(new Point[col.size()]);
        long begin = System.currentTimeMillis();
        double dis = closestPair(array, arr);
        long end = System.currentTimeMillis();
        System.out.println("version: 2");
        System.out.println("number of points: " + col.size());
        System.out.println("x1: " + arr[0].getX() + "\ty1: " + arr[0].getY());
        System.out.println("x2: " + arr[1].getX() + "\ty2: " + arr[1].getY());
        System.out.println("distance: " + dis);
        System.out.println("time: " + (end-begin) + "ms");
    }

    public static double closestPair(Point[] array, Point[] arr) {
        // sort by X coordinates
        if (array.length <= 1) {
            return Double.MAX_VALUE;
        }
        Point[] sortedX = sort(array, 0);

        double mid = sortedX[sortedX.length / 2].getX();

        double firstHalf = closestPair(Arrays.copyOfRange(sortedX, 0, (sortedX.length) / 2), arr);
        double secondHalf = closestPair(Arrays.copyOfRange(sortedX, (sortedX.length) / 2, sortedX.length), arr);
        double min = Math.min(firstHalf, secondHalf);
        ArrayList<Point> close = new ArrayList<Point>();
        for (int i = 0; i < sortedX.length; i++) {
            double dis = Math.abs(sortedX[i].getX() - mid);
            if (dis < min) {
                close.add(sortedX[i]);
            }
        }

        // sort by Y coordinates
        Point[] near = sort(close.toArray(new Point[close.size()]), 1);
        for (int i = 0; i < near.length; i++) {
            int k = 1;
            while (i + k < near.length && near[i + k].getY() < near[i].getY() + min && i != i + k) {
                if (min > near[i].distance(near[i + k])) {
                    arr[0] = near[i];
                    arr[1] = near[i+k];
                    min = near[i].distance(near[i + k]);
                }
                k++;
            }
        }
        return min;
    }

    public static Point[] sort(Point[] arr, int coor) {
        if (arr.length == 1 || arr.length == 0) {
            return arr;
        }
        Point[] left = Arrays.copyOfRange(arr, 0, arr.length / 2);
        Point[] right = Arrays.copyOfRange(arr, arr.length/ 2, arr.length);
        merge(left, right, arr, coor);
        return arr;
    }

    private static void merge(Point[] first, Point[] second, Point[] result, int coor) {
        // Index Position in first array - starting with first element
        int iFirst = 0;

        // Index Position in second array - starting with first element
        int iSecond = 0;

        // Index Position in merged array - starting with first position
        int iMerged = 0;

        // Compare elements at iFirst and iSecond,
        // and move smaller element at iMerged
        if (coor == 0) {
            while (iFirst < first.length && iSecond < second.length) {
                if (first[iFirst].getX() < (second[iSecond].getX())) {
                    result[iMerged] = first[iFirst];
                    iFirst++;
                } else {
                    result[iMerged] = second[iSecond];
                    iSecond++;
                }
                iMerged++;
            }
        } else {
            while (iFirst < first.length && iSecond < second.length) {
                if (first[iFirst].getY() < (second[iSecond].getY())) {
                    result[iMerged] = first[iFirst];
                    iFirst++;
                } else {
                    result[iMerged] = second[iSecond];
                    iSecond++;
                }
                iMerged++;
            }            
        }

        // copy remaining elements from both halves - each half will have already sorted
        // elements
        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
    }
}

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        double deltaX = y - other.y;
        double deltaY = x - other.x;
        double result = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
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