package hr.fer.zemris.java.webapp.gallery.servlets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class is used as a {@link Picture} database imitation.
 * The pictures are stored in an internal list.
 * @author Dorian Ivankovic
 *
 */
public class PictureDB {

	/**
	 * Internal collection of {@link Picture}'s.
	 */
	private static  List<Picture> pictures;
	
	/**
	 * Initializes the database.
	 * @param pics - collection of pictures to store in the database.
	 */
	public static void init(List<Picture> pics) {
		if(pictures==null) {
			pictures = new ArrayList<>(pics);
		}
	}
	
	/**
	 * Returns the tags that appear in all pictures in the database.
	 * @return all appearing tags
	 */
	public static Set<String> getTags() {
		Set<String> tags = new HashSet<>();
		for(Picture picture:pictures) {
			tags.addAll(picture.getTags());
		}
		
		return tags;
	}
	
	/**
	 * Returns all pictures containing the specified tag.
	 * @param tag - tag to search for in the pictures
	 * @return all pictures containing the specified tag
	 */
	public static List<String> getPicturesWithTag(String tag){
		List<String> taggedPictures = new ArrayList<>();
		for(Picture picture:pictures) {
			if(picture.getTags().contains(tag)) {
				taggedPictures.add(picture.getName());
			}
		}
		
		return taggedPictures;
	}
	
	/**
	 * Returns the {@link Picture} with the specified name.
	 * @param name - name of the picture
	 * @return picture with the specified name
	 */
	public static Picture getPictureByName(String name) {
		for(Picture picture : pictures) {
			if(picture.getName().equals(name)) {
				return picture;
			}
		}
		
		return null;
	}

}


