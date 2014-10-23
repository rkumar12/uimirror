package com.uimirror.rtp.shop.register;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;

import org.springframework.util.StringUtils;

import com.uimirror.core.Constants;
import com.uimirror.core.rest.extra.IllegalArgumentException;
import com.uimirror.core.service.BeanValidatorService;

public class RegisterShopForm implements BeanValidatorService {

  private static final long serialVersionUID = -1215523730014366150L;

  @Override
  public boolean isValid() {
    validate();
    return Boolean.TRUE;
  }

  @FormParam(RegisterShopConstants.CATEGORY)
  private String category;

  @FormParam(RegisterShopConstants.SHOP_NAME)
  private String shopName;

  @FormParam(RegisterShopConstants.LATITUDE)
  private String latitude;

  @FormParam(RegisterShopConstants.LONGITUDE)
  private String longitude;

  @FormParam(RegisterShopConstants.SHOP_ADDRESS)
  private String shopAddress;

  @FormParam(RegisterShopConstants.SHOP_CONTACT_NUMBER)
  private String shopContactNumber;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getShopAddress() {
    return shopAddress;
  }

  public void setShopAddress(String shopAddress) {
    this.shopAddress = shopAddress;
  }

  public String getShopContactNumber() {
    return shopContactNumber;
  }

  public void setShopContactNumber(String shopContactNumber) {
    this.shopContactNumber = shopContactNumber;
  }

  private void validate() {
    Set<String> fields = new HashSet<String>();
    Map<String, Object> errors = new LinkedHashMap<String, Object>(7);
    if (!StringUtils.hasText(getCategory()))
      fields.add(RegisterShopConstants.CATEGORY);
    if (!StringUtils.hasText(getLatitude()))
      fields.add(RegisterShopConstants.LATITUDE);   
    if (!StringUtils.hasText(getLongitude()))
      fields.add(RegisterShopConstants.LONGITUDE);   
    if (!StringUtils.hasText(getShopAddress()))
      fields.add(RegisterShopConstants.SHOP_ADDRESS);
    if (!StringUtils.hasText(getShopName()))
      fields.add(RegisterShopConstants.SHOP_NAME);
    if (!StringUtils.hasText(getShopContactNumber()))
      fields.add(RegisterShopConstants.SHOP_CONTACT_NUMBER);    
    if (fields.size() > 0) {
      errors.put(Constants.FIELDS, fields);
      errors.put(Constants.MESSAGE, "Invalid Input");
      informIllegalArgument(errors);
    }
  }
  
  /**
   * Throws the exception map object
   * @param msg
   */
  private void informIllegalArgument(Map<String, Object> msg){
    throw new IllegalArgumentException(msg);
  }

}
