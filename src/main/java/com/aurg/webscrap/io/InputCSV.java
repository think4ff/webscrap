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
	        	  MainUX.log.out( "�Է� ����Ÿ ���� : " + s ); 
	        	  error_count++;
	        	  continue;
	          }
	          // ù��° �ʵ��� PrimaryKey�� 12byte�̸� ����Է�
	          if( ss[0].length() == 12){
	        	  inputType = TYPE.AuctionInput;	
	          }
	          else // SaGun ��ǰ˻�
	          {
	        	  inputType = TYPE.SaGunInput;	        	  
	          }  
/*	          else
	          {
	        	  MainUX.log.out("�Էµ���Ÿ�� ���� ���� (������ ���õ�) : " + s);
	        	  logger.info("�Էµ���Ÿ�� ���� ���� (������ ���õ�) : " + s );
	        	  continue;
	          }*/
	          
	          if(  inputType == TYPE.AuctionInput ) // �����ȸ�� ��츦 ����
	          {
	        	  inputList.add(new DataAuctionInput(ss[0],ss[1],ss[2].replaceAll(" ", "")));
	          }
	          else // �����ȸ�� ���
	          {
	        	  inputList.add(new DataSagunInput(ss[0],ss[1],ss[2].replaceAll(" ", "")));
	          }
	          
	        }
	        
	        MainUX.log.out( "��ü �Է� ����Ÿ ���� ���� : " + error_count );       	    
	        in.close();
	    	
	        ////////////////////////////////////////////////////////////////
	    } catch (IOException e) {
	    	MainUX.log.out(e.toString());	          
	        return false;
	    }	    
	    
		return true;		
	}
}
