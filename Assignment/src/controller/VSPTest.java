package controller;

import java.util.ArrayList;
import java.util.Queue;

import model.HelperProcess;
import model.MemoryMap;

public class VSPTest {

	private int[]Memory;
	int policyParam;
	
	public VSPTest(int memSize, int policy) {
		// TODO Auto-generated constructor stub
		initializeMem(memSize);
		this.policyParam = policy;
	}
	
	// initialize memory with zeroes, means all holes
	private void initializeMem(int memSize){
		this.Memory = new int[memSize];
		for( int index = 0; index < memSize; index++ ){
			this.Memory[ index ] = 0;
		}
	}
		
	public int isSpaceAvailable(int totalSpaceRequired){
		
		int rslt=0;
		
		switch (this.policyParam) {
		case 1:
			rslt = isSpaceInFF(totalSpaceRequired);
			break;
		case 2:
			rslt = isSpaceInBF(totalSpaceRequired);
			System.out.println("Not yet Impemented");
			break;
		default:
			break;
		}
		
		return rslt;
	}
	// find the space = total space required
	private int isSpaceInFF(int totalSpaceRequired){
		
		int count=0;

		for( int index = 0; index < this.Memory.length; index++ ){
			if( this.Memory[index] != 0 ){
				count=0;
			}else if( this.Memory[index] == 0 ) {
				count++;
				if( count == totalSpaceRequired ){
					//System.out.println("First fit : " + count);
					return index;
				}
			}
		}
		
		return -1;
	}
	
	// find the space = total space required
	private int isSpaceInBF(int totalSpaceRequired){
		
		return -1;
	}
	
	private void allocateMemory(int ptr, HelperProcess currentProcess){
		
		for( int index = ptr ; index >ptr-currentProcess.totalSize(); index -- ){
			this.Memory[ index ] = currentProcess.getPId();
		}
		
		//printMemoryState();
	}
	
	public void deallocateMemory(HelperProcess currentProcess){
		System.out.println("Deallocating...");
		for( int index = 0; index < this.Memory.length; index ++){
			if( this.Memory [ index ] == currentProcess.getPId()){
				this.Memory [ index ] = 0;
			}
		}
	}
	
	public boolean addProcessToMem(HelperProcess currentProcess){
		//System.out.println("Current Process Id :"+currentProcess.getPId());
			
			int memPtr = isSpaceAvailable( currentProcess.totalSize());
			//System.out.println("Memory Pointer = "+memPtr);
			//System.out.println("total Space Required :"+currentProcess.totalSize());
			int tmpPtr = memPtr-currentProcess.totalSize()+1;
			//System.out.println("Mem index Ptr :"+memPtr);
			
			if( !(memPtr==-1)){
				System.out.println("MM moves Process "+currentProcess.getPId()+" to memory");
				currentProcess.setMemPtr(memPtr);
				allocateMemory( memPtr, currentProcess );
				
				return true;
			}
			
		return false;
	}
	
	public void printMemoryState(){
		System.out.println("Memory State :");
		for( int index = 0; index < this.Memory.length; index++ ){
			System.out.print("|" + this.Memory[ index ] +"| ");
		}
	}
	
	public ArrayList<MemoryMap> freeSpace(ArrayList<MemoryMap> memMap){
			
	        String output ="";
	        int count=0;
	       	for(int i=0; i< Memory.length; i++){
		        if(Memory[i]==0 && count==i){
		          //  System.out.println(i);
	            	output+=i+",";
		            count++;
		            
		        }else if(Memory[i]!=0){
		            //System.out.println(i);
		        	output+= "-1";
		            count=i+1;
		        }
		    }
		        
	       	String[] split1 =output.split("-1");
	    	for(int i=0; i< split1.length; i++){
	    		//System.out.println(split1[i]);
	    		String[] split2 = split1[i].split(",");
	    		if(!(split2[0].equals("")) && !(split2[split2.length-1].equals(""))){
	    			String rslt = ""+split2[0]+"-"+split2[split2.length-1]+": holes";
		    		//System.out.println(rslt);
	    			for(int j=0; j<memMap.size();j++){
	    				if(memMap.get(j).getStartIndex() == Integer.parseInt(split2[0])){
	    					memMap.remove(j);
	    				}if(memMap.get(j).getEndIndex() == Integer.parseInt(split2[split2.length-1])){
	    					
	    					int val1 = memMap.get(j).getEndIndex()-memMap.get(j).getStartIndex();
	    					int val2 = Integer.parseInt(split2[split2.length-1]) + Integer.parseInt(split2[0]);
	    					if(val2>val1){
	    						memMap.remove(j);
	    					}if(justHoles(memMap)){
	    						System.out.println("Overlap ******************************************** ");
	    						memMap.removeAll(memMap);
	    					}
	    				}
	    			}
		    		memMap.add(new MemoryMap("Hole",Integer.parseInt(split2[0]) , Integer.parseInt(split2[split2.length-1])));
		    		//System.out.println("End :"+split2[split2.length-1]);
	    		}

	    	}
	
	    return memMap;	
	}
	
	private boolean justHoles(ArrayList<MemoryMap> memMap){
		for(MemoryMap process: memMap){
			if(!(process.getName().equals("Hole"))){
				return false;
			}
		}
		
		return true;
	}
}
