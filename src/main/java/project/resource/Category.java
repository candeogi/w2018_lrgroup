package project.resource;

import com.fasterxml.jackson.core.*;
import java.io.*;
import java.text.ParseException;


/**
 * Represents data about a category.
 *
 * @author Luca Rossi & Davide Storato
 */

public class Category extends Resource
{

    private final String name;
    private final String description;
    private final boolean isCompany;

    /**
     * Creates a new Category
     * @param name category name
     * @param description a brief description of the category
     * @param isCompany tag if the category is for a company
     */

    public Category(final String name,  final String description,final boolean isCompany)
    {
        this.name = name;
        this.description = description;
        this.isCompany = isCompany;
    }

    /**
     * Creates a new category which is going to be inserted into the database
     * @param name category name
     * @param description a brief description of the category
     */

    public Category(final String name,  final String description)
    {
        this(name, description, false);
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompany() {
        return isCompany;
    }

    public String getName() {

        return name;
    }

    /**
     *
     *
     * @param out the stream to which the JSON representation of the {@code Resource} has to be written.
     *
     * @throws IOException if something goes wrong while parsing.
     */
    @Override
    public final void toJSON(final OutputStream out) throws IOException
    {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("answer");

        jg.writeStartObject();

        if(name!=null) jg.writeStringField("name", name);
        jg.writeStringField("description", description);
        jg.writeBooleanField("isCompany", isCompany);


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
    public static Category fromJSON(final InputStream in) throws IOException, ParseException
    {

        // the fields read from JSON
        String jName = null;
        String jDescription = null;
        boolean jIsCompany = false;


        final JsonParser jp = JSON_FACTORY.createParser(in);

        // I'm looking for the answer field
        //i'll keep looping until i find a token which is a field name or until i find my resource token
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "category".equals(jp.getCurrentName()) == false) {

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
                    case "name":
                        jp.nextToken();
                        jName = jp.getText();
                        break;
                    case "text":
                        jp.nextToken();
                        jDescription = jp.getText();
                        break;
                    case "fixed":
                        jp.nextToken();
                        jIsCompany = jp.getBooleanValue();
                        break;

                }
            }
        }

        return new Category(jName, jDescription, jIsCompany);
    }
}