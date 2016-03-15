/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.aurg.webscrap.io;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;

public class HttpUtilparam {

    public static CloseableHttpClient getHttpClient( final String SoeID, final String Password,final int type )
    {
        CloseableHttpClient httpclient;

        // Auth Scheme 지정
        final Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create()
                                            .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
                                            .register(AuthSchemes.BASIC, new BasicSchemeFactory())
                                            .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
                                            .register(AuthSchemes.SPNEGO,  new SPNegoSchemeFactory())
                                            .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
                                            .build();

        // NTLM 인증정보 지정
        final CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT),//AuthScope("localhost", 8080),
                new NTCredentials( SoeID, Password,"dummy", "APAC" ));
               //new NTCredentials( SoeID, Password,"APACKR081WV058", "APAC" ));


        // ------------- cookie start ------------------------
        // Auction은 필요 없는 정보임
        // Create a local instance of cookie store
        final CookieStore cookieStore = new BasicCookieStore();

        // 사건징핸내용에서 체크박스를 선택한것과 같은 효과를 내기위해서 Cookie 값을 설정한다.
        final BasicClientCookie cookie = new BasicClientCookie("songdal_view", "YES");
        cookie.setVersion(0);
        cookie.setDomain(".scourt.go.kr");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        // ------------- cookie end ------------------------

        if( type == 0 )  // TYPE.AuctionInput
        {
        	// HTTP Client 생성
            httpclient = HttpClients.custom()
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setDefaultCredentialsProvider(credsProvider)
                    .build();
        }
        else  // SagunInput
        {
        	// HTTP Client 생성
            httpclient = HttpClients.custom()
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setDefaultCredentialsProvider(credsProvider)
                    .setDefaultCookieStore(cookieStore)
                    .build();
        }

        return httpclient;
    }

    public static RequestConfig getConfig()
    {
    	// Proxy 정보 생성
        final HttpHost proxy = new HttpHost("krproxy.apac.nsroot.net", 8080);
        //HttpHost proxy = new HttpHost("192.193.81.42", 8080);
        final RequestConfig config = RequestConfig.custom()
                                            .setProxy(proxy)
                                            .build();

        return config;
    }

    public static void setHttpPost1(final HttpPost http_post, final int tab ) throws URISyntaxException
    {
        final String [] targetURL = { 
        		"http://www.courtauction.go.kr/RetrieveRealEstDetailInqSaList.laf",              // 사건내역  tab
                "http://www.courtauction.go.kr/RetrieveRealEstSaDetailInqGiilList.laf",          // 기일내역 tab
                "http://www.courtauction.go.kr/RetrieveRealEstSaDetailInqMungunSongdalList.laf"};// 문건/송달 내역 tab
        http_post.setURI(new URI(targetURL[ tab-1 ]));
    }

    public static void setHttpPost2( final HttpPost http_post ) throws URISyntaxException
    {
        http_post.setURI(new URI( "http://safind.scourt.go.kr/sf/servlet/SFSuperSvl"));
    }

}