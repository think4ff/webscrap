package com.aurg.webscrap.io;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aurg.webscrap.MainUX;
import com.aurg.webscrap.data.DataAuctionInput;
import com.aurg.webscrap.data.DataAuctionOutput;
import com.aurg.webscrap.data.DataRoot;
import com.aurg.webscrap.data.DataRoot.TYPE;
import com.aurg.webscrap.data.DataSagunInput;
import com.aurg.webscrap.data.DataSagunOutput;
import com.aurg.webscrap.parser.HtmlParserAuction;
import com.aurg.webscrap.parser.HtmlParserSagun;


/* 사건검색 Tab2 Sample 
 * <form name=?"frm1" action=?"/?RetrieveRealEstSaDetailInqGiilList.laf" class=?"table_contents" method=?"post" target=?"_self">?
<input type=?"hidden" name=?"jiwonNm" value=?"안양지원">?
<input type=?"hidden" name=?"saNo" value=?"20130130007395">?
<input type=?"hidden" name=?"maemulSer" value>?
<input type=?"hidden" name=?"maeGiil" value>?
<input type=?"hidden" name=?"srnID" value=?"PNO102018">?
<input type=?"hidden" name=?"_NAVI_CMD" value=?"InitMulSrch.laf">?
<input type=?"hidden" name=?"_NAVI_SRNID" value=?"PNO102014">?
<input type=?"hidden" name=?"_SRCH_SRNID" value=?"PNO102014">?
<input type=?"hidden" name=?"_CUR_CMD" value=?"RetrieveRealEstDetailInqSaList.laf">?
<input type=?"hidden" name=?"_CUR_SRNID" value=?"PNO102018">?
<input type=?"hidden" name=?"_NEXT_CMD" value=?"RetrieveRealEstSaDetailInqGiilList.laf">?
<input type=?"hidden" name=?"_NEXT_SRNID" value=?"PNO102019">?
<input type=?"hidden" name=?"_PRE_SRNID" value>?
<input type=?"hidden" name=?"_LOGOUT_CHK" value>?
<input type=?"hidden" name=?"_FORM_YN" value=?"Y">?
<input type=?"hidden" name=?"_C_saNo" value=?"20130130007395">?
<input type=?"hidden" name=?"_C_maemulSer" value>?
<input type=?"hidden" name=?"_C_srnID" value=?"PNO102019">?
<input type=?"hidden" name=?"_C_jiwonNm" value=?"안양지원">?
</form>?

*/
 
public class HttpIO extends Thread {
	
	//protected static Logger logger = Logger.getLogger( HttpIO.class.getName());
	protected static Logger logger = LoggerFactory.getLogger( HttpIO.class);

	String SoeID; 
	String Password; 
	int    SuccessCount;
	
	CloseableHttpClient httpclient = null;
    CloseableHttpResponse response = null;    	
    HttpPost httpPost = null;
    
    public HttpIO( String id, String passwd ) {
    	this.SoeID = id; 
    	this.Password = passwd;
    }
    
    // Http protocol Initialize
    void httpInit(TYPE type)
    {
    	
        logger.info( "http init start ================= ");
             
		// HTTP Client 생성 
    	if( type == TYPE.AuctionInput )
    	{
            httpclient = HttpUtilparam.getHttpClient ( SoeID, Password, 0 );
    	}
    	else
    	{
            httpclient = HttpUtilparam.getHttpClient ( SoeID, Password, 1 );
    	}
               
        httpPost = new HttpPost();            
        //dev_mode 가 아니면 proxy 설정
        if( !MainUX.isDevMode())
        {
            httpPost.setConfig( HttpUtilparam.getConfig());               	
        }
    }    
        
