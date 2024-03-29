package m2dl.osgi.decorationbundle.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import m2dl.osgi.apidecoratorbundle.LanguageDecoratorService;
import m2dl.osgi.cssbundle.impl.CssDecorator;
import m2dl.osgi.decorationbundle.DecoratorService;
import m2dl.osgi.decorationbundle.activator.Activator;
import m2dl.osgi.javabundle.JavaDecorator;

public class DecoratorServiceImplementation implements DecoratorService {
	/**
	 * Read the file f and return an html string with syntaxic coloration
	 * @param f The input file
	 * @return An html string
	 * @throws IOException If the fil is not found
	 */
	@Override
	public String decorate(File f) throws IOException {
		if (f != null) {
			Activator.logger.info("File selected: " + f.getName());
		} else {
			Activator.logger.info("File selection cancelled.");
		}
		
		String rawContent = fileToHtmlString(f);		
		String retHtmlContent = rawContent;
		
		// If it is a java file
		if(f.getAbsolutePath().toLowerCase().endsWith(".java")) { // Java file !
			Bundle b  = getBundleByPartName("java");
			if(b!= null) {
				LanguageDecoratorService service = getLanguageDecorationService(JavaDecorator.class, b);
				retHtmlContent = rawToHtml(service, rawContent);
			}
		} 
		
		// If it is a css file
		if(f.getAbsolutePath().toLowerCase().endsWith(".css")) { // css file
			Bundle b  = getBundleByPartName("css");
			if(b!= null) {
				LanguageDecoratorService service = getLanguageDecorationService(CssDecorator.class, b);
				// Add markup for css
				retHtmlContent = rawToHtml(service, rawContent);
			}
		} 
		
			
		
		 return retHtmlContent;
	}
	
	private String rawToHtml(LanguageDecoratorService service, String rawContent) {
		String markupContent = service.rawTextToMarkupText(rawContent);
		
		return service.htmlColorString(markupContent);
	}

	@Override
	public void loadBundle(File f) {
		Bundle myBundle;
		try {
			myBundle = Activator.context.installBundle(f.toURI().toString());
			m2dl.osgi.decorationbundle.activator.Activator.context.installBundle(f.toURI().toString());
			myBundle.start();

			Activator.logger.info("The bundle " + f + " installed and started");
		} catch (final BundleException e) {
			Activator.logger.error("Error on bundle loading. Action aborted.");
		}
	}
	

	/**
	 * Start or stop css bundle
	 */
	@Override
	public void toggleCss() {
		toggleStatusBundle("css");		
	}

	/**
	 * Start or stop java bundle
	 */
	@Override
	public void toggleJava() {
		toggleStatusBundle("java");		
	}

	
	/**
	 * Read a file line by line and return a string with markup <br/> instead of \n
	 * @param f The input file
	 * @return The string of file f
	 * @throws IOException If the file is not found
	 */
	private static String fileToHtmlString(File f) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    String ret = "";
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line.replace("\t", "    ").replace(" ", "&nbsp;"));
	            sb.append("\n<br/>");
	            line = br.readLine();
	        }
	        ret = sb.toString();
	    } finally {
	        br.close();
	    }
	    
	    
	    return ret+"";	   
	}

	/**
	 * start or stop a bundle with its name 
	 * @param bundle A part of bundle name
	 */
	private void toggleStatusBundle(String bundle) {
		Bundle b = getBundleByPartName(bundle);
		if(b == null) {
			Activator.logger.error("There is no "+bundle+" bundle installed");
			return;
		}
				
		try {
			if(b.getState() == Bundle.ACTIVE) {
				b.start();

			} else {
				b.stop();
			}
			return;
		} catch (BundleException e) {
			Activator.logger.error("Error on toggling decorator bundle : "+e.getMessage());
		}							
	}

	/**
	 * Get a bundle with its name
	 * @param bundleName Part of bundle name
	 * @return The bundle or null if we can't find a bundle with this name
	 */
	private Bundle getBundleByPartName(String bundleName) {
		for(Bundle b : Activator.context.getBundles()) {
			String s = b.getSymbolicName();
			if(s != null && s.toLowerCase().contains("decorator")) {
				return b;			
			}
		}
		return null;
	}

	/**
	 * Get the correct service implementation reference in bundle b 
	 * @param impl The class name of implementation
	 * @param b The bundle for the context
	 * @return The service instanciation or null if not found
	 */
	public LanguageDecoratorService getLanguageDecorationService(Class<?> impl, Bundle b) {
		ServiceReference<?>[] references;
		try {
			references = b.getBundleContext().getServiceReferences( impl.getName(), "(type=good_property)");
			return (( LanguageDecoratorService) Activator.context.getService(references[0]));
		} catch (InvalidSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
