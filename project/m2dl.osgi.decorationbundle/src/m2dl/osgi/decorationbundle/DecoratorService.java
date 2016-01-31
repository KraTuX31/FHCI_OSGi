package m2dl.osgi.decorationbundle;

import java.io.File;
import java.io.IOException;

public interface DecoratorService {
	public String decorate(File f) throws IOException ;
	public void loadBundle(File f);
	
	public void toggleCss();
	public void toggleJava();
}
