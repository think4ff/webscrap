package com.aurg.webscrap.parser;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.aurg.webscrap.data.MyInt;


public class HtmlParser {
	 protected static Logger logger = Logger.getLogger( HtmlParser.class.getName());

	// �־��� ��ġ(start_pos)���� ���� ���� ������ ���۹��ڿ�, �� ���ڿ��� ã�Ƽ� �߶󳽴�. 
	// 
	// webpage : �߶� html ��
	// start_str : �߶󳻱��� ���� ���ڿ�
	// start_pos : ������ġ 
	// end_str   : �߶󳻱� �� �� ���ڿ� 
	protected static String cut_value_in_nextBlock(String webpage, String start_str,MyInt start_pos, String end_str) {
		// TODO Auto-generated method stub
		String value;
		int start,end;
		
		if( start_str.equals("") )
		{
			start = 0;
		}
		else
		{
			start = webpage.indexOf(start_str,start_pos.getValue());
			if( start <  0 )
			{ 
				//GV.Log_E(FILENAME, "cut_value","start < 0");
				return "";
			}
			start +=  start_str.length();			
		}
		
		end = webpage.indexOf(end_str,start);

		if( end <  0 )
		{ 
			//GV.Log_E(FILENAME, "cut_value","end < 0");
			return "";			
		}
		
		value = webpage.substring(start, end);
		start_pos.setValue( end + end_str.length());		
		
        logger.info( "[" + value + "],start_pos:" + start_pos.getValue() );
		
		return value;
	}

	// �־��� ��ġ(start_pos)���� ���� ���� ������ ���۹��ڿ�, �� ���ڿ��� ã�Ƽ� �߶󳽴�. 
	// 
	// webpage : �߶� html ��
	// start_str : �߶󳻱��� ���� ���ڿ�
	// start_pos : ������ġ 
 	// end_str   : �߶󳻱� �� �� ���ڿ� 
	protected static String cut_value_in_previousBlock(String webpage, String start_str,int start_pos, String end_str) {
		// TODO Auto-generated method stub
		String value;
		int start,end;
		
		end = webpage.lastIndexOf( end_str,start_pos);
		if( end <  0 )
		{ 
			//GV.Log_E(FILENAME, "cut_value","start < 0");
			return "";			
		}
		
		//end -=  end_str.length();			

		start = webpage.lastIndexOf(start_str,end);

		if( start <  0 )
		{ 
			//GV.Log_E(FILENAME, "cut_value","end < 0");
			return "";			
		}
		
		start += start_str.length(); 
		
		value = webpage.substring(start, end);	
		
		return value;
	}
	
	// �־��� ��ġ(start_pos)�� ���Ե� ���� ������ ���۹��ڿ�, �� ���ڿ��� ã�Ƽ� �߶󳽴�.
	// ex) <td> � � ���ڿ� �Դϴ�. </td>  ���� "� � ���ڿ� �Դϴ�." �� �߶󳻷���
	// ���� ���ڿ��� �˻�, ��ġ�� ã�´�. ���Ƿ� 100�̶�� �ϸ�     
	// center_cut_value( html, "<td>" , 100 , "</td>" )�� �ϸ� ���ڿ��� �߶� �� �ִ�. 
	// 
	// webpage : �߶� html ��
	// start_str : �߶󳻱��� ���� ���ڿ�
	// start_pos : ������ġ 
 	// end_str   : �߶󳻱� �� �� ���ڿ� 
	protected static String cut_value_in_currentBlock(String webpage, String start_str,int start_pos, String end_str) {
		// TODO Auto-generated method stub
		String value;
		int start,end;
		
		start = webpage.lastIndexOf( start_str,start_pos);
		if( start <  0 )
		{ 
			//GV.Log_E(FILENAME, "cut_value","start < 0");
			return "";			
		}
		
		start += start_str.length(); 		

		end = webpage.indexOf(end_str,start);

		if( end <  0 )
		{ 
			//GV.Log_E(FILENAME, "cut_value","end < 0");
			return "";			
		}
		
		value = webpage.substring(start, end);	
		
		return value;
	}
	
