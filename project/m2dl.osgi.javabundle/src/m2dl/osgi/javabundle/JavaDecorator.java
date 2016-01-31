package m2dl.osgi.javabundle;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;

public class JavaDecorator implements LanguageDecoratorService {
	// Only some keywoards, we cann add more
	private static final String[] KEY_WORDS = 
		{"abstract", "assert",
		"boolean", "break", "byte", "case", "catch", "char", "class",
		"const", "continue", "default", "do", "double", "else", "enum",
		"extends", "false", "final", "finally", "float", "for", "goto",
		"if", "implements", "import", "instanceof", "int", "interface",
		"long", "native", "new", "null", "package", "private", "protected",
		"public", "return", "short", "static", "strictfp", "super",
		"switch", "synchronized", "this", "throw", "throws", "transient",
		"true", "try", "void", "volatile", "while" };
	
	private static final String keyColor = "#7f0055";
	private static final String commentColor = "grey";

	private static final String quoteColor = "red";
	 
	private String key(String key) {
		return key.replace(key, ":keyword{" + key + "}");
	}
	
	/**
	 * Take a text with rawtext in parameters and return an html text with keywords in color
	 * @param markup A text with markup keywords
	 * @return An html text with colors
	 */
	@Override
	public String htmlColorString(String raw) {
		String ret = raw;
		ret = ret.replaceAll(
				":comment\\{(//[a-zA-Z0-9\\- ]+)\\}", 
				"<font color=\""+commentColor+"\">$1</font>");

		ret = ret.replaceAll(
				":keyword\\{([a-zA-z0-9\\-@]+)\\}", 
				"<b><font color=\""+keyColor+"\">$1</font></b>");

		ret = ret.replaceAll(
				":quote\\{(.+)\\}", 
				"<i><font color=\""+quoteColor+"\">$1</font></i>");
		return ret;
		
	}
	
	/**
	 * Take a raw text in para\\\\meter, and return a text with markup keyword around keywords.
	 * @param raw The raw text
	 * @return A text with keywords
	 */
	@Override
	public String rawTextToMarkupText(String raw) {
		String ret = raw;
		for(String s : KEY_WORDS) {
			ret = ret.replace(s + " ", key(s) + " ");
			ret = ret.replace(s + "(", key(s) + "(");
		}
		
		// Comments replacing
		ret = ret.replaceAll("(//[a-zA-Z0-9\\- ]+)", ":comment{$1}");
		
		ret = ret.replaceAll("(\"[A-Za-z0-9\\- ]+\")", ":quote{$1}");
		// TODO add multilines comments
		ret = ret.replaceAll("(\\/\\*+((([^\\*])+)|([\\*]+(?!\\/)))[*]+\\/)", 
				":comment{$1}");
		return ret;
	}

}
