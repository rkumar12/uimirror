package com.uimirror.rtp.shop.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.service.TransformerService;

public class RegisterShopProvider implements Processor<RegisterShopForm, RegisterShopBean> {

  protected static final Logger LOG = LoggerFactory.getLogger(RegisterShopProvider.class);
  
  private @Autowired TransformerService<RegisterShopForm, ShopBasicInfo> registerShopFormToShopBasicInfoTransformer;
  
  private @Autowired TransformerService<RegisterShopForm, ShopOtherInfo> registerShopFormToShopOtherInfoTransformer;
  
  private @Autowired RegisterShopBasicInfoStore registerShopBasicInfoMogoDbStore;
  
  private @Autowired RegisterShopOtherInfoStore registerShopOtherInfoMogoDbStore;
  
  @Override
  public RegisterShopBean invoke(RegisterShopForm param) throws ApplicationException {
    
    ShopBasicInfo shopBasicInfo = registerShopFormToShopBasicInfoTransformer.transform(param);
    ShopOtherInfo shopOtherInfo = registerShopFormToShopOtherInfoTransformer.transform(param);
    
    shopBasicInfo = registerShopBasicInfoMogoDbStore.store(shopBasicInfo);
    
    shopOtherInfo = registerShopOtherInfoMogoDbStore.store(shopOtherInfo);
    
    RegisterShopBean registerShopBean = new RegisterShopBean();
    
    registerShopBean.setShopBasicInfo(shopBasicInfo);
    
    registerShopBean.setShopOtherInfo(shopOtherInfo);
    
    return registerShopBean;
  }
  

}
