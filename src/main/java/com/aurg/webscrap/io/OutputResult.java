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
        	filename = "�����ȸ����_MMdd_kkmmss";
        else
        	filename = "�����ȸ����_MMdd_kkmmss";
        	
        SimpleDateFormat sdf = new SimpleDateFormat( filename ); 		 

        filename= "./output/" + sdf.format(dt).toString() + ".csv";       
		
        BufferedWriter out = new BufferedWriter(new FileWriter( filename));

        // CSV column head�� ����Ѵ�. ��� output ��ü�� getFailColumnHead()�� ȣ���ϸ� �ǹǷ� 0��° ����Ÿ���� ȣ����. 
        out.write ( ((DataRoot)MainUX.FailList.get(0)).getFailColumnHead() + "\n" );
        // ���� ������������ ����Ѵ�. 
        for(int i=0; i< MainUX.FailList.size(); i++) {            
       	    out.write ( ((DataRoot)MainUX.FailList.get(i)).getFailInfo() + "\n" );
        }
        
        logger.info( filename + " �����Ϸ�!! " );	        
        
        out.close();
 		
		return filename;
	 }
}
