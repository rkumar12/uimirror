package com.uimirror.rtp.shop.register;

import org.springframework.util.Assert;

import com.uimirror.core.service.TransformerService;

public class RegisterShopFormToShopBasicInfoTransformer implements TransformerService<RegisterShopForm, Object> {

  @Override
  public Object transform(RegisterShopForm src) {
    Assert.notNull(src, "Source Can't be empty");

    return new ShopOtherInfo(null, src.getCategory(), src.getShopAddress(), src.getShopContactNumber());
  }

}
