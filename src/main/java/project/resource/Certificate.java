package project.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.ParseException;

/**
 * Represents data about a certificate
 *
 * @author Luca Rossi
 * @author Davide Storato
 */
public class Certificate extends Resource {


    private final String name;
    private final String organization;
    private final String dateCert;

    public Certificate(String name, String organization) {

        this.name = name;
        this.organization = organization;
        this.dateCert = null;
    }

    public Certificate(String name, String organization , String date) {

        this.name = name;
        this.organization = organization;
        this.dateCert = date;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return organization;
    }

    public String getDate(){return dateCert;}

    /**
     * @param out the stream to which the JSON representation of the {@code Resource} has to be written.
     * @throws IOException if something goes wrong while parsing.
     */

    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("certificate");

        jg.writeStartObject();



        jg.writeStringField("name", name);
        jg.writeStringField("organization", organization);
        if(dateCert != null){
            jg.writeStringField("achievmentdate ", dateCert );
        }

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }

    /**
     * Creates a {@code Certificate} from its JSON representation.
     *
     * @param in the input stream containing the JSON document.
     * @return the {@code Certificate} created from the JSON representation.
     * @throws IOException    if something goes wrong while parsing.
     * @throws ParseException if date is not parsed correctly
     */
    public static Certificate fromJSON(final InputStream in) throws IOException, ParseException {

        // the fields read from JSON

        String jName = null;
        String jOrganization = null;

        final JsonParser jp = JSON_FACTORY.createParser(in);

        // I'm looking for the certificate field
        //i'll keep looping until i find a token which is a field name or until i find my resource token
        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || !"certificate".equals(jp.getCurrentName())) {

            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no employee object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                switch (jp.getCurrentName()) {

                    case "name":
                        jp.nextToken();
                        jName = jp.getText();
                        break;
                    case "organization":
                        jp.nextToken();
                        jOrganization = jp.getText();
                        break;
                }
            }
        }

        return new Certificate(jName, jOrganization);
    }


}
