package com.aurg.webscrap.parser;

public class AuctionTab2GiIlNaeYeok {
	
    public AuctionTab2GiIlNaeYeok() {
		super();
		this.KiIlDate = "";
		this.KiIlCategory = "";
		this.KiIlJangSo = "";
		this.MinMaeKakPrice = "";
		this.KiIlResult = "";
		this.KiIlResult_price = "";
		this.KiIlResult_date = "";
	}
    
	public String KiIlDate;         // 기일
    public String KiIlCategory;     // 기일종류
    public String KiIlJangSo;       // 기일장소
    public String MinMaeKakPrice;   // 최저매각가격 
    public String KiIlResult;       // 기일결과 
    public String KiIlResult_price; // 기일결과 금액
    public String KiIlResult_date;  // 기일결과 날짜   
    
    public String toString()
    {
    	return  this.KiIlDate + "," + 
				this.KiIlCategory + "," + 
				this.KiIlJangSo  + "," + 
				this.MinMaeKakPrice  + "," + 
				this.KiIlResult  + "," + 
				this.KiIlResult_price  + "," + 
				this.KiIlResult_date;
    }
}
