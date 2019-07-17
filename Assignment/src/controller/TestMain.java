package controller;


import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import model.HelperProcess;
import model.MemoryMap;
import data.ParseFile;

public class TestMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String fileName = "F:\\Practice\\Assignment3\\input1.txt";
		ArrayList<HelperProcess> processData;
		ArrayList<MemoryMap> memMap = new ArrayList<MemoryMap>();
		
		ParseFile dataObj = new ParseFile();
		processData = dataObj.readFile(fileName);
		
		processData.sort(new StartTimeComparison());
		
		
		/*for(int i=0; i<processData.size(); i++){
			processData.get(i).printMetaData();
			System.out.println("***************************");
		}*/
		
		Queue<HelperProcess> processQueue = new LinkedList<HelperProcess>();
		processQueue.addAll(processData);
		
	/*	Iterator<HelperProcess> iterator = processQueue.iterator();
		while(iterator.hasNext()){
			iterator.next().printMetaData();
		  System.out.println("***************************");
		}*/
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		Queue<HelperProcess> inputQueue = new LinkedList<HelperProcess>();
		Queue<HelperProcess> runningQueue = new LinkedList<HelperProcess>();
		Queue<HelperProcess> tempQueue = new LinkedList<HelperProcess>();
		Queue<HelperProcess> waitQueue = new LinkedList<HelperProcess>();
		//		 		(MemSize,policy)	
		VSPTest vspObj = new VSPTest(2000, 1);
		FFHelperMethods helper = new FFHelperMethods();
		
		// 	Virtual Clock
		for( int timer=0; timer <100000 ; timer++ ){
			if(waitQueue.size()>0 && vspObj.isSpaceAvailable(waitQueue.peek().totalSize())!=-1){
				Iterator<HelperProcess> waitQIterator = waitQueue.iterator();
				while(waitQIterator.hasNext()){
					HelperProcess currentProcess = waitQIterator.next();
					inputQueue.add(currentProcess);
					waitQIterator.remove();
					//printInputQueue(inputQueue);
				}
			}
			inputQueue.addAll(matchArrivalTime(processQueue, timer)) ;
			tempQueue = matchFinishTime(runningQueue, timer);
			
			/*if(waitQueue.size()>0){
				Iterator<HelperProcess> waitQIterator = waitQueue.iterator();
				while(waitQIterator.hasNext()){
					HelperProcess currentProcess = waitQIterator.next();
					inputQueue.add(currentProcess);
					waitQIterator.remove();
				}
				//apnaSort(inputQueue);
			}*/
			
			if(inputQueue.size()>0 && vspObj.isSpaceAvailable(inputQueue.peek().totalSize())!=-1){

				Iterator<HelperProcess> inputQIterator = inputQueue.iterator();
				while(inputQIterator.hasNext()){
					HelperProcess currentProcess = inputQIterator.next();
					if (vspObj.addProcessToMem(currentProcess) == true){
						//System.out.println("Clock Time :" + timer);
						runningQueue.add(currentProcess);
						inputQIterator.remove();	
						printInputQueue(inputQueue);
						int tmpPtr = currentProcess.getMemPtr()-currentProcess.totalSize()+1;
						String map =tmpPtr+"-"+currentProcess.getMemPtr()+": Process "+currentProcess.getPId();
						//System.out.println(map);
						helper.removeDuplicate(memMap, tmpPtr);
						memMap.add(new MemoryMap("Process "+currentProcess.getPId(), tmpPtr, currentProcess.getMemPtr()));
						//memMap.add(currentProcess.getPId(),map);
						vspObj.freeSpace(memMap);
						 
						helper.printMemMap(memMap);
									
					}

				}
				
			}
			else if(inputQueue.size()>0 && vspObj.isSpaceAvailable(inputQueue.peek().totalSize())==-1){
				System.out.println("THis case<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+inputQueue.peek().getPId());
				Iterator<HelperProcess> inputQIterator = inputQueue.iterator();
				while(inputQIterator.hasNext()){
					HelperProcess currentProcess = inputQIterator.next();				
					waitQueue.add(currentProcess);
					inputQIterator.remove();
					printInputQueue(inputQueue);
				}

			}
			
			if (tempQueue.size()>0){
				Iterator<HelperProcess> tempIterator = tempQueue.iterator();
				while( tempIterator.hasNext() ){
					HelperProcess currentProcess = tempIterator.next();
					System.out.println("Clock Time :" + timer);
					System.out.println("Process "+currentProcess.getPId()+" is Removed from Memory");
					vspObj.deallocateMemory(currentProcess);
					tempIterator.remove();
				
					int tmpPtr = currentProcess.getMemPtr()-currentProcess.totalSize()+1;
					helper.removeDuplicate(memMap, tmpPtr);
				}
			/*	if(memMap.size()>tmpHelperProcess.getPId()){
					System.out.println("PID:"+tmpHelperProcess.getPId());
					memMap.remove(tmpHelperProcess.getPId());
				}*/
				//int start = tmpHelperProcess.getMemPtr()-tmpHelperProcess.totalSize()+1;
				//System.out.println("start :"+ start);
				//removeDuplicate(memMap, start);
				vspObj.freeSpace(memMap);
				helper.printMemMap(memMap);
				//print(tempQueue);
			}
			
			
		}
		Queue<HelperProcess> remainingQueue = new LinkedList<HelperProcess>();
		remainingQueue = runningQueue;
		System.out.println("Running Queue :"+remainingQueue.peek().getPId());
		
		Iterator<HelperProcess> tempIterator = remainingQueue.iterator();
		while( tempIterator.hasNext() ){
			HelperProcess currentProcess = tempIterator.next();
			System.out.println("Process "+currentProcess.getPId()+" is Removed from Memory");
			vspObj.deallocateMemory(currentProcess);
			tempIterator.remove();
			//tmpHelperProcess = currentProcess;
			int tmpPtr = currentProcess.getMemPtr()-currentProcess.totalSize()+1;
			helper.removeDuplicate(memMap, tmpPtr);
		}

		vspObj.freeSpace(memMap);
		helper.printMemMap(memMap);
		
		
	}
	
