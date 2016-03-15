package com.aurg.webscrap.data;


public class DataSagunInput extends DataRoot {
	String ds_nm;	       // 이름
	String bub_cd;         // 법원코드 
	String bub_lpas_cd;    // LPAS 내부 법원코드
	String sa_year;        // 사건 년도
	String sa_gubun_nm;    // 사건 구분 이름
	String cmd1_value;     // 명령어1 for 사건일반내용
	String cmd2_value;     // 명령어2 for 사건진행내용
	String sa_serial;      // 사건 번호

	DataSagunInput() {} 
	
	public DataSagunInput( String name, String bub_lpas_cd, String param3 ) 
	{
		super();
		
		// mapping 알고리즘을 구현하여 ,Sagun에 대한 값을 변환한다. 
		//this.type = type;
		this.ds_nm = name;                   // 이름 
	
		this.bub_cd= CodeTable.getBumCode( bub_lpas_cd );     // bum_name 으로부터 변환 해야함
		this.bub_lpas_cd = bub_lpas_cd;                       // LPAS 내부 법원코드를 저장하고 있는다.
		this.sa_year = param3.substring(0,4);
		this.sa_gubun_nm = param3.substring(4,6);
		this.cmd1_value= getCmdCode(this.sa_gubun_nm, 1);      // sa_gubun_nm으로 부터 변환 해야함 
		this.cmd2_value= getCmdCode(this.sa_gubun_nm, 2);      // sa_gubun_nm으로 부터 변환 해야함 

		this.sa_serial = param3.substring(6);
		
		this.type = TYPE.SaGunInput;
		// for Test System.out.println( toString()); // for debuging.. 
	}
	
	public String getCmdCode( String sa_gubun_nm , int idx )
	{
	    String cmd; 
		String cmd_sa_gubun = CodeTable.getSagunCode( sa_gubun_nm);

	    //---  법원 홈페이지 발췌   http://safind.scourt.go.kr/sf/mysafind.jsp파일  SearchSano()            ---//
	    //=========================================================================================================
	    // 업무별 Cmd 정의
	    //=========================================================================================================
	    if(cmd_sa_gubun == "WKS" || cmd_sa_gubun == "wks") cmd_sa_gubun = "ks";
	    else if(cmd_sa_gubun == "WHS" || cmd_sa_gubun == "whs") cmd_sa_gubun = "hs";
	    else if(cmd_sa_gubun == "WPT" || cmd_sa_gubun == "wpt") cmd_sa_gubun = "pt";
	    else if(cmd_sa_gubun == "WGJ" || cmd_sa_gubun == "wgj") cmd_sa_gubun = "gj";
	    else if(cmd_sa_gubun == "WCR" || cmd_sa_gubun == "wcr") cmd_sa_gubun = "cr";
	    else if(cmd_sa_gubun == "WYM" || cmd_sa_gubun == "wym") cmd_sa_gubun = "ym";
	    else if(cmd_sa_gubun == "WGB" || cmd_sa_gubun == "wgb") cmd_sa_gubun = "gb";
	    else if(cmd_sa_gubun == "WSB" || cmd_sa_gubun == "wsb") cmd_sa_gubun = "sb";
	    else if(cmd_sa_gubun == "WET" || cmd_sa_gubun == "wet") cmd_sa_gubun = "et";
	    else if(cmd_sa_gubun == "WKA" || cmd_sa_gubun == "wka") cmd_sa_gubun = "ka"; //2013.03.28 윤정현 [CJS4] 민사신청의 경우 기존 CMD를 호출하도록 사건구분 변경
	    else if(cmd_sa_gubun == "WGO" || cmd_sa_gubun == "wgo") cmd_sa_gubun = "go"; //2013.03.28 윤정현 [CJS4] 공시최고의 경우 기존 CMD를 호출하도록 사건구분 변경

	    if( idx == 1 )
	    	cmd = cmd_sa_gubun+".SF"+cmd_sa_gubun.toUpperCase()+"01s01Cmd";
	    else  // idx == 2
	    	cmd = cmd_sa_gubun+".SF"+cmd_sa_gubun.toUpperCase()+"02s01Cmd";
	    
	    return cmd;		
	}
		
	@Override
	public TYPE getType() {
		// TODO Auto-generated method stub
		return this.type;
	}

	public String getCmd1_value() {
		return cmd1_value;
	}
	
	public String getCmd2_value() {
		return cmd2_value;
	}	

	public String getBub_cd() {
		return bub_cd;
	}
	
	public String getBub_lpas_cd() {
		
		if( bub_lpas_cd.length() == 1 )
			return "0" + bub_lpas_cd;
		else 
		    return bub_lpas_cd;
	}

	public String getSa_year() {
		return sa_year;
	}

	public String getSa_gubun_nm() {
		return sa_gubun_nm;
	}

	public String getSa_serial() {
		return sa_serial;
	}

	public String getDs_nm() {
		return ds_nm;
	}
	
	public String toString() {
		return ds_nm + "," +	      // 이름
			   bub_cd + "," +         // 법원코드 
		       sa_year + "," +        // 사건 년도
		       sa_gubun_nm + "," +    // 사건 구분 이름
		       cmd1_value + "," +     // 명령어1 for 사건일반내용
		       cmd2_value + "," +     // 명령어2 for 사건진행내용
		       sa_serial;             // 사건 번호
	}
}
