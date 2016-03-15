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
		sheetNames = new String[] { "��ü","�ΰ�","����","�̼�","��å","�Ⱒ","����","���㰡","�Ļ꼱��","����" };
		rowIdx = new int[] {0,0,0,0,0,0,0,0,0,0};		
	}
	 
 	@Override
 	public String open() {
 		// TODO Auto-generated method stub
		String fileName = "";
		
        logger.info( "called");
		
		try {
			
	        Date dt = new Date();
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("��ǰ˻����_MMdd_kkmmss"); 		 
			fileName = "./output/" + sdf.format(dt).toString() + ".xls";       

			// TemporaryFile�� �����ϵ��� �����Ѵ�.
	        WorkbookSettings s = new WorkbookSettings();  
	        s.setUseTemporaryFileDuringWrite(true);	        
	        workBook = excelUtil.createWorkBook( new File(fileName),s);
	        
			// Sheet�� �ϰ� �����Ѵ�.
			for(int i=0; i < sheetNames.length; i++ )
			{
				excelUtil.createSheet(  sheetNames[i], workBook);
				addHeaderToSheet( i );
			}
		}
		catch( Exception e ) {
			logger.error("Excel ��������", e);
			MainUX.log.out("Excel ��������");
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
			logger.error("Excel Write ����!", e);
			MainUX.log.out("Excel Write ����!");
		} 		
 	}

 	@Override
 	public void close() {
 		// TODO Auto-generated method stub
 		logger.info( "called");
		try 
		{
			// Column Header�� �����ϰ� �߰��� row�� ������(rowIdx == 1) �ش� sheet�� �����Ѵ�.��, �ش� sheet�� ���� ����Ÿ�� ���� ������ �����Ѵ�.
			for(int i=(sheetNames.length-1); i >= 0 ; i-- )
			{
				if( rowIdx[i] == 1 )
				    excelUtil.removeSheet(i, workBook);
			}		
			
			workBook.write();
			workBook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Excel Close ����!", e);
			MainUX.log.out("Excel Close ����!");			
		}
 	}
	 
/*	 public String generateSagunResult() throws Exception
	 {
		String fileName = "";
		
        logger.info( "called");
		
		try {
			
	        Date dt = new Date();
	        SimpleDateFormat sdf = new SimpleDateFormat("��ǰ˻����_MMdd_kkmmss"); 		 

			fileName = sdf.format(dt).toString() + ".xls";       
	        workBook = excelUtil.createWorkBook( fileName , "��ü");
			addHeaderToSheet( 0 );  // Header �߰�
			
			// Sheet�� �ϰ� �����Ѵ�.
			for(int i=1; i < sheetNames.length; i++ )
			{
				excelUtil.createSheet(  sheetNames[i], workBook);
				addHeaderToSheet( i );
			}
			
			// output list���� �ϳ��� ����Ÿ�� ���� typeCheck�ϰ� Sheet�� �߰��Ѵ�. 
			for(int i=0; i < MainUX.OutputList.size(); i++ )
			{
				DataSagunOutput result = (DataSagunOutput)MainUX.OutputList.get(i);
				typeCheckAndAddtoSheet( result ); 			
			}

			// ������ Excel�� �� column width��  �����Ѵ�.
			for( int i=0; i < sheetNames.length; i++ )
			{
				WritableSheet targetSheet = workBook.getSheet( sheetNames[i] );			
				targetSheet.setColumnView(0, 32);  // key                  
				targetSheet.setColumnView(1, 41);  // ���Ǻ�   
				targetSheet.setColumnView( 2,10);  // ������
				targetSheet.setColumnView( 3, 7);  // ä����  
				targetSheet.setColumnView( 4,10);  // ���ð�����
				targetSheet.setColumnView( 5,10);  // �ΰ�������
				targetSheet.setColumnView( 6,10);  // ��å������ 
				targetSheet.setColumnView( 7,10);  // ����������  
				targetSheet.setColumnView( 8,17);  // ������� 	  		
				targetSheet.setColumnView( 9,14);  // ��������	 		
				targetSheet.setColumnView(10,14);  // ���ð���������				
			}
			
			// Column Header�� �����ϰ� �߰��� row�� ������(rowIdx == 1) �ش� sheet�� �����Ѵ�.��, �ش� sheet�� ���� ����Ÿ�� ���� ������ �����Ѵ�.
			for(int i=(sheetNames.length-1); i >= 0 ; i-- )
			{
				if( rowIdx[i] == 1 )
				    excelUtil.removeSheet(i, workBook);
			}			

		    // excel ���Ϸ� ���
		    excelUtil.flush(workBook);
		}
		catch( Exception e )
		{
			logger.error("Excel ��������", e);
			throw e;
		}
		
		return fileName;
	 }*/
	
