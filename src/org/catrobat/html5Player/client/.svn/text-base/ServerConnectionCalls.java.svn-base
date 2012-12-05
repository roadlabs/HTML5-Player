package org.catrobat.html5Player.client;

import org.catrobat.html5Player.client.threading.CatScheduler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class ServerConnectionCalls {
	// TODO refactor this ServerConnectionCalls shouldn't have to hold all this
	// members
	private String currentXML;
	
//	
//	private SpriteManager manager;
//	private Parser parser;

	private ServerConnectionServiceAsync getXMLSvc;

//	public ServerConnectionCalls(Parser newParser, SpriteManager newManager) {
//		getXMLSvc = GWT.create(ServerConnectionService.class);
//		parser = newParser;
//		manager = newManager;
//		currentXML = "";
//	}

	public ServerConnectionCalls() {
		getXMLSvc = GWT.create(ServerConnectionService.class);
		currentXML = "";
	}

	public void getXML(String name, String number) {
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
		getXMLSvc.getXML(name, number, callback);
	}

	// For Testing
	public String getCurrentXML() {
		return currentXML;
	}
}
