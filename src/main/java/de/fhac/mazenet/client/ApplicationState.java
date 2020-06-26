package de.fhac.mazenet.client;

import de.fhac.mazenet.server.generated.BoardData;

/**
 * Created by Lukas Szimtenings on 6/26/2020.
 */
public class ApplicationState
{
    private int id;

    public BoardData boardData;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
}
