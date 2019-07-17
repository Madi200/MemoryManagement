package controller;

import java.util.Comparator;

import model.HelperProcess;

public class StartTimeComparison implements Comparator<HelperProcess>{

	@Override
	public int compare(HelperProcess p1, HelperProcess p2) {
		// TODO Auto-generated method stub
		if( p1.getArrivalTime() == p2.getArrivalTime()){
			return 0;
		}else if( p1.getArrivalTime() > p2.getArrivalTime()){
			return 1;
		}
		return -1;
	}

}
