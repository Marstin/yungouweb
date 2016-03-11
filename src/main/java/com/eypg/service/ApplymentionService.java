package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Applymention;

public abstract interface ApplymentionService
  extends TService<Applymention, Integer>
{
  public abstract Pagination getApplymentionList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Integer getApplymentionListByCount(String paramString1, String paramString2, String paramString3);
}
