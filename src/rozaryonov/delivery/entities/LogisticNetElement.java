package rozaryonov.delivery.entities;

public class LogisticNetElement {
	Locality city;
	Locality neighbor;
	double distance;
	
	
	public LogisticNetElement() {
		super();
	}
	
	
	public LogisticNetElement(Locality city, Locality neighbor, double distance) {
		super();
		this.city = city;
		this.neighbor = neighbor;
		this.distance = distance;
	}


	public Locality getCity() {
		return city;
	}
	public void setCity(Locality city) {
		this.city = city;
	}
	public Locality getNeighbor() {
		return neighbor;
	}
	public void setNeighbor(Locality neighbor) {
		this.neighbor = neighbor;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "LogisticNetElement [city=" + city + ", neighbor=" + neighbor + ", distance=" + distance + "]";
	}
	
}
