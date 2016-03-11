package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Cardpassword;

public abstract interface CardpasswordService
  extends TService<Cardpassword, Integer>
{
  public abstract Cardpassword doCardRecharge(String paramString1, String paramString2);
  
  public abstract Pagination cardRechargeList(int paramInt1, int paramInt2);
  
  public abstract void deleteByID(Integer paramInteger);
}
