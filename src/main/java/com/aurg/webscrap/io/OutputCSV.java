package com.aurg.webscrap.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.aurg.webscrap.MainUX;
import com.aurg.webscrap.data.DataAuctionInput;
import com.aurg.webscrap.data.DataAuctionOutput;
import com.aurg.webscrap.data.DataRoot;
import com.aurg.webscrap.data.DataRoot.TYPE;

public class OutputCSV extends OutputResult{

	TYPE type;
	
	public OutputCSV(TYPE type) {
		super();
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	BufferedWriter out;
	protected static Logger logger = Logger.getLogger( OutputCSV.class.getName());
	String filename;
	 
	
	@Override
	public String open() {
		// TODO Auto-generated method stub
		logger.info( "called");
		
		if(type == TYPE.AuctionInput)
			filename = "��Ű˻����_MMdd_kkmmss";
		else 
			filename = "��ǰ˻����_MMdd_kkmmss";
		
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(filename); 		 
        filename= "./output/"+sdf.format(dt).toString() + ".csv";
				
        try {
			out = new BufferedWriter(new FileWriter( filename));
			
			if(type == TYPE.AuctionInput)
			{
				DataAuctionOutput tmpOutput = new DataAuctionOutput( new DataAuctionInput("����","����", "2013����12345"));
				out.write ( tmpOutput.getSuccessColumnHead() + "\n" );				
			}
			else // ��ǰ˻��� csv�� �����Ҷ� column header ��ºκ��� �����ؾ� ��. �׷����� ������??
			{
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error( filename + " ��������!", e);
			MainUX.log.out( filename + " ��������!");
		}		
		return filename;
	}

	@Override
	public void write(DataRoot result) {
		// TODO Auto-generated method stub
	 	 
	    logger.info( "called" );	        
   	    try {
			out.write ( result.toString() + "\n" );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(filename + " write����!", e);
			MainUX.log.out(filename + " write����!");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	    logger.info( "called" );	        
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(filename + " close ����!", e);
			MainUX.log.out(filename + " close ����!");
		}
	}

}
