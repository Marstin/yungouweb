package com.eypg.service;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Orderdetailaddress;

public abstract interface OrderdetailaddressService
  extends TService<Orderdetailaddress, Integer>
{
  public abstract Pagination orderdetailaddressList(int paramInt1, int paramInt2);
}
