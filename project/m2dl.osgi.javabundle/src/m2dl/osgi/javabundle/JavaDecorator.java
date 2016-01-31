package m2dl.osgi.javabundle;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;

public class JavaDecorator implements LanguageDecoratorService {
	// Only some keywoards, we cann add more
	private static final String[] KEY_WORDS = 
		{"package", "import", "if", "while", "void", "return", "public", "private", "protected", "class", "enum"};
	
	 
	private String key(String key) {
		return key.replace(key, ":keyword{"+key+"}");
	}
	
	/**
	 * Take a text with rawtext in parameters and return an html text with keywords in color
	 * @param markup A text with markup keywords
	 * @return An html text with colors
	 */
	@Override
	public String htmlColorString(String raw) {
		System.out.println("tata");
		return raw;
	}
	
	/**
	 * Take a raw text in parameter, and return a text with markup keyword around keywords.
	 * @param raw The raw text
	 * @return A text with keywords
	 */
	@Override
	public String rawTextToMarkupText(String raw) {
		String ret = raw;
		for(String s : KEY_WORDS) {
			ret = ret.replace(s, key(s));
		}
		
		// Comments replacing
		ret = ret.replaceAll("(//.*)", ":comment{$1}");
		
		// TODO add multilines comments
		return ret;
	}

}
