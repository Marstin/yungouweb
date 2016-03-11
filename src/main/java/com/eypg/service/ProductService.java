package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Product;

public abstract interface ProductService
  extends TService<Product, Integer>
{
  public abstract Pagination searchSpellbuyproduct(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Pagination searchProduct(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Product findProductByName(String paramString);
  
  public abstract Pagination ProductListByTypeIdList(String paramString1, String paramString2, int paramInt1, int paramInt2);
}
