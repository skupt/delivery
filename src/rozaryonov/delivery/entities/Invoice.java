package rozaryonov.delivery.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

public class Invoice {
	private long id;
	private Person person;
	private Timestamp creationDateTime;
	private BigDecimal sum;
	private InvoiceStatus invoiceStatus;
	private Set<Shipping> shippings;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Timestamp getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(Timestamp creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public Set<Shipping> getShippings() {
		return shippings;
	}
	public void setShippings(Set<Shipping> shippings) {
		this.shippings = shippings;
	}
	@Override
	public String toString() {
		return "Invoice [id=" + id + ", person=" + person + ", creationDateTime=" + creationDateTime + ", sum=" + sum
				+ ", invoiceStatus=" + invoiceStatus + ", shippings=" + shippings + "]";
	}
	
	
	
}
