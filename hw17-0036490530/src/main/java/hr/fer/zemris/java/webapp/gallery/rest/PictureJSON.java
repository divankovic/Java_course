package hr.fer.zemris.java.webapp.gallery.rest;

import java.util.List;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.webapp.gallery.servlets.Picture;
import hr.fer.zemris.java.webapp.gallery.servlets.PictureDB;

/**
 * Rest support for needed for GET operations regarding
 * {@link Picture}'s.
 * @author Dorian Ivankovic
 *
 */
@Path("/picturej")
public class PictureJSON {

	/**
	 * Returns the tags of all pictures from the database ({@link PictureDB}
	 * @return all tags
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		Set<String> tags = PictureDB.getTags();
		
		JSONObject result = new JSONObject();
		JSONArray tagsJson = new JSONArray();
		tags.forEach(tag->tagsJson.put(tag));
		
		result.put("tags", tagsJson);
		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * Returns the thumbnails of pictures containing the specified tag.
	 * @param tag - tag to search for in the pictures
	 * @return pictures containing the specified tag
	 */
	@Path("/tag/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThumbnails(@PathParam("tag") String tag) {
		List<String> thumbnails = PictureDB.getPicturesWithTag(tag);
		JSONObject result = new JSONObject();
		
		JSONArray thumbs = new JSONArray();
		thumbnails.forEach(t->thumbs.put(t));
		
		result.put("thumbs", thumbs);
		return Response.status(Status.OK).entity(result.toString()).build();
	}
	
	/**
	 * Returns the picture with the specified name (name is considered to be unique for the picture).
	 * @param name - name of the picture
	 * @return the picture with the specified name
	 */
	@Path("/pic/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Picture getPicture(@PathParam("name") String name) {
		Picture picture = PictureDB.getPictureByName(name);
		return picture;
	}
	
}
