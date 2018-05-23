package project.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a list of {@link Resource} objects.
 *
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 *
 * @param <T>
 *            the type of the actual class extending {@code Resource}.
 */
public final class ResourceList<T extends Resource> extends Resource {

    /**
     * The list of {@code Resource}s.
     */
    private final Iterable<T> list;

    /**
     * Creates a list of {@code Resource}s.
     *
     * @param list the list of {@code Resource}s.
     */
    public ResourceList(final Iterable<T> list) {
        this.list = list;
    }

    @Override
    public final void toJSON(final OutputStream out) throws IOException {

        final JsonGenerator jg = JSON_FACTORY.createGenerator(out);

        jg.writeStartObject();

        jg.writeFieldName("resource-list");

        jg.writeStartArray();

        jg.flush();

        boolean firstElement = true;

        for (final Resource r : list) {

            // very bad work-around to add commas between resources
            if (firstElement) {
                r.toJSON(out);
                jg.flush();

                firstElement = false;
            } else {
                jg.writeRaw(',');
                jg.flush();

                r.toJSON(out);
                jg.flush();
            }
        }

        jg.writeEndArray();

        jg.writeEndObject();

        jg.flush();

        jg.close();
    }

    /*public static ResourceList<Resource> fromJSON(final InputStream in) throws ParseException, IOException{

        JsonParser jp = JSON_FACTORY.createParser(in);

        while (jp.getCurrentToken() != JsonToken.FIELD_NAME || "resource-list".equals(jp.getCurrentName()) == false) {
            // there are no more events
            if (jp.nextToken() == null) {
                throw new IOException("Unable to parse JSON: no resource-list object found.");
            }
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            if (jp.getCurrentToken() == JsonToken.FIELD_NAME) {
                switch (jp.getCurrentName()) {
                    case "badge":
                        jp.nextToken();
                        jBadge = jp.getIntValue();
                        break;
                    case "surname":
                        jp.nextToken();
                        jSurname = jp.getText();
                        break;
                    case "age":
                        jp.nextToken();
                        jAge = jp.getIntValue();
                        break;
                    case "salary":
                        jp.nextToken();
                        jSalary = jp.getIntValue();
                        break;
                }
            }
        }


    }*/

}