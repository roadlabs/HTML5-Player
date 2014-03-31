package org.catrobat.html5player.client.formulaeditor;

import com.google.gwt.junit.client.GWTTestCase;

public class CompassAlgortihmTest  extends GWTTestCase {
	
	private CompassSimulationAlgorithm _algo;
	
	
	public void testAlgoResult()
	{
		_algo = new CompassSimulationAlgorithm();
		float result = 0.0f;
		for(int i = 0; i < 100; i++)
		{
			result = (Float)_algo.GetResult();
			
		}
		
		assertEquals(100, result);
	
	}


	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.client.formulaeditor";
	}
	
	

}
