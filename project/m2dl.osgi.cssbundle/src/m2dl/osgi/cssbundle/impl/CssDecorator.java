package m2dl.osgi.cssbundle.impl;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;

public class CssDecorator implements LanguageDecoratorService {

	@Override
	public String htmlColorString(String markupString) {
		System.out.println("toto");
		return null;
	}

	
}
