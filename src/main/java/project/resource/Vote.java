package project.resource;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * Represents data about a vote
 *
 * @author Alberto Pontini
 */
public class Vote //extends Resource
{
    //FORSE NON UTILIZZATA
    private final String idUser;
    private final int vote;
    private final int idAnswer;

    public Vote(String idUser, int vote, int idAnswer)
    {
        this.idUser = idUser;
        this.vote = vote;
        this.idAnswer = idAnswer;
    }

    public String getIDUser() {
        return idUser;
    }

    public int getVote() {
        return vote;
    }

    public int getIDAnswer()
    {
        return idAnswer;
    }

}
