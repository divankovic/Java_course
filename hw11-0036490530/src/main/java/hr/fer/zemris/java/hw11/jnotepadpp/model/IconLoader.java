package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Utility class used for loading resource images.
 * @author Dorian Ivankovic
 *
 */
public class IconLoader {
	
	/**
	 * Loads the icon with name <code>name</code> from the class c corresponding resources folder.
	 * @param c - class used to determine path to the image
	 * @param name - name of the image
	 * @return loaded image
	 */
	public static ImageIcon loadIcon(Class<?> c, String name) {
		byte[] bytes = null;
		
		try(InputStream is = c.getResourceAsStream("icons/"+name)){
			bytes = is.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(bytes); 
	}
}
