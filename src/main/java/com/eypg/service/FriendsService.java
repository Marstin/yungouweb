package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Friends;

public abstract interface FriendsService
  extends TService<Friends, Integer>
{
  public abstract Pagination getFriends(String paramString, int paramInt1, int paramInt2);
}
