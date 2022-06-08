import java.util.Date;
import java.util.Locale;

import com.github.javafaker.Faker;

public class Fake_Data {

	public static void main(String[] args) {
		
		Faker fk=new Faker(new Locale("en-IND"));
		
		String fname=fk.name().firstName();
		String lname=fk.name().lastName();		
		int age=fk.number().numberBetween(20, 60);
		int sal=fk.number().numberBetween(12000, 45000);
		String dob=fk.date().birthday().toLocaleString();
		
		System.out.println(fname);
		System.out.println(lname);
		System.out.println(dob);
		//System.out.println(age);
		System.out.println(sal);

	}

}
