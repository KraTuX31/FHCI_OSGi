package m2dl.osgi.cssbundle.impl;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;

public class CssDecorator implements LanguageDecoratorService {
	// Only some keywoards, we cnn add more
	private static final String[] KEY_WORDS = 
		{"color", "margin", "height", "image", "border", "max", "radius", "min", "position", "background", "width", "padding", "font", "@media", "display", "text-align"};

	private static final String keyColor = "#7f0055";
	private static final String commentColor = "grey";
	
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
		String ret = markupString;
		ret = ret.replaceAll(":comment\\{(/\\*\\*/)\\}", "<font color=\""+commentColor+"\">$1</font>");

		ret = ret.replaceAll(":keyword\\{([a-zA-Z-9\\-@]+)\\}", "<b><font color=\""+keyColor+"\">$1</font></b>");
		return ret
				;
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
			ret = ret.replaceAll(s, key(s));
		}
		
		// Comments replacing
		ret = ret.replaceAll("(/\\*.*?\\*/)", ":comment{$1}");
		return ret;
	}

	
}
