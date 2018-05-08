package project.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.*;
/**
 * Represents data about a question.
 * 
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */
public class Question extends Resource
{
	private final int ID;
	private final String title;
	private final String body;
	private final String timestamp;
	private final String lastModified;
	private final int IDUser; //Trasformare in tipo User?

	/**
	 * Creates a new question which is going to be inserted into the database
	 * 
	 * @param title
	 *            the title of the question.
	 * @param body
	 *            the body of the question.
	 * @param timestamp
	 *            the timestamp of the question.
	 */
	public Question(final int IDUser, final String title, final String body, final String timestamp)
	{
		this.ID = -1; //Essendo auto increment come campo, se creo una nuova domanda essa non avrà un ID 
		this.title = title;
		this.body = body;
		this.timestamp = timestamp;
		this.IDUser = IDUser;
		this.lastModified = "";
	}

	/**
	 * Creates a new question
	 * 
	 * @param ID
	 *            the ID of the question
	 * @param title
	 *            the title of the question.
	 * @param body
	 *            the body of the question.
	 * @param timestamp
	 *            the timestamp of the question.
	 * @param lastModified
	 *            the last modified timestamp of the question.
	 */
	public Question(final int ID, final int IDUser, final String title, final String body, final String timestamp ,final String lastModified)
	{
		this.ID = ID;
		this.title = title;
		this.body = body;
		this.IDUser = IDUser;
		this.timestamp = timestamp;
		this.lastModified = lastModified;
	}
	
	/**
	 * Returns the ID of the question.
	 * 
	 * @return the ID of the question.
	 */
	public final int getID()
	{
		return ID;
	}

	/**
	 * Returns the ID of the user who posted the question.
	 * 
	 * @return the ID of the user who posted the question.
	 */
	public final int getIDUser()
	{
		return IDUser;
	}

	/**
	 * Returns the title of the question.
	 * 
	 * @return the title of the question.
	 */
	public final String getTitle()
	{
		return title;
	}

	/**
	 * Returns the body of the question.
	 * 
	 * @return the body of the question.
	 */
	public final String getBody()
	{
		return body;
	}
	/**
	 * Returns the timestamp of the question.
	 * 
	 * @return the timestamp of the question.
	 */
	public final String getTimestamp()
	{
		return timestamp;
	}
	/**
	 * Returns the last modified timestamp of the question.
	 * 
	 * @return the last modified timestamp of the question.
	 */
	public final String getLastModified()
	{
		return lastModified;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("question");

		jg.writeStartObject();

		if(ID!=-1) jg.writeNumberField("ID",ID);
		jg.writeStringField("title", title);
		jg.writeStringField("body", body);
		jg.writeStringField("timestamp", timestamp);
		jg.writeStringField("lastModified",lastModified);
		jg.writeNumberField("IDUser",IDUser);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}
}
