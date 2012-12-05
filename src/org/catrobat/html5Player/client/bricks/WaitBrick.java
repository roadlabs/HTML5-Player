package org.catrobat.html5Player.client.bricks;

import org.catrobat.html5Player.client.Sprite;
import org.catrobat.html5Player.client.scripts.Script;

public class WaitBrick extends Brick {

	private int time;

	// TODO: better solution for this easy quick hack
	private Script script;

	public WaitBrick(String sprite, int time, Script script) {
		super(sprite);
		this.time = time;
		this.script = script;
	}

	public boolean execute(Sprite sprite) {
		script.pause(time);
		return true;
	}
}
