package com.eypg.service;

import java.util.List;

import com.eypg.dao.Pagination;
import com.eypg.pojo.Collectproduct;
import com.eypg.pojo.Product;
import com.eypg.pojo.Spellbuyproduct;

@SuppressWarnings("rawtypes")
public abstract interface SpellbuyproductService
  extends TService<Spellbuyproduct, Integer>
{
  public abstract List findByProductId(int paramInt);
  
  public abstract List findBySpellbuyProductId(int paramInt);
  
  public abstract List findBylotteryDetailByProductId(int paramInt);
  
  public abstract Pagination upcomingAnnounced(int paramInt1, int paramInt2);
  
  public abstract Pagination upcomingAnnouncedByTop(int paramInt1, int paramInt2);
  
  public abstract Pagination productPeriodList(Integer productId, int pageNo, int pageSize);
  
  public abstract Integer productPeriodListByCount(Integer paramInteger);
  
  public abstract Pagination getCollectGoodsList(String paramString, int paramInt1, int paramInt2);
  
  public abstract void addCollectGoods(Collectproduct paramCollectproduct);
  
  public abstract void delCollectGoods(Integer paramInteger);
  
  public abstract List checkCollectGoods(Integer paramInteger, String paramString);
  
  public abstract Spellbuyproduct findSpellbuyproductLastPeriod(Integer paramInteger);
  
  public abstract Spellbuyproduct findSpellbuyproductByStatus(Integer paramInteger);
  
  public abstract Pagination announcedProduct(int paramInt1, int paramInt2);
  
  public abstract List loadAllByType();
  
  public abstract List loadAll();
  
  public abstract List UpdateLatestlotteryGetList();
  
  public abstract List findSpellbuyproductByProductIdIsStatus(Integer paramInteger);
  
  public abstract List getIndexNewProduct();
  
  public abstract List getGoodsPeriodInfo(Integer paramInteger1, Integer paramInteger2);
 
  public abstract List findByProductId(int productId, boolean lock);
  
  public abstract Spellbuyproduct getByProductId(int productId);
  
  public abstract Spellbuyproduct getByProductId(int productId, boolean lock);
  
  public abstract  List<Object[]> mayfullBuyProducts(int pageNo, int pageSize, boolean lock);

  public abstract void generateNextPeriods(int nr);
  
  public void generateNextPeriod(Spellbuyproduct buyProduct);
  
  public abstract void updateStatus(Product product, Spellbuyproduct buyProduct);
  
}