	/**
	 * Table ���� ���ϴ� column���� return 
	 * @param html
	 * @param tableIndex (html ���� ���° table)
	 * @param searchVal(ã�� column name)
	 * @return
	 */
	protected static String getValueOne4Table(String html, int tableIndex,String searchVal) {
		String rtVal = "";
		int searchValIdx = 0;
		
		Document doc = Jsoup.parse(html);
		Element tableElement = doc.select("table").get(tableIndex);
		
		Elements tableHeaderEles = tableElement.select("th");
		for (int i = 0; i < tableHeaderEles.size(); i++) {
            if(searchVal.equals(tableHeaderEles.get(i).text()))
            	searchValIdx = i;
            
            logger.info("tHead=" + tableHeaderEles.get(i).text());
            logger.info("searchValIdx=" + searchValIdx);
         }
		
		Elements tableRowElements = tableElement.select("td");
		rtVal = tableRowElements.get(searchValIdx).text();
		logger.info("####################rtVal=" + rtVal);
		
		return rtVal;
	}
	
	
	protected static ArrayList<AuctionTab2GiIlNaeYeok> getValList4GiIlNaeYeok(String html, int tableIndex) {
		
		ArrayList <AuctionTab2GiIlNaeYeok> rtGiIlList = new ArrayList<AuctionTab2GiIlNaeYeok>() ;
		AuctionTab2GiIlNaeYeok tmpGiIl = null;
		
		Document doc = Jsoup.parse(html);
		Element tableElement = doc.select("table").get(tableIndex);
		
		Elements tableRowElements = tableElement.select(":not(thead) tr");
		for (int i = 0; i < tableRowElements.size(); i++) {
			
			tmpGiIl = new AuctionTab2GiIlNaeYeok();
			
            Element row = tableRowElements.get(i);
            Elements rowItems = row.select("td");
            for (int j = 0; j < rowItems.size(); j++) {
               logger.info("###########["+j+"]" + rowItems.get(j).text());
               //"���ǹ�ȣ, �����򰡾�" ����
               if(i==0 && (j==0 || j==1)) {
            	  continue;
               } else if((i==0 && j==2) || (i>0 && j==0)) {
            	   tmpGiIl.KiIlDate = rowItems.get(j).text().replaceAll("\\.", "").substring(0,8);
               } else if((i==0 && j==3) || (i>0 && j==1)) {
            	   tmpGiIl.KiIlCategory = rowItems.get(j).text();
               } else if((i==0 && j==4) || (i>0 && j==2)) {
            	   tmpGiIl.KiIlJangSo = rowItems.get(j).text();
               } else if((i==0 && j==5) || (i>0 && j==3)) {
            	   tmpGiIl.MinMaeKakPrice = rowItems.get(j).text().replaceAll("\\,|��", "");
               } else if((i==0 && j==6) || (i>0 && j==4)) {
            	   tmpGiIl.KiIlResult = rowItems.get(j).text();
               }
            }
			// ���ϰ���� �ݾ��� �ִ� ��� 
			if( tmpGiIl.KiIlResult.indexOf("��") >= 0 ) 
			{
				tmpGiIl.KiIlResult_price = cut_value_in_nextBlock(tmpGiIl.KiIlResult, "(", new MyInt(0), "��").replaceAll("\\,", "").trim();
			}
			// ���ϰ���� ��¥�� �ִ� ��� 
			else if( tmpGiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tmpGiIl.KiIlResult_date = cut_value_in_nextBlock(tmpGiIl.KiIlResult, "(", new MyInt(0), ")").replaceAll("\\.", "").trim();
			}
			// ���ϰ���� ��¥�� �ݾ��� �ִ� ��� ������� �ڸ���. ��) �Ű� (1,000��) ���  '�Ű�'�� �ڸ���.
			if( tmpGiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tmpGiIl.KiIlResult = cut_value_in_nextBlock(tmpGiIl.KiIlResult, "", new MyInt(0), " ").replaceAll("\\.", "").trim();
			}
//    		logger.info("#####################################");
//    		logger.info(tmpGiIl);
//    		logger.info("#####################################");
            rtGiIlList.add(tmpGiIl);
         }
		
		return rtGiIlList;
	}
}
