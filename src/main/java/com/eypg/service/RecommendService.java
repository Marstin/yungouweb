package com.eypg.service;

import com.eypg.pojo.Recommend;
import java.util.List;

public abstract interface RecommendService
  extends TService<Recommend, Integer>
{
  public abstract List getRecommend();
}
