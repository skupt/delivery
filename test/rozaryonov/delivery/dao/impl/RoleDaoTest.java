package rozaryonov.delivery.dao.impl;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import rozaryonov.delivery.dao.ConnectionWrapper;
import rozaryonov.delivery.entities.Role;

public class RoleDaoTest {
	private static Logger logger = LogManager.getLogger(RoleDaoTest.class.getName());

	@Test
	public void saveShouldReturnGeneratedKeysWhenRoleIdEquas0() {
		ConnectionWrapper.createDB();
		Connection cn = ConnectionWrapper.getConnection();
		RoleDao roleDao = new RoleDao(cn);
		Role r1 = new Role();
		r1.setName("role666");
		roleDao.save(r1);
		assertNotEquals(0L, r1.getId());
		
		r1.setName("role777");
		long id = r1.getId();
		roleDao.save(r1);
		assertEquals(id, r1.getId());
		
		roleDao.close();
		
	}

}
