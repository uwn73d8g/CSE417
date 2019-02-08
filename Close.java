/**
* @author Daniel Mariselli
*/
import java.util.*;
//sorting methods
import java.util.Collections;
//point methods
import java.awt.geom.Point2D;

public class Close {
	public static void main(String[] args){
		Calendar ts = Calendar.getInstance();
        long t1 = ts.getTimeInMillis();

		ArrayList<Point2D> arrayList = new ArrayList<Point2D>();
		ArrayList<Point2D> xList = new ArrayList<Point2D>();
		ArrayList<Point2D> yList = new ArrayList<Point2D>();
        Scanner kbd = new Scanner (System.in);

        
        while (kbd.hasNextDouble()) {
            double a = kbd.nextDouble();
            double b = kbd.nextDouble();
            arrayList.add(new Point2D.Double(a, b));
            xList.add(new Point2D.Double(a, b));
            yList.add(new Point2D.Double(a, b));
        }

        sort(xList, 0);
        sort(yList, 1);
        ClPair best = cp(xList, yList);

        Calendar ts2 = Calendar.getInstance();
        long t2 = ts2.getTimeInMillis();
        //System.out.println(t2-t1);

        System.out.println("(" + best.getP1().getX() + "," + 
        	best.getP1().getY() + ")-(" + best.getP2().getX() + "," + best.getP2().getY() 
        	+ ")=" + best.getDist());

	}

	public static ClPair cp(ArrayList<Point2D> a, ArrayList<Point2D> b) {
		if (a.size()>3) {
			ArrayList<Point2D> xL = new ArrayList<Point2D>();
			ArrayList<Point2D> xR = new ArrayList<Point2D>();
			ArrayList<Point2D> yL = new ArrayList<Point2D>();
			ArrayList<Point2D> yR = new ArrayList<Point2D>();
			
			//midpoint
			double mid = ((a.get((a.size()/2)-1).getX())+(a.get((a.size()/2)).getX()))/2.0;

			//setting xLR
			for (int i=0; i<a.size(); i++){
				if ((a.get(i).getX())<(a.get(a.size()/2).getX()))
					xL.add(new Point2D.Double(a.get(i).getX(), a.get(i).getY()));
				else 
					xR.add(new Point2D.Double(a.get(i).getX(), a.get(i).getY()));
			}

			//setting yLR
			for (int i=0; i<b.size(); i++){
				if (b.get(i).getX()<mid)
					yL.add(new Point2D.Double(b.get(i).getX(), b.get(i).getY()));
				else
					yR.add(new Point2D.Double(b.get(i).getX(), b.get(i).getY()));
			}

			ClPair right = cp(xR, yR);
			ClPair left = cp(xL, yL);

			ClPair best;

			if (right.getDist()-left.getDist() <= 0)
				best = right;
			else 
				best = left;

			double delta = best.getDist();
			ArrayList<Point2D> yPrime = new ArrayList<Point2D>();

			for (int i=0; i<b.size(); i++){
				if ((b.get(i).getX()>=(mid-delta))&&(b.get(i).getX()<=(mid+delta)))
					yPrime.add(b.get(i));
			}
			
			for (int i=0; i<yPrime.size()-1; i++){
				for (int l=i+1; l<yPrime.size(); l++){
					if (yPrime.get(i).getY()+delta > yPrime.get(l).getY() && yPrime.get(i).getY()-delta < yPrime.get(l).getY()){
						double deltaprime = yPrime.get(i).distance(yPrime.get(l));
						if (deltaprime < delta){
							delta = deltaprime;
							best = new ClPair(yPrime.get(i), yPrime.get(l), delta);
						}
					}
				}
			}
			return best;
		}

		else {
			ClPair brute = bruteForce(a);
			return brute;
		}
	}

	public static ClPair bruteForce(ArrayList<Point2D> a) {
		ClPair shortest = new ClPair(a.get(0), a.get(1), a.get(0).distance(a.get(1)));
		for (int i=0; i<a.size()-1; i++){
			for (int j=i+1; j<a.size(); j++){
				if (a.get(i).distance(a.get(j))<shortest.getDist())
					shortest = new ClPair(a.get(i), a.get(j), a.get(i).distance(a.get(j)));
			}
		}
		return shortest;
	}

	public static class ClPair {
		Point2D x;
		Point2D y;
		double dist;
		public ClPair(Point2D a, Point2D b, double d) {
			if (a.getX()<b.getX()){
				x = a;
				y = b;
			}
			else { 
				y = a;
				x = b;
			}
			dist = d;
		}
		public double getDist(){
			return dist;
		}
		public Point2D getP1() {
			return x;
		}
		public Point2D getP2() {
			return y;
		}
	}

	public static void sort(ArrayList<Point2D> z, int a){
		if (a==0){
			Collections.sort(z, new Comparator<Point2D>() {
				public int compare(Point2D point1, Point2D point2){
					return Double.compare(point1.getX(), point2.getX());
				}
			});

		}
		else
			Collections.sort(z, new Comparator<Point2D>() {
				public int compare(Point2D point1, Point2D point2){
					return Double.compare(point1.getY(), point2.getY());
				}
			});
	}

}