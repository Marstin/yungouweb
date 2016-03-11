package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.IndexImg;
import com.eypg.pojo.Suggestion;
import com.eypg.pojo.SysConfigure;
import java.util.List;

public abstract interface SysConfigureService
  extends TService<SysConfigure, Integer>
{
  public abstract List initializationIndexImgAll();
  
  public abstract List IndexImgAll();
  
  public abstract void addIndexImg(IndexImg paramIndexImg);
  
  public abstract IndexImg findByIndexImgId(String paramString);
  
  public abstract void delIndexImg(Integer paramInteger);
  
  public abstract void doSuggestion(Suggestion paramSuggestion);
  
  public abstract Pagination suggestionList(int paramInt1, int paramInt2);
  
  public abstract void delSuggestion(Integer paramInteger);
}
