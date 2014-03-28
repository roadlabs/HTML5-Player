package org.catrobat.html5player.client.formulaeditor;


//360 algorithm step 10 degreees
public class CompassSimulationAlgorithm implements ISimulationAlgortihm {

	private float _direction;
	
	public CompassSimulationAlgorithm()
	{
		_direction = 0.0F;
	}
	
	public void ResetToStart()
	{
		_direction = 0.0F;
	}
	public Object GetResult()
	{
		return _direction += 0.0F;
	}
}
