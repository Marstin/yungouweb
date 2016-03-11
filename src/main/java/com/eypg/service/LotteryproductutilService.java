package com.eypg.service;

import com.eypg.pojo.Lotteryproductutil;

import java.util.List;

public abstract interface LotteryproductutilService
  extends TService<Lotteryproductutil, Integer>
{
  public abstract List<Lotteryproductutil> loadAll();

  public abstract List<Lotteryproductutil> loadAll(Integer spellbuyProductStatus);
  
  public List<Lotteryproductutil> findList(int pageNo, int pageSize);
  
}
