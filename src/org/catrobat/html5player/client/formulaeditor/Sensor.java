package org.catrobat.html5player.client.formulaeditor;

import java.util.HashMap;

import com.google.gwt.user.client.Random;

public abstract class Sensor {

	protected HashMap<Integer, ISimulationAlgortihm> _execution;
	private int _executionID;
	protected Object _result;
	protected SensorNotifyingInterface _controller;
	public Sensor(SensorNotifyingInterface controller, int alorithmID){
		_controller = controller;
		_executionID = alorithmID;
		_execution = new HashMap<Integer, ISimulationAlgortihm>();
		InitAlgorithms();
	}
	
	protected abstract void InitAlgorithms();
	
	
	public Object GetResultObject()
	{
		return _execution.get(GetAlgorithmID()).GetResult();
	}
	
	private int GetAlgorithmID()
	{
		if(_executionID >= 0 && _execution.containsKey(_executionID))
			return _executionID;
		else{
			if(_execution.size() == 1)
				return 0;
			else
				return Random.nextInt(_execution.size()-1);
			
		}
			
		
			
	}
	

}
