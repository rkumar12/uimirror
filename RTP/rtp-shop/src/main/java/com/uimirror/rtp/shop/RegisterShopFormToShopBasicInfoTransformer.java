package com.uimirror.rtp.shop;

import org.springframework.util.Assert;

import com.uimirror.core.service.TransformerService;
import com.uimirror.rtp.shop.bean.ShopMeta;
import com.uimirror.rtp.shop.form.ShopRegisterForm;

public class RegisterShopFormToShopBasicInfoTransformer implements TransformerService<ShopRegisterForm, Object> {

  @Override
  public Object transform(ShopRegisterForm src) {
    Assert.notNull(src, "Source Can't be empty");

    //return new ShopMeta(null, src.getCategory(), src.getShopAddress(), src.getShopContactNumber());
    return null;
  }

}
