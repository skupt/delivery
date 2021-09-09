package rozaryonov.delivery.services;

import java.sql.Connection;
import java.util.Comparator;
import java.util.function.Predicate;

import rozaryonov.delivery.dao.ConnectionFactory;
import rozaryonov.delivery.dao.ConnectionWrapper;
import rozaryonov.delivery.dao.impl.InvoiceHasShippingDao;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.entities.Shipping;

public class Main {

	public static void main(String[] args) {
		Connection cn = ConnectionFactory.getConnection();
		InvoiceHasShippingDao dao = new InvoiceHasShippingDao(cn);
		System.out.println(dao.existsById(4L, 1L));
		
		//dao.deleteById(4, 1);
		dao.deleteInvoiceShippings(4);
		
		
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
		return s.getCreationTimestamp() +" "+ s.getLoadLocality().getName() + " - " + s.getUnloadLocality().getName() + " : " + s.getShipper();
	}

}
