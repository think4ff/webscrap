package com.aurg.webscrap.data;

import java.util.ArrayList;

public class DataAuctionOutput extends DataRoot {

/*	* ���ð������� -> ���ð�����
	* ���䱸������ -> ���䱸������
	* ����(�����) ->��������(LPAS)
	* �����Ű��ݾ�(�����) ->�����ݾ�(LPAS)
	* ���ϰ��(�����) ->�������(LPAS)  
	  ������� ( ����(�����) - > ����(LPAS), �Ű�(�����) - > �����㰡����(LPAS) )
	* �Ű�����(�����) ->��������(LPAS)
	* �Ű��ݾ�(�����) ->������(LPAS)
	* �����򰡾�(�����) - > 1�����簡(LPAS)
	* ������ޱ���(�����) ->������ޱ���(LPAS)
	* ������(�����) -> ��ݳ��α���(LPAS)
	* �۴޳��� -> �޸�*/
	
	public String mngNo;                              // Auction Primary Key 
	public String bub_nm;                             // �����̸�  
	public String db_key;                             // DB Upload �ÿ� ����� Key ��   
	public String GaeSiDate;                          // ���ð������� -> ���ð�����
	public String BaeDangYokuDate; 	                  // ���䱸������ -> ���䱸������  
	public ArrayList<String> YipChalDateList;         // ����(�����) ->��������(LPAS)
	public ArrayList<String> YipChalKeumAkList;       // �����Ű��ݾ�(�����) ->�����ݾ�(LPAS) 
	public ArrayList<String> YipChalResultList;	      // ���ϰ��(�����) ->�������(LPAS) / ������� ( ����(�����) - > ����(LPAS), �Ű�(�����) - > �����㰡����(LPAS) ) 
	public String NakChalDate;                        // �Ű�����(�����) ->��������(LPAS)  
	public String NakCahlKa;                          // �Ű��ݾ�(�����) ->������(LPAS) 
	public String IlChaBeobSaGa;                      // �����򰡾�(�����) - > 1�����簡(LPAS)
	public String DaeGeumGiHan;                       // ������ޱ���(�����) ->������ޱ���(LPAS)
	public String DaeGeumNapBuDate;                   // ������(�����) -> ��ݳ��α���(LPAS) 
	public ArrayList<String> MemoList;                // �۴޳��� -> �޸� 
	public String YipChalCount; 
	public String MemoCount;
	
	// Fail�� �߻� ���� �� ���Ǵ� �ʵ��.. 
	public String fail_reason;
	
	// ������
	public DataAuctionOutput( DataAuctionInput input )
	{
		YipChalDateList = new ArrayList<String>();
		YipChalKeumAkList = new ArrayList<String>();
		YipChalResultList = new ArrayList<String>();		
		MemoList = new ArrayList<String>();
				
		db_key = "";                      // db key
		GaeSiDate = "";                   // ���ð������� -> ���ð�����
		BaeDangYokuDate = ""; 	          // ���䱸������ -> ���䱸������  
		NakChalDate = "";                 // �Ű�����(�����) ->��������(LPAS)  
		NakCahlKa = "";                   // �Ű��ݾ�(�����) ->������(LPAS) 
		IlChaBeobSaGa = "";               // �����򰡾�(�����) - > 1�����簡(LPAS)
		DaeGeumGiHan = "";                // ������ޱ���(�����) ->������ޱ���(LPAS)
		DaeGeumNapBuDate  = "";           // ������(�����) -> ��ݳ��α���(LPAS) 
		YipChalCount = ""; 
		MemoCount = "";

		// Auction Primary Key
		this.mngNo = input.mngNo;
		// �����̸�
		this.bub_nm = input.bum_nm;
	}
		
	public String getSuccessColumnHead()
	{
		return "PrimaryKey,��Ź�ȣ,���ð�����,���䱸������,1�����簡,��������,������,������ޱ���,��ݳ��α���,��������,�޸𰹼�"; 
	}
	
	public String getFailColumnHead()
	{
		return "������,��Ź�ȣ,��������"; 
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
		
		// ���� List = "��������, ������¥1, �����ݾ�1, �������1,������¥2, �����ݾ�2, �������2,"  �������� �����.
		// ���� List�� 0���̸�  = "0" �� �����ȴ�. 
		YipChalCount = Integer.toString(YipChalDateList.size());		
		if( YipChalDateList.size() > 0  )
		{
			for(int i=0; i < YipChalDateList.size(); i++ ) 
			{
				strYipChal = strYipChal + YipChalDateList.get(i) + "," + YipChalKeumAkList.get(i) + "," + YipChalResultList.get(i) + ",";
			}
		}
		
		// Memo List = "�޸𰹼�, �޸�1, �޸�2, �޸�3"  �������� �����. 
		// Memo List�� 0���̸�  = "0" �� �����ȴ�. 		
		String strMemo = "";
		MemoCount = Integer.toString(MemoList.size());
		if( MemoList.size() > 0  )
		{
			for(int i=0; i < MemoList.size(); i++ ) 
			{
				strMemo = strMemo + MemoList.get(i) + ",";
			}
		}
				
		strReturn = mngNo + "," +                  // Primary key��
		            db_key + "," +                 // DB Key��  
		            GaeSiDate + "," +              // ���ð������� -> ���ð�����
		            BaeDangYokuDate + "," +        // ���䱸������ -> ���䱸������
                    IlChaBeobSaGa + "," +          // �����򰡾�(�����) - > 1�����簡(LPAS)
                    NakChalDate + "," +            // �Ű�����(�����) ->��������(LPAS)
                    NakCahlKa + "," +              // �Ű��ݾ�(�����) ->������(LPAS)   
                    DaeGeumGiHan + "," +           // ������ޱ���(�����) ->������ޱ���(LPAS)
                    DaeGeumNapBuDate + "," +       // ������(�����) -> ��ݳ��α���(LPAS)
                    YipChalCount + "," +           // �����������
                    MemoCount + "," +              // �޸𰹼����
		            strYipChal +                   // �������� ������ŭ �ݺ� ( ��������(LPAS), �����ݾ�(LPAS), �������(LPAS) )
                    strMemo;                       // �޸� ������ŭ �ݺ�
		
		return strReturn;
	}
	

	// ���н� ���� ������ ����Ѵ�.
	public String getFailInfo()
	{
		return bub_nm + "," + db_key + "," + fail_reason;    // �����̸�,��� �ĺ���ȣ,��������
	}
}
