package com.uimirror.rtp.shop.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.uimirror.core.Processor;
import com.uimirror.core.rest.extra.ApplicationException;
import com.uimirror.core.rest.extra.ResponseTransFormer;

public class RegisterShopProcessor implements Processor<RegisterShopForm, String> {

  protected static final Logger LOG = LoggerFactory.getLogger(RegisterShopProcessor.class);

  private @Autowired ResponseTransFormer<String> jsonResponseTransFormer;

  private @Autowired Processor<RegisterShopForm, RegisterShopBean> registerShopProvider;

  @Override
  public String invoke(RegisterShopForm param) throws ApplicationException {

    LOG.debug("[START]- Registering a Shop");

    param.isValid();

    RegisterShopBean registerShopBean = registerShopProvider.invoke(param);

    LOG.debug("[EXIT]- Registering a Shop");
    return jsonResponseTransFormer.doTransForm(registerShopBean);
  }

}
