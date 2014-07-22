 /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /**
 * Project  : WebQQCoreAsync
 * Package  : iqq.im.action
 * File     : LoginAction.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-2
 * License  : Apache License 2.0 
 */
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.QQException.QQErrorCode;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.util.QQEncryptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
 *
 *
 * @author solosky <solosky772@qq.com>
 *
 */
public class WebLoginAction extends AbstractHttpAction {
	private static final Logger LOG = LoggerFactory.getLogger(WebLoginAction.class);
	private String username;
	private String password;
	private long   uin;
	private String verifyCode;

	public WebLoginAction(QQContext context, QQActionListener listener,
			String username, String password, long uin, String verifyCode) {
		super(context, listener);
		this.username = username;
		this.password = password;
		this.uin = uin;
		this.verifyCode = verifyCode;
	}

	@Override
	public QQHttpRequest buildRequest() throws QQException {
		/*
			u:1070772010
			p:D2E8ECC0E10185EFAEECFD3532BC34F7
			verifycode:dsads
			webqq_type:10
			remember_uin:1
			login2qq:1
			aid:1003903
			u1:http://web2.qq.com/loginproxy.html?login2qq=1&webqq_type=10
			h:1
			ptredirect:0
			ptlang:2052
			daid:164
			from_ui:1
			pttype:1
			dumy:
			fp:loginerroralert
			action:4-28-1632882
			mibao_css:m_webqq
			t:1
			g:1
			js_type:0
			js_ver:10038
			login_sig:a4YzJkO9z19WM0-M6fZ9rRGyo7QhwGz7GjiQW4MiSdxldWj9uNf8D9D1DAZNlMqF
		 */
		
		//尝试登录，准备传递的参数值
		QQHttpRequest req = createHttpRequest("GET", QQConstants.URL_UI_LOGIN);
		req.addGetValue("u", username);
		req.addGetValue("p", QQEncryptor.encrypt(uin, password, verifyCode));
		req.addGetValue("verifycode", verifyCode);
		req.addGetValue("webqq_type", "10");
		req.addGetValue("remember_uin","1");
		req.addGetValue("login2qq", "1");
		req.addGetValue("aid", "1003903");
		req.addGetValue("u1", "http://web.qq.com/loginproxy.html?login2qq=1&webqq_type=10");
		req.addGetValue("h", "1");
		req.addGetValue("ptredirect", "0");
		req.addGetValue("ptlang", "2052");
		req.addGetValue("daid", "164");
		req.addGetValue("from_ui", "1");
		req.addGetValue("pttype", "1");
		req.addGetValue("dumy", "");
		req.addGetValue("fp", "loginerroralert");
		req.addGetValue("action", "4-28-1632882");
		req.addGetValue("mibao_css", "m_webqq");
		req.addGetValue("t", "1");
		req.addGetValue("g", "1");
		req.addGetValue("js_type", "0");
		req.addGetValue("js_ver", "10038");
		req.addGetValue("login_sig", getContext().getSession().getLoginSig());

		req.addHeader("Referer", QQConstants.REFFER);
		return req;
	}

	@Override
	protected void onHttpStatusOK(QQHttpResponse response) throws QQException {
		Pattern pt = Pattern.compile(QQConstants.REGXP_LOGIN);
	     Matcher mc = pt.matcher(response.getResponseString());
	     LOG.info("WebLogin: " + response.getResponseString());
	     if(mc.find()){
	    	int ret = Integer.parseInt(mc.group(1));
	    	switch(ret){
		    	case 0: notifyActionEvent(QQActionEvent.Type.EVT_OK, mc.group(3)); break;	
		    	case 3: throw new QQException(QQErrorCode.WRONG_PASSWORD, mc.group(5));
		    	case 4: throw new QQException(QQErrorCode.WRONG_CAPTCHA, mc.group(5));
		    	case 7: throw new QQException(QQErrorCode.IO_ERROR, mc.group(5));
		    	default: throw new QQException(QQErrorCode.INVALID_USER, mc.group(5));
	    	}
	     }else{
	    	 throw new QQException(QQErrorCode.UNEXPECTED_RESPONSE);
	     }		
	}

}
