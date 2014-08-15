package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Scene;
import org.catrobat.html5player.client.Sprite;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;


public class SpeakBrick extends Brick {

	private String text;

	public SpeakBrick(String sprite, String text) {
		super(sprite);
		setText(text);

	}

	@Override
	public boolean execute(Sprite sprite) {

		final PopupPanel messageLayer = new PopupPanel();
		messageLayer.setWidth(Scene.get().getCanvas().getOffsetWidth()-Scene.get().getCanvas().getAbsoluteLeft()+"px");
		messageLayer.setHeight(Scene.get().getCanvas().getOffsetHeight()/10+"px");
		messageLayer.getElement().setId("messageLayer");
		messageLayer.setWidget(new Label(this.getText()));
		messageLayer.setPopupPosition(Scene.get().getCanvas().getAbsoluteLeft(),Scene.get().getCanvas().getOffsetHeight());
		messageLayer.show();

		Timer t = new Timer() {
			  public void run() {
				  messageLayer.hide();
			  }
		};

		t.schedule(3000);

		//add TTS later

		return true;
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

}
