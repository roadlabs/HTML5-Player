/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2014 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client;

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
			if (sprite.getLook() == null)
				continue;
			Integer layer = null;
			if (sprite.isBackground()) {
				layer = Integer.MIN_VALUE;
			} else {
				layer = new Integer(sprite.getLook().getZPosition());
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
				CatrobatDebug.debug("zPos of sprite: " + sprite.getName() + ": " + sprite.getLook().getZPosition());
			}
		}
		
		//nur zum testen
//		Scene.get().drawAxis();
		//
		
//		Scene.get().update();
	}

	public void handleScreenClick(int relativeX, int relativeY) {
		
		CatrobatDebug.debug("handleScreenClick(" + relativeX + ", " + relativeY + ")");
		
		
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
		
		if (!touchedSprites.isEmpty()) {
			
			for (Sprite sprite : touchedSprites) {		
				
				//sprite is background, but there got more sprites, than the
				//background touched, do not start the WhenScripts of the 
				//background
				if(sprite.isBackground() && numberOfTouchedSprites > 1) {
					CatrobatDebug.warn("Background got touched but another sprite too...");
					continue;
				}
				else {
					CatrobatDebug.debug("start WhenScripts of sprite: " + sprite.getName());
					sprite.startTapScripts();
				}
			}
		}
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
			CatrobatDebug.debug(sprite.getLook().debug());
		}
	}

}
