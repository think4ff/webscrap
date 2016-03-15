package com.aurg.webscrap.data;

public class DataAuctionInput extends DataRoot {

	String mngNo;   // Action Primary Key�� 
	String bum_nm;  // �����̸�
	String year;    // �⵵
	String no;      // ��ǹ�ȣ
	String db_key;  // db_key�� Output ������ ���ؼ� backup �صд�.
	
	DataAuctionInput() {}
	public DataAuctionInput(String mngNo, String bum_nm, String auction_info ) {
		super();
		this.mngNo = mngNo;
		this.bum_nm = bum_nm;
		this.db_key = auction_info;
		this.year = auction_info.substring(0,4);
		this.no = auction_info.substring(6);
		this.type = TYPE.AuctionInput; 		
		// for Test System.out.println( toString() );
	}
	
	@Override
	public TYPE getType() {
		// TODO Auto-generated method stub
		return type;
	}
		
	public String getBum_nm() {
		return bum_nm;
	}
	public String getYear() {
		return year;
	}
	public String getNo() {
		return no;
	}
	
	public String getDbKey(){
		return db_key;
	}
	
	public String toString() {
		return bum_nm + "," +  
			   year + "," + 
			   no;
	}



}
