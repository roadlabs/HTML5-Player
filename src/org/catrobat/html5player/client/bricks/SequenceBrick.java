package org.catrobat.html5player.client.bricks;

import java.util.ArrayList;
import java.util.List;

import org.catrobat.html5player.client.Sprite;

public class SequenceBrick extends Brick {
  
  List<Brick> brickList;

  public List<Brick> getBrickList() {
    return brickList;
  }

  public void setBrickList(List<Brick> brickList) {
    this.brickList = brickList;
  }
  public SequenceBrick(String spriteName, List<Brick> brickList) {
    super(spriteName);
    this.brickList = brickList;
  }
  public SequenceBrick(String spriteName) {
    super(spriteName);
    this.brickList = new ArrayList<Brick>();
  }

  @Override
  protected boolean execute(Sprite sprite) {
    for(Brick b: this.brickList){
      if(!b.execute(sprite)){
        return false;
      }
    }
    return true;
  }

}
