package rozaryonov.delivery.dao.impl;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import rozaryonov.delivery.dao.ConnectionWrapper;

public class ShippingDaoTest {

	@Test
	public void test() {
		Connection cn = ConnectionWrapper.getConnection();
		ShippingDao dao = new ShippingDao(cn);
		StringBuilder sb = new StringBuilder();
		dao.findAll().forEach(e->sb.append(e.toString()));
		
		assertNotNull(sb.toString());
	}

}
