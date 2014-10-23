package com.uimirror.rtp.shop.register;

import org.springframework.util.Assert;

import com.uimirror.core.Location;
import com.uimirror.core.service.TransformerService;

public class RegisterShopFormToShopOtherInfoTransformer implements TransformerService<RegisterShopForm, Object>{

  @Override
  public Object transform(RegisterShopForm src) {
    Assert.notNull(src, "Source Can't be empty");
    
    return new ShopBasicInfo(null,src.getShopName(),new Location("",Double.valueOf(src.getLatitude()), Double.valueOf(src.getLongitude())),0);
  }

}
