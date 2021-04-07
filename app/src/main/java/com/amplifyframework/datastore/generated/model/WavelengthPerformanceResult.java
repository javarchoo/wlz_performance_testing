package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the WavelengthPerformanceResult type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "WavelengthPerformanceResults", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class WavelengthPerformanceResult implements Model {
/*
  public static final QueryField ID = field("WavelengthPerformanceResult", "id");
  public static final QueryField LOCATION = field("WavelengthPerformanceResult", "location");
  public static final QueryField LOCATION_NM = field("WavelengthPerformanceResult", "location_nm");
  public static final QueryField SELECTED_NETWORK = field("WavelengthPerformanceResult", "selected_network");
  public static final QueryField SELECTED_INOUT = field("WavelengthPerformanceResult", "selected_inout");
  public static final QueryField RADIO_NETWORK = field("WavelengthPerformanceResult", "radio_network");
  public static final QueryField RADIO_STRENGTH = field("WavelengthPerformanceResult", "radio_strength");
  public static final QueryField RADIO_STATUS = field("WavelengthPerformanceResult", "radio_status");
  public static final QueryField SERVER = field("WavelengthPerformanceResult", "server");
  public static final QueryField SERVER_NM = field("WavelengthPerformanceResult", "server_nm");
  public static final QueryField LATENCY_AVG = field("WavelengthPerformanceResult", "latency_avg");
  public static final QueryField LATENCY_MAX = field("WavelengthPerformanceResult", "latency_max");
  public static final QueryField LATENCY_MIN = field("WavelengthPerformanceResult", "latency_min");
  public static final QueryField LATENCY_MDEV = field("WavelengthPerformanceResult", "latency_mdev");
  public static final QueryField TPUT_TCP_UP = field("WavelengthPerformanceResult", "tput_tcp_up");
  public static final QueryField TPUT_TCP_DOWN = field("WavelengthPerformanceResult", "tput_tcp_down");
  public static final QueryField TPUT_UDP_UP = field("WavelengthPerformanceResult", "tput_udp_up");
  public static final QueryField TPUT_UDP_DOWN = field("WavelengthPerformanceResult", "tput_udp_down");
  public static final QueryField SELECTED_BOOSTZONE = field("WavelengthPerformanceResult", "selected_boostzone");
 */
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="String") String location_nm;
  private final @ModelField(targetType="String") String selected_network;
  private final @ModelField(targetType="String") String selected_inout;
  private final @ModelField(targetType="String") String radio_network;
  private final @ModelField(targetType="String") String radio_strength;
  private final @ModelField(targetType="String") String radio_status;
  private final @ModelField(targetType="String") String server;
  private final @ModelField(targetType="String") String server_nm;
  private final @ModelField(targetType="String") String latency_avg;
  private final @ModelField(targetType="String") String latency_max;
  private final @ModelField(targetType="String") String latency_min;
  private final @ModelField(targetType="String") String latency_mdev;
  private final @ModelField(targetType="String") String tput_tcp_up;
  private final @ModelField(targetType="String") String tput_tcp_down;
  private final @ModelField(targetType="String") String tput_udp_up;
  private final @ModelField(targetType="String") String tput_udp_down;
  private final @ModelField(targetType="Boolean") Boolean selected_boostzone;
  public String getId() {
      return id;
  }
  
  public String getLocation() {
      return location;
  }
  
  public String getLocationNm() {
      return location_nm;
  }
  
  public String getSelectedNetwork() {
      return selected_network;
  }
  
  public String getSelectedInout() {
      return selected_inout;
  }
  
  public String getRadioNetwork() {
      return radio_network;
  }
  
  public String getRadioStrength() {
      return radio_strength;
  }
  
  public String getRadioStatus() {
      return radio_status;
  }
  
  public String getServer() {
      return server;
  }
  
  public String getServerNm() {
      return server_nm;
  }
  
  public String getLatencyAvg() {
      return latency_avg;
  }
  
  public String getLatencyMax() {
      return latency_max;
  }
  
  public String getLatencyMin() {
      return latency_min;
  }
  
  public String getLatencyMdev() {
      return latency_mdev;
  }
  
  public String getTputTcpUp() {
      return tput_tcp_up;
  }
  
  public String getTputTcpDown() {
      return tput_tcp_down;
  }
  
  public String getTputUdpUp() {
      return tput_udp_up;
  }
  
  public String getTputUdpDown() {
      return tput_udp_down;
  }
  
  public Boolean getSelectedBoostzone() {
      return selected_boostzone;
  }
  
  private WavelengthPerformanceResult(String id, String location, String location_nm, String selected_network, String selected_inout, String radio_network, String radio_strength, String radio_status, String server, String server_nm, String latency_avg, String latency_max, String latency_min, String latency_mdev, String tput_tcp_up, String tput_tcp_down, String tput_udp_up, String tput_udp_down, Boolean selected_boostzone) {
    this.id = id;
    this.location = location;
    this.location_nm = location_nm;
    this.selected_network = selected_network;
    this.selected_inout = selected_inout;
    this.radio_network = radio_network;
    this.radio_strength = radio_strength;
    this.radio_status = radio_status;
    this.server = server;
    this.server_nm = server_nm;
    this.latency_avg = latency_avg;
    this.latency_max = latency_max;
    this.latency_min = latency_min;
    this.latency_mdev = latency_mdev;
    this.tput_tcp_up = tput_tcp_up;
    this.tput_tcp_down = tput_tcp_down;
    this.tput_udp_up = tput_udp_up;
    this.tput_udp_down = tput_udp_down;
    this.selected_boostzone = selected_boostzone;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      WavelengthPerformanceResult wavelengthPerformanceResult = (WavelengthPerformanceResult) obj;
      return ObjectsCompat.equals(getId(), wavelengthPerformanceResult.getId()) &&
              ObjectsCompat.equals(getLocation(), wavelengthPerformanceResult.getLocation()) &&
              ObjectsCompat.equals(getLocationNm(), wavelengthPerformanceResult.getLocationNm()) &&
              ObjectsCompat.equals(getSelectedNetwork(), wavelengthPerformanceResult.getSelectedNetwork()) &&
              ObjectsCompat.equals(getSelectedInout(), wavelengthPerformanceResult.getSelectedInout()) &&
              ObjectsCompat.equals(getRadioNetwork(), wavelengthPerformanceResult.getRadioNetwork()) &&
              ObjectsCompat.equals(getRadioStrength(), wavelengthPerformanceResult.getRadioStrength()) &&
              ObjectsCompat.equals(getRadioStatus(), wavelengthPerformanceResult.getRadioStatus()) &&
              ObjectsCompat.equals(getServer(), wavelengthPerformanceResult.getServer()) &&
              ObjectsCompat.equals(getServerNm(), wavelengthPerformanceResult.getServerNm()) &&
              ObjectsCompat.equals(getLatencyAvg(), wavelengthPerformanceResult.getLatencyAvg()) &&
              ObjectsCompat.equals(getLatencyMax(), wavelengthPerformanceResult.getLatencyMax()) &&
              ObjectsCompat.equals(getLatencyMin(), wavelengthPerformanceResult.getLatencyMin()) &&
              ObjectsCompat.equals(getLatencyMdev(), wavelengthPerformanceResult.getLatencyMdev()) &&
              ObjectsCompat.equals(getTputTcpUp(), wavelengthPerformanceResult.getTputTcpUp()) &&
              ObjectsCompat.equals(getTputTcpDown(), wavelengthPerformanceResult.getTputTcpDown()) &&
              ObjectsCompat.equals(getTputUdpUp(), wavelengthPerformanceResult.getTputUdpUp()) &&
              ObjectsCompat.equals(getTputUdpDown(), wavelengthPerformanceResult.getTputUdpDown()) &&
              ObjectsCompat.equals(getSelectedBoostzone(), wavelengthPerformanceResult.getSelectedBoostzone());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getLocation())
      .append(getLocationNm())
      .append(getSelectedNetwork())
      .append(getSelectedInout())
      .append(getRadioNetwork())
      .append(getRadioStrength())
      .append(getRadioStatus())
      .append(getServer())
      .append(getServerNm())
      .append(getLatencyAvg())
      .append(getLatencyMax())
      .append(getLatencyMin())
      .append(getLatencyMdev())
      .append(getTputTcpUp())
      .append(getTputTcpDown())
      .append(getTputUdpUp())
      .append(getTputUdpDown())
      .append(getSelectedBoostzone())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("WavelengthPerformanceResult {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("location_nm=" + String.valueOf(getLocationNm()) + ", ")
      .append("selected_network=" + String.valueOf(getSelectedNetwork()) + ", ")
      .append("selected_inout=" + String.valueOf(getSelectedInout()) + ", ")
      .append("radio_network=" + String.valueOf(getRadioNetwork()) + ", ")
      .append("radio_strength=" + String.valueOf(getRadioStrength()) + ", ")
      .append("radio_status=" + String.valueOf(getRadioStatus()) + ", ")
      .append("server=" + String.valueOf(getServer()) + ", ")
      .append("server_nm=" + String.valueOf(getServerNm()) + ", ")
      .append("latency_avg=" + String.valueOf(getLatencyAvg()) + ", ")
      .append("latency_max=" + String.valueOf(getLatencyMax()) + ", ")
      .append("latency_min=" + String.valueOf(getLatencyMin()) + ", ")
      .append("latency_mdev=" + String.valueOf(getLatencyMdev()) + ", ")
      .append("tput_tcp_up=" + String.valueOf(getTputTcpUp()) + ", ")
      .append("tput_tcp_down=" + String.valueOf(getTputTcpDown()) + ", ")
      .append("tput_udp_up=" + String.valueOf(getTputUdpUp()) + ", ")
      .append("tput_udp_down=" + String.valueOf(getTputUdpDown()) + ", ")
      .append("selected_boostzone=" + String.valueOf(getSelectedBoostzone()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static WavelengthPerformanceResult justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new WavelengthPerformanceResult(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      location,
      location_nm,
      selected_network,
      selected_inout,
      radio_network,
      radio_strength,
      radio_status,
      server,
      server_nm,
      latency_avg,
      latency_max,
      latency_min,
      latency_mdev,
      tput_tcp_up,
      tput_tcp_down,
      tput_udp_up,
      tput_udp_down,
      selected_boostzone);
  }
  public interface BuildStep {
    WavelengthPerformanceResult build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep location(String location);
    BuildStep locationNm(String locationNm);
    BuildStep selectedNetwork(String selectedNetwork);
    BuildStep selectedInout(String selectedInout);
    BuildStep radioNetwork(String radioNetwork);
    BuildStep radioStrength(String radioStrength);
    BuildStep radioStatus(String radioStatus);
    BuildStep server(String server);
    BuildStep serverNm(String serverNm);
    BuildStep latencyAvg(String latencyAvg);
    BuildStep latencyMax(String latencyMax);
    BuildStep latencyMin(String latencyMin);
    BuildStep latencyMdev(String latencyMdev);
    BuildStep tputTcpUp(String tputTcpUp);
    BuildStep tputTcpDown(String tputTcpDown);
    BuildStep tputUdpUp(String tputUdpUp);
    BuildStep tputUdpDown(String tputUdpDown);
    BuildStep selectedBoostzone(Boolean selectedBoostzone);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String location;
    private String location_nm;
    private String selected_network;
    private String selected_inout;
    private String radio_network;
    private String radio_strength;
    private String radio_status;
    private String server;
    private String server_nm;
    private String latency_avg;
    private String latency_max;
    private String latency_min;
    private String latency_mdev;
    private String tput_tcp_up;
    private String tput_tcp_down;
    private String tput_udp_up;
    private String tput_udp_down;
    private Boolean selected_boostzone;
    @Override
     public WavelengthPerformanceResult build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new WavelengthPerformanceResult(
          id,
          location,
          location_nm,
          selected_network,
          selected_inout,
          radio_network,
          radio_strength,
          radio_status,
          server,
          server_nm,
          latency_avg,
          latency_max,
          latency_min,
          latency_mdev,
          tput_tcp_up,
          tput_tcp_down,
          tput_udp_up,
          tput_udp_down,
          selected_boostzone);
    }
    
    @Override
     public BuildStep location(String location) {
        this.location = location;
        return this;
    }
    
    @Override
     public BuildStep locationNm(String locationNm) {
        this.location_nm = locationNm;
        return this;
    }
    
    @Override
     public BuildStep selectedNetwork(String selectedNetwork) {
        this.selected_network = selectedNetwork;
        return this;
    }
    
    @Override
     public BuildStep selectedInout(String selectedInout) {
        this.selected_inout = selectedInout;
        return this;
    }
    
    @Override
     public BuildStep radioNetwork(String radioNetwork) {
        this.radio_network = radioNetwork;
        return this;
    }
    
    @Override
     public BuildStep radioStrength(String radioStrength) {
        this.radio_strength = radioStrength;
        return this;
    }
    
    @Override
     public BuildStep radioStatus(String radioStatus) {
        this.radio_status = radioStatus;
        return this;
    }
    
    @Override
     public BuildStep server(String server) {
        this.server = server;
        return this;
    }
    
    @Override
     public BuildStep serverNm(String serverNm) {
        this.server_nm = serverNm;
        return this;
    }
    
    @Override
     public BuildStep latencyAvg(String latencyAvg) {
        this.latency_avg = latencyAvg;
        return this;
    }
    
    @Override
     public BuildStep latencyMax(String latencyMax) {
        this.latency_max = latencyMax;
        return this;
    }
    
    @Override
     public BuildStep latencyMin(String latencyMin) {
        this.latency_min = latencyMin;
        return this;
    }
    
    @Override
     public BuildStep latencyMdev(String latencyMdev) {
        this.latency_mdev = latencyMdev;
        return this;
    }
    
    @Override
     public BuildStep tputTcpUp(String tputTcpUp) {
        this.tput_tcp_up = tputTcpUp;
        return this;
    }
    
    @Override
     public BuildStep tputTcpDown(String tputTcpDown) {
        this.tput_tcp_down = tputTcpDown;
        return this;
    }
    
    @Override
     public BuildStep tputUdpUp(String tputUdpUp) {
        this.tput_udp_up = tputUdpUp;
        return this;
    }
    
    @Override
     public BuildStep tputUdpDown(String tputUdpDown) {
        this.tput_udp_down = tputUdpDown;
        return this;
    }
    
    @Override
     public BuildStep selectedBoostzone(Boolean selectedBoostzone) {
        this.selected_boostzone = selectedBoostzone;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String location, String locationNm, String selectedNetwork, String selectedInout, String radioNetwork, String radioStrength, String radioStatus, String server, String serverNm, String latencyAvg, String latencyMax, String latencyMin, String latencyMdev, String tputTcpUp, String tputTcpDown, String tputUdpUp, String tputUdpDown, Boolean selectedBoostzone) {
      super.id(id);
      super.location(location)
        .locationNm(locationNm)
        .selectedNetwork(selectedNetwork)
        .selectedInout(selectedInout)
        .radioNetwork(radioNetwork)
        .radioStrength(radioStrength)
        .radioStatus(radioStatus)
        .server(server)
        .serverNm(serverNm)
        .latencyAvg(latencyAvg)
        .latencyMax(latencyMax)
        .latencyMin(latencyMin)
        .latencyMdev(latencyMdev)
        .tputTcpUp(tputTcpUp)
        .tputTcpDown(tputTcpDown)
        .tputUdpUp(tputUdpUp)
        .tputUdpDown(tputUdpDown)
        .selectedBoostzone(selectedBoostzone);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
    
    @Override
     public CopyOfBuilder locationNm(String locationNm) {
      return (CopyOfBuilder) super.locationNm(locationNm);
    }
    
    @Override
     public CopyOfBuilder selectedNetwork(String selectedNetwork) {
      return (CopyOfBuilder) super.selectedNetwork(selectedNetwork);
    }
    
    @Override
     public CopyOfBuilder selectedInout(String selectedInout) {
      return (CopyOfBuilder) super.selectedInout(selectedInout);
    }
    
    @Override
     public CopyOfBuilder radioNetwork(String radioNetwork) {
      return (CopyOfBuilder) super.radioNetwork(radioNetwork);
    }
    
    @Override
     public CopyOfBuilder radioStrength(String radioStrength) {
      return (CopyOfBuilder) super.radioStrength(radioStrength);
    }
    
    @Override
     public CopyOfBuilder radioStatus(String radioStatus) {
      return (CopyOfBuilder) super.radioStatus(radioStatus);
    }
    
    @Override
     public CopyOfBuilder server(String server) {
      return (CopyOfBuilder) super.server(server);
    }
    
    @Override
     public CopyOfBuilder serverNm(String serverNm) {
      return (CopyOfBuilder) super.serverNm(serverNm);
    }
    
    @Override
     public CopyOfBuilder latencyAvg(String latencyAvg) {
      return (CopyOfBuilder) super.latencyAvg(latencyAvg);
    }
    
    @Override
     public CopyOfBuilder latencyMax(String latencyMax) {
      return (CopyOfBuilder) super.latencyMax(latencyMax);
    }
    
    @Override
     public CopyOfBuilder latencyMin(String latencyMin) {
      return (CopyOfBuilder) super.latencyMin(latencyMin);
    }
    
    @Override
     public CopyOfBuilder latencyMdev(String latencyMdev) {
      return (CopyOfBuilder) super.latencyMdev(latencyMdev);
    }
    
    @Override
     public CopyOfBuilder tputTcpUp(String tputTcpUp) {
      return (CopyOfBuilder) super.tputTcpUp(tputTcpUp);
    }
    
    @Override
     public CopyOfBuilder tputTcpDown(String tputTcpDown) {
      return (CopyOfBuilder) super.tputTcpDown(tputTcpDown);
    }
    
    @Override
     public CopyOfBuilder tputUdpUp(String tputUdpUp) {
      return (CopyOfBuilder) super.tputUdpUp(tputUdpUp);
    }
    
    @Override
     public CopyOfBuilder tputUdpDown(String tputUdpDown) {
      return (CopyOfBuilder) super.tputUdpDown(tputUdpDown);
    }
    
    @Override
     public CopyOfBuilder selectedBoostzone(Boolean selectedBoostzone) {
      return (CopyOfBuilder) super.selectedBoostzone(selectedBoostzone);
    }
  }
  
}
