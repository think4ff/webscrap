package com.aurg.webscrap.data;

import org.apache.log4j.Logger;



public class CodeTable {
	
	static SagunCode [] sagunCodeTable;
	static String [] bumCodeTable;
	static String [] bumNameTable;
	static boolean init;
	
	protected static Logger logger = Logger.getLogger( CodeTable.class.getName());

	CodeTable() 
	{		
	}
		
	static String getBumCode( String LpasCode ) 
	{
		init();
		
		int idx = Integer.valueOf(LpasCode);
		
		if( idx >= bumCodeTable.length ){
			logger.error( LpasCode + "-법원 코드를 찾을 수 없습니다.");
			return "error";
		}
		
		return bumCodeTable[ idx ];
	}
	
	static String getBumName( String LpasCode ) 
	{
		init();
		
		int idx = Integer.valueOf(LpasCode);
		
		if( idx >= bumNameTable.length ){
			logger.error( LpasCode + "-법원 이름을 찾을 수 없습니다.");
			return "error";
		}
		
		return bumNameTable[ idx ];
	}
	
	static String getSagunCode( String SagunNm ) 
	{
		init();
		
		for(int i=0; i < sagunCodeTable.length; i++ )
		{
			if( sagunCodeTable[i].name.equals( SagunNm ) )
			{
				return sagunCodeTable[i].code;
			}
		}
		
		logger.error( SagunNm + "-사건 코드를 찾을 수 없습니다.");
		
		return "error";
	}
	
