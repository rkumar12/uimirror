package com.uimirror.rtp.shop.register;

import java.util.Map;

import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

public class ShopOtherInfo extends BeanBasedDocument<ShopOtherInfo> implements BeanValidatorService {
  private static final long serialVersionUID = -6504474875834652281L;
  
  private String category;
  
  private String shopAdress;
  
  private String shopContactNumber;

  @Override
  public boolean isValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ShopOtherInfo initFromMap(Map<String, Object> src) {
    // TODO Auto-generated method stub
    return null;
  }

}
