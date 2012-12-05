package org.catrobat.html5Player.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServerConnectionServiceAsync {

	void getXML(String name, String number, AsyncCallback<String> callback);

}
