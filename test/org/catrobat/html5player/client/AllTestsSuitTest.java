/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.html5player.client;

import org.catrobat.html5player.client.bricks.*;
import org.catrobat.html5player.client.scripts.*;
import org.catrobat.html5player.client.threading.*;

import junit.framework.Test;
import junit.framework.TestSuite;


import com.google.gwt.junit.tools.GWTTestSuite;

public class AllTestsSuitTest extends GWTTestSuite{
	public static Test suite() {
	    TestSuite suite = new TestSuite("Test all TestCases");
	    suite.addTestSuite(ChangeVolumeByBrickTest.class);
	    suite.addTestSuite(ChangeXByBrickTest.class);
	    suite.addTestSuite(ChangeYByBrickTest.class);
	    suite.addTestSuite(ComeToFrontBrickTest.class);
	    suite.addTestSuite(GoNStepsBackBrickTest.class);
	    suite.addTestSuite(HideBrickTest.class);
	    suite.addTestSuite(MoveNStepsBrickTest.class);
	    suite.addTestSuite(PlaceAtBrickTest.class);
	    suite.addTestSuite(PointInDirectionBrickTest.class);
	    suite.addTestSuite(SetSizeToBrickTest.class);
	    suite.addTestSuite(SetVolumeToBrickTest.class);
	    suite.addTestSuite(SetXBrickTest.class);
	    suite.addTestSuite(SetYBrickTest.class);
	    suite.addTestSuite(ShowBrickTest.class);
	    suite.addTestSuite(TurnLeftBrickTest.class);
	    suite.addTestSuite(TurnRightBrickTest.class);
	    suite.addTestSuite(SetVariableBrickTest.class);
	    suite.addTestSuite(ChangeSizeByNBrickTest.class);

	    suite.addTestSuite(RepeatBrickTest.class);
	    suite.addTestSuite(ForeverBrickTest.class);
	    suite.addTestSuite(LoopEndBrickTest.class);
	    suite.addTestSuite(SetLookBrickTest.class);
	    suite.addTestSuite(NextLookBrickTest.class);
	    suite.addTestSuite(BroadcastBrickTest.class);
	    suite.addTestSuite(BroadcastWaitBrickTest.class);
	    suite.addTestSuite(WaitBrickTest.class);
	    suite.addTestSuite(SpeakBrickTest.class);

	    suite.addTestSuite(NoteBrickTest.class);
	    suite.addTestSuite(PointToBrickTest.class);
	    suite.addTestSuite(ClearGraphicEffectBrickTest.class);

	    suite.addTestSuite(SetGhostEffectBrickTest.class);
	    suite.addTestSuite(ChangeGhostEffectByBrickTest.class);

	    suite.addTestSuite(SetBrightnessBrickTest.class);
	    suite.addTestSuite(ChangeBrightnessBrickTest.class);

	    suite.addTestSuite(IfOnEdgeBounceBrickTest.class);

	    suite.addTestSuite(ParserTest.class);
	    suite.addTestSuite(SpriteTest.class);
	    suite.addTestSuite(MessageContainerTest.class);
	    suite.addTestSuite(BroadcastScriptTest.class);
	    suite.addTestSuite(StartScriptTest.class);
	    suite.addTestSuite(WhenScriptTest.class);
	    suite.addTestSuite(StageTest.class);
	    suite.addTestSuite(SpriteManagerTest.class);

	    suite.addTestSuite(CatThreadTest.class);
	    suite.addTestSuite(CatSchedulerTest.class);
	    suite.addTestSuite(WaitCountTest.class);

	    suite.addTestSuite(ImageHandlerTest.class);
	    suite.addTestSuite(SceneTest.class);

	    suite.addTestSuite(InvalidProjectIdTest.class);
	    suite.addTestSuite(RotationTest.class);
	    suite.addTestSuite(UnsupportedVersionTest.class);

	    suite.addTestSuite(ConstTest.class);

	    return suite;
	  }

}
