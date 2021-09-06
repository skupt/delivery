package rozaryonov.delivery.entities.validators;

import rozaryonov.delivery.entities.Person;

public class PersonValidator implements Validatable<Person> {
	public static final String LOGPASS="\\w{3,}";

	@Override
	public boolean validate(Person p) {
		if (p.getLogin()==null || p.getPassword()==null) return false;
		boolean result = p.getLogin().matches(LOGPASS) && p.getPassword().matches(LOGPASS);

		return result;
	}
	
	public static void main (String [] args) {
		System.out.println("pp".matches(LOGPASS));
		System.out.println("ppqwe".matches(LOGPASS));
		System.out.println("йцукк".matches(LOGPASS));
		
		Person p = new Person();
		p.setLogin("ll");
		p.setPassword("pass123");
		
		PersonValidator pv = new PersonValidator();
		System.out.println(pv.validate(p));
		
	}

}
