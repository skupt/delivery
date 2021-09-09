package rozaryonov.delivery.entities;

public class SettlementType {
	private long id;
	private String name;
	private int vector;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVector() {
		return vector;
	}
	public void setVector(int vector) {
		this.vector = vector;
	}
	@Override
	public String toString() {
		return "SettlmentType [id=" + id + ", name=" + name + ", vector=" + vector + "]";
	}
	
	
	
}
