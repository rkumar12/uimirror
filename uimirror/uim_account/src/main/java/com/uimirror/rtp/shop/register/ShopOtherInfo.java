package com.uimirror.rtp.shop.register;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.account.user.bean.UserAccountLogs;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;

public class ShopOtherInfo extends BeanBasedDocument<ShopOtherInfo> implements BeanValidatorService {
  private static final long serialVersionUID = -6504474875834652281L;

  private String category;

  private String shopAdress;

  private String shopContactNumber;

  // Don't Use this until it has specific requirement
  public ShopOtherInfo() {
    super();
  }

  public ShopOtherInfo(Map<String, Object> details) {
    super(details);
  }

  public ShopOtherInfo(String id, String category, String shopAddress, String shopContactNumber) {
    super(id);
    this.category = category;
    this.shopAdress = shopAddress;
    this.shopContactNumber = shopContactNumber;
  }

  @Override
  public boolean isValid() {
    boolean valid = Boolean.TRUE;
    if (!StringUtils.hasText(getId()))
      valid = Boolean.FALSE;
    if (!StringUtils.hasText(getCategory()))
      valid = Boolean.FALSE;
    if (!StringUtils.hasText(getShopAdress()))
      valid = Boolean.FALSE;
    if (!StringUtils.hasText(getShopContactNumber()))
      valid = Boolean.FALSE;
    return valid;
  }

  @Override
  public ShopOtherInfo initFromMap(Map<String, Object> src) {
    // Validate the source shouldn't be empty
    validateSource(src);
    // Initialize the state
    return init(src);
  }

  @Override
  public Map<String, Object> toMap() {
    // First check if it represents a valid state then can be serialized
    if (!isValid())
      throw new IllegalStateException("Can't be serailized the state of the object");
    return serailize();
  }

  /**
   * Serialize the current state that needs to be persisted to the system.
   * 
   * @return
   */
  public Map<String, Object> serailize() {
    Map<String, Object> state = new LinkedHashMap<String, Object>(16);
    state.put(RegisterShopDBFields.ID, getShopId());
    state.put(RegisterShopDBFields.CATEGORY, getCategory());
    state.put(RegisterShopDBFields.SHOP_ADDRESS, getShopAdress());
    state.put(RegisterShopDBFields.SHOP_CONTACT_NUMBER, getShopContactNumber());
    return state;
  }

  /**
   * converts a map that comes from DB into ShopBasicInfo object.
   * 
   * @param raw
   * @return {@link UserAccountLogs}
   */
  @SuppressWarnings("unchecked")
  private ShopOtherInfo init(Map<String, Object> raw) {

    String id = (String) raw.get(RegisterShopDBFields.ID);
    String category = (String) raw.get(RegisterShopDBFields.CATEGORY);
    String shopAddress = (String) raw.get(RegisterShopDBFields.SHOP_ADDRESS);
    String shopContactNumber = (String) raw.get(RegisterShopDBFields.SHOP_CONTACT_NUMBER);
    return new ShopOtherInfo(id, category, shopAddress, shopContactNumber);
  }

  public String getShopId() {
    return getId();
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getShopAdress() {
    return shopAdress;
  }

  public void setShopAdress(String shopAdress) {
    this.shopAdress = shopAdress;
  }

  public String getShopContactNumber() {
    return shopContactNumber;
  }

  public void setShopContactNumber(String shopContactNumber) {
    this.shopContactNumber = shopContactNumber;
  }

}