    // Http 관련 객체들을 Close 한다.
    void httpClose() 
    {
		logger.info("called");			
    	
		try 
		{
	        response.close();
	        httpclient.close();    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
    		logger.error("http close exception" , e);		
    		MainUX.log.out( " http close exception!!" );		
		}		
    }

    
	// 법원에서 경매사건의 HTTP IO를 수행하고 Response 값을 확인한다. 
	// 성공일때는 Parsing 진행 및 Return true
	// 실패일때는 Return false 
    // 
    // 항상 3개 Tab 정보를 순차적으로 읽어들인다. 
    boolean httpExecuteAuction(DataAuctionInput input, int tab , DataAuctionOutput output )
    {
    	//System.out.println(input.toString());    	
    	boolean rtn = false; 
    	
        String cmd;
		logger.info( input.toString() );			
        
        try {
        	    HttpUtilparam.setHttpPost1( httpPost, tab );

                List <NameValuePair> nvps = new ArrayList <NameValuePair>();

                nvps.add(new BasicNameValuePair("jiwonNm",  input.getBum_nm() ));
                
                int auction_no = Integer.parseInt( input.getNo() );                
                cmd = String.format("013%07d", auction_no);
                
                nvps.add(new BasicNameValuePair("saNo",  input.getYear() + cmd )); //  "20130130029018"

                // 고정값  - 기일내역 필수항목이지만, 사건내역, 문건/송달내역에 전송해도 상관없음...
                nvps.add(new BasicNameValuePair("_SRCH_SRNID", "PNO102014"));        
                
                // Encoding Type 지정
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "EUC-KR"));
                
                // set fake user agent
                httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");

                //System.out.println("Executing request " + httpPost.getRequestLine() + " to " +   + " via " + proxy);

