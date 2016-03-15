package com.aurg.webscrap.io;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import jxl.WorkbookSettings;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aurg.webscrap.MainUX;
import com.aurg.webscrap.data.DataRoot;
import com.aurg.webscrap.data.DataSagunOutput;

public class OutputExcel extends OutputResult {

  	private JxlUtil excelUtil;
	WritableWorkbook workBook;
	String [] sheetNames;
	int    [] rowIdx;
  	protected static Logger logger = Logger.getLogger( OutputExcel.class.getName());

	public OutputExcel() {
		super();
		// TODO Auto-generated constructor stub
        excelUtil = new JxlUtil(); 
		sheetNames = new String[] { "전체","인가","폐지","이송","면책","기각","취하","불허가","파산선고","개시" };
		rowIdx = new int[] {0,0,0,0,0,0,0,0,0,0};		
	}
	 
 	@Override
 	public String open() {
 		// TODO Auto-generated method stub
		String fileName = "";
		
        logger.info( "called");
		
		try {
			
	        Date dt = new Date();
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("사건검색결과_MMdd_kkmmss"); 		 
			fileName = "./output/" + sdf.format(dt).toString() + ".xls";       

			// TemporaryFile을 생성하도록 설정한다.
	        WorkbookSettings s = new WorkbookSettings();  
	        s.setUseTemporaryFileDuringWrite(true);	        
	        workBook = excelUtil.createWorkBook( new File(fileName),s);
	        
			// Sheet를 일괄 생성한다.
			for(int i=0; i < sheetNames.length; i++ )
			{
				excelUtil.createSheet(  sheetNames[i], workBook);
				addHeaderToSheet( i );
			}
		}
		catch( Exception e ) {
			logger.error("Excel 생성오류", e);
			MainUX.log.out("Excel 생성오류");
		}
		
        return fileName; 
 	}

