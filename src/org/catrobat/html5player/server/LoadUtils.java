package org.catrobat.html5player.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

public class LoadUtils {
	
	
	
	public static String getFileExtension(String fileName)
	{
		String extension = "";
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}

	public static ProjectData loadDatafromZipStream(ZipInputStream zip) throws IOException
	{
		String xml = "";
		Map<String,String> images = new HashMap<String,String>();
		Map<String,String> sounds = new HashMap<String,String>();
        ZipEntry zipEntry;
        StringBuilder s = new StringBuilder();
        while((zipEntry = zip.getNextEntry())!=null)
        {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
        	//System.out.println(zipEntry.getName() + " " + zipEntry.isDirectory()+ " "+ zipEntry.toString() );
        	if(zipEntry.getName().endsWith(".xml"))
        	{
        	      while ((read = zip.read(buffer, 0, 1024)) >= 0) {
        	           s.append(new String(buffer, 0, read));
        	      }
        		xml = s.toString();
        	}
        	else if(!zipEntry.getName().endsWith(".nomedia"))
        	{
            	if(zipEntry.getName().contains("images/"))
            	{
					while ((read = zip.read(buffer, 0, 1024)) >= 0) {
						baos.write(buffer, 0, read);
					}
					String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(baos.toByteArray()));
					base64 = "data:image/"+LoadUtils.getFileExtension(zipEntry.getName())+";base64," + base64;
					images.put(zipEntry.getName().replaceFirst("images/", ""), base64);
            	}
            	else if(zipEntry.getName().contains("sounds/"))
            	{
					while ((read = zip.read(buffer, 0, 1024)) >= 0) {
						baos.write(buffer, 0, read);
					}
					String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(baos.toByteArray()));
					base64 = "data:audio/"+LoadUtils.getFileExtension(zipEntry.getName())+";base64," + base64;
					sounds.put(zipEntry.getName().replaceFirst("sounds/", ""), base64);
            	}
        	}
        }
        if(xml == "")
        {
        	return null;
        }
        return new ProjectData(xml, images, sounds);
	}
}
