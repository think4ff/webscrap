package com.aurg.webscrap.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.aurg.webscrap.MainUX;
import com.aurg.webscrap.data.DataAuctionInput;
import com.aurg.webscrap.data.DataRoot.TYPE;
import com.aurg.webscrap.data.DataSagunInput;

public class InputCSV {
	String filePath; 
	protected static Logger logger = Logger.getLogger( InputCSV.class.getName());

	
	public InputCSV(String filePath) {
		super();
		this.filePath = filePath;				
	}

	public boolean ReadFile( ArrayList<Object> inputList )
	{
        TYPE inputType;
        int  error_count=0;
  	    logger.info( "called" );

  	  
        // InputList Initialize
  	    inputList.clear();
  	    
	    try {
	        ////////////////////////////////////////////////////////////////
	        BufferedReader in = new BufferedReader(new FileReader(filePath));
	        String s;

	        // Read column header 
	        s = in.readLine();
	        if( s == null ) 
	        {
	        	in.close();
	        	return false;
	        }
	        
	        while ((s = in.readLine()) != null) {
	          // System.out.println(s);
	          String [] ss = s.split(",");	          

	          if( ss == null || ss.length != 3 || ss[0].equals("") || ss[1].equals("") || ss[2].equals("") )
	          {
	        	  MainUX.log.out( "입력 데이타 오류 : " + s ); 
	        	  error_count++;
	        	  continue;
	          }
	          // 첫번째 필드인 PrimaryKey가 12byte이면 경매입력
	          if( ss[0].length() == 12){
	        	  inputType = TYPE.AuctionInput;	
	          }
	          else // SaGun 사건검색
	          {
	        	  inputType = TYPE.SaGunInput;	        	  
	          }  
/*	          else
	          {
	        	  MainUX.log.out("입력데이타의 형식 오류 (데이터 무시됨) : " + s);
	        	  logger.info("입력데이타의 형식 오류 (데이터 무시됨) : " + s );
	        	  continue;
	          }*/
	          
	          if(  inputType == TYPE.AuctionInput ) // 경매조회의 경우를 구분
	          {
	        	  inputList.add(new DataAuctionInput(ss[0],ss[1],ss[2].replaceAll(" ", "")));
	          }
	          else // 사건조회의 경우
	          {
	        	  inputList.add(new DataSagunInput(ss[0],ss[1],ss[2].replaceAll(" ", "")));
	          }
	          
	        }
	        
	        MainUX.log.out( "전체 입력 데이타 오류 갯수 : " + error_count );       	    
	        in.close();
	    	
	        ////////////////////////////////////////////////////////////////
	    } catch (IOException e) {
	    	MainUX.log.out(e.toString());	          
	        return false;
	    }	    
	    
		return true;		
	}
}
