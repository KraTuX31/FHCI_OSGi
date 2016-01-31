package m2dl.osgi.cssbundle.impl;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;

public class CssDecorator implements LanguageDecoratorService {
	// Only some keywoards, we cnn add more
	private static final String[] KEY_WORDS = 
		{"color", "margin", "height", "position", "background", "width", "height", "padding", "font", "@media", "display", "text-align"};

	private String key(String key) {
		return key.replace(key, ":keyword{"+key+"}");
	}

	/**
	 * Take a text with rawtext in parameters and return an html text with keywords in color
	 * @param markup A text with markup keywords
	 * @return An html text with colors
	 */
	@Override
	public String htmlColorString(String markupString) {
		return markupString;
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
		// TODO add multine comments
		return ret;
	}

	
}
