package project.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.*;

/**
 * Represents data about a vote
 *
 * @author Alberto Pontini
 */
public class Votes extends Resource
{
    private final int votes;
    private final int idVoted;
    private final String type;

    public Votes(String type, int votes, int idVoted)
    {
        this.type = type;
        this.votes = votes;
        this.idVoted = idVoted;
    }

    public String getType() {
        return type;
    }

    public int getVotes() {
        return votes;
    }

    public int getIDVoted()
    {
        return idVoted;
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

        jg.writeFieldName("votes");

        jg.writeStartObject();

        jg.writeStringField("type", type);
        jg.writeNumberField("votes",votes);
        jg.writeNumberField("idVoted",idVoted);

        jg.writeEndObject();

        jg.writeEndObject();

        jg.flush();
    }
}
