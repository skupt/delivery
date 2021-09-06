package rozaryonov.delivery.services;

import rozaryonov.delivery.dao.ConnectionWrapper;

public class Main {

	public static void main(String[] args) {
		String msg = MessageManager.RU.getString("pageLogin.semd");
		System.out.println(msg);
		
		System.out.println(PasswordEncoder.getHash(""));
		//40bd001563085fc35165329ea1ff5c5ecbdbbeef 123 vasya user
		//6216f8a75fd5bb3d5f22b6f9958cdede3fc086c2 111 sasha manager
		//b1b3773a05c0ed0176787a4f1574ff0075f7521e qwerty petya user
		//da39a3ee5e6b4b0d3255bfef95601890afd80709 ""
		
		ConnectionWrapper.createDB();
		
	}

}
