package com.eypg.sms;

import com.eypg.sms.chuangming.CmSender;
import com.eypg.sms.office2.Office2Sender;
import com.eypg.sms.smsbao.SmsbaoSender;
import com.eypg.util.ApplicationListenerImpl;
import com.eypg.util.StringUtil;

public class SmsSenderFactory {

	public static SmsSender create() {
		String smsType = ApplicationListenerImpl.sysConfigureJson.getSmsType();
		if(StringUtil.equals(smsType, "chuangming"))
			return new CmSender();
		else if(StringUtil.equals(smsType, "smsbao"))
			return new SmsbaoSender();
		else if(StringUtil.equals(smsType, "2office"))
			return new Office2Sender();
		return new CmSender();
	}
	
	public static SmsSender cmsender() {
		return new CmSender();
	}
	
	public static SmsSender smsbaosender() {
		return new SmsbaoSender();
	}
	
}
