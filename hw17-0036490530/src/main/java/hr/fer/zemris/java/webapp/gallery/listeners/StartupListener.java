package hr.fer.zemris.java.webapp.gallery.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.webapp.gallery.servlets.Picture;
import hr.fer.zemris.java.webapp.gallery.servlets.PictureDB;


/**
 * The listener initializes the application once it's started by parsing opisnik.txt that
 * contains all information about the pictures needed for the gallery application.
 * The information is stored in {@link PictureDB}.
 * @author Dorian Ivankovic
 *
 */
@WebListener
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		List<Picture> pictures = new ArrayList<>();
		String picturePath = sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt");
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(picturePath));
			int n = lines.size();
			int i = 0;
			
			while(i<n) {
				if(lines.get(i).isEmpty()) {
					i++;
					continue;
				}
				
				String pictureName = lines.get(i++);
				String pictureDescription = lines.get(i++);
				String[] tags = lines.get(i++).split(",");
				
				Set<String> tagSet = new HashSet<>();
				for(String tag:tags) {
					tagSet.add(tag.trim());
				}
				
				pictures.add(new Picture(pictureName, pictureDescription, tagSet));
			}
			
			PictureDB.init(pictures);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}