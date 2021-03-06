package project.resource;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the data about an user.
 *
 * @author lrgroup
 * @author Alberto Pontini (alberto.pontini@studenti.unipd.it)
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
    private final boolean isAdmin;
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
     * @param isAdmin
     *              specifies if the user has administrative rights
     * @param registrationDate
     *              the registrationDate of the user
     * @param birthday
     *              the birthday of the user
     * @param description
     *              the descriptiom of the user
     */
    public User(final String email, final String name, final String surname, final String username,
                final String photoProfile, final String password, final boolean isAdmin ,final Date registrationDate,
                final Date birthday, final String description)
    {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.photoProfile = photoProfile;
        this.password = password;
        this.isAdmin = isAdmin;
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
    public User(final String email,final String username, final String password, final Date registrationDate)
    {

        this(email, "", "", username, null, password, false, registrationDate,
                null, "");
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
     * Return the user's password.
     *
     * @return the user's password.
     */
    public final boolean isAdmin(){
        return isAdmin;
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

		jg.writeFieldName("user");

		jg.writeStartObject();

		jg.writeStringField("email", email);
        jg.writeStringField("name",name);
        jg.writeStringField("surname",surname);
        jg.writeStringField("username",username);
        jg.writeStringField("photoProfile",photoProfile);
        jg.writeBooleanField("isAdmin",isAdmin);
        jg.writeStringField("registrationDate",registrationDate.toString());
        jg.writeStringField("birthday",birthday.toString());
        jg.writeStringField("description",description);

		jg.writeEndObject();

		jg.writeEndObject();

		jg.flush();
	}

    /**
     * Creates a {@code User} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     *
     * @return the {@code Answer} created from the JSON representation.
     *
     * @throws IOException if something goes wrong while parsing.
     *
     * @throws ParseException if date is not parsed correctly
     */
    public static User fromJSON(final InputStream in) throws IOException, ParseException
    {

        // the fields read from JSON
        String jEmail = null;
        String jName = null;
        String jSurname = null;
        String jUsername = null;
        String jPhotoProfile = null;
        boolean jIsAdmin = false;
        String jRegDate = null;
        String jBirthday = null;
        String jDescription = null;
        //String jCompany = null;


        final JsonParser jp = JSON_FACTORY.createParser(in);

        // I'm looking for the user field
        //i'll keep looping until i find a token which is a field name or until i find my resource token
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "user".equals(jp.getCurrentName()) == false) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no user object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT)
        {
            if (jp.getCurrentToken() == JsonToken.FIELD_NAME)
            {
                switch (jp.getCurrentName())
                {
                    case "email":
                        jp.nextToken();
                        jEmail = jp.getText();
                        break;
                    case "name":
                        jp.nextToken();
                        jName = jp.getText();
                        break;
                    case "surname":
                        jp.nextToken();
                        jSurname = jp.getText();
                        break;
                    case "username":
                        jp.nextToken();
                        jUsername = jp.getText();
                        break;
                    case "photoProfile":
                        jp.nextToken();
                        jPhotoProfile= jp.getText();
                        break;
                    case "isAdmin":
                        jp.nextToken();
                        jIsAdmin = jp.getBooleanValue();
                        break;
                    case "registrationDate":
                        jp.nextToken();
                        jRegDate = jp.getText();
                        break;
                    case "birthday":
                        jp.nextToken();
                        jBirthday = jp.getText();
                        break;
                    case "description":
                        jp.nextToken();
                        jDescription = jp.getText();
                        break;

                }
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        java.util.Date parsedDate = dateFormat.parse(jRegDate);
        Date regDate = new java.sql.Date(parsedDate.getTime());
        parsedDate = dateFormat.parse(jBirthday);
        Date birthday = new java.sql.Date(parsedDate.getTime());

        //TODO Implementare Password
        return new User(jEmail,jName,jSurname,jUsername,jPhotoProfile,"",jIsAdmin,regDate,birthday,jDescription);
    }
}
