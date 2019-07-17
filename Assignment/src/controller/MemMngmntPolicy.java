package controller;

import java.util.Queue;

import model.HelperProcess;

public class MemMngmntPolicy {

	public void firstFit(int memSize,Queue<HelperProcess> processQueue){
		
		int Memory[] = new int[memSize];
		for( int index = 0; index < memSize; index++ ){
			Memory[ index ] = 0;
		}
	}
}
