package org.catrobat.html5player.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RotationTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.catrobat.html5player.html5player";
	}

	
	
	public void testRotateRight(){
		VerticalPanel panel = new VerticalPanel();
		panel.setSize("200px", "400px");
		Html5Player.rotateRight(panel);
		assertEquals(90, Html5Player.getRotatonAngle());
		assertEquals("rotate(90deg) translate(-100px,-100px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateRight(panel);
		assertEquals(180, Html5Player.getRotatonAngle());
		assertEquals("rotate(180deg) translate(0px,0px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateRight(panel);
		assertEquals(270, Html5Player.getRotatonAngle());
		assertEquals("rotate(270deg) translate(100px,100px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateRight(panel);
		assertEquals("rotate(0deg) translate(0px,0px)", panel.getElement().getStyle().getProperty("transform"));
		assertEquals(0, Html5Player.getRotatonAngle());
	}
	
	public void testRotateLeft(){
		VerticalPanel panel = new VerticalPanel();
		panel.setSize("200px", "400px");
		Html5Player.rotateLeft(panel);
		assertEquals(-90, Html5Player.getRotatonAngle());
		assertEquals("rotate(-90deg) translate(100px,100px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateLeft(panel);
		assertEquals(-180, Html5Player.getRotatonAngle());
		assertEquals("rotate(-180deg) translate(0px,0px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateLeft(panel);
		assertEquals(-270, Html5Player.getRotatonAngle());
		assertEquals("rotate(-270deg) translate(-100px,-100px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateLeft(panel);
		assertEquals("rotate(0deg) translate(0px,0px)", panel.getElement().getStyle().getProperty("transform"));
		assertEquals(0, Html5Player.getRotatonAngle());
	}
	
	public void testRotateLeftandRight(){
		VerticalPanel panel = new VerticalPanel();
		panel.setSize("200px", "400px");
		Html5Player.rotateLeft(panel);
		assertEquals(-90, Html5Player.getRotatonAngle());
		assertEquals("rotate(-90deg) translate(100px,100px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateRight(panel);
		assertEquals(0, Html5Player.getRotatonAngle());
		assertEquals("rotate(0deg) translate(0px,0px)", panel.getElement().getStyle().getProperty("transform"));	
	}
	
	public void testRotateRightandLeft(){
		VerticalPanel panel = new VerticalPanel();
		panel.setSize("200px", "400px");
		Html5Player.rotateRight(panel);
		assertEquals(90, Html5Player.getRotatonAngle());
		assertEquals("rotate(90deg) translate(-100px,-100px)", panel.getElement().getStyle().getProperty("transform"));
		Html5Player.rotateLeft(panel);
		assertEquals(0, Html5Player.getRotatonAngle());
		assertEquals("rotate(0deg) translate(0px,0px)", panel.getElement().getStyle().getProperty("transform"));
	}

}
