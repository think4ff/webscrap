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
        String filename= "경매_" + input.getNo() + "_tab" + tab + ".html";       
		
        BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter( filename));
	     	out.write ( html );	        
	        out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println( filename + " 생성완료!!" );
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
		
		// db key 값을 할당한다
		result.db_key = input.getDbKey();
						
		rtBoolean = parsePrefix(input, html, result);
		
		return rtBoolean;
	}
	
	private static boolean parsePrefix(DataAuctionInput input, String html, DataAuctionOutput result ) {
		
		// 유효한 경매물건인지 판별한다. 
		if( html.indexOf("해당 사건번호는 잘못된 번호입니다") >= 0 )
		{
			result.fail_reason = "사건이 존재하지 않습니다.사건정보를 확인하세요.";
			return false;
		}
		
		// 30일 경과한 경우 경매물건인지 판별한다. 
		if( html.indexOf("30일 경과한 경우") >= 0 )
		{
			result.fail_reason = "사건이 종국되고 30일 경과한 사건입니다.";
			return false;
		}	
		return true;
	}
	
	
	static public boolean doParsing( int tab, DataAuctionInput input,  String html, DataAuctionOutput result )
	{
		
        // for test - 전체 HTML 출력
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
		
		// db key 값을 할당한다
		result.db_key = input.getDbKey();
						
		// 유효한 경매물건인지 판별한다. 
		if( html.indexOf("해당 사건번호는 잘못된 번호입니다") >= 0 )
		{
			result.fail_reason = "사건이 존재하지 않습니다.사건정보를 확인하세요.";
			return false;
		}
		
		// 30일 경과한 경우 경매물건인지 판별한다. 
		if( html.indexOf("30일 경과한 경우") >= 0 )
		{
			result.fail_reason = "사건이 종국되고 30일 경과한 사건입니다.";
			return false;
		}		
		
		//  개시결정일자 -> 개시결정일 
		pos = html.indexOf("개시결정일자"); 
		if( pos >= 0 )  
		{
			//TODO:테스트를 위한 주석
//			result.GaeSiDate = cut_value_in_nextBlock( html, "<td>", new MyInt(pos), "</td>" ).replaceAll("\\.", "");
			//XXX:테스트
			result.GaeSiDate = getValueOne4Table(html, 0,"개시결정일자").replaceAll("\\.", "");
		}
		
		// 배당요구종기일 -> 배당요구종기일
		pos = html.indexOf("배당요구종기일"); 
		if( pos >= 0 )  
		{
//			tempStr = cut_value_in_nextBlock( html, "<td>20", new MyInt(pos), "</td>" ).replaceAll("\\.|\\r|\\n", "").trim();
//			if( !tempStr.equals("")  )
//			{
//				result.BaeDangYokuDate = "20" + tempStr.substring(0, 6);   // 배당요구 종기일 뒤에 다른 정보는 무시한다. "131205 (연기)"라면 앞 6자리만 자른다.  
//			}
			//XXX:테스트
			result.BaeDangYokuDate = getValueOne4Table(html, 1,"배당요구종기일").replaceAll("\\.", "");
		}
		
		return true;		
	}
	
	static boolean parseTab2( DataAuctionInput input, String html, DataAuctionOutput result )
	{		
		MyInt start_pos = new MyInt();
		int count=0;
		
		logger.info("called");
		
		// Tab2에 유효 데이타가 있는지 확인한다
		if( html.indexOf("검색결과가 없습니다") >= 0 )
		{
			return true;
		}
		
		// 감정평가액(대법원) - > 1차법사가(LPAS)
		start_pos.setValue( html.indexOf("기일결과") );
//		result.IlChaBeobSaGa = cut_value_in_nextBlock(html, "\"txtright\">", start_pos, "원").replaceAll("\\,", "").trim();
		//XXX:테스트
		result.IlChaBeobSaGa = getValueOne4Table(html, 0,"감정평가액").replaceAll("\\,|원", "");
		
		ArrayList <AuctionTab2GiIlNaeYeok> GiIlList = new ArrayList<AuctionTab2GiIlNaeYeok> () ;

		/*
		// 기일 테이블 Parsing 하여 KiIl List에 담는다
		while( count++<100 )// true )// 혹시 무한루프를 돌까봐.. 실제로 불가능한 값 100보다 작은동안 loop를 돌도록 하였다. 실제로는 100을 절대 만날수 없음
		{
			AuctionTab2GiIlNaeYeok tempKiIl = new AuctionTab2GiIlNaeYeok();
			
			tempKiIl.KiIlDate = cut_value_in_nextBlock(html, "<td class=\"txtcenter\">", start_pos, "(").replaceAll("\\.", "").trim();
			
			if( tempKiIl.KiIlDate.equals("")) 
				break;
			tempKiIl.KiIlCategory = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").trim(); 
			tempKiIl.KiIlJangSo = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").trim(); 
			tempKiIl.MinMaeKakPrice = cut_value_in_nextBlock(html, "\"txtright\">", start_pos, "</td>").replaceAll("\\,|원", "").trim(); 
			tempKiIl.KiIlResult = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").replaceAll("\\t", "").trim(); 

			// 기일결과에 금액이 있는 경우 
			if( tempKiIl.KiIlResult.indexOf("원") >= 0 ) 
			{
				tempKiIl.KiIlResult_price = cut_value_in_nextBlock(tempKiIl.KiIlResult, "(", new MyInt(0), "원").replaceAll("\\,", "").trim();
			}
			// 기일결과에 날짜가 있는 경우 
			else if( tempKiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tempKiIl.KiIlResult_date = cut_value_in_nextBlock(tempKiIl.KiIlResult, "(", new MyInt(0), ")").replaceAll("\\.", "").trim();
			}
			
			// 기일결과에 날짜나 금액이 있는 경우 공백까지 자른다. 예) 매각 (1,000원) 경우  '매각'만 자른다.
			if( tempKiIl.KiIlResult.indexOf("(") >= 0 )
			{
				tempKiIl.KiIlResult = cut_value_in_nextBlock(tempKiIl.KiIlResult, "", new MyInt(0), " ").replaceAll("\\.", "").trim();
			}
			
			logger.info( tempKiIl.toString() );
			KiIL_list.add(tempKiIl);
		}
		*/
		
		//XXX:테스트
		GiIlList = getValList4GiIlNaeYeok(html, 0);
		
		// KiIl list를 분석하여 result를 생성한다.
		for(int i=0; i < GiIlList.size(); i++ )
		{
			//System.out.println( KiIL_list.get(i).toString() );
			
            //------------------------------------------------------------------			
	        // 1. 기일종류가 매각기일 일때 기일결과에 상관없이
	        //   - 기일(대법원) ->입찰일자(LPAS)
	        //   - 최저매각금액(대법원) ->입찰금액(LPAS)
	        //   - 기일결과(대법원) ->입찰결과(LPAS)
			//------------------------------------------------------
			// 입찰결과 Mapping table
			//------------------------------------------------------
			// 기존 LPAS Code 값 
			//------------------------------------------------------
            //    1 (bm401)    유찰         
		    //    2 (bm402)    낙찰허가결정  
            //    3 (bm403)    항고
            //	  4 (bm404)    재입찰
            //	  5 (bm401)    기일변경     
			//------------------------------------------------------
			// CODE	    LPAS	       대법원
			//------------------------------------------------------
			// bm401	유찰           유찰
			// bm402	낙찰           매각
			// bm403	항고	       항고
			// bm404	재입찰	       재입찰
			// bm405	기일변경	   변경
			// bm406	매각허가결정   최고가매각허가결정
			// bm407	매각취소       최고가매각허가취소결정
			// bm408	배당기일       배당기일
			//------------------------------------------------------

			if( GiIlList.get(i).KiIlCategory.equals("매각기일") )
			{
				result.YipChalDateList.add( GiIlList.get(i).KiIlDate );
				result.YipChalKeumAkList.add( GiIlList.get(i).MinMaeKakPrice );
				
				if( GiIlList.get(i).KiIlResult.equals("유찰"))
				{
					result.YipChalResultList.add( "1" );
				}
     			else if( GiIlList.get(i).KiIlResult.equals("매각"))
	    		{
		    		result.YipChalResultList.add( "2" );
			    }
				else if( GiIlList.get(i).KiIlResult.equals("항고"))
				{
					result.YipChalResultList.add( "3" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("재입찰"))
				{
					result.YipChalResultList.add( "4" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("변경"))
				{
					result.YipChalResultList.add( "5" );
				}
     			else if( GiIlList.get(i).KiIlResult.equals("최고가매각허가결정"))
				{
					result.YipChalResultList.add( "6" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("최고가매각허가취소결정"))
				{
					result.YipChalResultList.add( "7" );
				}
				else if( GiIlList.get(i).KiIlResult.equals("배당기일"))
				{
					result.YipChalResultList.add( "8" );
				}				
				// 공백으로 처리
				else if( GiIlList.get(i).KiIlResult.equals("진행") || GiIlList.get(i).KiIlResult.equals("") ) 
				{
					result.YipChalResultList.add( "" );
				}
				else  // 그외 결과일때는 예외상황이며 공백으로 처리함. 
				{
					result.YipChalResultList.add( "" );
					logger.error("다음 입찰결과를 공백으로 처리합니다. --> " + GiIlList.get(i).KiIlResult );
				}
			}	
			
			//------------------------------------------------------------------
			//	2. 기일결과가 매각일때 
			//	   - 기일(대법원) ->낙찰일자(LPAS)
			//	   - 매각금액(대법원) ->낙찰가(LPAS)
			if( GiIlList.get(i).KiIlResult.equals("매각"))
			{
				result.NakChalDate = GiIlList.get(i).KiIlDate;
				result.NakCahlKa = GiIlList.get(i).KiIlResult_price;					
			}	
			
			//------------------------------------------------------------------
			// 	3. 기일결과가 납부일때
			//	   - 납부일(대법원) -> 대금납부기일(LPAS)
			//  			
			if( GiIlList.get(i).KiIlResult.equals("납부"))
			{
				result.DaeGeumNapBuDate = GiIlList.get(i).KiIlResult_date;
			}

			// 3. 기일종류가 "대금지급기한" 일때	   - 기일(대법원) ->대금지급기한(LPAS)
			if( GiIlList.get(i).KiIlCategory.equals("대금지급기한") )
			{
                result.DaeGeumGiHan = GiIlList.get(i).KiIlDate;
			}

		}		
		
		
		return true;		
	}

	
	// tab3은 크게 2개 블럭으로 구분된다. 문건처리내역  및 송달내역이다. 
	// 이중 문건송달내역에서만 키워드들을 검색한다. 
	static boolean parseTab3( DataAuctionInput input, String html, DataAuctionOutput result )
	{
		// 송달내역 -> 메모		
		int last_pos;
		String tmpDate;
		String tmpJeobSuNaeYeok;		
		String [] keywords = new String[] { "한국씨티은행","배당요구","유치권","임차인","임금채권","근로복지공단" };
		MyInt  start_pos = new MyInt(); 
		
		logger.info("called");
		
		last_pos = html.indexOf("<h3>송달내역</h3>");  
		start_pos.setValue( html.indexOf("\"txtcenter\">결과</th>") + "\"txtcenter\">결과</th>".length() );

		// 다음 날짜 찾기
		tmpDate = cut_value_in_nextBlock(html, "\"txtcenter\">", start_pos, "</td>").replaceAll("\\.", "").trim();
		// 다음 내용 찾기
		tmpJeobSuNaeYeok = cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").replaceAll("\\t", "").trim();
		
		// 현재 검색 시작위치가 last_pos보다 작은동안 반복해서 검색한다. 
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
			// 다음 날짜 찾기
			tmpDate = cut_value_in_nextBlock(html, "\"txtcenter\">", start_pos, "</td>").replaceAll("\\.", "").trim();
			// 다음 내용 찾기
			tmpJeobSuNaeYeok =  cut_value_in_nextBlock(html, "<td>", start_pos, "</td>").replaceAll("\\t", "").trim();
			
			// System.out.println( tmpDate + " " +  tmpJeobSuNaeYeok);
		}				
				
		return true;		
	}
	
}
