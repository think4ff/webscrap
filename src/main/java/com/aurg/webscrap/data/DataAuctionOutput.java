package com.aurg.webscrap.data;

import java.util.ArrayList;

public class DataAuctionOutput extends DataRoot {

/*	* 개시결정일자 -> 개시결정일
	* 배당요구종기일 -> 배당요구종기일
	* 기일(대법원) ->입찰일자(LPAS)
	* 최저매각금액(대법원) ->입찰금액(LPAS)
	* 기일결과(대법원) ->입찰결과(LPAS)  
	  입찰결과 ( 유찰(대법원) - > 유찰(LPAS), 매각(대법원) - > 낙찰허가결정(LPAS) )
	* 매각일자(대법원) ->낙찰일자(LPAS)
	* 매각금액(대법원) ->낙찰가(LPAS)
	* 감정평가액(대법원) - > 1차법사가(LPAS)
	* 대금지급기한(대법원) ->대금지급기한(LPAS)
	* 납부일(대법원) -> 대금납부기일(LPAS)
	* 송달내역 -> 메모*/
	
	public String mngNo;                              // Auction Primary Key 
	public String bub_nm;                             // 법원이름  
	public String db_key;                             // DB Upload 시에 사용할 Key 값   
	public String GaeSiDate;                          // 개시결정일자 -> 개시결정일
	public String BaeDangYokuDate; 	                  // 배당요구종기일 -> 배당요구종기일  
	public ArrayList<String> YipChalDateList;         // 기일(대법원) ->입찰일자(LPAS)
	public ArrayList<String> YipChalKeumAkList;       // 최저매각금액(대법원) ->입찰금액(LPAS) 
	public ArrayList<String> YipChalResultList;	      // 기일결과(대법원) ->입찰결과(LPAS) / 입찰결과 ( 유찰(대법원) - > 유찰(LPAS), 매각(대법원) - > 낙찰허가결정(LPAS) ) 
	public String NakChalDate;                        // 매각일자(대법원) ->낙찰일자(LPAS)  
	public String NakCahlKa;                          // 매각금액(대법원) ->낙찰가(LPAS) 
	public String IlChaBeobSaGa;                      // 감정평가액(대법원) - > 1차법사가(LPAS)
	public String DaeGeumGiHan;                       // 대금지급기한(대법원) ->대금지급기한(LPAS)
	public String DaeGeumNapBuDate;                   // 납부일(대법원) -> 대금납부기일(LPAS) 
	public ArrayList<String> MemoList;                // 송달내역 -> 메모 
	public String YipChalCount; 
	public String MemoCount;
	
	// Fail이 발생 했을 때 사용되는 필드들.. 
	public String fail_reason;
	
	// 생성자
	public DataAuctionOutput( DataAuctionInput input )
	{
		YipChalDateList = new ArrayList<String>();
		YipChalKeumAkList = new ArrayList<String>();
		YipChalResultList = new ArrayList<String>();		
		MemoList = new ArrayList<String>();
				
		db_key = "";                      // db key
		GaeSiDate = "";                   // 개시결정일자 -> 개시결정일
		BaeDangYokuDate = ""; 	          // 배당요구종기일 -> 배당요구종기일  
		NakChalDate = "";                 // 매각일자(대법원) ->낙찰일자(LPAS)  
		NakCahlKa = "";                   // 매각금액(대법원) ->낙찰가(LPAS) 
		IlChaBeobSaGa = "";               // 감정평가액(대법원) - > 1차법사가(LPAS)
		DaeGeumGiHan = "";                // 대금지급기한(대법원) ->대금지급기한(LPAS)
		DaeGeumNapBuDate  = "";           // 납부일(대법원) -> 대금납부기일(LPAS) 
		YipChalCount = ""; 
		MemoCount = "";

		// Auction Primary Key
		this.mngNo = input.mngNo;
		// 법원이름
		this.bub_nm = input.bum_nm;
	}
		
	public String getSuccessColumnHead()
	{
		return "PrimaryKey,경매번호,개시결정일,배당요구종기일,1차법사가,낙찰일자,낙찰가,대금지급기한,대금납부기일,입찰갯수,메모갯수"; 
	}
	
	public String getFailColumnHead()
	{
		return "법원명,경매번호,오류원인"; 
	}
		
	
	@Override
	public TYPE getType() {
		// TODO Auto-generated method stub
		return TYPE.AuctionOutput;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String strReturn;
		
		String strYipChal = "";  
		
		// 입찰 List = "입찰갯수, 입찰날짜1, 입찰금액1, 입찰결과1,입찰날짜2, 입찰금액2, 입찰결과2,"  형식으로 만든다.
		// 입찰 List가 0개이면  = "0" 만 생성된다. 
		YipChalCount = Integer.toString(YipChalDateList.size());		
		if( YipChalDateList.size() > 0  )
		{
			for(int i=0; i < YipChalDateList.size(); i++ ) 
			{
				strYipChal = strYipChal + YipChalDateList.get(i) + "," + YipChalKeumAkList.get(i) + "," + YipChalResultList.get(i) + ",";
			}
		}
		
		// Memo List = "메모갯수, 메모1, 메모2, 메모3"  형식으로 만든다. 
		// Memo List가 0개이면  = "0" 만 생성된다. 		
		String strMemo = "";
		MemoCount = Integer.toString(MemoList.size());
		if( MemoList.size() > 0  )
		{
			for(int i=0; i < MemoList.size(); i++ ) 
			{
				strMemo = strMemo + MemoList.get(i) + ",";
			}
		}
				
		strReturn = mngNo + "," +                  // Primary key값
		            db_key + "," +                 // DB Key값  
		            GaeSiDate + "," +              // 개시결정일자 -> 개시결정일
		            BaeDangYokuDate + "," +        // 배당요구종기일 -> 배당요구종기일
                    IlChaBeobSaGa + "," +          // 감정평가액(대법원) - > 1차법사가(LPAS)
                    NakChalDate + "," +            // 매각일자(대법원) ->낙찰일자(LPAS)
                    NakCahlKa + "," +              // 매각금액(대법원) ->낙찰가(LPAS)   
                    DaeGeumGiHan + "," +           // 대금지급기한(대법원) ->대금지급기한(LPAS)
                    DaeGeumNapBuDate + "," +       // 납부일(대법원) -> 대금납부기일(LPAS)
                    YipChalCount + "," +           // 입찰갯수출력
                    MemoCount + "," +              // 메모갯수출력
		            strYipChal +                   // 입찰정보 개수만큼 반복 ( 입찰일자(LPAS), 입찰금액(LPAS), 입찰결과(LPAS) )
                    strMemo;                       // 메모 개수만큼 반복
		
		return strReturn;
	}
	

	// 실패시 실패 원인을 출력한다.
	public String getFailInfo()
	{
		return bub_nm + "," + db_key + "," + fail_reason;    // 법원이름,경매 식별번호,실패이유
	}
}
