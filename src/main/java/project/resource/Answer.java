package project.resource;

import com.fasterxml.jackson.core.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * Represents data about an answer.
 * 
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */

public class Answer extends Resource
{

	private final int ID;
	private final boolean fixed;
	private final String text;
	private final int parentID; //Trasformare in Answer/Question?
	private final Timestamp timestamp;
	private final String IDUser;
	private final int questionID;
	/**
	 * Creates a new answer.
	 * @param ID
	 *            the ID of the answer;
	 * @param fixed
	 *            whether or not the answer is marked as the solution
	 * @param text
	 *            the body of the answer.
	 * @param timestamp
	 *            the timestamp of the answer.
	 */

	public Answer(final int ID, final String IDUser ,final boolean fixed, final String text, final int parentID ,final Timestamp timestamp,final int questionID)
	{
		this.ID = ID;
		this.fixed = fixed;
		this.text = text;
		this.parentID = parentID;
		this.timestamp = timestamp;
		this.IDUser = IDUser;
		this.questionID=questionID;
	}

	/**
	 * Creates a new answer which is going to be inserted into the database
	 * 
	 * @param fixed
	 *            whether or not the answer is marked as the solution
	 * @param text
	 *            the body of the answer.
	 * @param timestamp
	 *            the timestamp of the answer.
	 */

	public Answer(final String IDUser, final boolean fixed, final String text, final int parentID, final Timestamp timestamp,final int questionID)
	{
		this(-1,IDUser,fixed, text, parentID, timestamp,questionID);
	}

	/**
	 * Returns the ID of the answer.
	 * 
	 * @return the ID of the answer.
	 */

	public int getID()
	{
		return ID;
	}

	/**
	 * Returns whether or not the answer is marked as the solution.
	 * 
	 * @return whether or not the answer is marked as the solution.
	 */

	public boolean isFixed()
	{
		return fixed;
	}

	/**
	 * Returns the text of the answer.
	 * 
	 * @return the text of the answer.
	 */

	public String getText()
	{
		return text;
	}

	/**
	 * Returns the ID of the user who posted the answer.
	 * 
	 * @return the ID of the user who posted the answer.
	 */
	public final String getIDUser()
	{
		return IDUser;
	}

	/**
	 * Returns the timestamp of the answer.
	 * 
	 * @return the timestamp of the answer.
	 */

	public Timestamp getTimestamp()
	{
		return timestamp;
	}

	public int getParentID()
	{
		return parentID;
	}

	public int getQuestionID() {
		return questionID;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException
	{

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("answer");

		jg.writeStartObject();

		if(ID!=-1) jg.writeNumberField("ID",ID);
		jg.writeStringField("text", text);
		jg.writeBooleanField("fixed", fixed);
		jg.writeStringField("timestamp", timestamp.toString());
		jg.writeStringField("IDUser", IDUser);
		jg.writeNumberField("parentID", parentID);
		jg.writeNumberField("questionID", questionID);


		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}

	/**
	 * Creates a {@code Answer} from its JSON representation.
	 *
	 * @param in the input stream containing the JSON document.
	 *
	 * @return the {@code Answer} created from the JSON representation.
	 *
	 * @throws IOException if something goes wrong while parsing.
	 *
	 * @throws ParseException if date is not parsed correctly
	 */
	public static Answer fromJSON(final InputStream in) throws IOException, ParseException
	{

		// the fields read from JSON
		int jID = -1;
		String jText = null;
		boolean jFixed = false;
		String jTimestamp = null;
		String jIDUser = null;
		int jParentID = -1;
		int jQuestionID=-1;
		

		final JsonParser jp = JSON_FACTORY.createParser(in);

		// I'm looking for the answer field
		//i'll keep looping until i find a token which is a field name or until i find my resource token
		while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "answer".equals(jp.getCurrentName()) == false) {

			// there are no more events
			if (jp.nextToken() == null) {
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
					case "text":
						jp.nextToken();
						jText = jp.getText();
						break;
					case "fixed":
						jp.nextToken();
						jFixed = jp.getBooleanValue();
						break;
					case "timestamp":
						jp.nextToken();
						jTimestamp = jp.getText();
						break;
					case "IDUser":
						jp.nextToken();
						jIDUser= jp.getText();
						break;
					case "parentID":
						jp.nextToken();
						jParentID = jp.getIntValue();
						break;
					case "questionID":
						jp.nextToken();
						jQuestionID = jp.getIntValue();
						break;

				}
			}
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Date parsedDate = dateFormat.parse(jTimestamp);
    	Timestamp databaseTimestamp = new java.sql.Timestamp(parsedDate.getTime());

		return new Answer(jID, jIDUser, jFixed, jText, jParentID ,databaseTimestamp,jQuestionID);
	}
}