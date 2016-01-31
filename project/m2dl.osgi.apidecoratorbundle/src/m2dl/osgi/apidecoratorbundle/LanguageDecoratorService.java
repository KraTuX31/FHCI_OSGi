package m2dl.osgi.apidecoratorbundle;

public interface LanguageDecoratorService {
	
	/**
	 * Take a text with rawtext in parameters and return an html text with keywords in color
	 * @param markup A text with markup keywords
	 * @return An html text with colors
	 */
	public String htmlColorString(String markup);
	
	/**
	 * Take a raw text in parameter, and return a text with markup keyword around keywords.
	 * @param raw The raw text
	 * @return A text with keywords
	 */
	public String rawTextToMarkupText(String raw);
}
