/**
 * Catroid: An on-device visual programming system for Android devices Copyright (C) 2010-2013 The
 * Catrobat Team (<http://developer.catrobat.org/credits>)
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * An additional term exception under section 7 of the GNU Affero General Public License, version 3,
 * is available at http://developer.catrobat.org/license_additional_term
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client.bricks;

import org.catrobat.html5player.client.Sprite;
import org.catrobat.html5player.client.Stage;
import org.catrobat.html5player.client.common.Look;
import org.catrobat.html5player.client.formulaeditor.Formula;

public class PlaceAtBrick extends Brick {
  
  public Formula xPosition;
  public Formula yPosition;

  public PlaceAtBrick(String sprite, Formula x, Formula y) {
    super(sprite);
    xPosition = x;
    yPosition = y;
  }

  @Override
  public boolean execute(Sprite sprite) {
    int x = xPosition.interpretInteger(sprite);
    int y = yPosition.interpretInteger(sprite);
    Look look = sprite.getLook();
    look.setXPosition(x + Stage.getInstance().getStageMiddleX());
    look.setYPosition(-y + Stage.getInstance().getStageMiddleY());
    look.setMiddleX(x);
    look.setMiddleY(-y);
    
    return true;
  }

}
