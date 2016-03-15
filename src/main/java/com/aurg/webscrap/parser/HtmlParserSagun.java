package com.aurg.webscrap.parser;

import org.apache.log4j.Logger;

import com.aurg.webscrap.data.DataSagunInput;
import com.aurg.webscrap.data.DataSagunOutput;
import com.aurg.webscrap.data.MyInt;

public class HtmlParserSagun extends HtmlParser {
	
	 protected static Logger logger = Logger.getLogger( HtmlParserSagun.class.getName());
	
	static public boolean doParsing( int tab, DataSagunInput input,  String html, DataSagunOutput result )
	{
        // for test - 전체 HTML 출력
        // System.out.println( "------------------------------------------------------------------" );
        // System.out.println( html );                
		
		if( tab == 1 ) return parseTab1( input, html, result );
		if( tab == 2 ) return parseTab2( input, html, result );
		
		return false;
	}

	static boolean parseTab1( DataSagunInput input, String html, DataSagunOutput result )
	{
		int pos ;
		String tempStr;
		
		logger.info("called");
		
		// 입력 오유인지를 판변한다. 
		// 		
		if( html.indexOf("사건이 존재하지 않습니다") >= 0 )
		{
			result.fail_reason = "사건이 존재하지 않습니다.사건정보를 확인하세요.";
			return false;
		}
		
		// 사건번호 
		pos = html.indexOf(">사건번호 : "); 
		if( pos >= 0 )  
		{
			// 법원에서 얻는 사건번호 형식은           - 인천지방법원 2013개회45918			
			// 결과로 생성해야 하는 사건번호 형식은 - 인천지방법원2013개회045918-03
			// 그래서 적절한 가공이 필요하다.
			// 1. 사건번호를 얻는다. > 인천지방법원 2013개회45918
			tempStr = cut_value_in_nextBlock( html, ": ", new MyInt(pos), "</td" );
			//System.out.println(" 1:[" + tempStr+"]");
			// 2. 공백뒤 부분만 잘라낸다.  > 2013개회45918
			tempStr = tempStr.substring(tempStr.indexOf(" ")+1);			         
			//System.out.println(" 2:[" + tempStr +"]");
			// 3. 사건번호에 0을 붙이기 위해서, 앞 6자리(2013개회)까지 자르고, 사건번호와 lpas 법원코드를 붙인다.
			result.key = tempStr.substring(0, 6) + input.getSa_serial() +  "-" + input.getBub_lpas_cd();
			//System.out.println(" 3:[" + result.key+"]");

		}

		// 재판부
		pos = html.indexOf("재판부"); 		
		if( pos >= 0 )  
		{
			result.JaePanBu = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" );			
		}
		
		// 접수일
		pos = html.indexOf("접수일"); 		
		if( pos >= 0 )  
		{
			result.JeobSuDate = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" ).replaceAll("\\.", "");			
		}
		
		// 채무자
		pos = html.indexOf(">채무자"); 		
		if( pos >= 0 )  
		{
			result.ChaeMuJa = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" );			
		}
		
		// 개시결정일 
		pos = html.indexOf("개시결정일"); 		
		if( pos >= 0 )  
		{
			result.GaeSiDate = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" ).replaceAll("\\.", "");
		}
		
		// 변제계획인가일 
		pos = html.indexOf("변제계획인가일"); 		
		if( pos >= 0 )  
		{
			result.InGaDate = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" ).replaceAll("\\.", "");			
		}
		
		// 면책결정일
		pos = html.indexOf("면책결정일"); 		
		if( pos >= 0 )  
		{
			result.MyunChekDate = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" ).replaceAll("\\.", "");			
		}
		
		// 절차폐지결정일
		pos = html.indexOf("절차폐지결정일"); 		
		if( pos >= 0 )  
		{
			result.PageDate = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" ).replaceAll("\\.", "");	
		}
		
		// 종국결과
		pos = html.indexOf("종국결과"); 		
		if( pos >= 0 )  
		{
			result.JongKukResult = cut_value_in_nextBlock( html, "&nbsp;", new MyInt(pos), "</td" ).replaceAll("\\.", "");			
		}
		
		return true;
	}
	
	static boolean parseTab2( DataSagunInput input, String html, DataSagunOutput result )
	{
		int pos;
		
		logger.info("called");
		
        // 금지결정
        pos = html.indexOf("한국씨티은행에게 금지명령(급여소득자) 송달"); 		
     	if( pos >= 0 )  
     	{
     		result.KumGiKyulJung = cut_value_in_nextBlock( html, "#CC6600\">", new MyInt(pos), "<" ).replaceAll("\\.", "");			
     	}

        // 개시결정통지서
        pos = html.indexOf("한국씨티은행에게 개시결정통지서/채권자목록/변제계획안 송달"); 		
     	if(pos>= 0 )  
     	{
     		result.GaesiKyulJung = cut_value_in_nextBlock( html, "#CC6600\">", new MyInt(pos), "<" ).replaceAll("\\.", "");			
     	}

		return true;
	}
}
