package org.catrobat.html5player.client.formulaeditor;

public class SensorCustomEvent {
	public final float[] values;
	public Sensors sensor;
	public long timestamp;

	public SensorCustomEvent(Sensors sourceSensor, float[] values) {
		sensor = sourceSensor;
		this.values = new float[values.length];
		System.arraycopy(values, 0, this.values, 0, values.length);
		timestamp = System.currentTimeMillis();
	}
}