package com.aurg.webscrap.data;

public class DataSagunOutput extends DataRoot {
	// public boolean isSuccess; 
	// key, ���Ǻ�, ������, ä����, ���ð�����, �ΰ�������, ��å������, ����������, �������,��������,���ð���������
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
	
	// Fail�� �߻� ���� �� ���Ǵ� �ʵ��.. 
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
		
		// Fail �� ����� ������ input ��ü�� ���� �޾ƿ´�
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
		return ds_nm + "," +	                         // �̸�
	           bub_lpas_cd + "," +                       // lpas �����ڵ�   
	           bub_nm + "," +                            // ���� �ѱ� �̸�
		       sa_year + sa_gubun_nm + sa_serial + "," + // ��� �⵵+��� ���� �̸�+��� ��ȣ
		       fail_reason;                              // Fail ����
	}
	

	public String getFailColumnHead()
	{
		return "�̸�,Lpas�����ڵ�,�����̸�,�������,��������"; 
	}	
}
