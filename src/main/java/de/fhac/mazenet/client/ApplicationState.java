package de.fhac.mazenet.client;

import de.fhac.mazenet.server.generated.PositionData;

/**
 * Created by Lukas Szimtenings on 6/26/2020.
 */
public class ApplicationState
{
    private int id;
    private PositionData lastshiftpos;
    
    public PositionData getLastshiftpos() {
		return lastshiftpos;
	}

	public void setLastshiftpos(PositionData lastshiftpos) {
		this.lastshiftpos = lastshiftpos;
	}

	public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
}
