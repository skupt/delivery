package rozaryonov.delivery.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Settlements {
	private long id;
	private LocalDateTime creationDatetime;
	private Person person;
	private SettlementType settlementType;
	private BigDecimal amount;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getCreationDatetime() {
		return creationDatetime;
	}
	public void setCreationDatetime(LocalDateTime creationDatetime) {
		this.creationDatetime = creationDatetime;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public SettlementType getSettlementType() {
		return settlementType;
	}
	public void setSettlementType(SettlementType settlementType) {
		this.settlementType = settlementType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Settlements [id=" + id + ", creationDatetime=" + creationDatetime + ", person=" + person
				+ ", settlementType=" + settlementType + ", amount=" + amount + "]";
	}
	
	
}
