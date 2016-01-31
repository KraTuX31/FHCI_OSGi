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

public class DecoratorServiceImplementation implements DecoratorService {
	@Override
	public String decorate(File f) throws IOException {
		if (f != null) {
			Activator.logger.info("File selected: " + f.getName());
		} else {
			Activator.logger.info("File selection cancelled.");
		}
		
		String rawContent = fileToHtmlString(f);
		String markupContent = null;;
		if(f.getAbsolutePath().toLowerCase().endsWith(".java")) { // Java file !
			Bundle b  = getBundleByPartName("java");
			if(b!= null) {
				LanguageDecoratorService service = getLanguageDecorationService(null, b);
			}
			// TODO call java decorator
		} 
		
		if(f.getAbsolutePath().toLowerCase().endsWith(".css")) { // css file
			Bundle b  = getBundleByPartName("css");
			// TODO call css decorator
			if(b!= null) {
				LanguageDecoratorService service = getLanguageDecorationService(CssDecorator.class, b);
				service.htmlColorString(rawContent);
			}
		} 
		
		 // Other file
		if(markupContent == null) {
			// TO HTML
		}
		
		
		 return "";
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
	

	@Override
	public void toggleCss() {
		toggleStatusBundle("css");		
	}

	@Override
	public void toggleJava() {
		toggleStatusBundle("java");		
	}

	
	private static String fileToHtmlString(File f) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    String ret = "";
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("<br/>");
	            line = br.readLine();
	        }
	        ret = sb.toString();
	    } finally {
	        br.close();
	    }
	    
	    return ret+"";	   
	}

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

	private Bundle getBundleByPartName(String bundleName) {
		for(Bundle b : Activator.context.getBundles()) {
			String s = b.getSymbolicName();
			if(s != null && s.toLowerCase().contains("decorator")) {
				return b;			
			}
		}
		return null;
	}

	public DecoratorServiceImplementation() {
	}
	
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
