package com.aurg.webscrap.io;

import com.aurg.webscrap.MainUX;
import com.aurg.webscrap.data.DataRoot;
import com.aurg.webscrap.data.DataRoot.TYPE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class OutputResult {

 	 protected static Logger logger = Logger.getLogger( OutputResult.class.getName());
 	 TYPE type; 
 	 OutputExcel ExcelOut;

 	 public String open( ) 
 	 { 
 		 return "";
 	 }
 	 
 	 public void write( DataRoot result )
 	 {
 		 
 	 }
 	 
 	 public void close()
 	 {
 		 
 	 }
		
	 public static String WriteFailList(TYPE type ) throws Exception
	 {
        logger.info( "called" );	        
		String filename;
        Date dt = new Date();
        
        if( MainUX.FailList.size() == 0 ) return "";
        
        if( type == TYPE.AuctionInput )
        	filename = "경매조회실패_MMdd_kkmmss";
        else
        	filename = "사건조회실패_MMdd_kkmmss";
        	
        SimpleDateFormat sdf = new SimpleDateFormat( filename ); 		 

        filename= "./output/" + sdf.format(dt).toString() + ".csv";       
		
        BufferedWriter out = new BufferedWriter(new FileWriter( filename));

        // CSV column head를 출력한다. 모든 output 객체의 getFailColumnHead()를 호출하면 되므로 0번째 데이타에서 호출함. 
        out.write ( ((DataRoot)MainUX.FailList.get(0)).getFailColumnHead() + "\n" );
        // 실제 오류정보들을 출력한다. 
        for(int i=0; i< MainUX.FailList.size(); i++) {            
       	    out.write ( ((DataRoot)MainUX.FailList.get(i)).getFailInfo() + "\n" );
        }
        
        logger.info( filename + " 생성완료!! " );	        
        
        out.close();
 		
		return filename;
	 }
}
