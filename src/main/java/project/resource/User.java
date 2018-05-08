package project.resource;


import com.fasterxml.jackson.core.JsonGenerator;
import java.io.*;
/**
 * Represents the data about an user.
 * 
 * @author Alberto Pontini
 * @version 1.00
 * @since 1.00
 */
public class User extends Resource
{
	/**
	 * The Username of the user
	 */
	private final String username;
	private final String description;
	private final String name;
	private final String surname;
	private final String email;
	private final String registrationDate;
	private final String profilePic;

	/**
	 * Creates a new employee
	 * 
	 * @param username
	 *            the username of the user
	 * @param description
	 *            the descriptiom of the user
	 * @param name
	 *            the name of the user
	 * @param surname
	 *            the surname of the user
	 * @param email
	 *            the email of the user
	 * @param registrationDate
	 *            the registrationDate of the user
	 * @param profilePic
	 *            the directory of the user's profile picture
	 */
	public User(final String username, final String description, final String name, final String surname, final String email, final String registrationDate, final String profilePic)
	{
		this.username = username;
		this.description = description;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.registrationDate = registrationDate;
		this.profilePic = profilePic;
	}

	/**
	 * Returns the username of the user.
	 * 
	 * @return the username of the user.
	 */
	public final String getUsername()
	{
		return username;
	}

	/**
	 * Returns the description of the user.
	 * 
	 * @return the description of the user.
	 */
	public final String getDescription()
	{
		return description;
	}

	/**
	 * Returns the name of the user.
	 * 
	 * @return the name of the user.
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * Returns the surname of the user.
	 * 
	 * @return the surname of the user.
	 */
	public final String getSurname()
	{
		return surname;
	}

	/**
	 * Returns the email of the user.
	 * 
	 * @return the email of the user.
	 */
	public final String getEmail()
	{
		return email;
	}

	/**
	 * Returns the registration date of the user.
	 * 
	 * @return the registration date of the user.
	 */
	public final String getRegistrationDate()
	{
		return registrationDate;
	}
	/**
	 * Returns the user's profile pic path.
	 * 
	 * @return the user's profile pic path.
	 */
	public final String getProfilePic()
	{
		return profilePic;
	}

	@Override
	public final void toJSON(final OutputStream out) throws IOException {

		final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

		jg.writeStartObject();

		jg.writeFieldName("user");

		jg.writeStartObject();

		jg.writeStringField("username", username);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}
}
