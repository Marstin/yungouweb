package com.eypg.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eypg.iapppay.Order;
import com.eypg.iapppay.config.IAppPaySDKConfig;
import com.eypg.iapppay.sign.SignHelper;
import com.eypg.iapppay.util.HttpUtils;
import com.eypg.iapppay.util.SignUtils;
import com.eypg.pojo.Commissionpoints;
import com.eypg.pojo.Commissionquery;
import com.eypg.pojo.Consumerdetail;
import com.eypg.pojo.Consumetable;
import com.eypg.pojo.Latestlottery;
import com.eypg.pojo.Lotteryproductutil;
import com.eypg.pojo.Product;
import com.eypg.pojo.ProductCart;
import com.eypg.pojo.ProductJSON;
import com.eypg.pojo.Randomnumber;
import com.eypg.pojo.Spellbuyproduct;
import com.eypg.pojo.Spellbuyrecord;
import com.eypg.pojo.SysConfigure;
import com.eypg.pojo.User;
import com.eypg.service.CommissionpointsService;
import com.eypg.service.CommissionqueryService;
import com.eypg.service.ConsumerdetailService;
import com.eypg.service.ConsumetableService;
import com.eypg.service.LatestlotteryService;
import com.eypg.service.LotteryproductutilService;
import com.eypg.service.ProductService;
import com.eypg.service.RandomnumberService;
import com.eypg.service.SpellbuyproductService;
import com.eypg.service.SpellbuyrecordService;
import com.eypg.service.UserService;
import com.eypg.util.ApplicationListenerImpl;
import com.eypg.util.ConfigUtil;
import com.eypg.util.RandomUtil;
import com.eypg.util.StringUtil;
import com.eypg.util.Struts2Utils;
import com.jubaobar.pay.JubaoPay;
import com.jubaobar.pay.RSA;

@Component("JubaopayAction")
public class JubaoPayAction extends BaseAction {

	private static final long serialVersionUID = -5196480632527407047L;
	private static final Logger LOG = LoggerFactory.getLogger(JubaoPayAction.class);
	private static String secret = ConfigUtil.getValue("secret", "1yyg1234567890");
	private static final String JUBAOPAY_GATEWAY = "https://www.jubaopay.com/apipay.htm";