 	@Override
 	public void write(DataRoot result) {
 		logger.info( "called");
 		
 		// TODO Auto-generated method stub
		 try {
			typeCheckAndAddtoSheet((DataSagunOutput)result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Excel Write 오류!", e);
			MainUX.log.out("Excel Write 오류!");
		} 		
 	}

 	@Override
 	public void close() {
 		// TODO Auto-generated method stub
 		logger.info( "called");
		try 
		{
			// Column Header를 제외하고 추가된 row가 없을때(rowIdx == 1) 해당 sheet를 삭제한다.즉, 해당 sheet에 실제 데이타가 없기 때문에 삭제한다.
			for(int i=(sheetNames.length-1); i >= 0 ; i-- )
			{
				if( rowIdx[i] == 1 )
				    excelUtil.removeSheet(i, workBook);
			}		
			
			workBook.write();
			workBook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Excel Close 오류!", e);
			MainUX.log.out("Excel Close 오류!");			
		}
 	}
	 
/*	 public String generateSagunResult() throws Exception
	 {
		String fileName = "";
		
        logger.info( "called");
		
		try {
			
	        Date dt = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("사건검색결과_MMdd_kkmmss"); 		 

			fileName = sdf.format(dt).toString() + ".xls";       
	        workBook = excelUtil.createWorkBook( fileName , "전체");
			addHeaderToSheet( 0 );  // Header 추가
			
			// Sheet를 일괄 생성한다.
			for(int i=1; i < sheetNames.length; i++ )
			{
				excelUtil.createSheet(  sheetNames[i], workBook);
				addHeaderToSheet( i );
			}
			
			// output list에서 하나씩 데이타를 빼서 typeCheck하고 Sheet에 추가한다. 
			for(int i=0; i < MainUX.OutputList.size(); i++ )
			{
				DataSagunOutput result = (DataSagunOutput)MainUX.OutputList.get(i);
				typeCheckAndAddtoSheet( result ); 			
			}

			// 생성된 Excel의 각 column width를  지정한다.
			for( int i=0; i < sheetNames.length; i++ )
			{
				WritableSheet targetSheet = workBook.getSheet( sheetNames[i] );			
				targetSheet.setColumnView(0, 32);  // key                  
				targetSheet.setColumnView(1, 41);  // 재판부   
				targetSheet.setColumnView( 2,10);  // 접수일
				targetSheet.setColumnView( 3, 7);  // 채무자  
				targetSheet.setColumnView( 4,10);  // 개시결정일
				targetSheet.setColumnView( 5,10);  // 인가결정일
				targetSheet.setColumnView( 6,10);  // 면책결정일 
				targetSheet.setColumnView( 7,10);  // 폐지결정일  
				targetSheet.setColumnView( 8,17);  // 종국결과 	  		
				targetSheet.setColumnView( 9,14);  // 금지결정	 		
				targetSheet.setColumnView(10,14);  // 개시결정통지서				
			}
			
			// Column Header를 제외하고 추가된 row가 없을때(rowIdx == 1) 해당 sheet를 삭제한다.즉, 해당 sheet에 실제 데이타가 없기 때문에 삭제한다.
			for(int i=(sheetNames.length-1); i >= 0 ; i-- )
			{
				if( rowIdx[i] == 1 )
				    excelUtil.removeSheet(i, workBook);
			}			

		    // excel 파일로 출력
		    excelUtil.flush(workBook);
		}
		catch( Exception e )
		{
			logger.error("Excel 생성오류", e);
			throw e;
		}
		
		return fileName;
	 }*/
	
/*	   인가 : 변제계획인가일이 있으면 인가로 분류, 종국결과는 참조하지 않음.
	   폐지 : 페지결정일이 있거나 종국결과에 폐지가 있으면.
	   이송 : 종국결과에 이송이 있으면.
	   면책 : 면책결정일이 있거나 종국결과에 인용 or 일부인용이 있으면 면책으로 분류
	   기각 : 종국결과에 기각이 있으면. 
	   취하 : 종국결과에 취하 or 신청취하
	   불허가 : 종국결과에 불허가 있으면
	   파산선고 : 종국결과에 파산선고 있으면*/
	 public boolean typeCheckAndAddtoSheet( DataSagunOutput result ) throws Exception 
	 {
		addDataToSheet( 0 /* "전체" */,  result );
		
		// 인가 : 변제계획인가일이 있으면 인가로 분류, 종국결과는 참조하지 않음.
		// 면책 : 면책결정일이 있거나 종국결과에 인용 or 일부인용이 있으면 면책으로 분류
		// 면책결정일이 있고, 종국결과에 인용이 있는 경우가 있는데..이럴경우 면책을 우선으로 분류해야 함. 
		// 그래서 면책을 먼저 체크하도록 순서를 변경함. 
		
		// 면책 : 면책결정이 있거나 종국결과에 인용이 있으면
		if( !result.MyunChekDate.equals("") || result.JongKukResult.indexOf("인용") >= 0 )
		{
			addDataToSheet( 4 /* "면책" */,  result ); 	
		}
		
		//  인가 : 인가결정일이 있으면, 종국결과는 참조하지 않음.
		else if( !result.InGaDate.equals("") )
		{
			addDataToSheet( 1 /* "인가" */,  result ); 	
		}
		
		//  폐지 : 페지결정일이 있거나 종국결과에 폐지가 있으면.
		else if( !result.PageDate.equals("") || result.JongKukResult.indexOf("폐지") >= 0 )
		{
			addDataToSheet( 2 /* "폐지" */,  result ); 	
		}
		
		// 이송 : 종국결과에 이송이 있으면.
		else if( result.JongKukResult.indexOf("이송") >= 0 )
		{
			addDataToSheet( 3 /* "이송" */,  result ); 	
		}
				
		// 기각 : 종국결과에 기각이 있으면. 
		else if( result.JongKukResult.indexOf("기각") >= 0 )
		{
			addDataToSheet( 5 /* "기각" */,  result ); 	
		}
		
		// 취하 : 종국결과에 취하 or 신청취하
		else if( result.JongKukResult.indexOf("취하") >= 0 || result.JongKukResult.indexOf("신청취하") >= 0 )
		{
			addDataToSheet( 6 /* "취하" */,  result ); 	
		}
		
		// 불허가 : 종국결과에 불허가 있으면
		else if( result.JongKukResult.indexOf("불허가") >= 0 )
		{
			addDataToSheet( 7 /* "불허가" */,  result ); 	
		}
		
		// 파산선고 : 종국결과에 파산선고 있으면
		else if( result.JongKukResult.indexOf("파산선고") >= 0 )
		{
			addDataToSheet(8 /* "파산선고" */,  result ); 	
		}
		// 개시 : 종국결과가 ""이면
		else if( result.JongKukResult.indexOf("") >= 0 )  
		{
			addDataToSheet(9 /* "개시" */,  result ); 
		}
		else
		{
			logger.error("Excel Sheet 분류오류 -종국결과 =" + result.JongKukResult );
		}
		
		return true; 		
	 }
	

	// 주어진 Sheet에 Column header를 입력한다. 
	private void addHeaderToSheet( int sheetIdx ) 
	{	
		WritableSheet targetSheet = workBook.getSheet( sheetNames[sheetIdx] );  
		
        // key, 재판부, 접수일, 채무자, 개시결정일, 인가결정일, 면책결정일, 폐지결정일, 종국결과,금지결정,개시결정통지서			
	    excelUtil.addCellToSheet(0, rowIdx[sheetIdx], "key" , null, targetSheet);         // key
	    excelUtil.addCellToSheet(1, rowIdx[sheetIdx], "재판부", null, targetSheet);         // 재판부
	    excelUtil.addCellToSheet(2, rowIdx[sheetIdx], "접수일", null, targetSheet);         // 접수일
	    excelUtil.addCellToSheet(3, rowIdx[sheetIdx], "채무자", null, targetSheet);         // 채무자
	    excelUtil.addCellToSheet(4, rowIdx[sheetIdx], "개시결정일", null, targetSheet);      // 개시결정일
	    excelUtil.addCellToSheet(5, rowIdx[sheetIdx], "인가결정일", null, targetSheet);      // 인가결정일
	    excelUtil.addCellToSheet(6, rowIdx[sheetIdx], "면책결정일", null, targetSheet);      // 면책결정일
	    excelUtil.addCellToSheet(7, rowIdx[sheetIdx], "폐지결정일", null, targetSheet);      // 폐지결정일
	    excelUtil.addCellToSheet(8, rowIdx[sheetIdx], "종국결과", null, targetSheet);       // 종국결과
	    excelUtil.addCellToSheet(9, rowIdx[sheetIdx], "금지결정", null, targetSheet);       // 금지결정
	    excelUtil.addCellToSheet(10, rowIdx[sheetIdx], "개시결정통지서", null, targetSheet);  // 개시결정통지서
	    
	    // Column Width 지정
		targetSheet.setColumnView(0, 17);  // key
		targetSheet.setColumnView(1, 41);  // 재판부   
		targetSheet.setColumnView( 2,10);  // 접수일
		targetSheet.setColumnView( 3, 7);  // 채무자  
		targetSheet.setColumnView( 4,10);  // 개시결정일
		targetSheet.setColumnView( 5,10);  // 인가결정일
		targetSheet.setColumnView( 6,10);  // 면책결정일 
		targetSheet.setColumnView( 7,10);  // 폐지결정일  
		targetSheet.setColumnView( 8,17);  // 종국결과 	  		
		targetSheet.setColumnView( 9,14);  // 금지결정	 		
		targetSheet.setColumnView(10,14);  // 개시결정통지서	
		
	    rowIdx[sheetIdx]++;
	}
	
	// 주어진 Sheet에 결과를 입력한다.
	public void addDataToSheet( int sheetIdx, DataSagunOutput result ) throws Exception
	{
		WritableSheet targetSheet = workBook.getSheet( sheetNames[sheetIdx] ); 
		
		if( targetSheet == null ) 
		{
			excelUtil.createSheet(  sheetNames[sheetIdx], workBook);
			addHeaderToSheet( sheetIdx );
		}
				
        // key, 재판부, 접수일, 채무자, 개시결정일, 인가결정일, 면책결정일, 폐지결정일, 종국결과,금지결정,개시결정통지서			
	    excelUtil.addCellToSheet(0, rowIdx[sheetIdx], result.key , null, targetSheet);             // key
	    excelUtil.addCellToSheet(1, rowIdx[sheetIdx], result.JaePanBu, null, targetSheet);         // 재판부
	    excelUtil.addCellToSheet(2, rowIdx[sheetIdx], result.JeobSuDate, null, targetSheet);       // 접수일
	    excelUtil.addCellToSheet(3, rowIdx[sheetIdx], result.ChaeMuJa, null, targetSheet);         // 채무자
	    excelUtil.addCellToSheet(4, rowIdx[sheetIdx], result.GaeSiDate, null, targetSheet);        // 개시결정일
	    excelUtil.addCellToSheet(5, rowIdx[sheetIdx], result.InGaDate, null, targetSheet);         // 인가결정일
	    excelUtil.addCellToSheet(6, rowIdx[sheetIdx], result.MyunChekDate, null, targetSheet);     // 면책결정일
	    excelUtil.addCellToSheet(7, rowIdx[sheetIdx], result.PageDate, null, targetSheet);         // 폐지결정일
	    excelUtil.addCellToSheet(8, rowIdx[sheetIdx], result.JongKukResult, null, targetSheet);    // 종국결과
	    excelUtil.addCellToSheet(9, rowIdx[sheetIdx], result.KumGiKyulJung, null, targetSheet);    // 금지결정
	    excelUtil.addCellToSheet(10, rowIdx[sheetIdx], result.GaesiKyulJung, null, targetSheet);   // 개시결정통지서
      
	    logger.info("Excel/" + sheetNames[sheetIdx] + "/" +result.toString());
	    // idx 증가
	    rowIdx[sheetIdx]++;
	}

}
