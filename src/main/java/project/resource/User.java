package project.resource;


import com.fasterxml.jackson.core.JsonGenerator;
import java.io.*;
import java.util.Date;
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
    private final String email;
    private final String name;
    private final String surname;
    private final String username;
    private final String photoProfile;
    private final String password;
    private final Date birthday;
    private final Date registrationDate;
    private final String description;

    /**
     * Creates a new user
     * @param email
     *              the email of the user
     * @param name
     *              the name of the user
     * @param surname
     *              the surname of the user
     * @param username
     *              the username of the user
     * @param photoProfile
     *              the directory of the user's profile picture
     * @param password
     *              the password of the user
     * @param registrationDate
     *              the registrationDate of the user
     * @param birthday
     *              the birthday of the user
     * @param description
     *              the descriptiom of the user
     */
    public User(final String email, final String name, final String surname, final String username,
                final String photoProfile, final String password, final Date registrationDate,
                final Date birthday, final String description){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.photoProfile = photoProfile;
        this.password = password;
        this.registrationDate = registrationDate;
        this.birthday = birthday;
        this.description = description;
    }


    /**
     * Creates a new user: name, surname, photoProfile, birthday and description are set to null
     * @param email
     *              the email of the user
     * @param username
     *              the username of the user
     * @param password
     *              the password of the user
     * @param registrationDate
     *              the registration date of the user
     */
    public User(final String email, final String username, final String password, final Date registrationDate){

        this(email, null, null, username, null, password, registrationDate,
                null, null);
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
     * Returns the username of the user.
     *
     * @return the username of the user.
     */
    public final String getUsername()
    {
        return username;
    }

    /**
     * Returns the user's profile pic path.
     *
     * @return the user's profile pic path.
     */
    public final String getPhotoProfile()
    {
        return photoProfile;
    }

    /**
     * Return the user's password.
     *
     * @return the user's password.
     */
    public final String getPassword(){
        return password;
    }
    /**
     * Returns the registration date of the user.
     *
     * @return the registration date of the user.
     */
    public final Date getRegistrationDate()
    {
        return registrationDate;
    }

    /**
     * Returns the user's birthday date.
     *
     * @return the user's birthday date.
     */
    public final Date getBirthday(){
        return birthday;
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
