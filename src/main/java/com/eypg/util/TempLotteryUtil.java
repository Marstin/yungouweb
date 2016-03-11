package com.eypg.util;

import com.eypg.pojo.Latestlottery;
import com.eypg.pojo.Product;
import com.eypg.pojo.ProductCart;
import com.eypg.pojo.Spellbuyproduct;
import com.eypg.pojo.Spellbuyrecord;
import com.eypg.pojo.User;
import com.eypg.service.LatestlotteryService;
import com.eypg.service.ProductService;
import com.eypg.service.SpellbuyproductService;
import com.eypg.service.SpellbuyrecordService;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext*.xml"})
@Repository
@Service("TempLotteryUtil")
public class TempLotteryUtil
{
  @Autowired
  private SpellbuyrecordService spellbuyrecordService;
  @Autowired
  private SpellbuyproductService spellbuyproductService;
  @Autowired
  private LatestlotteryService latestlotteryService;
  @Autowired
  private ProductService productService;
  private Product product;
  private User user;
  private Spellbuyproduct spellbuyproduct;
  private Latestlottery latestlottery;
  private Spellbuyrecord spellbuyrecord;
  private ProductCart productCart;
  Calendar calendar = Calendar.getInstance();
  
  @Test
  public void go()
    throws InterruptedException
  {
    List<Latestlottery> list = latestlotteryService.query("from Latestlottery latestlottery where 1=1");
    System.err.println("size:" + list.size());
    for (Latestlottery latestlottery : list)
    {
      Long l = latestlottery.getDateSum();
      l = Long.valueOf(l.longValue() - 1L);
      latestlottery.setDateSum(l);
      System.err.println("default:" + latestlottery.getDateSum() + "   update:" + l);
      latestlotteryService.add(latestlottery);
    }
  }
}
