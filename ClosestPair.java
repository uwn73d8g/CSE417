//Alex Chou

import java.util.*;

public class ClosestPair{
	public ArrayList<Point> pointList = new ArrayList<Point>();
	public ArrayList<Point> xSorted = new ArrayList<Point>();
	public ArrayList<Point> ySorted = new ArrayList<Point>();
	private Point p1;
	private Point p2;
	private double dist;


	public Point getP1(){
		return p1;
	}
	public Point getP2(){
		return p2;
	}
	public double getDist(){
		return dist;
	}
	public void setP1(Point p){
		p1=p;
	}
	public void setP2(Point p){
		p2=p;
	}
	public void setDist(double d){
		dist=d;
	}

	//main
	public static void main(String[] args){
        ClosestPair cp=new ClosestPair();
		cp.go(args);
	}

	public void go(String[] args){
        setDist(Double.MAX_VALUE);
		Scanner sc = new Scanner(System.in);
		// int noPoints=sc.nextInt();
		while(sc.hasNextDouble()){
		 	double x=sc.nextDouble();
			double y=sc.nextDouble();
			Point tempPoint=new Point(x,y);
			pointList.add(tempPoint);
			xSorted.add(tempPoint);
			ySorted.add(tempPoint);
		}
		mergeSortX(xSorted);
		mergeSortY(ySorted);
        closestPair(xSorted,ySorted);
        System.out.println(getP1());
        System.out.println(getP2());

		if (getP1().getX()>getP2().getX()){
			System.out.println("("+getP1().getX()+", "+getP1().getY()+")-("+getP2().getX()+", "+getP2().getY()+")="+getDist());	
		}
		else{
			if (getP1().getX()<getP2().getX()){
				System.out.println("("+getP2().getX()+", "+getP2().getY()+")-("+getP1().getX()+", "+getP1().getY()+")="+getDist());	
			}
			else{
				if (getP1().getY()>getP2().getY()){
					System.out.println("("+getP1().getX()+", "+getP1().getY()+")-("+getP2().getX()+", "+getP2().getY()+")="+getDist());				
				}
				else{
					System.out.println("("+getP2().getX()+", "+getP2().getY()+")-("+getP1().getX()+", "+getP1().getY()+")="+getDist());		
				}
			}
		}
	}

	//sorting algorithm O(nlogn) in theory
	//Mergesort X
    public void mergeSortX(ArrayList<Point> a){
		mergeSortX(a,0,a.size()-1);
    }
    public void mergeSortX(ArrayList<Point> a,int lo,int hi){
		if (hi<=lo){}
		else{
	    	int mid=(lo+hi)/2;
	    	mergeSortX(a,lo,mid);
	    	mergeSortX(a,mid+1,hi);
	    	mergeX(a,lo,mid,mid+1,hi);
		}
    }
    public void mergeX(ArrayList<Point> a,int lo1, int hi1, int lo2, int hi2){
		ArrayList<Point> b = new ArrayList<Point>(hi2-lo1+1);
		int p1=lo1;
		int p2=lo2;
		int k=0;
		while((p1<=hi1)&&(p2<=hi2)){
			if (a.get(p1).getX()<a.get(p2).getX()){
			    b.add(k++,a.get(p1++));
			}
		    else{
			    b.add(k++,a.get(p2++));
			}
		}
		while(p1<=hi1)
		    b.add(k++,a.get(p1++));
		while(p2<=hi2)
		    b.add(k++,a.get(p2++));
		for(int i=0; i<k; ++i)
		    a.set(lo1+i,b.get(i));
	}


	//Mergesort Y
	public void mergeSortY(ArrayList<Point> a){
		mergeSortY(a,0,a.size()-1);
    }
    public void mergeSortY(ArrayList<Point> a,int lo,int hi){
		if (hi<=lo){}
		else{
	    	int mid=(lo+hi)/2;
	    	mergeSortY(a,lo,mid);
	    	mergeSortY(a,mid+1,hi);
	    	mergeY(a,lo,mid,mid+1,hi);
		}
    }
    public void mergeY(ArrayList<Point> a,int lo1, int hi1, int lo2, int hi2){
		ArrayList<Point> b = new ArrayList<Point>(hi2-lo1+1);
		int p1=lo1;
		int p2=lo2;
		int k=0;
		while((p1<=hi1)&&(p2<=hi2)){
			if (a.get(p1).getY()<a.get(p2).getY()){
			    b.add(k++,a.get(p1++));
			}
		    else{
			    b.add(k++,a.get(p2++));
			}
		}
		while(p1<=hi1)
		    b.add(k++,a.get(p1++));
		while(p2<=hi2)
		    b.add(k++,a.get(p2++));
		for(int i=0; i<k; ++i)
		    a.set(lo1+i,b.get(i));
	}

	public void closestPair(ArrayList<Point> a, ArrayList<Point> b){
		int size=a.size();
		if (size<=3){
			bruteForce(a);
		}
		else{
			ArrayList<Point> xLeft=new ArrayList<Point>(size/2);
			ArrayList<Point> xRight=new ArrayList<Point>((size/2)+1);
			ArrayList<Point> yLeft=new ArrayList<Point>(size/2);
			ArrayList<Point> yRight=new ArrayList<Point>((size/2)+1);
			for (int i=0; i<size; i++){
				if (i<(size/2)){
					xLeft.add(a.get(i));
				}
				else{
					xRight.add(a.get(i));
				}
			}
			double xSplit=((a.get(size/2).getX()+a.get((size/2)+1).getX())/2.0);
			for (int j=0; j<b.size(); j++){
				if (b.get(j).getX()<xSplit){
					yLeft.add(b.get(j));
				}
				else{
					yRight.add(b.get(j));
				}
            }
            closestPair(xRight,yRight);

			closestPair(xLeft,yLeft);
			double delta=getDist();
			ArrayList<Point> yPrime=new ArrayList<Point>(size);
			for (int k=0; k<b.size(); k++){
				if ((b.get(k).getX()>(xSplit-delta))&&(b.get(k).getX()<(xSplit+delta))){
					yPrime.add(b.get(k));
				}
			}
			for (int l=0; l<yPrime.size()-1; l++){
				for (int m=l+1; m<yPrime.size(); m++){
					if ((b.get(m).getY()>(b.get(l).getY()-delta))&&(b.get(m).getY()<(b.get(l).getY()+delta))){
                    //    if (m < yPrime.size() && yPrime.get(m).getY() < yPrime.get(l).getY() + delta){ 
                        if (distanceCalc(b.get(m),b.get(l))<getDist()){
							setDist(distanceCalc(b.get(m),b.get(l)));
							setP1(b.get(l));
							setP2(b.get(m));
						}
					}
				}
			}
		}
	}

	public void bruteForce(ArrayList<Point> a){
		int size=a.size();
		for (int i=0; i<size-1; i++){
			for (int j=i+1; j<size; j++){
				if (dist==0){
					dist=distanceCalc(a.get(i),a.get(j));
				}
				if (distanceCalc(a.get(i),a.get(j))<dist){
					setDist(distanceCalc(a.get(i),a.get(j)));
					setP1(a.get(i));
					setP2(a.get(j));
				}
			}
		}
	}

	public double distanceCalc(Point a, Point b){
		double xDif=a.getX()-b.getX();
		double yDif=a.getY()-b.getY();
		xDif=Math.pow(xDif,2.0);
		yDif=Math.pow(yDif,2.0);
		return Math.sqrt(xDif+yDif);
	}
}