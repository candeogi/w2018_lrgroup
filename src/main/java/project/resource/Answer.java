package project.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.*;

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
	private final int answersToID;
	private final String timestamp;

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

	public Answer(final int ID, final boolean fixed, final String text, final int answersToID ,final String timestamp)
	{
		this.ID = ID;
		this.fixed = fixed;
		this.text = text;
		this.answersToID = answersToID;
		this.timestamp = timestamp;
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

	public Answer(final boolean fixed, final String text, final int answersToID, final String timestamp)
	{
		this.ID = -1;
		this.fixed = fixed;
		this.text = text;
		this.answersToID = answersToID;
		this.timestamp = timestamp;
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
	 * Returns the timestamp of the answer.
	 * 
	 * @return the timestamp of the answer.
	 */

	public String getTimestamp()
	{
		return timestamp;
	}

	public int getAnswersToID()
	{
		return answersToID;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("answer");

		jg.writeStartObject();

		if(ID!=-1) jg.writeNumberField("ID",ID);
		jg.writeStringField("text", text);
		jg.writeBooleanField("fixed", fixed);
		jg.writeStringField("timestamp", timestamp);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}
}