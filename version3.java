
// Ziyi Zhuang
// 1531016
// uwn73d8g
import java.util.*;
// import java.math.double;
public class version3 {
    private static double best;
    private static Point[] arr;

    public static void main(String args[]) {
        List<Point> col = new ArrayList<Point>();
        arr = new Point[2];

        best = Double.POSITIVE_INFINITY;

        // Using Scanner for Getting Input from User
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            double x = in.nextDouble();
            double y = in.nextDouble();
            Point p = new Point(x, y);
            col.add(p);
        }
        in.close();

        // Point[] sortedX = sort(array, 0);
        Collections.sort(col, new Comparator<Point>() {
            public int compare(Point obj1, Point obj2) {
                return (int) (obj1.getX() - obj2.getX());
            }
        });
        Point[] sortedX = col.toArray(new Point[col.size()]);

        System.out.println("sorted2 " + sortedX[2].getX() + sortedX[2].getY());
        System.out.println("sorted3 " + sortedX[3].getX() + sortedX[3].getY());
        System.out.println("sorted5 " + sortedX[4].getX() + sortedX[4].getY());

        long begin = System.currentTimeMillis();
        double dis = closestPair(sortedX);

        long end = System.currentTimeMillis();
        System.out.println("version: 3");
        System.out.println("number of points: " + col.size());
        System.out.println("x1: " + arr[0].getX() + "\ty1: " + arr[0].getY());
        System.out.println("x2: " + arr[1].getX() + "\ty2: " + arr[1].getY());
        System.out.println("distance: " + dis);
        System.out.println("time: " + (end - begin) + "ms");
    }

    public static double closestPair(Point[] sortedX) {
        // sort by X coordinates
        if (sortedX.length <= 1) {
            return Double.MAX_VALUE;
        }
        double mid = sortedX[(sortedX.length) / 2].getX();
        double firstHalf = closestPair(Arrays.copyOfRange(sortedX, 0, (sortedX.length) / 2));
        double secondHalf = closestPair(Arrays.copyOfRange(sortedX, (sortedX.length) / 2, sortedX.length));
        double min = Math.min(firstHalf, secondHalf);
        ArrayList<Point> close = new ArrayList<Point>();
        for (int i = 0; i < sortedX.length; i++) {
            double dis = Math.abs(sortedX[i].getX() - mid);
            if (dis < min) {
                close.add(sortedX[i]);
            }
        }
        // sort by Y coordinates
        Collections.sort(close, new Comparator<Point>() {
            public int compare(Point obj1, Point obj2) {
                return (int) (obj1.getY() - obj2.getY());
            }
        });
        Point[] near = close.toArray(new Point[close.size()]);

        for (int i = 0; i < near.length; i++) {
            int k = 1;
            while (i + k < near.length && near[i + k].getY() < near[i].getY() + min) {
                if (min > near[i].distance(near[i + k])) {
                    min = near[i].distance(near[i + k]);
                    System.out.println("near    " + near[i].distance(near[i + k]));
                    System.out.println("best  " + best);

                    if (near[i].distance(near[i + k]) < best) {
                        best = near[i].distance(near[i + k]);
                        arr[0] = near[i];
                        arr[1] = near[i + k];
                        // System.out.println(arr[0].getX());
                        // System.out.println(arr[1].getX());

                    }
                }
                k++;
            }
        }
        return min;
    }
    // public static Point[] sort(Point[] input, int coor) {
    // int N = input.length;
    // if (N <= 1) return input;
    // Point[] a = new Point[N/2];
    // Point[] b = new Point[N - N/2];
    // for (int i = 0; i < a.length; i++)
    // a[i] = input[i];
    // for (int i = 0; i < b.length; i++)
    // b[i] = input[i + N/2];
    // if (coor == 0) {
    // return mergeX(a, b);
    // } else {
    // return mergeY(a, b);
    // }
    // // return merge(mergesort(a), mergesort(b));
    // }
    // public static Point[] sort(Point[] arr, int coor) {
    // if (arr.length < 2) {
    // return arr;
    // }
    // Point[] left = Arrays.copyOfRange(arr, 0, arr.length / 2);
    // Point[] right = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
    // if (coor == 0) {
    // mergeX(left, right);
    // } else {
    // mergeY(left, right);

    // }
    // return arr;
    // }
    // private static Point[] mergeX(Point[] a, Point[] b) {
    // Point[] c = new Point[a.length + b.length];
    // int i = 0, j = 0;
    // for (int k = 0; k < c.length; k++) {
    // if (i >= a.length) c[k] = b[j++];
    // else if (j >= b.length) c[k] = a[i++];
    // else if (a[i].getX() <= b[j].getX()) c[k] = a[i++];
    // else c[k] = b[j++];
    // }
    // return c;
    // }
    // private static Point[] mergeY(Point[] a, Point[] b) {
    // Point[] c = new Point[a.length + b.length];
    // int i = 0, j = 0;
    // for (int k = 0; k < c.length; k++) {
    // if (i >= a.length) c[k] = b[j++];
    // else if (j >= b.length) c[k] = a[i++];
    // else if (a[i].getY() <= b[j].getY()) c[k] = a[i++];
    // else c[k] = b[j++];
    // }
    // return c;
    // }
    // private static void merge(Point[] first, Point[] second, Point[] result, int
    // coor) {
    // // Index Position in first array - starting with first element
    // int iFirst = 0;

    // // Index Position in second array - starting with first element
    // int iSecond = 0;

    // // Index Position in merged array - starting with first position
    // int iMerged = 0;

    // // Compare elements at iFirst and iSecond,
    // // and move smaller element at iMerged
    // if (coor == 0) {
    // while (iFirst < first.length && iSecond < second.length) {
    // if (first[iFirst].getX() < second[iSecond].getX()) {
    // result[iMerged] = first[iFirst];
    // iFirst++;
    // } else {
    // result[iMerged] = second[iSecond];
    // iSecond++;
    // }
    // iMerged++;
    // }
    // } else {
    // while (iFirst < first.length && iSecond < second.length) {
    // if (first[iFirst].getY() < (second[iSecond].getY())) {
    // result[iMerged] = first[iFirst];
    // iFirst++;
    // } else {
    // result[iMerged] = second[iSecond];
    // iSecond++;
    // }
    // iMerged++;
    // }
    // }

    // // copy remaining elements from both halves - each half will have already
    // sorted
    // // elements
    // System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
    // System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
    // }
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
        double result = (double)(Math.sqrt((double)(deltaX * deltaX + deltaY * deltaY)));
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