	RandomUtil randomUtil = new RandomUtil();
	@Autowired
	ConsumetableService consumetableService;
	@Autowired
	ConsumerdetailService consumerdetailService;
	@Autowired
	private SpellbuyrecordService spellbuyrecordService;
	@Autowired
	private RandomnumberService randomnumberService;
	@Autowired
	private UserService userService;
	@Autowired
	private SpellbuyproductService spellbuyproductService;
	@Autowired
	private LatestlotteryService latestlotteryService;
	@Autowired
	private ProductService productService;
	@Autowired
	CommissionqueryService commissionqueryService;
	@Autowired
	CommissionpointsService commissionpointsService;
	@Autowired
	LotteryproductutilService lotteryproductutilService;
	private User user;
	private String userId;
	private Consumetable consumetable;
	private ProductCart productCart;
	private Product product;
	private Spellbuyproduct spellbuyproduct;
	private Spellbuyrecord spellbuyrecord;
	private Randomnumber randomnumber;
	private Latestlottery latestlottery;
	private Consumerdetail consumerdetail;
	private List<ProductCart> productCartList;
	private List<ProductJSON> successCartList;
	private ProductJSON productJSON;
	private String paymentStatus;
	private Commissionquery commissionquery;
	private Commissionpoints commissionpoints;
	private Lotteryproductutil lotteryproductutil;
	private String tradeNo;
	private String money;
	private String title;
	private String memo;
	private String alipay_account;
	private String tenpay_account;
	private String gateway;
	private String sign;
	private String token;
	private String payName;
	private Integer moneyCount;
	private String integral;
	private String out_trade_no;
	Random random = new Random();
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	
	public String goPay() throws Exception {
		request = Struts2Utils.getRequest();
		response = Struts2Utils.getResponse();
		
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("moneyCount", moneyCount.toString());
		map.put("out_trade_no", out_trade_no);
		String mysign="";
		try {
			mysign = getSign(map);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(!StringUtil.equals(token, mysign)){
			Struts2Utils.render("text/html",
					"<script>alert(\"验证失败，请联系客服！\");window.location.href=\"/index.html\";</script>",
					StringUtil.ENCA_UTF8);
			return null;
		}
		
		final SysConfigure conf = ApplicationListenerImpl.sysConfigureJson;
		String notify_url = conf.getWwwUrl() + "/jubaopay/notifyUrl.action";
		String return_url = conf.getWwwUrl() + "/jubaopay/returnUrl.action";
		String subject = conf.getSaitName();
		float total_fee = moneyCount;
		//float total_fee = 0.01f;
		//String payid = "pre_" + String.valueOf(System.currentTimeMillis());
		String payMethod = "ALL";
		
		RSA.intialize();
		
		JubaoPay jubaoPay = new JubaoPay();
		jubaoPay.setEncrypt("payid",out_trade_no);
		jubaoPay.setEncrypt("partnerid",conf.getJubaoPayPartner());//商户合作号，由聚宝云计费生成的唯一标识
		jubaoPay.setEncrypt("amount",total_fee+"");
		jubaoPay.setEncrypt("payerName",userId);
		jubaoPay.setEncrypt("goodsName",subject);
		jubaoPay.setEncrypt("returnURL",return_url);
		jubaoPay.setEncrypt("callBackURL",notify_url);
		jubaoPay.setEncrypt("payMethod",payMethod);
		jubaoPay.setEncrypt("remark", subject);
		jubaoPay.interpret();
		
		String message = jubaoPay.getMessage();
	    String signature = jubaoPay.getSignature();
	    
		String sHtmlText = buildRequest(message, signature, payMethod, "post", "确认");
		LOG.debug(sHtmlText);
		Struts2Utils.render("text/html", sHtmlText, StringUtil.ENCA_UTF8);
		// Struts2Utils.render("text/html", sHtmlText);
		return null;
	}
	
	/**
	* 对参数进行MD5
	*
	* @param params
	* 排好序的参数Map
	* @param secret
	* 应用的密钥
	* @return MD5签名字符串
	* @throws UnsupportedEncodingException
	*/
	protected static String getSign(final TreeMap<String, String> params) throws UnsupportedEncodingException {
		if (null == params || params.isEmpty()) {
			return (String) null;
		}

		/*if (StringUtils.isEmpty(secret)) {
			return (String) null;
		}*/
		StringBuilder sb = new StringBuilder();
		for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			sb/*.append(entry.getKey())*/.append(defaultString(entry.getValue()));
		}
		sb.append(secret);
		byte[] bytes = sb.toString().getBytes("UTF-8");
		return DigestUtils.md5Hex(bytes);
	}
	
	public static String defaultString(String str) {
		return str == null ? "" : str;
	}
	
	public static String buildRequest(String message, String signature, String payMethod, String strMethod, String strButtonName)
	  {
	    StringBuffer sbHtml = new StringBuffer();
	    
	    sbHtml.append("<form id=\"payBillForm\" name=\"payBillForm\" action=\""+JUBAOPAY_GATEWAY + "\" method=\"" + strMethod + "\">");
	    sbHtml.append("<input type=\"hidden\" name=\"message\" value=\"" + message + "\"/>");
	    sbHtml.append("<input type=\"hidden\" name=\"signature\" value=\"" + signature + "\"/>");
	    sbHtml.append("<input type=\"submit\" value=\"" + payMethod + "\" style=\"display:none;\"></form>");
	    sbHtml.append("<script>document.forms['payBillForm'].submit();</script>");
	    
	    return sbHtml.toString();
	  }
	
	public String UrlEncode(String src, String charset)
			throws UnsupportedEncodingException
	{
		return URLEncoder.encode(src, charset).replace("+", "%20");
	}

	public String returnUrl() throws UnsupportedEncodingException {
		if (afterPaying()) {
			Struts2Utils.render("text/html",
					"<script>window.location.href=\"/mycart/shopok.html?id="
							+ out_trade_no + "\";</script>",
					StringUtil.ENCA_UTF8);
		}else{
			Struts2Utils
			.render("text/html",
					"<script>alert(\"验证失败，请联系客服！\");window.location.href=\"/index.html\";</script>",
					StringUtil.ENCA_UTF8);
		}
		return null;
	}

