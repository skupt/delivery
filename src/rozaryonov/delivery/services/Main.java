package rozaryonov.delivery.services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import rozaryonov.delivery.dao.ConnectionFactory;
import rozaryonov.delivery.dao.ConnectionWrapper;
import rozaryonov.delivery.dao.impl.InvoiceHasShippingDao;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.repository.impl.InvoiceRepo;
import rozaryonov.delivery.repository.impl.SettlementsRepo;
import rozaryonov.delivery.repository.impl.ShippingRepo;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		Connection cn = ConnectionFactory.getConnection();
		InvoiceRepo repo = new InvoiceRepo(cn);
//		 List<Invoice> il = repo.findFilterSort(
//				 Timestamp.valueOf("2000-06-06 10:00:00"), 
//				 Timestamp.valueOf("2070-06-06 10:00:00"), 
//				 (e)-> true, 
//				 Comparator.comparing((Invoice s) -> s.getId()));
//		 for (Invoice in : il) System.out.println(InvStr(in));
		 Page<Invoice, InvoiceRepo> page = new Page(repo, Comparator.comparing((Invoice s) -> s.getCreationDateTime().toLocalDateTime()));
		 //page.setComparator(Comparator.comparing((Settlements s)-> s.getPerson().getName()));
		 page.setRowsOnPage(10);
		 //Page<Shipping, ShippingRepo> page = new Page(repo, after, before, rowsOnPage, predicate, comparator);
		 page.init();
		 cn.close();
		 for (Invoice s : page.nextPage()) {System.out.println(page.getCurPageNum() + ".\t" + InvStr(s));}
		 System.out.println("*********");
		 for (Invoice s : page.prevPage()) {System.out.println(page.getCurPageNum() + ".\t" + InvStr(s));}
		 System.out.println("*********");
		 for (Invoice s : page.prevPage()) {System.out.println(page.getCurPageNum() + ".\t" + InvStr(s));}
		 System.out.println("*********");
		 for (Invoice s : page.prevPage()) {System.out.println(page.getCurPageNum() + ".\t" + InvStr(s));}
		 System.out.println("*********");
		
		/*
		Connection cn = ConnectionFactory.getConnection();
		 SettlementsRepo repo = new SettlementsRepo(cn);
		 Page<Settlements, SettlementsRepo> page = new Page(repo, Comparator.comparing((Settlements s) -> s.getCreationDatetime()));
		 //page.setComparator(Comparator.comparing((Settlements s)-> s.getPerson().getName()));
		 page.setRowsOnPage(1);
		 //Page<Shipping, ShippingRepo> page = new Page(repo, after, before, rowsOnPage, predicate, comparator)
		 page.init();
		 cn.close();
		 for (Settlements s : page.nextPage()) {System.out.println(page.getCurPageNum() + ".\t" + SettlStr(s));}
		 for (Settlements s : page.nextPage()) {System.out.println(page.getCurPageNum() + ".\t" + SettlStr(s));}
		 for (Settlements s : page.prevPage()) {System.out.println(page.getCurPageNum() + ".\t" + SettlStr(s));}
		*/
		
		
		/*
		//ConnectionWrapper.USED_CONNECTION = ConnectionWrapper.CONNECTION_URL_TEST;
		Connection cn = ConnectionFactory.getConnection();
		 ShippingRepo repo = new ShippingRepo(cn);
		 Page<Shipping, ShippingRepo> page = new Page(repo, Comparator.comparing((Shipping s) -> s.getCreationTimestamp()));
		 //page.setPredicat(e->e.getShippingStatus().getName().equals("just created"));
		 page.setComparator(Comparator.comparing((Shipping s)-> s.getShipper()));
		 //Page<Shipping, ShippingRepo> page = new Page(repo, after, before, rowsOnPage, predicate, comparator)
		 page.init();
		 cn.close();
		 for (Shipping s : page.nextPage()) {System.out.println(page.getCurPageNum() + ".\t" + ShipStr(s));}
		 for (Shipping s : page.nextPage()) {System.out.println(page.getCurPageNum() + ".\t" + ShipStr(s));}
		 for (Shipping s : page.nextPage()) {System.out.println(page.getCurPageNum() + ".\t" + ShipStr(s));}
		 System.out.println("----------------");
		 for (Shipping s : page.prevPage()) {System.out.println(page.getCurPageNum() + ".\t" + ShipStr(s));}
		 for (Shipping s : page.prevPage()) {System.out.println(page.getCurPageNum() + ".\t" + ShipStr(s));}
		 */
		
		
		/*
		Connection cn = ConnectionFactory.getConnection();
		InvoiceHasShippingDao dao = new InvoiceHasShippingDao(cn);
		System.out.println(dao.existsById(4L, 1L));
		
		//dao.deleteById(4, 1);
		dao.deleteInvoiceShippings(4);
		*/
		
		/*
		Connection cn = ConnectionFactory.getConnection();
		ShippingDao sd = new ShippingDao(cn);
		Pagination<Shipping, ShippingDao> pag = new Pagination<>();
		
		pag.setDao(sd);
		Comparator<Shipping> c = Comparator.comparing((Shipping s)-> s.getPerson().getName());
		Predicate<Shipping> p = (e)-> e.getUnloadLocality().getName().equals("Ровно");
		pag.setComparator(c);
		pag.setPredicat(p);
		pag.setCapacity(1);
		pag.init();
		
		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("--------------------------");
		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("--------------------------");
		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("--------------------------");
		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("--------------------------");
		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("--------------------------");
		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("--------------------------");
		
		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("**************************");
		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("**************************");
		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("**************************");
		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("**************************");
		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("**************************");
		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
		System.out.println("**************************");
		*/
		
			
		
		
		
		
		
		/*
		String msg = MessageManager.RU.getString("pageLogin.semd");
		System.out.println(msg);
		
		System.out.println(PasswordEncoder.getHash(""));
		//40bd001563085fc35165329ea1ff5c5ecbdbbeef 123 vasya user
		//6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2 111 sasha manager
		//b1b3773a05c0ed0176787a4f1574ff0075f7521e qwerty petya user
		//da39a3ee5e6b4b0d3255bfef95601890afd80709 ""
		
		ConnectionWrapper.createDB();
		*/
		
	}

	private static String ShipStr(Shipping s) {
		return s.getCreationTimestamp().toLocalDateTime().format(DateTimeFormatter.ISO_DATE) + " " + s.getLoadLocality().getName() + " - " + s.getUnloadLocality().getName()
				+ " : " + s.getShipper() + " | " + s.getFare() + " | " + s.getShippingStatus().getName();
	}
	private static String SettlStr(Settlements s) {
		return s.getCreationDatetime().format(DateTimeFormatter.ISO_DATE) + " " + s.getPerson().getLogin() + " - "
				+ " : " + s.getSettlementType().getName() + " | " + s.getAmount();
	}

	private static String InvStr(Invoice s) {
		return s.getCreationDateTime().toLocalDateTime().format(DateTimeFormatter.ISO_DATE) + " " + s.getPerson().getLogin() + " - "
				+ " : " + s.getInvoiceStatus().getName() + " | " + s.getSum();
	}

}
