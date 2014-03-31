package org.catrobat.html5player.client.formulaeditor;

import com.google.gwt.junit.client.GWTTestCase;

public class CompassSensorTest extends GWTTestCase{

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.client.formulaeditor";
	}
	
	public void testGetSensorObject()
	{
		CompassSensor sensor = (CompassSensor)SensorController.GetController().GetSensor(Sensors.COMPASS_DIRECTION);
		assertNotNull(sensor);
	}
	
	public void testGetResultByAlgorithm0()
	{
		CompassSensor sensor = new CompassSensor(SensorController.GetController(),0);
		float result= 0.0f;
		for(int i = 0; i < 100; i++ ){
			result += (Float)sensor.GetResultObject();
		}
		
		assertEquals(100, result);
	}
}
