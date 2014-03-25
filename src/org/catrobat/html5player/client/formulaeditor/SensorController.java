package org.catrobat.html5player.client.formulaeditor;


import java.util.HashMap;




public class SensorController implements SensorNotifyingInterface {

	private HashMap<Sensors, Sensor>  _sensors;
	private static SensorController _instance;
	
	private SensorController(){
		Init();
		
	}
	
	protected void Init() {
		_sensors = new HashMap<Sensors, Sensor>();
	}
	public static SensorController GetController(){
		if (_instance == null) {
			_instance = new SensorController();
		}
		
		return _instance;
	}
	
	
	public Sensor GetSensor(Sensors sensor){
		
		if (!_sensors.containsKey(sensor)) {
			GenerateSensor(sensor);
		}
		
			return _sensors.get(sensor);

	}
	
	public void ActivateSensor(Sensors sensor)
	{
		if (!_sensors.containsKey(sensor)) {
			GenerateSensor(sensor);
		}
	}
	
	private void GenerateSensor(Sensors sensor)
	{
		Sensor concrete = null;
		switch (sensor) {		
		case LOOK_ROTATION:
			concrete = new OrientationSensor(this);
			break;
		default:
			break;
		}
		
		_sensors.put(sensor, concrete);
	}

	@Override
	public void Notify(Sensor sensor) {
		Object result = sensor.GetResultObject();
		if(sensor instanceof OrientationSensor)
			org.catrobat.html5player.client.Html5Player.HandleRotation(result);
	}
	
}
