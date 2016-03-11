package com.eypg.sms.office2;

import java.util.Date;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.eypg.pojo.SysConfigure;
import com.eypg.sms.SmsSender;
import com.eypg.sms.zucp.ZucpSender;
import com.eypg.util.ApplicationListenerImpl;

public class Office2Sender extends SmsSender {

	String loginUrl = "http://sms.2office.net:8080/WebService/sms3.aspx";
	
	@Override
	public boolean send(String mobile, String content) {
		SysConfigure conf = ApplicationListenerImpl.sysConfigureJson;
		HttpClient httpClient = new HttpClient();
		boolean isSuccess = false;
		try {
			PostMethod postMethod = new PostMethod(loginUrl);
			postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
			NameValuePair[] data = {
					new NameValuePair("account", conf.getMessagePartner()),	//门牌号码
					new NameValuePair("password", Md5.md5Encrypt(conf.getMessageKey())),	//登录密码+授权码
					new NameValuePair("mobile", mobile),
					new NameValuePair("content",content),
					new NameValuePair("channel", conf.getMessageChannel()),
					new NameValuePair("smsid", getSmsId()),
					new NameValuePair("sendType", "1") };
			postMethod.setRequestBody(data);
			int statusCode = httpClient.executeMethod(postMethod);

			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				Header locationHeader = postMethod
						.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					System.out.println("The page was redirected to:" + location);
				} else {
					System.err.println("Location field value is null.");
				}
			} else {
				String body = postMethod.getResponseBodyAsString();
				if (body == null) {
					System.out.println("结果为空");
				}
				System.out.println("结果为" + postMethod.getResponseBodyAsString());
				isSuccess=true;
			}

			postMethod.releaseConnection();

		} catch (Exception e) {
			// e.printStackTrace();
		}

		return isSuccess;
	}

	@Override
	public String getMesBalance() {
		return "";
	}
	
	public static String getSmsId(){
		Date date = new Date();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
		return formatter.format(date);
	}


	public static void main(String[] args) throws Exception {
		new Office2Sender().send("13758249979","test111");
	
	}
	
}
