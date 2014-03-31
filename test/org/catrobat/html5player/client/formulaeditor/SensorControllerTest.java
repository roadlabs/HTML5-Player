package org.catrobat.html5player.client.formulaeditor;

import com.google.gwt.junit.client.GWTTestCase;

public class SensorControllerTest extends GWTTestCase{

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.client.formulaeditor";
	}
	
	public void testSensorControllerObject()
	{
		SensorController sensorctrl = SensorController.GetController();
		assertNotNull(sensorctrl);
	}
	
	public void testGetCompassSensor(){
		
		Sensor sensor = SensorController.GetController().GetSensor(Sensors.COMPASS_DIRECTION);
		
		assertTrue(sensor instanceof CompassSensor);
	}
	

	
}
