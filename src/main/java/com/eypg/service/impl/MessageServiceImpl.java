package com.eypg.service.impl;

import com.eypg.dao.BaseDAO;
import com.eypg.pojo.Message;
import com.eypg.service.MessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl
  implements MessageService
{
  @Autowired
  @Qualifier("baseDao")
  BaseDAO baseDao;
  
  public void add(Message t)
  {
    baseDao.saveOrUpdate(t);
  }
  
  public void delete(Integer id) {}
  
  public Message findById(String id)
  {
    return null;
  }
  
  public List<Message> query(String hql)
  {
    return null;
  }
  
  public void update(String hql) {}
}
