package com.eypg.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eypg.dao.BaseDAO;
import com.eypg.pojo.OrderId;
import com.eypg.service.OrderIdService;

@Service
public class OrderIdServiceImpl implements OrderIdService{
	@Autowired
	BaseDAO baseDAO;

	@Override
	public String addOxid() {
		final OrderId oid = new OrderId(System.currentTimeMillis());
		baseDAO.saveOrUpdate(oid);
		return oid.getOxid();
	}
	
}
