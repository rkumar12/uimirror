package com.uimirror.rtp.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;
import com.uimirror.rtp.shop.bean.Shop;
import com.uimirror.rtp.shop.form.ShopRegisterForm;

public class RegisterShopProcessor implements Processor<ShopRegisterForm, String> {

  protected static final Logger LOG = LoggerFactory.getLogger(RegisterShopProcessor.class);

  private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;

  private @Autowired Processor<ShopRegisterForm, Shop> registerShopProvider;

  @Override
  public String invoke(ShopRegisterForm param) throws ApplicationException {

    LOG.debug("[START]- Registering a Shop");

    param.isValid();

    Shop registerShopBean = registerShopProvider.invoke(param);

    LOG.debug("[EXIT]- Registering a Shop");
    return jsonResponseTransFormer.doTransForm(registerShopBean);
  }

}
