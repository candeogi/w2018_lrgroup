package project.resource;

import com.fasterxml.jackson.core.*;
import java.io.*;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Represents data about a question.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
 */
public class Question extends Resource
{
	private final int ID;
	private final String title;
	private final String body;
	private final Timestamp timestamp;
	private final Timestamp lastModified;
	private final String IDUser; //Trasformare in tipo User?

	/**
	 * Creates a new question
	 * 
	 * @param ID
	 *            the ID of the question
	 * @param title
	 *            the title of the question.
	 * @param body
	 *            the body of the question.
	 * @param IDUser
	 * 				the identifier of the user that creates the question
	 * @param timestamp
	 *            the timestamp of the question.
	 * @param lastModified
	 *            the last modified timestamp of the question.
	 */
	public Question(final int ID, final String IDUser, final String title, final String body, final Timestamp timestamp ,final Timestamp lastModified)
	{
		this.ID = ID;
		this.title = title;
		this.body = body;
		this.IDUser = IDUser;
		this.timestamp = timestamp;
		this.lastModified = lastModified;
	}

	/**
	 * Creates a new question which is going to be inserted into the database
	 * @param IDUser the identifier of the user that creates the question
	 * @param title
	 *            the title of the question.
	 * @param body
	 *            the body of the question.
	 * @param timestamp
	 *            the timestamp of the question.
	 */
	public Question(final String IDUser, final String title, final String body, final Timestamp timestamp)
	{
		this(-1,IDUser, title,body,timestamp,timestamp);
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
	public final String getIDUser()
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
	public final Timestamp getTimestamp()
	{
		return timestamp;
	}
	/**
	 * Returns the last modified timestamp of the question.
	 * 
	 * @return the last modified timestamp of the question.
	 */
	public final Timestamp getLastModified()
	{
		return lastModified;
	}

	/**
	 *
	 * @param out  the stream to which the JSON representation of the {@code Resource} has to be written.
	 *
	 * @throws IOException if something goes wrong while parsing.
	 */
	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("question");

		jg.writeStartObject();

		if(ID!=-1) jg.writeNumberField("ID",ID);
		jg.writeStringField("title", title);
		jg.writeStringField("body", body);
		jg.writeStringField("timestamp", timestamp.toString());
		if(lastModified!=null) jg.writeStringField("lastModified",lastModified.toString());
		jg.writeStringField("IDUser",IDUser);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}

	/**
	 * Creates a {@code Question} from its JSON representation.
	 *
	 * @param in the input stream containing the JSON document.
	 *
	 * @return the {@code Question} created from the JSON representation.
	 *
	 * @throws IOException if something goes wrong while parsing.
	 *
	 * @throws ParseException if date is not parsed correctly
	 */
	public static Question fromJSON(final InputStream in) throws IOException, ParseException
	{

		// the fields read from JSON
		int jID = -1;
		String jBody = null;
		String jTitle = null;
		String jTimestamp = null;
		String jLastModified = null;
		String jIDUser = null;
		

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// I'm looking for the answer field
		//i'll keep looping until i find a token which is a field name or until i find my resource token
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "question".equals(jp.getCurrentName()) == false)
		{
			// there are no more events
			if (jp.nextToken() == null)
			{
				throw new IOException("Unable to parse JSON: no employee object found.");
			}
		}

		while (jp.nextToken() != JsonToken.END_OBJECT)
		{
			if (jp.getCurrentToken() == JsonToken.FIELD_NAME)
			{
				switch (jp.getCurrentName())
				{
					case "ID":
						jp.nextToken();
						jID = jp.getIntValue();
						break;
					case "body":
						jp.nextToken();
						jBody = jp.getText();
						break;
					case "lastModified":
						jp.nextToken();
						jLastModified = jp.getText();
						break;
					case "timestamp":
						jp.nextToken();
						jTimestamp = jp.getText();
						break;
					case "IDUser":
						jp.nextToken();
						jIDUser= jp.getText();
						break;
					case "title":
						jp.nextToken();
						jTitle= jp.getText();
						break;
				}
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date parsedDate = dateFormat.parse(jTimestamp);
    	Timestamp databaseTimestamp = new Timestamp(parsedDate.getTime());
    	parsedDate = dateFormat.parse(jLastModified);
    	Timestamp databaseLastModified = new Timestamp(parsedDate.getTime());
		return new Question(jID, jIDUser, jTitle, jBody, databaseTimestamp ,databaseLastModified);
		
	}
}
