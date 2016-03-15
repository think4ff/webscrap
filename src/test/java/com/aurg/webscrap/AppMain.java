package com.aurg.webscrap;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppMain {

	final static Logger log = LoggerFactory.getLogger(AppMain.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://www.courtauction.go.kr/RetrieveRealEstDetailInqSaList.laf";
		
		urlConnect(url);
	}
	
	private static boolean urlConnect(String url) {
		
		try {
			Document doc = Jsoup.connect(url)
					.data("jiwonNm","법원이름")
					.data("saNo","20140130001288")
					.data("_SRCH_SRNID", "PNO102014")
					.userAgent("Mozilla")
					.cookie("songdal_view", "YES")
					.timeout(10000)
					.post()
					;
			
			Element ele = doc.body();
			
			log.debug(ele.toString());
			
		} catch (IOException e) {
//			e.printStackTrace();
			log.error("{} - 연결 오류", url);
			return false;
		}
		
		
		return true;
	}

}
