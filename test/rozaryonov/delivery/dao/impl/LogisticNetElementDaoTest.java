package rozaryonov.delivery.dao.impl;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import rozaryonov.delivery.dao.ConnectionWrapper;
import rozaryonov.delivery.entities.LogisticNetElement;

public class LogisticNetElementDaoTest {

	@Test
	public void test() {
		Connection con = ConnectionWrapper.getConnection();
		LogisticNetElementDao dao = new LogisticNetElementDao(con);
		List<LogisticNetElement> nel = (ArrayList<LogisticNetElement>) dao.findByNetConfig(1);
		String actual = nel.stream().map(e->e.toString()).collect(Collectors.joining("\n"));
		String exp = "LogisticNetElement [city=Locality [id=1, name=Киев], neighbor=Locality [id=1, name=Киев], distance=100.0]\n" + 
				"LogisticNetElement [city=Locality [id=2, name=Львов], neighbor=Locality [id=1, name=Киев], distance=550.0]\n" + 
				"LogisticNetElement [city=Locality [id=3, name=Луцк], neighbor=Locality [id=1, name=Киев], distance=438.0]\n" + 
				"LogisticNetElement [city=Locality [id=4, name=Ужгород], neighbor=Locality [id=1, name=Киев], distance=809.0]\n" + 
				"LogisticNetElement [city=Locality [id=5, name=Ровно], neighbor=Locality [id=1, name=Киев], distance=336.0]\n" + 
				"LogisticNetElement [city=Locality [id=6, name=Тернополь], neighbor=Locality [id=1, name=Киев], distance=420.0]\n" + 
				"LogisticNetElement [city=Locality [id=7, name=Хмельницкий], neighbor=Locality [id=1, name=Киев], distance=323.0]\n" + 
				"LogisticNetElement [city=Locality [id=8, name=Житомир], neighbor=Locality [id=1, name=Киев], distance=140.0]\n" + 
				"LogisticNetElement [city=Locality [id=9, name=Черкассы], neighbor=Locality [id=1, name=Киев], distance=192.0]\n" + 
				"LogisticNetElement [city=Locality [id=10, name=Чернигов], neighbor=Locality [id=1, name=Киев], distance=148.0]\n" + 
				"LogisticNetElement [city=Locality [id=11, name=Кропивницкий], neighbor=Locality [id=1, name=Киев], distance=303.0]\n" + 
				"LogisticNetElement [city=Locality [id=12, name=Николаев], neighbor=Locality [id=1, name=Киев], distance=481.0]\n" + 
				"LogisticNetElement [city=Locality [id=13, name=Херсон], neighbor=Locality [id=1, name=Киев], distance=547.0]\n" + 
				"LogisticNetElement [city=Locality [id=14, name=Одесса], neighbor=Locality [id=1, name=Киев], distance=475.0]\n" + 
				"LogisticNetElement [city=Locality [id=15, name=Днепр], neighbor=Locality [id=1, name=Киев], distance=478.0]\n" + 
				"LogisticNetElement [city=Locality [id=16, name=Запорожье], neighbor=Locality [id=1, name=Киев], distance=556.0]\n" + 
				"LogisticNetElement [city=Locality [id=17, name=Сумы], neighbor=Locality [id=1, name=Киев], distance=369.0]\n" + 
				"LogisticNetElement [city=Locality [id=18, name=Харьков], neighbor=Locality [id=1, name=Киев], distance=482.0]\n" + 
				"LogisticNetElement [city=Locality [id=19, name=Полтава], neighbor=Locality [id=1, name=Киев], distance=344.0]\n" + 
				"LogisticNetElement [city=Locality [id=20, name=Винница], neighbor=Locality [id=1, name=Киев], distance=263.0]\n" + 
				"LogisticNetElement [city=Locality [id=21, name=Черновцы], neighbor=Locality [id=1, name=Киев], distance=531.0]\n" + 
				"LogisticNetElement [city=Locality [id=22, name=Ивано-Франковск], neighbor=Locality [id=1, name=Киев], distance=605.0]";
		
		assertEquals(exp, actual);
	}

}
