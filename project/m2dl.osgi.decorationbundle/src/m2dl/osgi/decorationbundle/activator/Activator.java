package m2dl.osgi.decorationbundle.activator;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import m2dl.osgi.decorationbundle.DecoratorService;
import m2dl.osgi.decorationbundle.impl.DecoratorServiceImplementation;

public class Activator implements BundleActivator {
	public static final Logger logger = LogManager.getLogger(Activator.class);
	public static BundleContext context;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("type",  "good_property");
		properties.put("name", "DecoratorService");
		Activator.context = context;
		
		// Declaration of DecoratorService		 
		context.registerService(DecoratorService.class.getName(), new DecoratorServiceImplementation(), properties);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
