package com.aurg.webscrap.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aurg.webscrap.data.DataAuctionInput;
import com.aurg.webscrap.data.DataAuctionOutput;
import com.aurg.webscrap.data.MyInt;

public class HtmlParserAuction extends HtmlParser {
	
//	protected static Logger logger = Logger.getLogger( HtmlParserAuction.class.getName());
	protected static Logger logger = LoggerFactory.getLogger( HtmlParserAuction.class);
	
	static public void saveFile( DataAuctionInput input , String html, int tab )
	{
        String filename= "���_" + input.getNo() + "_tab" + tab + ".html";       
		
        BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter( filename));
	     	out.write ( html );	        
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println( filename + " �����Ϸ�!!" );
	}
	
	/**
	 * fetch the all table of page.
	 * @param tab
	 * @param input
	 * @param html
	 * @param result
	 * @return
	 */
	static public boolean doParsingAll(int tab, DataAuctionInput input,  String html, DataAuctionOutput result ) {
		if( tab == 1 ) return parseTab1All( input, html, result );
//		if( tab == 2 ) return parseTab2All( input, html, result );
//		if( tab == 3 ) return parseTab3All( input, html, result );
		
		return true;
	}
	
	
	static boolean parseTab1All( DataAuctionInput input, String html, DataAuctionOutput result ) {
		
		logger.info("called");
		boolean rtBoolean = true;
		
		// db key ���� �Ҵ��Ѵ�
		result.db_key = input.getDbKey();
						
		rtBoolean = parsePrefix(input, html, result);
		
		return rtBoolean;
	}
	
	private static boolean parsePrefix(DataAuctionInput input, String html, DataAuctionOutput result ) {
		
		// ��ȿ�� ��Ź������� �Ǻ��Ѵ�. 
		if( html.indexOf("�ش� ��ǹ�ȣ�� �߸��� ��ȣ�Դϴ�") >= 0 )
		{
			result.fail_reason = "����� �������� �ʽ��ϴ�.��������� Ȯ���ϼ���.";
			return false;
		}
		
		// 30�� ����� ��� ��Ź������� �Ǻ��Ѵ�. 
		if( html.indexOf("30�� ����� ���") >= 0 )
		{
			result.fail_reason = "����� �����ǰ� 30�� ����� ����Դϴ�.";
			return false;
		}	
		return true;
	}
	
	
	static public boolean doParsing( int tab, DataAuctionInput input,  String html, DataAuctionOutput result )
	{
		
        // for test - ��ü HTML ���
        //System.out.println( "------------------------------------------------------------------" );
        //System.out.println( html );             
        // saveFile( input, html, tab );
		        
		if( tab == 1 ) return parseTab1( input, html, result );
		if( tab == 2 ) return parseTab2( input, html, result );
		if( tab == 3 ) return parseTab3( input, html, result );
				
		return true;
	}

	
	static boolean parseTab1( DataAuctionInput input, String html, DataAuctionOutput result )
	{
		int pos ;
		String tempStr;

		logger.info("called");
		
		// db key ���� �Ҵ��Ѵ�
		result.db_key = input.getDbKey();
						
		// ��ȿ�� ��Ź������� �Ǻ��Ѵ�. 
		if( html.indexOf("�ش� ��ǹ�ȣ�� �߸��� ��ȣ�Դϴ�") >= 0 )
		{
			result.fail_reason = "����� �������� �ʽ��ϴ�.��������� Ȯ���ϼ���.";
			return false;
		}
		
		// 30�� ����� ��� ��Ź������� �Ǻ��Ѵ�. 
		if( html.indexOf("30�� ����� ���") >= 0 )
		{
			result.fail_reason = "����� �����ǰ� 30�� ����� ����Դϴ�.";
			return false;
		}		
		
		//  ���ð������� -> ���ð����� 
		pos = html.indexOf("���ð�������"); 
		if( pos >= 0 )  
		{
			//TODO:�׽�Ʈ�� ���� �ּ�
//			result.GaeSiDate = cut_value_in_nextBlock( html, "<td>", new MyInt(pos), "</td>" ).replaceAll("\\.", "");
			//XXX:�׽�Ʈ
			result.GaeSiDate = getValueOne4Table(html, 0,"���ð�������").replaceAll("\\.", "");
		}
		
		// ���䱸������ -> ���䱸������
		pos = html.indexOf("���䱸������"); 
		if( pos >= 0 )  
		{
//			tempStr = cut_value_in_nextBlock( html, "<td>20", new MyInt(pos), "</td>" ).replaceAll("\\.|\\r|\\n", "").trim();
//			if( !tempStr.equals("")  )
//			{
//				result.BaeDangYokuDate = "20" + tempStr.substring(0, 6);   // ���䱸 ������ �ڿ� �ٸ� ������ �����Ѵ�. "131205 (����)"��� �� 6�ڸ��� �ڸ���.  
//			}
			//XXX:�׽�Ʈ
			result.BaeDangYokuDate = getValueOne4Table(html, 1,"���䱸������").replaceAll("\\.", "");
		}
		
		return true;		
	}
	
	static boolean parseTab2( DataAuctionInput input, String html, DataAuctionOutput result )
	{		
		MyInt start_pos = new MyInt();
		int count=0;
		
		logger.info("called");
		
		// Tab2�� ��ȿ ����Ÿ�� �ִ��� Ȯ���Ѵ�
		if( html.indexOf("�˻������ �����ϴ�") >= 0 )
		{
			return true;
		}
		
		// �����򰡾�(�����) - > 1�����簡(LPAS)
		start_pos.setValue( html.indexOf("���ϰ��") );
//		result.IlChaBeobSaGa = cut_value_in_nextBlock(html, "\"txtright\">", start_pos, "��").replaceAll("\\,", "").trim();
		//XXX:�׽�Ʈ
		result.IlChaBeobSaGa = getValueOne4Table(html, 0,"�����򰡾�").replaceAll("\\,|��", "");
		
		ArrayList <AuctionTab2GiIlNaeYeok> GiIlList = new ArrayList<AuctionTab2GiIlNaeYeok> () ;

		/*
		// ���� ���̺� Parsing �Ͽ� KiIl List�� ��´�
		while( count++<100 )// true )// Ȥ�� ���ѷ����� �����.. ������ �Ұ����� �� 100���� �������� loop�� ������ �Ͽ���. �����δ� 100�� ���� ������ ����
		{
			AuctionTab2GiIlNaeYeok tempKiIl = new AuctionTab2GiIlNaeYeok();
			
			tempKiIl.KiIlDate = cut_value_in_nextBlock(html, "<td class=\"txtcenter\">", start_pos, "(").replaceAll("\\.", "").trim();
			
			if( tempKiIl.KiIlDate.equals("")) 
				break;
			tempKiIl.KiIlCategory = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").trim(); 
			tempKiIl.KiIlJangSo = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").trim(); 
			tempKiIl.MinMaeKakPrice = cut_value_in_nextBlock(html, "\"txtright\">", start_pos, "</td>").replaceAll("\\,|��", "").trim(); 
			tempKiIl.KiIlResult = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").replaceAll("\\t", "").trim(); 

			// ���ϰ���� �ݾ��� �ִ� ��� 
			if( tempKiIl.KiIlResult.indexOf("��") >= 0 ) 
			{
				tempKiIl.KiIlResult_price = cut_value_in_nextBlock(tempKiIl.KiIlResult, "(", new MyInt(0), "��").replaceAll("\\,", "").trim();
			}
			// ���ϰ���� ��¥�� �ִ� ��� 
			else if( tempKiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tempKiIl.KiIlResult_date = cut_value_in_nextBlock(tempKiIl.KiIlResult, "(", new MyInt(0), ")").replaceAll("\\.", "").trim();
			}
			
			// ���ϰ���� ��¥�� �ݾ��� �ִ� ��� ������� �ڸ���. ��) �Ű� (1,000��) ���  '�Ű�'�� �ڸ���.
			if( tempKiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tempKiIl.KiIlResult = cut_value_in_nextBlock(tempKiIl.KiIlResult, "", new MyInt(0), " ").replaceAll("\\.", "").trim();
			}
			
			logger.info( tempKiIl.toString() );
			KiIL_list.add(tempKiIl);
		}
		*/
		
		//XXX:�׽�Ʈ
		GiIlList = getValList4GiIlNaeYeok(html, 0);
		
		// KiIl list�� �м��Ͽ� result�� �����Ѵ�.
		for(int i=0; i < GiIlList.size(); i++ )
		{
			//System.out.println( KiIL_list.get(i).toString() );
			
            //------------------------------------------------------------------			
	        // 1. ���������� �Ű����� �϶� ���ϰ���� �������
	        //   - ����(�����) ->��������(LPAS)
	        //   - �����Ű��ݾ�(�����) ->�����ݾ�(LPAS)
	        //   - ���ϰ��(�����) ->�������(LPAS)
			//------------------------------------------------------
			// ������� Mapping table
			//------------------------------------------------------
			// ���� LPAS Code �� 
			//------------------------------------------------------
            //    1 (bm401)    ����         
		    //    2 (bm402)    �����㰡����  
            //    3 (bm403)    �װ�
            //	  4 (bm404)    ������
            //	  5 (bm401)    ���Ϻ���     
			//------------------------------------------------------
			// CODE	    LPAS	       �����
			//------------------------------------------------------
			// bm401	����           ����
			// bm402	����           �Ű�
			// bm403	�װ�	       �װ�
			// bm404	������	       ������
			// bm405	���Ϻ���	   ����
			// bm406	�Ű��㰡����   �ְ��Ű��㰡����
			// bm407	�Ű����       �ְ��Ű��㰡��Ұ���
			// bm408	������       ������
			//------------------------------------------------------

			if( GiIlList.get(i).KiIlCategory.equals("�Ű�����") )
			{
				result.YipChalDateList.add( GiIlList.get(i).KiIlDate );
				result.YipChalKeumAkList.add( GiIlList.get(i).MinMaeKakPrice );
				
				if( GiIlList.get(i).KiIlResult.equals("����"))
				{
					result.YipChalResultList.add( "1" );
				}
     			else if( GiIlList.get(i).KiIlResult.equals("�Ű�"))
	    		{
		    		result.YipChalResultList.add( "2" );
			    }
				else if( GiIlList.get(i).KiIlResult.equals("�װ�"))
				{
					result.YipChalResultList.add( "3" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("������"))
				{
					result.YipChalResultList.add( "4" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("����"))
				{
					result.YipChalResultList.add( "5" );
				}
     			else if( GiIlList.get(i).KiIlResult.equals("�ְ��Ű��㰡����"))
				{
					result.YipChalResultList.add( "6" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("�ְ��Ű��㰡��Ұ���"))
				{
					result.YipChalResultList.add( "7" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("������"))
				{
					result.YipChalResultList.add( "8" );
				}				
				// �������� ó��
				else if( GiIlList.get(i).KiIlResult.equals("����") || GiIlList.get(i).KiIlResult.equals("") ) 
				{
					result.YipChalResultList.add( "" );
				}
				else  // �׿� ����϶��� ���ܻ�Ȳ�̸� �������� ó����. 
				{
					result.YipChalResultList.add( "" );
					logger.error("���� ��������� �������� ó���մϴ�. --> " + GiIlList.get(i).KiIlResult );
				}
			}	
			
			//------------------------------------------------------------------
			//	2. ���ϰ���� �Ű��϶� 
			//	   - ����(�����) ->��������(LPAS)
			//	   - �Ű��ݾ�(�����) ->������(LPAS)
			if( GiIlList.get(i).KiIlResult.equals("�Ű�"))
			{
				result.NakChalDate = GiIlList.get(i).KiIlDate;
				result.NakCahlKa = GiIlList.get(i).KiIlResult_price;					
			}	
			
			//------------------------------------------------------------------
			// 	3. ���ϰ���� �����϶�
			//	   - ������(�����) -> ��ݳ��α���(LPAS)
			//  			
			if( GiIlList.get(i).KiIlResult.equals("����"))
			{
				result.DaeGeumNapBuDate = GiIlList.get(i).KiIlResult_date;
			}

			// 3. ���������� "������ޱ���" �϶�	   - ����(�����) ->������ޱ���(LPAS)
			if( GiIlList.get(i).KiIlCategory.equals("������ޱ���") )
			{
                result.DaeGeumGiHan = GiIlList.get(i).KiIlDate;
			}

		}		
		
		
		return true;		
	}

	
	// tab3�� ũ�� 2�� ������ ���еȴ�. ����ó������  �� �۴޳����̴�. 
	// ���� ���Ǽ۴޳��������� Ű������� �˻��Ѵ�. 
	static boolean parseTab3( DataAuctionInput input, String html, DataAuctionOutput result )
	{
		// �۴޳��� -> �޸�		
		int last_pos;
		String tmpDate;
		String tmpJeobSuNaeYeok;		
		String [] keywords = new String[] { "�ѱ���Ƽ����","���䱸","��ġ��","������","�ӱ�ä��","�ٷκ�������" };
		MyInt  start_pos = new MyInt(); 
		
		logger.info("called");
		
		last_pos = html.indexOf("<h3>�۴޳���</h3>");  
		start_pos.setValue( html.indexOf("\"txtcenter\">���</th>") + "\"txtcenter\">���</th>".length() );

		// ���� ��¥ ã��
		tmpDate = cut_value_in_nextBlock(html, "\"txtcenter\">", start_pos, "</td>").replaceAll("\\.", "").trim();
		// ���� ���� ã��
		tmpJeobSuNaeYeok = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").replaceAll("\\t", "").trim();
		
		// ���� �˻� ������ġ�� last_pos���� �������� �ݺ��ؼ� �˻��Ѵ�. 
		while( last_pos > start_pos.getValue() )
		{
			for(int i=0; i < keywords.length; i++ ) 
			{
				if( tmpJeobSuNaeYeok.indexOf(keywords[i]) >= 0 )
				{
					result.MemoList.add( tmpDate + " " + tmpJeobSuNaeYeok.replaceAll(",","") );
					break;
				}
			}
			// ���� ��¥ ã��
			tmpDate = cut_value_in_nextBlock(html, "\"txtcenter\">", start_pos, "</td>").replaceAll("\\.", "").trim();
			// ���� ���� ã��
			tmpJeobSuNaeYeok =  cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").replaceAll("\\t", "").trim();
			
			// System.out.println( tmpDate + " " +  tmpJeobSuNaeYeok);
		}				
				
		return true;		
	}
	
}
