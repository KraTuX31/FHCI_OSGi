package m2dl.osgi.editor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UtilFiles {
	public static String fileToHtmlString(File f) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("<br/>");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }

	}

}
