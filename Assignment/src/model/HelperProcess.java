package model;
import java.util.ArrayList;



public class HelperProcess {

	private int p_id;
	private long arrivalTime;
	private long lifeTime;
	private int numOfChunks;
	public ArrayList<Integer> chunks = new ArrayList<Integer>();
	private int memPtr;
	
	
	public HelperProcess (int processId, long arrTime, long lifeTime, int totalChunks, ArrayList<Integer> cks){
		this.p_id = processId;
		this.arrivalTime = arrTime;
		this.lifeTime = lifeTime;
		this.numOfChunks = totalChunks;
		chunks = cks;
	}
	
	public ArrayList<Integer> getChunk(){
		return this.chunks;
	}

	public void printMetaData(){
		System.out.println("ProcessID :"+ this.p_id);
		System.out.println("Arrival Time :"+ this.arrivalTime);
		System.out.println("Life Time :"+ this.lifeTime);
		System.out.println("Total Chunks :"+ this.numOfChunks);
		System.out.println(chunks.size());
		System.out.println(chunks);
	}
	
	public int getPId(){
		return this.p_id;
	}
	
	public long getArrivalTime(){
		return this.arrivalTime;
	}
	
	public long getLifeTime(){
		return this.lifeTime;
	}
	
	public void setMemPtr(int ptr){
		this.memPtr = ptr;
	}
	
	public int getMemPtr(){
		return this.memPtr;
	}
	
	public ArrayList<Integer> getChunks(){
		return this.chunks;
	}
	
	public int totalSize() {
	     int sum = 0; 
	     for (int i : this.chunks)
	         sum = sum + i;
	     return sum;
	}
	
	public int compareTo(HelperProcess process) {
		//return this.arrivalTime.compareTo(process.arrivalTime);
	    return new Double(this.arrivalTime).compareTo(new Double(process.arrivalTime));
	}
}

