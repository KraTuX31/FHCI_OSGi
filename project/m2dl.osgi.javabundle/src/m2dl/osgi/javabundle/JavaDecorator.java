package m2dl.osgi.javabundle;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;

public class JavaDecorator implements LanguageDecoratorService {

	@Override
	public String htmlColorString(String raw) {
		System.out.println("tata");
		return null;
	}

}
