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
    
	public String KiIlDate;         // ����
    public String KiIlCategory;     // ��������
    public String KiIlJangSo;       // �������
    public String MinMaeKakPrice;   // �����Ű����� 
    public String KiIlResult;       // ���ϰ�� 
    public String KiIlResult_price; // ���ϰ�� �ݾ�
    public String KiIlResult_date;  // ���ϰ�� ��¥   
    
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
