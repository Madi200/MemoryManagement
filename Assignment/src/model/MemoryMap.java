package model;

import model.HelperProcess;

public class MemoryMap {
	
	private int memStartIndex;
	private int memEndIndex;
	private int id;
	String name;
	
	public MemoryMap(String name,int start, int end) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.memStartIndex = start;
		this.memEndIndex = end;
	}
	
	public int  getStartIndex(){
		return this.memStartIndex;
	}
	
	public int  getEndIndex(){
		return this.memEndIndex;
	}
	
	public String getName(){
		return this.name;
	}
	public void print(){
		System.out.println("\t"+this.memStartIndex+"-"+this.memEndIndex+" :"+this.name);
	}
	
	public int compareTo(MemoryMap map) {
		//return this.arrivalTime.compareTo(process.arrivalTime);
	    return new Double(this.memStartIndex).compareTo(new Double(map.memStartIndex));
	}
}

