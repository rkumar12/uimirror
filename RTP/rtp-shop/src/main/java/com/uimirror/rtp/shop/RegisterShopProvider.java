package com.uimirror.rtp.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;
import com.uimirror.rtp.shop.bean.Shop;
import com.uimirror.rtp.shop.bean.ShopInfo;
import com.uimirror.rtp.shop.bean.ShopMeta;
import com.uimirror.rtp.shop.form.ShopRegisterForm;

public class RegisterShopProvider implements Processor<ShopRegisterForm, Shop> {

  protected static final Logger LOG = LoggerFactory.getLogger(RegisterShopProvider.class);
  
  private @Autowired TransformerService<ShopRegisterForm, ShopInfo> registerShopFormToShopBasicInfoTransformer;
  
  private @Autowired TransformerService<ShopRegisterForm, ShopMeta> registerShopFormToShopOtherInfoTransformer;
  
  private @Autowired RegisterShopBasicInfoStore registerShopBasicInfoMogoDbStore;
  
  private @Autowired RegisterShopOtherInfoStore registerShopOtherInfoMogoDbStore;
  
  @Override
  public Shop invoke(ShopRegisterForm param) throws ApplicationException {
    
    ShopInfo shopBasicInfo = registerShopFormToShopBasicInfoTransformer.transform(param);
    ShopMeta shopOtherInfo = registerShopFormToShopOtherInfoTransformer.transform(param);
    
    shopBasicInfo = registerShopBasicInfoMogoDbStore.store(shopBasicInfo);
    
    shopOtherInfo = registerShopOtherInfoMogoDbStore.store(shopOtherInfo);
    
    Shop registerShopBean = new Shop();
    
    registerShopBean.setShopBasicInfo(shopBasicInfo);
    
    registerShopBean.setShopOtherInfo(shopOtherInfo);
    
    return registerShopBean;
  }
  

}
