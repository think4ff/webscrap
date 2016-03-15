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
			logger.error( LpasCode + "-���� �ڵ带 ã�� �� �����ϴ�.");
			return "error";
		}
		
		return bumCodeTable[ idx ];
	}
	
	static String getBumName( String LpasCode ) 
	{
		init();
		
		int idx = Integer.valueOf(LpasCode);
		
		if( idx >= bumNameTable.length ){
			logger.error( LpasCode + "-���� �̸��� ã�� �� �����ϴ�.");
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
		
		logger.error( SagunNm + "-��� �ڵ带 ã�� �� �����ϴ�.");
		
		return "error";
	}
	
	static void init()
	{
		if( init == true ) return; 
		
		init = true; 
		// ��� �����ڵ� ���̺� - LPAS������ �ѱ� �α��� �ڵ常 ����ϹǷ� �ѱ���, �����ڴ� ��� �ּ�ó����
		sagunCodeTable = new SagunCode [] { 
            new SagunCode( "����", "cv" ),
    		new SagunCode( "����", "cv" ),
    		new SagunCode( "����", "cv" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "whp" ),
    		new SagunCode( "����", "whp" ),
    		new SagunCode( "��Ȯ", "whp" ),
    		new SagunCode( "��ȸ", "whp" ),
    		new SagunCode( "���", "cr" ),
    		new SagunCode( "���", "ym" ),
    		//new SagunCode( "�����", "wey" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		//new SagunCode( "��", "gw" ),
    		//new SagunCode( "��", "hs" ),
    		new SagunCode( "����", "hs" ),
    		new SagunCode( "����", "hs" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "cv" ),
    		//new SagunCode( "��", "ks" ),
    		//new SagunCode( "��", "cr" ),
    		//new SagunCode( "��", "hs" ),
    		new SagunCode( "����", "ks" ),
    		new SagunCode( "����", "ks" ),
    		//new SagunCode( "��", "cv" ),
    		//new SagunCode( "��", "cr" ),
    		//new SagunCode( "��", "hs" ),
    		//new SagunCode( "��", "ks" ),
    		new SagunCode( "���", "ks" ),
    		new SagunCode( "����", "ks" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "cr" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "ks" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "cv" ),
    		//new SagunCode( "��", "cr" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "ks" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "gb" ),
    		//new SagunCode( "��", "cr" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "hg" ),
    		new SagunCode( "���", "et" ),
    		new SagunCode( "����", "et" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "hs" ),
    		new SagunCode( "����", "hg" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "et" ),
    		//new SagunCode( "��", "cr" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "et" ),
    		new SagunCode( "�ζ�", "hg" ),
    		new SagunCode( "�θ�", "hg" ),
    		new SagunCode( "��ī", "et" ),
    		//new SagunCode( "��", "et" ),
    		//new SagunCode( "�簡��", "cv" ),
    		//new SagunCode( "�簡��", "cv" ),
    		//new SagunCode( "�簡��", "cv" ),
    		//new SagunCode( "�簨��", "cr" ),
    		//new SagunCode( "�簨��", "cr" ),
    		//new SagunCode( "�簨��", "cr" ),
    		//new SagunCode( "����", "cr" ),
    		//new SagunCode( "�����", "cr" ),
    		//new SagunCode( "�����", "cr" ),
    		new SagunCode( "�籸", "hs" ),
    		//new SagunCode( "�籸��", "hs" ),
    		//new SagunCode( "�籸��", "hs" ),
    		new SagunCode( "���", "hg" ),
    		new SagunCode( "�糪", "cv" ),
    		new SagunCode( "���", "ks" ),
    		new SagunCode( "���", "cr" ),
    		new SagunCode( "�紩", "hs" ),
    		//new SagunCode( "�����", "ks" ),
    		//new SagunCode( "�����", "ks" ),
    		new SagunCode( "���", "cv" ),
    		new SagunCode( "�絵", "cr" ),
    		new SagunCode( "���", "hs" ),
    		new SagunCode( "���", "ks" ),
    		//new SagunCode( "����", "ks" ),
    		//new SagunCode( "�����", "ks" ),
    		new SagunCode( "���", "hg" ),
    		new SagunCode( "���", "hg" ),
    		new SagunCode( "�縣", "ks" ),
    		new SagunCode( "�縶", "hg" ),
    		new SagunCode( "���", "cv" ),
    		new SagunCode( "�繫", "hg" ),
    		new SagunCode( "���", "ks" ),
    		new SagunCode( "���", "hg" ),
    		new SagunCode( "�罺", "hg" ),
    		new SagunCode( "����", "hg" ),
    		//new SagunCode( "��ī��", "et" ),
    		new SagunCode( "����", "pt" ),
    		new SagunCode( "����", "pt" ),
    		//new SagunCode( "��", "gb" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		//new SagunCode( "��", "et" ),
    		//new SagunCode( "���簡��", "cv" ),
    		//new SagunCode( "���簡��", "cv" ),
    		//new SagunCode( "���簡��", "cv" ),
    		//new SagunCode( "���籸", "hs" ),
    		//new SagunCode( "���糪", "cv" ),
    		//new SagunCode( "���紩", "hs" ),
    		//new SagunCode( "�������", "ks" ),
    		//new SagunCode( "�������", "ks" ),
    		//new SagunCode( "�����", "ks" ),
    		//new SagunCode( "������", "ks" ),
    		//new SagunCode( "�������", "ks" ),
    		//new SagunCode( "���縣", "ks" ),
    		//new SagunCode( "�����", "cv" ),
    		//new SagunCode( "�����", "ks" ),
    		//new SagunCode( "���罺", "hg" ),
    		new SagunCode( "���", "ka" ),
    		new SagunCode( "���", "ka" ),
    		new SagunCode( "����", "ka" ),
    		//new SagunCode( "��", "dc" ),
    		new SagunCode( "����", "wdc" ),
    		//new SagunCode( "ó", "gb" ),
    		//new SagunCode( "��", "cr" ),
    		new SagunCode( "�ʱ�", "cr" ),
    		new SagunCode( "�ʺ�", "cr" ),
    		new SagunCode( "�ʻ�", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "����", "cr" ),
    		new SagunCode( "��ġ", "cr" ),
    		//new SagunCode( "��", "hs" ),
    		new SagunCode( "ġ��", "cr" ),
    		new SagunCode( "ġ��", "cr" ),
    		new SagunCode( "ġ��", "cr" ),
    		new SagunCode( "ġ��", "cr" ),
    		new SagunCode( "ġ��", "cr" ),
    		new SagunCode( "ġ��", "cr" ),
    		new SagunCode( "ġ��", "cr" ),
    		//new SagunCode( "ī", "ka" ),
    		new SagunCode( "ī��", "go" ),
    		new SagunCode( "ī��", "ka" ),
    		new SagunCode( "ī��", "ka" ),
    		//new SagunCode( "ī����", "ka" ),
    		new SagunCode( "ī��", "ka" ),
    		new SagunCode( "ī��", "ka" ),
    		new SagunCode( "ī��", "ka" ),
    		new SagunCode( "ī��", "et" ),
    		new SagunCode( "ī��", "jj" ),
    		new SagunCode( "ī��", "ka" ),
    		new SagunCode( "ī��", "et" ),
    		new SagunCode( "īȮ", "ka" ),
    		//new SagunCode( "��", "cr" ),
    		new SagunCode( "Ÿ��", "gj" ),
    		new SagunCode( "Ÿä", "gj" ),
    		//new SagunCode( "Ǫ", "sb" ),
    		new SagunCode( "Ǫ��", "sb" ),
    		new SagunCode( "�ϱ�", "whp" ),
    		new SagunCode( "�ϴ�", "whp" ),
    		new SagunCode( "�ϸ�", "whp" ),
    		new SagunCode( "����", "whp" ),
    		new SagunCode( "��Ȯ", "whp" ),
    		new SagunCode( "���", "et" ),
    		//new SagunCode( "��", "pt" ),
    		new SagunCode( "ȣ��", "et" ),
    		new SagunCode( "ȸ��", "whp" ),
    		new SagunCode( "ȸ��", "whp" ),
    		new SagunCode( "ȸ��", "whp" ),
    		new SagunCode( "ȸȮ", "whp" ),
    		//new SagunCode( "��", "pt" ),
    		//new SagunCode( "��", "hg" ),
    		//new SagunCode( "��", "hg" ) 
    		};
		
		bumCodeTable = new String [] { 
				"dummy",  
				"000210", // 01-�����߾�����
				"000214", // 02-�������������
				"000240", // 03-��õ�������
				"000250", // 04-�����������
				"000260", // 05-��õ�������
				"000280", // 06-�����������
				"000270", // 07-û���������
				"000310", // 08-�뱸�������
				"000410", // 09-�λ��������
				"000411", // 10-����������
				"000420", // 11-â���������
				"000510", // 12-�����������
				"000520", // 13-�����������
				"000530" }; // 14-����������� 
		
		bumNameTable = new String [] { 
				"dummy",  
				"�����߾�����",  // 01-�����߾�����
				"�������������", // 02-�������������
				"��õ�������",  // 03-��õ�������
				"�����������",  // 04-�����������
				"��õ�������",  // 05-��õ�������
				"�����������",  // 06-�����������
				"û���������",  // 07-û���������
				"�뱸�������",  // 08-�뱸�������
				"�λ��������",  // 09-�λ��������
				"����������",  // 10-����������
				"â���������",  // 11-â���������
				"�����������",  // 12-�����������
				"�����������",  // 13-�����������
				"����������� " }; // 14-����������� 
		
	}	// end of static void init()
} //  end of public class CodeTable
