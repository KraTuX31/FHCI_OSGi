package m2dl.osgi.main;

public class MainEditor {
	public static void main(String[] args) {
		MainOSGi.main(null);
		
		/*
		 * We can start the application with static installation of each required bundles.
		 */
		MainOSGi.installAndStartBundle("/tmp/plugins/m2dl.osgi.apidecoratorbundle_1.0.0.jar");
		MainOSGi.installAndStartBundle("/tmp/plugins/m2dl.osgi.javabundle_1.0.0.201601311711.jar");
		MainOSGi.installAndStartBundle("/tmp/plugins/m2dl.osgi.cssbundle_1.0.0.jar");
		MainOSGi.installAndStartBundle("/tmp/plugins/m2dl.osgi.decorationbundle_1.0.0.jar");
		MainOSGi.installAndStartBundle("/tmp/plugins/m2dl.osgi.editor_1.0.0.jar"); // Start editor
		MainOSGi.showInstalledBundles();
		

		System.exit(0);
	}
}
