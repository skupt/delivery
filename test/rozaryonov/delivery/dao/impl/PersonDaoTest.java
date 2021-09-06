package rozaryonov.delivery.dao.impl;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import rozaryonov.delivery.dao.ConnectionWrapper;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Role;

public class PersonDaoTest {

	@Test
	public void saveNewPersonTest() {
		ConnectionWrapper.createDB();
		Connection cn = ConnectionWrapper.getConnection();
		RoleDao rd = new RoleDao(cn);
		PersonDao pd = new PersonDao(cn);

		Role r1 = new Role();
		r1.setName("testRole");
		rd.save(r1);
		
		Person p = new Person();
		p.setLogin("testLogin");
		p.setPassword("testPassword");
		p.setEmail("1@q.r");
		p.setName("Rtut");
		p.setSurname("Surname");
		p.setPatronomic("Patron");
		p.setTitle("Ms.");
		p.setRole(r1);

		pd.save(p);
		
		Person p2 = pd.findById(p.getId()).orElse(null);
		rd.close();
		pd.close();

		assertEquals(p.toString(), p2.toString());
	}
	
	@Test
	public void testFindAll() {
		ConnectionWrapper.createDB();
		Connection cn = ConnectionWrapper.getConnection();
		PersonDao pd = new PersonDao(cn);
		StringBuilder sb = new StringBuilder();
		for (Person p: pd.findAll()) {
			sb.append(p.toString()).append("\n");
		}
		String expected = "Person [id=1, login=vasya, password=123, email=a@a.com, name=, surname=null, patronomic=null, title=null, role=Role [id=2, name=user]]\n" + 
				"Person [id=2, login=petya, password=111, email=b@a.com, name=null, surname=null, patronomic=null, title=null, role=Role [id=2, name=user]]\n" + 
				"Person [id=3, login=sahsa, password=000, email=c@a.com, name=, surname=, patronomic=, title=, role=Role [id=1, name=manager]]\n" + 
				"";
		
		assertEquals(expected, sb.toString());
		
	}

}
