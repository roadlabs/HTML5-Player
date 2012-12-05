package org.catrobat.html5Player.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.catrobat.html5Player.client.Const;
import org.catrobat.html5Player.client.ServerConnectionService;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServerConnectionServiceImpl extends RemoteServiceServlet implements ServerConnectionService{

	private static final long serialVersionUID = 1L;

	@Override
	public String getXML(String name, String number) {
		String xml = "";
		URL url;
		try {
			url = new URL(Const.PROJECT_PATH + number +"/"+ Const.PROJECT_FILE);
			System.out.println("ProjectURL: " + url);
			xml = new Scanner(url.openStream()).useDelimiter("//Z").next();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
			
			
			//TODO: handle exception
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			
			//TODO: handle exception
			
			
		}
		return xml;	
	}

}
