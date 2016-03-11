package com.wangyin.wepaypc.client;

import com.eypg.alipay.config.AlipayConfig;
import com.eypg.alipay.util.AlipayCore;
import com.wangyin.wepaypc.model.BasePayOrderInfo;
import com.wangyin.wepaypc.util.RSACoder;
import com.wangyin.wepaypc.util.Sha256Util;
import com.wangyin.wepaypc.util.SignUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SignHelper
{
  private static Logger logger = Logger.getLogger(SignHelper.class);
  
  public static String getSign(BasePayOrderInfo clientPayOrderInfo, String key)
    throws Exception
  {
    List<String> unSignedKeyList = new ArrayList();
    unSignedKeyList.add("merchantSign");
    unSignedKeyList.add("version");
    unSignedKeyList.add("successCallbackUrl");
    unSignedKeyList.add("forPayLayerUrl");
    
    String strSourceData = SignUtil.signString(clientPayOrderInfo, unSignedKeyList);
    logger.info("source:" + strSourceData);
    

    byte[] sha256SourceSignByte = Sha256Util.encrypt(strSourceData.getBytes("UTF-8"));
    
    byte[] newsks = RSACoder.encryptByPrivateKey(sha256SourceSignByte, key);
    String result = RSACoder.encryptBASE64(newsks);
    

    logger.info("sign:" + result);
    return result;
  }
  
  public static String getUrl(BasePayOrderInfo clientPayOrderInfo, String oriUrl, String key)
    throws Exception
  {
    StringBuffer sb = new StringBuffer();
    sb.append(oriUrl);
    sb.append("?version=" + urlEncode(clientPayOrderInfo.getVersion()));
    sb.append("&token=" + urlEncode(clientPayOrderInfo.getToken()));
    sb.append("&merchantNum=" + urlEncode(clientPayOrderInfo.getMerchantNum()));
    sb.append("&merchantRemark=" + urlEncode(clientPayOrderInfo.getMerchantRemark()));
    sb.append("&tradeNum=" + urlEncode(clientPayOrderInfo.getTradeNum()));
    sb.append("&tradeName=" + urlEncode(clientPayOrderInfo.getTradeName()));
    sb.append("&tradeDescription=" + urlEncode(clientPayOrderInfo.getTradeDescription()));
    sb.append("&tradeTime=" + urlEncode(clientPayOrderInfo.getTradeTime()));
    sb.append("&tradeAmount=" + urlEncode(clientPayOrderInfo.getTradeAmount()));
    sb.append("&currency=" + urlEncode(clientPayOrderInfo.getCurrency()));
    sb.append("&notifyUrl=" + urlEncode(clientPayOrderInfo.getNotifyUrl()));
    sb.append("&successCallbackUrl=" + urlEncode(clientPayOrderInfo.getSuccessCallbackUrl()));
    sb.append("&forPayLayerUrl=" + urlEncode(clientPayOrderInfo.getForPayLayerUrl()));
    sb.append("&ip=" + urlEncode(clientPayOrderInfo.getIp()));
    sb.append("&merchantSign=" + urlEncode(getSign(clientPayOrderInfo, key)));
    logger.info("url:" + sb.toString());
    return sb.toString();
  }
  
  public static String buildRequest(BasePayOrderInfo clientPayOrderInfo, String oriUrl, String key) throws Exception
  {
    StringBuffer sbHtml = new StringBuffer();
    
    sbHtml.append("<form id=\"batchForm\" name=\"batchForm\" action=\""+oriUrl + "\" method=\"post\">");
    sbHtml.append("<input type=\"hidden\" name=\"version\" value=\"" + clientPayOrderInfo.getVersion() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"token\" value=\"" + clientPayOrderInfo.getToken() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"merchantNum\" value=\"" + clientPayOrderInfo.getMerchantNum() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"merchantRemark\" value=\"" + clientPayOrderInfo.getMerchantRemark() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"tradeNum\" value=\"" + clientPayOrderInfo.getTradeNum() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"tradeName\" value=\"" + clientPayOrderInfo.getTradeName() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"tradeDescription\" value=\"" + clientPayOrderInfo.getTradeDescription() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"tradeTime\" value=\"" + clientPayOrderInfo.getTradeTime() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"tradeAmount\" value=\"" + clientPayOrderInfo.getTradeAmount() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"currency\" value=\"" + clientPayOrderInfo.getCurrency() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"notifyUrl\" value=\"" + clientPayOrderInfo.getNotifyUrl() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"successCallbackUrl\" value=\"" + clientPayOrderInfo.getSuccessCallbackUrl() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"forPayLayerUrl\" value=\"" + clientPayOrderInfo.getForPayLayerUrl() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"ip\" value=\"" + clientPayOrderInfo.getIp() + "\"/>");
    sbHtml.append("<input type=\"hidden\" name=\"merchantSign\" value=\"" + getSign(clientPayOrderInfo, key) + "\"/>");
    String strButtonName = "确认";
    sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
    sbHtml.append("<script>document.forms['batchForm'].submit();</script>");
    
    return sbHtml.toString();
  }
  
  public static String urlEncode(String input)
  {
    try
    {
      if ((input == null) || (input.length() == 0)) {
        return "";
      }
      return URLEncoder.encode(input, "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      throw new IllegalArgumentException("Unsupported Encoding Exception", e);
    }
  }
}
