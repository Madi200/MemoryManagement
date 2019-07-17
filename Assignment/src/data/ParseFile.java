package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import model.HelperProcess;



public class ParseFile {

	public ParseFile(){
		
	}
	
	public ArrayList<HelperProcess> readFile(String fileName) throws IOException{
		ArrayList<HelperProcess> input = new ArrayList<HelperProcess>();

		  File file = new File(fileName);
		 
		  BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  String st;
		  int count =0;
		  String storeString="";
		  int pid = 0;
		  String arrTime="";
		  String lifeTime="";
		  String totalChunks="";
		  ArrayList<Integer> chunks = new ArrayList<Integer>();
		  
		  try {
			  st = br.readLine();
			while ((st = br.readLine()) != null){
				storeString +=st;
			      switch(count) {
			         case 0 :
			            //System.out.println(st);
			            pid=Integer.parseInt(st.trim());
			           // System.out.println(pid);
			            count++;
			            break;
			         case 1 :
				            //System.out.println(st);
				            String[] parts = st.split("\\s+");
				            arrTime =  parts[0].trim(); 
				            lifeTime = parts[1].trim();
				         //   System.out.println(arrTime);
				           // System.out.println(lifeTime);
				            count++;
				            break;
			         case 2 :
				            //System.out.println(st);
				            String[] split = st.split("\\s+");
				            totalChunks = split[0].trim();
				            //System.out.println(totalChunks);
				            count++;
				            for (int i=1; i<split.length; i++){
				            	//System.out.print(split[i]+" ");
				            	chunks.add(Integer.parseInt(split[i].trim()));
				            }
/*				            System.out.println();
				            System.out.println("@@@@@@@@@@@@@@@@@@");
				            for(int i=0;i<chunks.size();i++){
				            	System.out.print(chunks.get(i)+" ");
				            }*/
				            break;
			         case 3 :
			        	 	//System.out.println();
				            //System.out.println("********************");
			        	 	//System.out.println(chunks.size());
			        	 
			        	 	//System.out.println(chunks);
				            input.add(new HelperProcess( pid,Long.parseLong(arrTime), Long.parseLong(lifeTime), Integer.parseInt(totalChunks), chunks));
				            //System.out.println(input.get(0).getChunkSize());
				            chunks= new ArrayList<Integer>();
				            //System.out.println(chunks);
				            count=0;
				            break; 
			         default :
				            System.out.println("Invalid");
				            break;
			}
			    
		}
		  }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			br.close();
		}
	
	return input;
	}
	
}
