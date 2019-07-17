package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import model.HelperProcess;
import model.MemoryMap;

public class FFHelperMethods {

	
	public void removeDuplicate(ArrayList<MemoryMap> map,int start){
		for(int i=0; i<map.size();i++){
			if(map.get(i).getStartIndex() == start){
				map.remove(i);
			}
		}
	}
	
	public void printMemMap(ArrayList<MemoryMap> map){
		
		System.out.print("Memory Map :");
		map.sort(new StartIndexComparator());
		for(int i=0; i<map.size();i++){
			map.get(i).print();
		}
	}
	
	
	
/*	public static Queue<HelperProcess> apnaSort(Queue<HelperProcess> inputQueue){
		ArrayList<HelperProcess> tmp = new ArrayList<HelperProcess>();
		tmp.addAll(inputQueue);
		tmp.sort(new StartTimeComparison());
		inputQueue.addAll(tmp);
		return inputQueue;
	}*/
}
