package project.resource;

import com.fasterxml.jackson.core.*;

import java.io.*;
import java.text.ParseException;


/**
 * Represents data about a category.
 *
 * @author Luca Rossi
 * @author Davide Storato
 */

public class WebSite extends Resource {

    private final String address;
    private final String type;


    /**
     * @param address the address of the website
     * @param type the type of the website (ownsite, bitbucket, github or linkedin)
     */

    public WebSite(final String address, final String type) {
        this.address = address;
        this.type = type;
    }

    /**
     * @return the related address
     */
    public String getAddress() {
        return address;
    }


    /**
     * @return the related type
     */
    public String getType() {
        return type;
    }

    /**
     * @param out the stream to which the JSON representation of the {@code Resource} has to be written.
     * @throws IOException if something goes wrong while parsing.
     */
    @Override
    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("website");

        jg.writeStartObject();

        if (address != null) jg.writeStringField("address", address);
        jg.writeStringField("type", type);


        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Creates a {@code WebSite} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     * @return the {@code WebSite} created from the JSON representation.
     * @throws IOException    if something goes wrong while parsing.
     * @throws ParseException if date is not parsed correctly
     */
    public static WebSite fromJSON(final InputStream in) throws IOException, ParseException {

        // the fields read from JSON
        String jAddress = null;
        String jType = null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // I'm looking for the answer field
        //i'll keep looping until i find a token which is a field name or until i find my resource token
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"WebSite".equals(jp.getCurrentName())) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no employee object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                switch (jp.getCurrentName()) {
                    case "address":
                        jp.nextToken();
                        jAddress = jp.getText();
                        break;
                    case "type":
                        jp.nextToken();
                        jType = jp.getText();
                        break;


                }
            }
        }

        return new WebSite(jAddress, jType);
    }
}