	static void init()
	{
		if( init == true ) return; 
		
		init = true; 
		// 사건 구분코드 테이블 - LPAS에서는 한글 두글자 코드만 사용하므로 한글자, 세글자는 모두 주석처리함
		sagunCodeTable = new SagunCode [] { 
            new SagunCode( "가단", "cv" ),
    		new SagunCode( "가소", "cv" ),
    		new SagunCode( "가합", "cv" ),
    		new SagunCode( "감고", "cr" ),
    		new SagunCode( "감노", "cr" ),
    		new SagunCode( "감도", "cr" ),
    		new SagunCode( "감로", "cr" ),
    		new SagunCode( "감모", "cr" ),
    		new SagunCode( "감오", "cr" ),
    		new SagunCode( "감초", "cr" ),
    		new SagunCode( "개기", "whp" ),
    		new SagunCode( "개보", "whp" ),
    		new SagunCode( "개확", "whp" ),
    		new SagunCode( "개회", "whp" ),
    		new SagunCode( "고단", "cr" ),
    		new SagunCode( "고약", "ym" ),
    		//new SagunCode( "고약전", "wey" ),
    		new SagunCode( "고정", "cr" ),
    		new SagunCode( "고합", "cr" ),
    		//new SagunCode( "과", "gw" ),
    		//new SagunCode( "구", "hs" ),
    		new SagunCode( "구단", "hs" ),
    		new SagunCode( "구합", "hs" ),
    		//new SagunCode( "그", "hg" ),
    		//new SagunCode( "나", "cv" ),
    		//new SagunCode( "너", "ks" ),
    		//new SagunCode( "노", "cr" ),
    		//new SagunCode( "누", "hs" ),
    		new SagunCode( "느단", "ks" ),
    		new SagunCode( "느합", "ks" ),
    		//new SagunCode( "다", "cv" ),
    		//new SagunCode( "도", "cr" ),
    		//new SagunCode( "두", "hs" ),
    		//new SagunCode( "드", "ks" ),
    		new SagunCode( "드단", "ks" ),
    		new SagunCode( "드합", "ks" ),
    		//new SagunCode( "라", "hg" ),
    		//new SagunCode( "로", "cr" ),
    		//new SagunCode( "루", "hg" ),
    		//new SagunCode( "르", "ks" ),
    		//new SagunCode( "마", "hg" ),
    		//new SagunCode( "머", "cv" ),
    		//new SagunCode( "모", "cr" ),
    		//new SagunCode( "무", "hg" ),
    		//new SagunCode( "므", "ks" ),
    		//new SagunCode( "바", "hg" ),
    		//new SagunCode( "버", "gb" ),
    		//new SagunCode( "보", "cr" ),
    		//new SagunCode( "부", "hg" ),
    		//new SagunCode( "브", "hg" ),
    		new SagunCode( "비단", "et" ),
    		new SagunCode( "비합", "et" ),
    		//new SagunCode( "사", "hg" ),
    		//new SagunCode( "수", "hs" ),
    		new SagunCode( "수흐", "hg" ),
    		//new SagunCode( "스", "hg" ),
    		//new SagunCode( "아", "et" ),
    		//new SagunCode( "오", "cr" ),
    		//new SagunCode( "으", "hg" ),
    		//new SagunCode( "인", "et" ),
    		new SagunCode( "인라", "hg" ),
    		new SagunCode( "인마", "hg" ),
    		new SagunCode( "인카", "et" ),
    		//new SagunCode( "자", "et" ),
    		//new SagunCode( "재가단", "cv" ),
    		//new SagunCode( "재가소", "cv" ),
    		//new SagunCode( "재가합", "cv" ),
    		//new SagunCode( "재감고", "cr" ),
    		//new SagunCode( "재감노", "cr" ),
    		//new SagunCode( "재감도", "cr" ),
    		//new SagunCode( "재고단", "cr" ),
    		//new SagunCode( "재고정", "cr" ),
    		//new SagunCode( "재고합", "cr" ),
    		new SagunCode( "재구", "hs" ),
    		//new SagunCode( "재구단", "hs" ),
    		//new SagunCode( "재구합", "hs" ),
    		new SagunCode( "재그", "hg" ),
    		new SagunCode( "재나", "cv" ),
    		new SagunCode( "재너", "ks" ),
    		new SagunCode( "재노", "cr" ),
    		new SagunCode( "재누", "hs" ),
    		//new SagunCode( "재느단", "ks" ),
    		//new SagunCode( "재느합", "ks" ),
    		new SagunCode( "재다", "cv" ),
    		new SagunCode( "재도", "cr" ),
    		new SagunCode( "재두", "hs" ),
    		new SagunCode( "재드", "ks" ),
    		//new SagunCode( "재드단", "ks" ),
    		//new SagunCode( "재드합", "ks" ),
    		new SagunCode( "재라", "hg" ),
    		new SagunCode( "재루", "hg" ),
    		new SagunCode( "재르", "ks" ),
    		new SagunCode( "재마", "hg" ),
    		new SagunCode( "재머", "cv" ),
    		new SagunCode( "재무", "hg" ),
    		new SagunCode( "재므", "ks" ),
    		new SagunCode( "재브", "hg" ),
    		new SagunCode( "재스", "hg" ),
    		new SagunCode( "재으", "hg" ),
    		//new SagunCode( "재카기", "et" ),
    		new SagunCode( "재허", "pt" ),
    		new SagunCode( "재후", "pt" ),
    		//new SagunCode( "저", "gb" ),
    		new SagunCode( "전로", "cr" ),
    		new SagunCode( "전모", "cr" ),
    		new SagunCode( "전초", "cr" ),
    		//new SagunCode( "주", "et" ),
    		//new SagunCode( "준재가단", "cv" ),
    		//new SagunCode( "준재가소", "cv" ),
    		//new SagunCode( "준재가합", "cv" ),
    		//new SagunCode( "준재구", "hs" ),
    		//new SagunCode( "준재나", "cv" ),
    		//new SagunCode( "준재누", "hs" ),
    		//new SagunCode( "준재느단", "ks" ),
    		//new SagunCode( "준재느합", "ks" ),
    		//new SagunCode( "준재드", "ks" ),
    		//new SagunCode( "준재드단", "ks" ),
    		//new SagunCode( "준재드합", "ks" ),
    		//new SagunCode( "준재르", "ks" ),
    		//new SagunCode( "준재머", "cv" ),
    		//new SagunCode( "준재므", "ks" ),
    		//new SagunCode( "준재스", "hg" ),
    		new SagunCode( "즈기", "ka" ),
    		new SagunCode( "즈단", "ka" ),
    		new SagunCode( "즈합", "ka" ),
    		//new SagunCode( "차", "dc" ),
    		new SagunCode( "차전", "wdc" ),
    		//new SagunCode( "처", "gb" ),
    		//new SagunCode( "초", "cr" ),
    		new SagunCode( "초기", "cr" ),
    		new SagunCode( "초보", "cr" ),
    		new SagunCode( "초사", "cr" ),
    		new SagunCode( "초재", "cr" ),
    		new SagunCode( "초적", "cr" ),
    		new SagunCode( "초치", "cr" ),
    		//new SagunCode( "추", "hs" ),
    		new SagunCode( "치고", "cr" ),
    		new SagunCode( "치노", "cr" ),
    		new SagunCode( "치도", "cr" ),
    		new SagunCode( "치로", "cr" ),
    		new SagunCode( "치모", "cr" ),
    		new SagunCode( "치오", "cr" ),
    		new SagunCode( "치초", "cr" ),
    		//new SagunCode( "카", "ka" ),
    		new SagunCode( "카공", "go" ),
    		new SagunCode( "카구", "ka" ),
    		new SagunCode( "카기", "ka" ),
    		//new SagunCode( "카기전", "ka" ),
    		new SagunCode( "카단", "ka" ),
    		new SagunCode( "카담", "ka" ),
    		new SagunCode( "카명", "ka" ),
    		new SagunCode( "카열", "et" ),
    		new SagunCode( "카조", "jj" ),
    		new SagunCode( "카합", "ka" ),
    		new SagunCode( "카허", "et" ),
    		new SagunCode( "카확", "ka" ),
    		//new SagunCode( "코", "cr" ),
    		new SagunCode( "타기", "gj" ),
    		new SagunCode( "타채", "gj" ),
    		//new SagunCode( "푸", "sb" ),
    		new SagunCode( "푸초", "sb" ),
    		new SagunCode( "하기", "whp" ),
    		new SagunCode( "하단", "whp" ),
    		new SagunCode( "하면", "whp" ),
    		new SagunCode( "하합", "whp" ),
    		new SagunCode( "하확", "whp" ),
    		new SagunCode( "행심", "et" ),
    		//new SagunCode( "허", "pt" ),
    		new SagunCode( "호파", "et" ),
    		new SagunCode( "회기", "whp" ),
    		new SagunCode( "회단", "whp" ),
    		new SagunCode( "회합", "whp" ),
    		new SagunCode( "회확", "whp" ),
    		//new SagunCode( "후", "pt" ),
    		//new SagunCode( "흐", "hg" ),
    		//new SagunCode( "히", "hg" ) 
    		};
		
		bumCodeTable = new String [] { 
				"dummy",  
				"000210", // 01-서울중앙지법
				"000214", // 02-의정부지방법원
				"000240", // 03-인천지방법원
				"000250", // 04-수원지방법원
				"000260", // 05-춘천지방법원
				"000280", // 06-대전지방법원
				"000270", // 07-청주지방법원
				"000310", // 08-대구지방법원
				"000410", // 09-부산지방법원
				"000411", // 10-울산지방법원
				"000420", // 11-창원지방법원
				"000510", // 12-광주지방법원
				"000520", // 13-전주지방법원
				"000530" }; // 14-제주지방법원 
		
		bumNameTable = new String [] { 
				"dummy",  
				"서울중앙지법",  // 01-서울중앙지법
				"의정부지방법원", // 02-의정부지방법원
				"인천지방법원",  // 03-인천지방법원
				"수원지방법원",  // 04-수원지방법원
				"춘천지방법원",  // 05-춘천지방법원
				"대전지방법원",  // 06-대전지방법원
				"청주지방법원",  // 07-청주지방법원
				"대구지방법원",  // 08-대구지방법원
				"부산지방법원",  // 09-부산지방법원
				"울산지방법원",  // 10-울산지방법원
				"창원지방법원",  // 11-창원지방법원
				"광주지방법원",  // 12-광주지방법원
				"전주지방법원",  // 13-전주지방법원
				"제주지방법원 " }; // 14-제주지방법원 
		
	}	// end of static void init()
} //  end of public class CodeTable
