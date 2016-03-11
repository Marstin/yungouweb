package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Consumerdetail;

public abstract interface ConsumerdetailService
  extends TService<Consumerdetail, Integer>
{
  public abstract Pagination userByConsumetableDetail(String paramString, int paramInt1, int paramInt2);
  
  public abstract Integer userByConsumetableDetailByCount(String paramString);
  
  public abstract Pagination orderInfo(String paramString, int paramInt1, int paramInt2);
}
