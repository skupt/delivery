package rozaryonov.delivery.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TaskReader {
	public static void main (String [] args) throws IOException {
		List<String> ls = Files.readAllLines(Paths.get("//home//uadmin//Документы//EPAM//final project//Доставка вантажу_ua.txt"), 
				Charset.forName("cp1251"));
		Files.write(Paths.get("//home//uadmin//Документы//EPAM//final project//Доставка вантажу_ua_utf.txt"), 
				ls, Charset.forName("UTF-8"));
		
		
	}
	
}
