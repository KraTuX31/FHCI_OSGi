package m2dl.osgi.decorationbundle;

import java.io.File;
import java.io.IOException;

public interface DecoratorService {
	/**
	 * Read the file f and return an html string with syntaxic coloration
	 * @param f The input file
	 * @return An html string
	 * @throws IOException If the fil is not found
	 */
	public String decorate(File f) throws IOException ;
	public void loadBundle(File f);
	
	/**
	 * Start or stop css bundle
	 */
	public void toggleCss();
	
	/**
	 * Start or stop java bundle
	 */
	public void toggleJava();
}
