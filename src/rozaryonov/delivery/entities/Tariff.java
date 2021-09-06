package rozaryonov.delivery.entities;

import java.sql.Timestamp;

public class Tariff {
	private long id;
	private Timestamp creationTimestamp;
	private long logisticConfigId;
	private int truckVelocity;
	private double density;
	private double paperwork;
	private int targetedReceipt;
	private int targetedDelivery;
	private double shippingRate;
	private double insuranceWorth;
	private double insuranceRate;
	
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
	public long getLogisticConfigId() {
		return logisticConfigId;
	}
	public void setLogisticConfigId(long logisticConfigId) {
		this.logisticConfigId = logisticConfigId;
	}
	public int getTruckVelocity() {
		return truckVelocity;
	}
	public void setTruckVelocity(int truckVelocity) {
		this.truckVelocity = truckVelocity;
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public double getPaperwork() {
		return paperwork;
	}
	public void setPaperwork(double paperwork) {
		this.paperwork = paperwork;
	}
	public int getTargetedReceipt() {
		return targetedReceipt;
	}
	public void setTargetedReceipt(int targetedReceipt) {
		this.targetedReceipt = targetedReceipt;
	}
	public int getTargetedDelivery() {
		return targetedDelivery;
	}
	public void setTargetedDelivery(int targetedDelivery) {
		this.targetedDelivery = targetedDelivery;
	}
	public double getShippingRate() {
		return shippingRate;
	}
	public void setShippingRate(double shippingRate) {
		this.shippingRate = shippingRate;
	}
	public double getInsuranceWorth() {
		return insuranceWorth;
	}
	public void setInsuranceWorth(double insuranceWorth) {
		this.insuranceWorth = insuranceWorth;
	}
	public double getInsuranceRate() {
		return insuranceRate;
	}
	public void setInsuranceRate(double insuranceRate) {
		this.insuranceRate = insuranceRate;
	}
	@Override
	public String toString() {
		return "Tariff [id=" + id + ", creationTimestamp=" + creationTimestamp + ", logisticConfigId="
				+ logisticConfigId + ", truckVelocity=" + truckVelocity + ", density=" + density + ", paperwork="
				+ paperwork + ", targetedReceipt=" + targetedReceipt + ", targetedDelivery=" + targetedDelivery
				+ ", shippingRate=" + shippingRate + ", insuranceWorth=" + insuranceWorth + ", insuranceRate="
				+ insuranceRate + "]";
	}
	
	

}
