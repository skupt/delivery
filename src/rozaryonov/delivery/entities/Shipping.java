package rozaryonov.delivery.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Shipping {
	private long id;
	private Timestamp creationTimestamp;
	private long personId;
	private Timestamp downloadDatetime;
	private long loadLocalityId;
	private String shipper;
	private String downloadAddress;
	private String consignee;
	private long unloadLocalityId;
	private String unloadAddress;
	private Timestamp unloadingDatetime;
	private double distance;
	private double weight;
	private double volume;
	private BigDecimal fare;
	private long shippingStatusId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public Timestamp getDownloadDatetime() {
		return downloadDatetime;
	}
	public void setDownloadDatetime(Timestamp downloadDatetime) {
		this.downloadDatetime = downloadDatetime;
	}
	public long getLoadLocalityId() {
		return loadLocalityId;
	}
	public void setLoadLocalityId(long loadLocalityId) {
		this.loadLocalityId = loadLocalityId;
	}
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getDownloadAddress() {
		return downloadAddress;
	}
	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public long getUnloadLocalityId() {
		return unloadLocalityId;
	}
	public void setUnloadLocalityId(long unloadLocalityId) {
		this.unloadLocalityId = unloadLocalityId;
	}
	public String getUnloadAddress() {
		return unloadAddress;
	}
	public void setUnloadAddress(String unloadAddress) {
		this.unloadAddress = unloadAddress;
	}
	public Timestamp getUnloadingDatetime() {
		return unloadingDatetime;
	}
	public void setUnloadingDatetime(Timestamp unloadingDatetime) {
		this.unloadingDatetime = unloadingDatetime;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public BigDecimal getFare() {
		return fare;
	}
	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}
	public long getShippingStatusId() {
		return shippingStatusId;
	}
	public void setShippingStatusId(long shippingStatusId) {
		this.shippingStatusId = shippingStatusId;
	}
	@Override
	public String toString() {
		return "Shipping [id=" + id + ", creationTimestamp=" + creationTimestamp + ", personId=" + personId
				+ ", downloadDatetime=" + downloadDatetime + ", loadLocalityId=" + loadLocalityId + ", shipper="
				+ shipper + ", downloadAddress=" + downloadAddress + ", cosignee=" + consignee + ", unloadLocalityId="
				+ unloadLocalityId + ", unloadAddress=" + unloadAddress + ", unloadingDatetime=" + unloadingDatetime
				+ ", distance=" + distance + ", weight=" + weight + ", volume=" + volume + ", fare=" + fare
				+ ", shippingStatusId=" + shippingStatusId + "]";
	}

	public static class Builder {
		private Shipping s;
		
		public Builder () {
			s = new Shipping();
		}
		public Builder withCreationTimestamp(Timestamp val) {
			s.creationTimestamp = val;
			return this;
		}
		public Builder withPersonId(long val) {
			s.personId = val;
			return this;
		}
		public Builder withDownLoadDateTime(Timestamp val) {
			s.downloadDatetime = val;
			return this;
		}
		public Builder withLoadLocalityId (long val) {
			s.loadLocalityId = val;
			return this;
		}
		public Builder withShipper(String val) {
			s.shipper = val;
			return this;
		}
		public Builder withdownloadAddress(String val) {
			s.downloadAddress = val;
			return this;
		}
		public Builder withConsignee(String val) {
			s.consignee = val;
			return this;
		}
		public Builder withUnloadLocalityId(long val) {
			s.unloadLocalityId = val;
			return this;
		}
		public Builder withUnloadAddress(String val) {
			s.unloadAddress = val;
			return this;
		}
		public Builder withUnloadingDatetime(Timestamp val) {
			s.unloadingDatetime = val;
			return this;
		}
		public Builder withDistance(double val) {
			s.distance = val;
			return this;
		}
		public Builder withWeight(double val) {
			s.weight = val;
			return this;
		}
		public Builder withVolume(double val) {
			s.volume = val;
			return this;
		}
		public Builder withFare(BigDecimal val) {
			s.fare = val;
			return this;
		}
		public Builder withShippingStatusId(long val) {
			s.shippingStatusId = val;
			return this;
		}
		public Shipping build() {
			return s;
		}

		
				
	}
}
