package org.catrobat.html5player.client.formulaeditor;


public abstract class Sensor {

	protected Object _result;
	protected SensorNotifyingInterface _controller;
	public Sensor(SensorNotifyingInterface controller){
		_controller = controller;
	}
	public Object GetResultObject()
	{
		return _result;
	}
	

}
