/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
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



import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class ServerConnectionCalls {
	private String currentXML;
	private ServerConnectionServiceAsync getXMLSvc;
	public ServerConnectionCalls() {
		getXMLSvc = GWT.create(ServerConnectionService.class);
		currentXML = "";
	}

	public void getXML(String number) {
		if (getXMLSvc == null) {
			getXMLSvc = GWT.create(ServerConnectionService.class);
		}
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(final Throwable caught) {
				Window.alert("Server Error");
				
//				Window.alert(caught.getMessage());
			}

			public void onSuccess(final String result) {
				
				currentXML = result;
				Stage.getInstance().start(currentXML);
				
//				long start = System.currentTimeMillis();
//				
//				parser.parseXML(manager, currentXML);
//				
//				CatrobatDebug.on();
//				
//				CatrobatDebug.console("XML got parsed");
//				CatrobatDebug.console("Parsing needed " + (System.currentTimeMillis() - start) + "ms");
//				
//				ImageHandler.get().loadImages();
//				
//				CatrobatDebug.console("actual number of attached images: " + ImageHandler.get().getNumberOfAttachedImages());
//				
//				CatrobatDebug.off();
//				
//				 //add StartScripts to scheduler
//				Stage.getInstance().getSpriteManager().playCatroid();
//				
//				Stage.getInstance().getSpriteManager().debugSpriteCostumes();
//				
//				CatrobatDebug.console("actual scripts waiting to run: " + CatScheduler.get().getThreadCount());
//				
//				//start CatScheduler
//				Scheduler.get().scheduleFixedDelay(CatScheduler.get(), CatScheduler.SCHEDULE_DELAY);
				
			}
		};
		getXMLSvc.getXML(number, callback);
	}
	
	
	
	public void getXML() {
		if (getXMLSvc == null) {
			getXMLSvc = GWT.create(ServerConnectionService.class);
		}
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(final Throwable caught) {
				Window.alert("Server Error");
			}

			public void onSuccess(final String result) {
				currentXML = result;
				Stage.getInstance().start(currentXML);
			}
		};
		getXMLSvc.getXML(callback);
	}
	public void getImage(final String name) {
		if (getXMLSvc == null) {
			getXMLSvc = GWT.create(ServerConnectionService.class);
		}
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(final Throwable caught) {
				Window.alert("Server Error");
			}

			public void onSuccess(final String result) {
				System.out.println(result);
				if(result == null)
				{
					Window.alert("Image Error" + name);
				}
				//System.out.println(currentXML);
				Image image = new Image(result); 
				//System.out.println(result);
				ImageHandler.get().newImage(name, image);
			}
		};
		getXMLSvc.getImage(name, callback);
	}
	public void getSound(final String name, final Audio audio) {
		if (getXMLSvc == null) {
			getXMLSvc = GWT.create(ServerConnectionService.class);
		}
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(final Throwable caught) {
				Window.alert("Server Error or URL Problem");
			}

			public void onSuccess(final String result) {
				System.out.println(result);
				if(result == null)
				{
					Window.alert("Sound Error" + name);
				}
				audio.addSource(result);
				audio.load();
			}
		};
		getXMLSvc.getSound(name, callback);
	}
	
	public void getXMLFromProjectFileUrl(String url) {
		if (getXMLSvc == null) {
			getXMLSvc = GWT.create(ServerConnectionService.class);
		}
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(final Throwable caught) {
				Window.alert("Server Error or URL Problem");
			}

			public void onSuccess(final String result) {
				currentXML = result;
				Stage.getInstance().start(currentXML);
			}
		};
		getXMLSvc.getXMLFromProjectFileUrl(url, callback);
	}

	
	

	// For Testing
	public String getCurrentXML() {
		return currentXML;
	}
}
