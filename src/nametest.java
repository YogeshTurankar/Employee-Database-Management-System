import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class nametest {

	public static void main(String[] args) throws Exception {
		
		String date="1991-07-24";
		String format="[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}";
		if(date.matches(format)) {
			System.out.println("Valid date");
		}else {
			System.out.println("Invalid date");
		}
		
		

	}

}