/*	public static void print(Queue<HelperProcess> queue){
		Iterator<HelperProcess> iterator2 = queue.iterator();
		while(iterator2.hasNext()){
			iterator2.next().printMetaData();
		  System.out.println("***************************");
		}
	}*/
	
	public static void printInputQueue(Queue<HelperProcess>  queue){
		Iterator<HelperProcess> iterator3 = queue.iterator();
		String output ="";
		while(iterator3.hasNext()){
			HelperProcess crntProcess = iterator3.next(); 
			output +=" "+crntProcess.getPId();
		}
		System.out.println("\t Input Queue:["+output.trim()+"]");
	}
	// Create input Queue & return all the process whom arrivalTime matched with virtual clock current time
	public static Queue<HelperProcess> matchArrivalTime(Queue<HelperProcess> pQueue,int time){
		Queue<HelperProcess> inputQueue = new LinkedList<HelperProcess>();
		Iterator<HelperProcess> iterator = pQueue.iterator();
		String output="";
		boolean isMatch = false;
		while( iterator.hasNext() ){
			HelperProcess currentProcess = iterator.next(); 
			if ( currentProcess.getArrivalTime() == time ){
				output+=" "+currentProcess.getPId();
				if(isMatch)
					System.out.println("	 Process "+currentProcess.getPId()+" Arrives");
				else
					System.out.println("t = "+time+":  Process "+currentProcess.getPId()+" Arrives");
				
				inputQueue.add(currentProcess);
				printInputQueue(inputQueue);
				//System.out.println("\t Input Queue:["+output.trim()+"]");
				iterator.remove();
				isMatch = true;
			}
		}
		
		return inputQueue;
	}

	public static Queue<HelperProcess> matchFinishTime(Queue<HelperProcess> runningQueue,int time){
		//System.out.println("match finish");
		Queue<HelperProcess> removed = new LinkedList<HelperProcess>();
		Iterator<HelperProcess> iterator2 = runningQueue.iterator();
		//System.out.println("iterator"+runningQueue.size());
		while( iterator2.hasNext() ){
		HelperProcess currentProcess = iterator2.next(); 
		
		  if ( currentProcess.getLifeTime() == time ){
			  System.out.println("Process "+currentProcess.getPId() +" Completes");
			  removed.add(currentProcess);
			  iterator2.remove();
		  }
		}
		
		return removed;
	}


}
