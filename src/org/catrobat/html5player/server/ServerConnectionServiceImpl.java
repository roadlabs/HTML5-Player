/**
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2013 The Catrobat Team
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
package org.catrobat.html5player.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpSession;

import org.catrobat.html5player.client.Const;
import org.catrobat.html5player.client.ServerConnectionService;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServerConnectionServiceImpl extends RemoteServiceServlet implements ServerConnectionService{

	private static final long serialVersionUID = 1L;

	@Override
	public String getXML(String number) {
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
	@Override
	public String getXML(){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ProjectData pd = (ProjectData) session.getAttribute("projectdata");
		
		System.out.println("xml from projectdata:"+pd.getXml());
		return pd.getXml();
	}
	@Override
	public String getImage(String name){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ProjectData pd = (ProjectData) session.getAttribute("projectdata");
		return pd.getImage(name);
	}
	@Override
	public String getSound(String name){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ProjectData pd = (ProjectData) session.getAttribute("projectdata");
		return pd.getSound(name);
	}
	
	@Override
	public String getXMLFromProjectFileUrl(String url)
	{
		String xml = "";
		URL tmpurl;
		try {
			tmpurl = new URL(url);

		InputStream stream = tmpurl.openStream();
		ZipInputStream zip = new ZipInputStream(stream);
		ProjectData pd = LoadUtils.loadDatafromZipStream(zip);
		HttpSession session = this.getThreadLocalRequest().getSession();
		session.setAttribute("projectdata", pd);
		xml = pd.getXml();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	

}