                response = httpclient.execute( httpPost);
                if( response.getStatusLine().toString().equals("HTTP/1.1 200 OK")) 
                {
                	// Parsing 처리
                	rtn = HtmlParserAuction.doParsing( tab, input, EntityUtils.toString(response.getEntity()), output );
                }
                else    // fail시에 실패 원인을 얻어온다. 
                {
                	rtn = false;
                	output.fail_reason = response.getStatusLine().toString();
                	logger.error("Not HTTP 200 : " + response.getStatusLine().toString());	
                }
                
    	} catch ( Exception e ) {
    		output.fail_reason = e.toString();
    		logger.error("경매조회 실패" , e);
    	}    	
    	
    	return rtn;
    }
    
	// 법원에서 사건의 HTTP IO를 수행하고 Response 값을 확인한다. 
	// 성공일때는 Parsing 진행 및 Return true
	// 실패일때는 Return false
    //
    // Type에 따라서 사건일반, 사건경과 Tab을 읽는다. 
    boolean httpExcuteSagun(DataSagunInput input, int tab , DataSagunOutput output )
    {
    	boolean rtn = false; 
 
		logger.info( input.toString() );			
    	
        try {

            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            
            // input 입력값 사용 ----
            nvps.add(new BasicNameValuePair("sa_year",  input.getSa_year() ));      
            nvps.add(new BasicNameValuePair("sch_sa_gbn", input.getSa_gubun_nm()));
            nvps.add(new BasicNameValuePair("sa_serial", input.getSa_serial()));    
            nvps.add(new BasicNameValuePair("ds_nm",  input.getDs_nm()));
            
            if( tab == 1)
                nvps.add(new BasicNameValuePair("cmd",  input.getCmd1_value() ));
            else // tab2
            	nvps.add(new BasicNameValuePair("cmd",  input.getCmd2_value() ));	
            	
            // 법원이름으로 조회하는 함수 구현 필요함. (법원web의 js 참고)
            nvps.add(new BasicNameValuePair("sch_bub_cd", input.getBub_cd()));        
            nvps.add(new BasicNameValuePair("sch_bub_nm", input.getBub_cd()));
            
            // 고정값 ----
            nvps.add(new BasicNameValuePair("link", "N"));
            nvps.add(new BasicNameValuePair("listLinkYn", ""));
            nvps.add(new BasicNameValuePair("theme", "scourt"));    
            nvps.add(new BasicNameValuePair("mysafindYn", "Y"));
            nvps.add(new BasicNameValuePair("cryptKey", "kbIkIAlOoy1QSiXHEqRF5JYIraqLZ4HQxyyvqLgo9xA=")); 
            nvps.add(new BasicNameValuePair("callDomain", "u2U0gM82WZCIu3MNuBrdyWDM5j/iWe9Le8ZGNAAju60="));  
            nvps.add(new BasicNameValuePair("isExternalDomain", "N"));

            // 불필요함.. 
            // nvps.add(new BasicNameValuePair("type", "0"));
            // nvps.add(new BasicNameValuePair("link", "N"));
            // nvps.add(new BasicNameValuePair("mode", ""));
            // nvps.add(new BasicNameValuePair("gongYn", ));
            // nvps.add(new BasicNameValuePair("isExternalDomain", "N"));
            // nvps.add(new BasicNameValuePair("saveCookieYn", "off"));
            // nvps.add(new BasicNameValuePair("SagbnSort", "on"));
            // nvps.add(new BasicNameValuePair("sch_bub_nm.selectedIndex", "154"));
            // nvps.add(new BasicNameValuePair("sa_gu_bun", "whp"));    
            
            // Encoding Type 지정 
            HttpUtilparam.setHttpPost2( httpPost );
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "EUC-KR"));
            
            // set fake user agent
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36");
            
            logger.info("Before httpclient.execute() ");

            HttpClientContext context = null;
            response = httpclient.execute( httpPost,context);
            logger.info("after httpclient.execute() ");

        	// String tempstr = response.getStatusLine().toString();
            if( response.getStatusLine().toString().equals("HTTP/1.1 200 OK")) 
            {
            	// Parsing 처리
            	rtn = HtmlParserSagun.doParsing( tab, input, EntityUtils.toString(response.getEntity()), output );
            }
            else    // fail시에 실패 원인을 얻어온다. 
            {
            	rtn = false;
            	output.fail_reason = response.getStatusLine().toString();
            	logger.error("Not HTTP 200 : " + response.getStatusLine().toString());	
            }
            // EntityUtils.consume(response.getEntity());            
        } catch ( Exception e ) {
          	  // e.printStackTrace();
		      output.fail_reason = e.toString();
	    	  logger.error("사건조회 실패" , e);		      
		      // System.out.println("Here ~!!!!!!!!!!!!!!!");
        }    	
        
    	return rtn;    	
    }
     
    // 입력데이타에 해당하는 정보를 법원사이트에서 조회해온다. 
    private boolean httpExecute( DataRoot inputData,  DataRoot outputData )
    {
    	boolean rtn = false;

    	switch( inputData.getType())
    	{
    	case AuctionInput:	    		
   		    rtn = httpExecuteAuction( (DataAuctionInput) inputData, 1, (DataAuctionOutput)outputData);	   		    
   		    if( rtn == true )  rtn = httpExecuteAuction( (DataAuctionInput) inputData, 2, (DataAuctionOutput)outputData);
   		    if( rtn == true )  rtn = httpExecuteAuction( (DataAuctionInput) inputData, 3, (DataAuctionOutput)outputData );
    		break;
    	default:    //  SaGun
			rtn = httpExcuteSagun( (DataSagunInput) inputData, 1, (DataSagunOutput)outputData );
    		if( rtn == true ) rtn = httpExcuteSagun( (DataSagunInput) inputData, 2, (DataSagunOutput)outputData );
    		break;    		
    	}	   	
		
		return rtn;
    }

 	//2015-01-08, 1초에 200개 이상 데이타를 읽지 못하도록 Delay를 추가
    // 데이타 하나 읽는 시간이 300 msec 미만이면, 300 msec가 되도록 지연을 준다. 
    // 즉, 개당 최소 300sec 가 소요되도록 하여서  1분에 최대 200개까지 읽도록 속도 조절을 한다. 
    void endTimeCheck( Date startTime )
    {
    	long max_diff = 300;
    	Date endTime = new Date();
        long diff = endTime.getTime() - startTime.getTime();
     	    	
        logger.info( " *************  ************* ************* ");
		logger.info( " *************  diff : " + diff );

        if( diff < max_diff )
        {
        	Date before = new Date();
        	logger.info( " -------------  before : " + before.getTime() );
    		logger.info( " -------------  sleep : " + (max_diff - diff) );
            httpSleep( max_diff - diff );
        	Date end = new Date();
        	logger.info( " -------------  end : " + end.getTime() );

        }
    }
    
    void httpSleep(long milisec)
    {
    	if( milisec <= 0) return;
 	    try {
	    	sleep(milisec);  // 지연을 준다.
	    }
	    catch( Exception e)
	    {
	    	e.printStackTrace();   
	    	MainUX.log.out( e.toString());
	    }
    }
    
    /**
     * Executes the GetMethod and prints some status information.
     */
    @Override
    public void run() {
    	int count=0;
    	boolean rtn;
    	OutputResult outputHandle;
    	TYPE type=null;
    	String fileName;
    	
    	logger.info( "start");
    	
        type = ((DataRoot)MainUX.InputList.get(0)).getType();

        //------------------------------------------------------
        // HTTP Init
        httpInit(type);

      //------------------------------------------------------
        // Output file Handle
        if( type == TYPE.AuctionInput )
        	outputHandle = new OutputCSV(type);
        else
        	outputHandle = new OutputExcel();        
        
        fileName = outputHandle.open();
        MainUX.log.out( fileName + " 생성" );
        logger.info(fileName + " 생성");       
        
        // 시작 시간 기록..
    	Date startTime = new Date();
        
        SuccessCount = 0;
        // HTTP Execute for All InitList as 1 by 1
    	for(int i=0; i < MainUX.InputList.size() ; i++) 
    	{
        	count=0;

        	//DataAuctionOutput AuctionOuput2 = new DataAuctionOutput();
        	//DataSagunOutput SagunOutput2 = new DataSagunOutput((DataSagunInput)MainUX.InputList.get(i));
        	Object OutputObj = null; 
        	
        	//2015-01-08, 1초에 200개 이상 데이타를 읽지 못하도록 Delay를 추가
        	Date item_startT = new Date(); 
        	
			// 읽기 실패이면 3번까지 재시도한다.
         	do{
                if( type == TYPE.AuctionInput )
                	OutputObj = new DataAuctionOutput((DataAuctionInput)MainUX.InputList.get(i));
                else
                	OutputObj = new DataSagunOutput((DataSagunInput)MainUX.InputList.get(i));
                
         	    rtn = httpExecute( (DataRoot)MainUX.InputList.get(i), (DataRoot)OutputObj );

         	    httpSleep(0);
    		    count++;
    		    if(rtn == false)
    		    {
    		        httpInit(type);
    		    }
    		}while( rtn == false && count < 3);
    		  
         	//------------------------------------------------------------------
         	// 진행률을 UX에 표기한다. 
         	int value = (int)((((float)(i+1)/(float)MainUX.InputList.size()))*100); // Percentage를 계산한다.         	
         	MainUX.updateUx( String.format("%d/%d",i+1,MainUX.InputList.size()) , value );
         	
         	if( (i+1) % 100 == 0)   // 100개 단위로 UX 로그를 출력한다.
         	{
         		MainUX.log.out( String.format("%d/%d 진행 중......",i+1,MainUX.InputList.size()));
         	}
         	
         	//2015-01-08, 1초에 200개 이상 데이타를 읽지 못하도록 Delay를 추가
         	endTimeCheck( item_startT );
         	
			// 조회 성공이면 성공리스트에, 실패면 Fail list에 담는다.
			if( rtn == true )
			{
				SuccessCount++;
		        outputHandle.write( (DataRoot)OutputObj);
				logger.info( "조회성공 : " + OutputObj.toString() );
			}
			else  // 3번모두 실패하여 rtn == false
			{
				MainUX.FailList.add( OutputObj );
				
				// for Test 
				logger.info( "조회실패 : " + ((DataRoot)OutputObj).getFailInfo() );
				MainUX.log.out( "조회실패 : " + ((DataRoot)OutputObj).getFailInfo() );
			}
    	} // for 

    	// Http protocol close처리를 한다.
       	httpClose();
    	
       	// output file close
       	outputHandle.close();

		MainUX.log.out( String.format(" 조회 성공 - %d건중 %d건", MainUX.InputList.size(), SuccessCount ) );
       	// 조회실패 결과를 출력한다.
       	filewrite_FailList(type);

       	//----------------------------------------------------
       	// 소요시간 계산
    	Date endTime = new Date();
        long diff = endTime.getTime() - startTime.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        MainUX.log.out( String.format("전체 소요시간 - %02d:%02d:%02d", diffHours, diffMinutes, diffSeconds));
        logger.info(String.format("전체 소요시간 - %02d:%02d:%02d", diffHours, diffMinutes, diffSeconds));
	    MainUX.log.out( "=============== 조회 완료 ===============" );
		logger.info( "end");	
    }

	// 성공 실패 내역을 파일 및 log 창으로 출력한다.
	void filewrite_FailList(TYPE type )
	{  		
		// 실패항목이 있는지 확인한다. 
	    if( MainUX.FailList.size() != 0 )
	    {
	    	MainUX.log.out( String.format(" 조회 실패 - %d건중 %d건", MainUX.InputList.size(), MainUX.FailList.size() ) );
	    	
	    	//-----------------------------------------------------------------------------------
	        //  Fail 내역을 파일로 출력한다.
	        try {
				MainUX.log.out( " 조회 실패리스트 : " + OutputResult.WriteFailList(type) );	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("조회실패리스트 생성오류", e);
				MainUX.log.out( " 조회 실패 리스트 출력 파일 생성 오류 !!" );			
			}        
	    }
	            
	}
}
