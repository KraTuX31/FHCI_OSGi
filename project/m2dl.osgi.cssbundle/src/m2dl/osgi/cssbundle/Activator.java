package m2dl.osgi.cssbundle;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import m2dl.osgi.cssbundle.impl.CssDecorator;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("type",  "good_property");
		properties.put("name", "LanguageDecoratorService");

		// Declaration of CSSDecorator
		context.registerService(CssDecorator.class.getName(), new CssDecorator(), properties);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
