package controller;

import java.util.Comparator;

import model.MemoryMap;

public class StartIndexComparator implements Comparator<MemoryMap>{

	@Override
	public int compare(MemoryMap map1, MemoryMap map2) {
		// TODO Auto-generated method stub
				if( map1.getStartIndex() == map2.getStartIndex()){
					return 0;
				}else if( map1.getStartIndex() > map2.getStartIndex()){
					return 1;
				}
				return -1;
	}

}
