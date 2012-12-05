package org.catrobat.html5Player.client;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SpriteManager {

	private Set<Sprite> sprites = new LinkedHashSet<Sprite>();

	SpriteManager() {
	}

	public void addSprite(Sprite sprite) {
		if (sprite != null && !sprites.contains(sprite)) {
			sprites.add(sprite);
		}
	}

	public Sprite getSprite(String name, boolean create) {
		if (name == null)
			return null;
		Sprite sprite = null;
		for (Sprite s : sprites) {
			if (s.getName().equals(name)) {
				sprite = s;
				break;
			}
		}
		if (create && sprite == null) {
			sprite = new Sprite(name);
			sprites.add(sprite);
		}
		return sprite;
	}

	public Collection<Sprite> getSpriteList() {
		return Collections.unmodifiableCollection(sprites);
	}

	public void playCatroid() {
		for (Sprite sprite : sprites) {
			sprite.run();
		}
	}

	public void redrawScreen() {
		
		Scene.get().clearCanvas();

		Map<Integer, Set<Sprite>> tmp = new TreeMap<Integer, Set<Sprite>>();
		for (Sprite sprite : sprites) {
			if (sprite.getCostume() == null)
				continue;
			Integer layer = null;
			if (sprite.isBackground()) {
				layer = Integer.MIN_VALUE;
			} else {
				layer = new Integer(sprite.getCostume().getZPosition());
			}
		
			Set<Sprite> spriteList = tmp.get(layer);
			if (spriteList == null) {
				spriteList = new LinkedHashSet<Sprite>();
				tmp.put(layer, spriteList);
			}
			spriteList.add(sprite);
		}
		for (Set<Sprite> layeredSprites : tmp.values()) {
			for (Sprite sprite : layeredSprites) {
				sprite.drawSprite();
				
//				CatrobatDebug.on();
				CatrobatDebug.console("zPos of sprite: " + sprite.getName() + ": " + sprite.getCostume().getZPosition());
				CatrobatDebug.off();
			}
		}
		
		//nur zum testen
//		Scene.get().drawAxis();
		//
		
//		Scene.get().update();
	}

	public void handleScreenClick(int relativeX, int relativeY) {
		
		CatrobatDebug.on();
		CatrobatDebug.console("handleScreenClick(" + relativeX + ", " + relativeY + ")");
		
		
//		TreeMap<Integer, Set<Sprite>> tmp = new TreeMap<Integer, Set<Sprite>>();
//		
//		int highestZPosition = Integer.MIN_VALUE;
//		
//		for (Sprite sprite : sprites) {
//			if (sprite.processOnTouch(relativeX, relativeY)) {
//				
//				Integer layer = null;
//				if (sprite.isBackground()) {
//					layer = Integer.MIN_VALUE;
//				} else {
//					layer = new Integer(sprite.getCostume().getZPosition());
//				}
//				
//				CatrobatDebug.on();
//				CatrobatDebug.console("layer " + layer);
//				
//				if(highestZPosition < layer) {
//					highestZPosition = layer;
//				}
//			
//				Set<Sprite> spriteList = tmp.get(layer);
//				if (spriteList == null) {
//					spriteList = new LinkedHashSet<Sprite>();
//					tmp.put(layer, spriteList);
//				}
//				
//				spriteList.add(sprite);
//			}
//		}
//		
//		for(Sprite spriteTouched : tmp.get(highestZPosition)) {
//			CatrobatDebug.console("start WhenScripts of sprite: " + spriteTouched.getName());
//			CatrobatDebug.off();
//			spriteTouched.startTapScripts();
//		}

		//-------------
		
		
		
		Set<Sprite> touchedSprites = new LinkedHashSet<Sprite>();
		for (Sprite sprite : sprites) {
			if (sprite.processOnTouch(relativeX, relativeY)) {
				touchedSprites.add(sprite);
			}
		}
		
		int numberOfTouchedSprites = touchedSprites.size();
		
		CatrobatDebug.on();
		
		if (!touchedSprites.isEmpty()) {
			
			for (Sprite sprite : touchedSprites) {		
				
				//sprite is background, but there got more sprites, than the
				//background touched, do not start the WhenScripts of the 
				//background
				if(sprite.isBackground() && numberOfTouchedSprites > 1) {
					
					CatrobatDebug.console("Background got touched but another sprite too...");
					
					continue;
				}
				else {
					
					CatrobatDebug.console("start WhenScripts of sprite: " + sprite.getName());
					
					sprite.startTapScripts();
				}
				
			}
		}
		
		CatrobatDebug.off();
		

	}

	/**
	 * stops all sounds and dumps the sprites
	 */
	public void clearSprites() {
		if(sprites.size() > 0) {
			this.stopAllSounds();
			this.sprites.clear();
		}
	}
	
	/**
	 * iterates over sprites an calls stopSound()
	 */
	public void stopAllSounds() {
		for (Sprite sprite : sprites) {
			sprite.stopSound();
		}
	}
	
	
	
	
	/**
	 * FOR UNIT-TESTING
	 */
	public void reset() {
		this.sprites.clear();
	}
	
	/**
	 * FOR TESTING
	 */
	public void debugSpriteCostumes() {
		for (Sprite sprite : sprites) {
			CatrobatDebug.on();
			CatrobatDebug.console(sprite.getCostume().debug());
			CatrobatDebug.off();
		}
	}

}
