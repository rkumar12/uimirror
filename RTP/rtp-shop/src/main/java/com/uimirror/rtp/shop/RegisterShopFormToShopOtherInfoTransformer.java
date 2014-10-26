package com.uimirror.rtp.shop;

import org.springframework.util.Assert;

import com.uimirror.core.Location;
import com.uimirror.core.service.TransformerService;
import com.uimirror.rtp.shop.bean.ShopInfo;
import com.uimirror.rtp.shop.form.ShopRegisterForm;

public class RegisterShopFormToShopOtherInfoTransformer implements TransformerService<ShopRegisterForm, Object>{

  @Override
  public Object transform(ShopRegisterForm src) {
    Assert.notNull(src, "Source Can't be empty");
    
    return new ShopInfo(null,src.getShopName(),new Location("",Double.valueOf(src.getLatitude()), Double.valueOf(src.getLongitude())),0);
  }

}