	public String notifyUrl() throws Exception {
		if (afterPaying()) {
			Struts2Utils.render("text/html", "success", StringUtil.ENCA_UTF8);
		}else{
			Struts2Utils.render("text/html", "fail", StringUtil.ENCA_UTF8);
		}
		return null;
	}

	private final boolean afterPaying() throws UnsupportedEncodingException {
		request = Struts2Utils.getRequest();
		response = Struts2Utils.getResponse();
		String message = request.getParameter("message");
		String signature = request.getParameter("signature");
		
		RSA.intialize();
		// 解密，校验签名，并处理业务逻辑处理
		JubaoPay jubaopay = new JubaoPay();
		boolean result = jubaopay.decrypt(message, signature);
		String payid = null;
		String amount = null;
		String remark = null;
		String orderNo = null;
		String state = null;
		String modifyTime = null;
		// 签名验证成功
		if(result){
			payid = jubaopay.getEncrypt("payid");
			amount = jubaopay.getEncrypt("amount");
			remark = jubaopay.getEncrypt("remark");
			orderNo = jubaopay.getEncrypt("orderNo");
			state = jubaopay.getEncrypt("state");
			modifyTime = jubaopay.getEncrypt("modifyTime");
			
			final Consumetable cons = consumetableService.paid(payid, orderNo);
			return true;
		} else {
			// 签名验证失败
			payid = "签名验证失败";
			return false;
		}
	}
	
	public Integer getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(Integer moneyCount) {
		this.moneyCount = moneyCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Consumetable getConsumetable() {
		return consumetable;
	}

	public void setConsumetable(Consumetable consumetable) {
		this.consumetable = consumetable;
	}

	public ProductCart getProductCart() {
		return productCart;
	}

	public void setProductCart(ProductCart productCart) {
		this.productCart = productCart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Spellbuyproduct getSpellbuyproduct() {
		return spellbuyproduct;
	}

	public void setSpellbuyproduct(Spellbuyproduct spellbuyproduct) {
		this.spellbuyproduct = spellbuyproduct;
	}

	public Spellbuyrecord getSpellbuyrecord() {
		return spellbuyrecord;
	}

	public void setSpellbuyrecord(Spellbuyrecord spellbuyrecord) {
		this.spellbuyrecord = spellbuyrecord;
	}

	public Randomnumber getRandomnumber() {
		return randomnumber;
	}

	public void setRandomnumber(Randomnumber randomnumber) {
		this.randomnumber = randomnumber;
	}

	public Latestlottery getLatestlottery() {
		return latestlottery;
	}

	public void setLatestlottery(Latestlottery latestlottery) {
		this.latestlottery = latestlottery;
	}

	public Consumerdetail getConsumerdetail() {
		return consumerdetail;
	}

	public void setConsumerdetail(Consumerdetail consumerdetail) {
		this.consumerdetail = consumerdetail;
	}

	public List<ProductCart> getProductCartList() {
		return productCartList;
	}

	public void setProductCartList(List<ProductCart> productCartList) {
		this.productCartList = productCartList;
	}

	public List<ProductJSON> getSuccessCartList() {
		return successCartList;
	}

	public void setSuccessCartList(List<ProductJSON> successCartList) {
		this.successCartList = successCartList;
	}

	public ProductJSON getProductJSON() {
		return productJSON;
	}

	public void setProductJSON(ProductJSON productJSON) {
		this.productJSON = productJSON;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Commissionquery getCommissionquery() {
		return commissionquery;
	}

	public void setCommissionquery(Commissionquery commissionquery) {
		this.commissionquery = commissionquery;
	}

	public Commissionpoints getCommissionpoints() {
		return commissionpoints;
	}

	public void setCommissionpoints(Commissionpoints commissionpoints) {
		this.commissionpoints = commissionpoints;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public Lotteryproductutil getLotteryproductutil() {
		return lotteryproductutil;
	}

	public void setLotteryproductutil(Lotteryproductutil lotteryproductutil) {
		this.lotteryproductutil = lotteryproductutil;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}

	public String getTenpay_account() {
		return tenpay_account;
	}

	public void setTenpay_account(String tenpay_account) {
		this.tenpay_account = tenpay_account;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
