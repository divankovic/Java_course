package hr.fer.zemris.java.webapp.gallery.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;


import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.webapp.gallery.servlets.Picture;

/**
 * This class is used as a converter of {@link Picture}'s to JSON format.
 * @author Dorian Ivankovic
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class PictureWriter implements MessageBodyWriter<Picture> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return type == Picture.class;
	}
	
	@Override
	public long getSize(Picture p, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return toData(p).length;
	}

	@Override
	public void writeTo(Picture p, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		
		
		entityStream.write(toData(p));
	}
	
	/**
	 * Converts the picture <code>p</code> to JSON.
	 * @param p - picture to convert to JSON
	 * @return JSON of the picture
	 */
	private byte[] toData(Picture p) {
		String text;
		if(p==null) {
			text = "null;";
		} else {
			JSONObject result = new JSONObject();
			result.put("name", p.getName());
			result.put("description", p.getDescription());
			
			JSONArray tags = new JSONArray();
			for(String t : p.getTags()) {
				tags.put(t);
			}
			result.put("tags", tags);
			
			text = result.toString();
		}
		return text.getBytes(StandardCharsets.UTF_8);
	}
}
