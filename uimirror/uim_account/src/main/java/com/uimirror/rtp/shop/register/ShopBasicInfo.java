package com.uimirror.rtp.shop.register;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.uimirror.account.user.UserAccountDBFields;
import com.uimirror.account.user.bean.UserAccountLogs;
import com.uimirror.core.Location;
import com.uimirror.core.mongo.feature.BeanBasedDocument;
import com.uimirror.core.service.BeanValidatorService;
import com.uimirror.core.user.UserDBFields;

public class ShopBasicInfo extends BeanBasedDocument<ShopBasicInfo> implements BeanValidatorService {

  private static final long serialVersionUID = -6504474875834652281L;
  private String shopName;
  private Location location;
  private int numberOfPromos;

  // Don't Use this until it has specific requirement
  public ShopBasicInfo() {
    super();
  }

  public ShopBasicInfo(Map<String, Object> details) {
    super(details);
  }

  public ShopBasicInfo(String id, String shopName, Location location, int numberOfPromos) {
    super(id);
    this.shopName = shopName;
    this.location = location;
    this.numberOfPromos = numberOfPromos;
  }

  public String getShopId() {
    return getId();
  }

  @Override
  public boolean isValid() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public ShopBasicInfo initFromMap(Map<String, Object> src) {
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
    state.put(RegisterShopDBFields.SHOP_NAME, getShopName());
    state.put(RegisterShopDBFields.LATITUDE, location.getLatitude());
    state.put(RegisterShopDBFields.LONGITUDE, location.getLongitude());
    state.put(RegisterShopDBFields.NUMBER_OF_PROMOS, getNumberOfPromos());
    return state;
  }

  /**
   * converts a map that comes from DB into ShopBasicInfo object.
   * 
   * @param raw
   * @return {@link UserAccountLogs}
   */
  @SuppressWarnings("unchecked")
  private ShopBasicInfo init(Map<String, Object> raw) {

    String id = (String) raw.get(RegisterShopDBFields.ID);
    String shopName = (String) raw.get(RegisterShopDBFields.SHOP_NAME);
    double latitude = (double) raw.get(RegisterShopDBFields.LATITUDE);
    double longitude = (double) raw.get(RegisterShopDBFields.LONGITUDE);
    return new ShopBasicInfo(id, shopName, new Location(null, longitude, latitude), 0);
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public int getNumberOfPromos() {
    return numberOfPromos;
  }

  public void setNumberOfPromos(int numberOfPromos) {
    this.numberOfPromos = numberOfPromos;
  }

}
