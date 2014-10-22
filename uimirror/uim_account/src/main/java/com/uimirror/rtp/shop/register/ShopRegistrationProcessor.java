package com.uimirror.rtp.shop.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;

public class ShopRegistrationProcessor implements Processor<RegisterShopForm, Object> {

  protected static final Logger LOG = LoggerFactory.getLogger(ShopRegistrationProcessor.class);

  @Override
  public Object invoke(RegisterShopForm param) throws ApplicationException {

    LOG.debug("[START]- Registering a Shop");
    
    return null;
  }

}
