package com.aurg.webscrap.data;

public class DataSagunOutput extends DataRoot {
	// public boolean isSuccess; 
	// key, 재판부, 접수일, 채무자, 개시결정일, 인가결정일, 면책결정일, 폐지결정일, 종국결과,금지결정,개시결정통지서
	public String key;
	public String JaePanBu;
	public String JeobSuDate;
	public String ChaeMuJa;
	public String GaeSiDate;
	public String MyunChekDate;
	public String InGaDate;
	public String PageDate;
	public String JongKukResult;
	public String KumGiKyulJung;
	public String GaesiKyulJung;
	
	// Fail이 발생 했을 때 사용되는 필드들.. 
	public String ds_nm;		
	public String bub_lpas_cd;
	public String bub_nm;
	public String sa_year;
	public String sa_gubun_nm;
	public String sa_serial;		
	public String fail_reason;
	
	public DataSagunOutput(DataSagunInput input ) {
		// TODO Auto-generated constructor stub
		this.key = "";
		this.JaePanBu = "";
		this.JeobSuDate = "";
		this.ChaeMuJa = "";
		this.GaeSiDate = "";
		this.MyunChekDate = "";
		this.InGaDate = "";
		this.PageDate = "";
		this.JongKukResult = "";
		this.KumGiKyulJung = "";
		this.GaesiKyulJung = "";
		
		// Fail 시 출력할 정보를 input 객체로 부터 받아온다
		this.fail_reason = "";		
		this.ds_nm = input.ds_nm;	
		this.bub_lpas_cd = input.bub_lpas_cd; 
		this.bub_nm = CodeTable.getBumName(input.bub_lpas_cd);
		this.sa_year = input.sa_year;
		this.sa_gubun_nm = input.sa_gubun_nm;
		this.sa_serial = input.sa_serial;		
	}
	
	@Override
	public TYPE getType() {
		// TODO Auto-generated method stub
		return TYPE.SaGunOutput;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return key + "," + 
		       JaePanBu + "," + 
		       JeobSuDate + "," + 
		       ChaeMuJa + "," + 
		       GaeSiDate + "," + 
		       InGaDate + "," + 
		       MyunChekDate + "," +
		       PageDate + "," + 
		       JongKukResult + "," + 
		       KumGiKyulJung + "," + 
		       GaesiKyulJung;
	}
		
	public String getFailInfo()
	{
		return ds_nm + "," +	                         // 이름
	           bub_lpas_cd + "," +                       // lpas 법원코드   
	           bub_nm + "," +                            // 법원 한글 이름
		       sa_year + sa_gubun_nm + sa_serial + "," + // 사건 년도+사건 구분 이름+사건 번호
		       fail_reason;                              // Fail 이유
	}
	

	public String getFailColumnHead()
	{
		return "이름,Lpas법원코드,법원이름,사건정보,오류원인"; 
	}	
}
