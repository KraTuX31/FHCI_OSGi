package m2dl.osgi.javabundle;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("type",  "good_property");
		properties.put("name", "LanguageDecoratorService");
		context.registerService(JavaDecorator.class.getName(), new JavaDecorator(), properties);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye Java World!!");
	}

}
