package org.catrobat.html5player.client.formulaeditor;

public class CompassSensor extends Sensor {

	public CompassSensor(SensorNotifyingInterface controller)
	{
		super(controller,-1);
	}
	
	@Override
	protected void InitAlgorithms()
	{
		_execution.put(0, new CompassSimulationAlgorithm());
	}
	

}
