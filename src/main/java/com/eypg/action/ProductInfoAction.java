package com.eypg.action;

import com.eypg.pojo.Product;
import com.eypg.pojo.Productimage;
import com.eypg.service.ProductImageService;
import com.eypg.service.ProductService;
import com.eypg.util.ApplicationListenerImpl;
import com.eypg.util.Struts2Utils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ProductInfoAction")
public class ProductInfoAction extends BaseAction
{
  private static final long serialVersionUID = -5354743639843709687L;
  @Autowired
  private ProductService productService;
  @Autowired
  private ProductImageService productImageService;
  private String id;
  private Product product;
  private List<Productimage> productimageList;
  
  public String index()
  {
    if (!ApplicationListenerImpl.sysConfigureAuth)
    {
      Struts2Utils.renderHtml("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>授权过期 1元拍购开发中心</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><div align=\"center\" style=\"color: #f60;font-family: arial,微软雅黑;font-size: 18px;margin-top: 180px;\">该系统授权已过期，请联系管理员重新授权！谢谢合作。<a href=\"http://www.51openos.com\">www.51openos.com</a></div></body></html>", new String[0]);
      

      return null;
    }
    product = ((Product)productService.findById(id));
    
    productimageList = productImageService.findByProductId(String.valueOf(product.getProductId()), "show");
    
    return "index";
  }
  
  public String getId()
  {
    return id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public Product getProduct()
  {
    return product;
  }
  
  public void setProduct(Product product)
  {
    this.product = product;
  }
  
  public List<Productimage> getProductimageList()
  {
    return productimageList;
  }
  
  public void setProductimageList(List<Productimage> productimageList)
  {
    this.productimageList = productimageList;
  }
}