/*	   �ΰ� : ������ȹ�ΰ����� ������ �ΰ��� �з�, ��������� �������� ����.
	   ���� : ������������ �ְų� ��������� ������ ������.
	   �̼� : ��������� �̼��� ������.
	   ��å : ��å�������� �ְų� ��������� �ο� or �Ϻ��ο��� ������ ��å���� �з�
	   �Ⱒ : ��������� �Ⱒ�� ������. 
	   ���� : ��������� ���� or ��û����
	   ���㰡 : ��������� ���㰡 ������
	   �Ļ꼱�� : ��������� �Ļ꼱�� ������*/
	 public boolean typeCheckAndAddtoSheet( DataSagunOutput result ) throws Exception 
	 {
		addDataToSheet( 0 /* "��ü" */,  result );
		
		// �ΰ� : ������ȹ�ΰ����� ������ �ΰ��� �з�, ��������� �������� ����.
		// ��å : ��å�������� �ְų� ��������� �ο� or �Ϻ��ο��� ������ ��å���� �з�
		// ��å�������� �ְ�, ��������� �ο��� �ִ� ��찡 �ִµ�..�̷���� ��å�� �켱���� �з��ؾ� ��. 
		// �׷��� ��å�� ���� üũ�ϵ��� ������ ������. 
		
		// ��å : ��å������ �ְų� ��������� �ο��� ������
		if( !result.MyunChekDate.equals("") || result.JongKukResult.indexOf("�ο�") >= 0 )
		{
			addDataToSheet( 4 /* "��å" */,  result ); 	
		}
		
		//  �ΰ� : �ΰ��������� ������, ��������� �������� ����.
		else if( !result.InGaDate.equals("") )
		{
			addDataToSheet( 1 /* "�ΰ�" */,  result ); 	
		}
		
		//  ���� : ������������ �ְų� ��������� ������ ������.
		else if( !result.PageDate.equals("") || result.JongKukResult.indexOf("����") >= 0 )
		{
			addDataToSheet( 2 /* "����" */,  result ); 	
		}
		
		// �̼� : ��������� �̼��� ������.
		else if( result.JongKukResult.indexOf("�̼�") >= 0 )
		{
			addDataToSheet( 3 /* "�̼�" */,  result ); 	
		}
				
		// �Ⱒ : ��������� �Ⱒ�� ������. 
		else if( result.JongKukResult.indexOf("�Ⱒ") >= 0 )
		{
			addDataToSheet( 5 /* "�Ⱒ" */,  result ); 	
		}
		
		// ���� : ��������� ���� or ��û����
		else if( result.JongKukResult.indexOf("����") >= 0 || result.JongKukResult.indexOf("��û����") >= 0 )
		{
			addDataToSheet( 6 /* "����" */,  result ); 	
		}
		
		// ���㰡 : ��������� ���㰡 ������
		else if( result.JongKukResult.indexOf("���㰡") >= 0 )
		{
			addDataToSheet( 7 /* "���㰡" */,  result ); 	
		}
		
		// �Ļ꼱�� : ��������� �Ļ꼱�� ������
		else if( result.JongKukResult.indexOf("�Ļ꼱��") >= 0 )
		{
			addDataToSheet(8 /* "�Ļ꼱��" */,  result ); 	
		}
		// ���� : ��������� ""�̸�
		else if( result.JongKukResult.indexOf("") >= 0 )  
		{
			addDataToSheet(9 /* "����" */,  result ); 
		}
		else
		{
			logger.error("Excel Sheet �з����� -������� =" + result.JongKukResult );
		}
		
		return true; 		
	 }
	

	// �־��� Sheet�� Column header�� �Է��Ѵ�. 
	private void addHeaderToSheet( int sheetIdx ) 
	{	
		WritableSheet targetSheet = workBook.getSheet( sheetNames[sheetIdx] );  
		
        // key, ���Ǻ�, ������, ä����, ���ð�����, �ΰ�������, ��å������, ����������, �������,��������,���ð���������			
	    excelUtil.addCellToSheet(0, rowIdx[sheetIdx], "key" , null, targetSheet);         // key
	    excelUtil.addCellToSheet(1, rowIdx[sheetIdx], "���Ǻ�", null, targetSheet);         // ���Ǻ�
	    excelUtil.addCellToSheet(2, rowIdx[sheetIdx], "������", null, targetSheet);         // ������
	    excelUtil.addCellToSheet(3, rowIdx[sheetIdx], "ä����", null, targetSheet);         // ä����
	    excelUtil.addCellToSheet(4, rowIdx[sheetIdx], "���ð�����", null, targetSheet);      // ���ð�����
	    excelUtil.addCellToSheet(5, rowIdx[sheetIdx], "�ΰ�������", null, targetSheet);      // �ΰ�������
	    excelUtil.addCellToSheet(6, rowIdx[sheetIdx], "��å������", null, targetSheet);      // ��å������
	    excelUtil.addCellToSheet(7, rowIdx[sheetIdx], "����������", null, targetSheet);      // ����������
	    excelUtil.addCellToSheet(8, rowIdx[sheetIdx], "�������", null, targetSheet);       // �������
	    excelUtil.addCellToSheet(9, rowIdx[sheetIdx], "��������", null, targetSheet);       // ��������
	    excelUtil.addCellToSheet(10, rowIdx[sheetIdx], "���ð���������", null, targetSheet);  // ���ð���������
	    
	    // Column Width ����
		targetSheet.setColumnView(0, 17);  // key
		targetSheet.setColumnView(1, 41);  // ���Ǻ�   
		targetSheet.setColumnView( 2,10);  // ������
		targetSheet.setColumnView( 3, 7);  // ä����  
		targetSheet.setColumnView( 4,10);  // ���ð�����
		targetSheet.setColumnView( 5,10);  // �ΰ�������
		targetSheet.setColumnView( 6,10);  // ��å������ 
		targetSheet.setColumnView( 7,10);  // ����������  
		targetSheet.setColumnView( 8,17);  // ������� 	  		
		targetSheet.setColumnView( 9,14);  // ��������	 		
		targetSheet.setColumnView(10,14);  // ���ð���������	
		
	    rowIdx[sheetIdx]++;
	}
	
	// �־��� Sheet�� ����� �Է��Ѵ�.
	public void addDataToSheet( int sheetIdx, DataSagunOutput result ) throws Exception
	{
		WritableSheet targetSheet = workBook.getSheet( sheetNames[sheetIdx] ); 
		
		if( targetSheet == null ) 
		{
			excelUtil.createSheet(  sheetNames[sheetIdx], workBook);
			addHeaderToSheet( sheetIdx );
		}
				
        // key, ���Ǻ�, ������, ä����, ���ð�����, �ΰ�������, ��å������, ����������, �������,��������,���ð���������			
	    excelUtil.addCellToSheet(0, rowIdx[sheetIdx], result.key , null, targetSheet);             // key
	    excelUtil.addCellToSheet(1, rowIdx[sheetIdx], result.JaePanBu, null, targetSheet);         // ���Ǻ�
	    excelUtil.addCellToSheet(2, rowIdx[sheetIdx], result.JeobSuDate, null, targetSheet);       // ������
	    excelUtil.addCellToSheet(3, rowIdx[sheetIdx], result.ChaeMuJa, null, targetSheet);         // ä����
	    excelUtil.addCellToSheet(4, rowIdx[sheetIdx], result.GaeSiDate, null, targetSheet);        // ���ð�����
	    excelUtil.addCellToSheet(5, rowIdx[sheetIdx], result.InGaDate, null, targetSheet);         // �ΰ�������
	    excelUtil.addCellToSheet(6, rowIdx[sheetIdx], result.MyunChekDate, null, targetSheet);     // ��å������
	    excelUtil.addCellToSheet(7, rowIdx[sheetIdx], result.PageDate, null, targetSheet);         // ����������
	    excelUtil.addCellToSheet(8, rowIdx[sheetIdx], result.JongKukResult, null, targetSheet);    // �������
	    excelUtil.addCellToSheet(9, rowIdx[sheetIdx], result.KumGiKyulJung, null, targetSheet);    // ��������
	    excelUtil.addCellToSheet(10, rowIdx[sheetIdx], result.GaesiKyulJung, null, targetSheet);   // ���ð���������
      
	    logger.info("Excel/" + sheetNames[sheetIdx] + "/" +result.toString());
	    // idx ����
	    rowIdx[sheetIdx]++;
	}

}
