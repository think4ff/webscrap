package com.aurg.webscrap.parser;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.aurg.webscrap.data.MyInt;


public class HtmlParser {
	 protected static Logger logger = Logger.getLogger( HtmlParser.class.getName());

	// 주어진 위치(start_pos)으로 부터 뒤쪽 블럭에서 시작문자열, 끝 문자열을 찾아서 잘라낸다. 
	// 
	// webpage : 잘라낼 html 문
	// start_str : 잘라내기할 시작 문자열
	// start_pos : 시작위치 
	// end_str   : 잘라내기 할 끝 문자열 
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

	// 주어진 위치(start_pos)으로 부터 앞쪽 블럭에서 시작문자열, 끝 문자열을 찾아서 잘라낸다. 
	// 
	// webpage : 잘라낼 html 문
	// start_str : 잘라내기할 시작 문자열
	// start_pos : 시작위치 
 	// end_str   : 잘라내기 할 끝 문자열 
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
	
	// 주어진 위치(start_pos)가 포함된 현재 블럭에서 시작문자열, 끝 문자열을 찾아서 잘라낸다.
	// ex) <td> 어떤 어떤 문자열 입니다. </td>  에서 "어떤 어떤 문자열 입니다." 를 잘라내려면
	// 먼저 문자열을 검색, 위치를 찾는다. 임의로 100이라고 하면     
	// center_cut_value( html, "<td>" , 100 , "</td>" )를 하면 문자열을 잘라낼 수 있다. 
	// 
	// webpage : 잘라낼 html 문
	// start_str : 잘라내기할 시작 문자열
	// start_pos : 시작위치 
 	// end_str   : 잘라내기 할 끝 문자열 
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
	 * Table 에서 원하는 column값을 return 
	 * @param html
	 * @param tableIndex (html 에서 몇번째 table)
	 * @param searchVal(찾을 column name)
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
               //"물건번호, 감정평가액" 제외
               if(i==0 && (j==0 || j==1)) {
            	  continue;
               } else if((i==0 && j==2) || (i>0 && j==0)) {
            	   tmpGiIl.KiIlDate = rowItems.get(j).text().replaceAll("\\.", "").substring(0,8);
               } else if((i==0 && j==3) || (i>0 && j==1)) {
            	   tmpGiIl.KiIlCategory = rowItems.get(j).text();
               } else if((i==0 && j==4) || (i>0 && j==2)) {
            	   tmpGiIl.KiIlJangSo = rowItems.get(j).text();
               } else if((i==0 && j==5) || (i>0 && j==3)) {
            	   tmpGiIl.MinMaeKakPrice = rowItems.get(j).text().replaceAll("\\,|원", "");
               } else if((i==0 && j==6) || (i>0 && j==4)) {
            	   tmpGiIl.KiIlResult = rowItems.get(j).text();
               }
            }
			// 기일결과에 금액이 있는 경우 
			if( tmpGiIl.KiIlResult.indexOf("원") >= 0 ) 
			{
				tmpGiIl.KiIlResult_price = cut_value_in_nextBlock(tmpGiIl.KiIlResult, "(", new MyInt(0), "원").replaceAll("\\,", "").trim();
			}
			// 기일결과에 날짜가 있는 경우 
			else if( tmpGiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tmpGiIl.KiIlResult_date = cut_value_in_nextBlock(tmpGiIl.KiIlResult, "(", new MyInt(0), ")").replaceAll("\\.", "").trim();
			}
			// 기일결과에 날짜나 금액이 있는 경우 공백까지 자른다. 예) 매각 (1,000원) 경우  '매각'만 자른다.
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
