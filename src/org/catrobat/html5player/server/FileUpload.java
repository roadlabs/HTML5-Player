package org.catrobat.html5player.server;


import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        ServletFileUpload upload = new ServletFileUpload();

        try{
            FileItemIterator iter = upload.getItemIterator(request);
            System.out.println("UploadServlet iter:"+iter);
            while (iter.hasNext()) {

                FileItemStream item = iter.next();
                System.out.println(":Field Name:"+item.getFieldName());
                System.out.println(":Name:"+item.getName());
                System.out.println(":is form field:"+item.isFormField());
                //String name = item.getFieldName();
                InputStream stream = item.openStream();

                ZipInputStream zip = new ZipInputStream(stream);
                ProjectData pd = LoadUtils.loadDatafromZipStream(zip);
                HttpSession session = request.getSession();
                session.setAttribute("projectdata", pd);
//                ZipEntry zipEntry;
//                StringBuilder s = new StringBuilder();
//                while((zipEntry = zip.getNextEntry())!=null)
//                {
//                	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[1024];
//                    int read = 0;
//                	System.out.println(zipEntry.getName() + " " + zipEntry.isDirectory()+ " "+ zipEntry.toString() );
//                	if(zipEntry.getName().endsWith(".xml"))
//                	{
//                	      while ((read = zip.read(buffer, 0, 1024)) >= 0) {
//                	           s.append(new String(buffer, 0, read));
//                	      }
//                	
//                		String xml = s.toString();
//                		pd.setXml(xml);
//                	}
//                	else if(!zipEntry.getName().endsWith(".nomedia"))
//                	{
//	                	if(zipEntry.getName().contains("images/"))
//	                	{
//							while ((read = zip.read(buffer, 0, 1024)) >= 0) {
//								baos.write(buffer, 0, read);
//							}
//							String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(baos.toByteArray()));
//							base64 = "data:image/"+LoadUtils.getFileExtension(zipEntry.getName())+";base64," + base64;
//	                		pd.addImage(zipEntry.getName().replaceFirst("images/", ""), base64);
//	                	}
//	                	else if(zipEntry.getName().contains("sounds/"))
//	                	{
//							while ((read = zip.read(buffer, 0, 1024)) >= 0) {
//								baos.write(buffer, 0, read);
//							}
//							String base64 = StringUtils.newStringUtf8(Base64.encodeBase64(baos.toByteArray()));
//							base64 = "data:audio/"+LoadUtils.getFileExtension(zipEntry.getName())+";base64," + base64;
//	                		pd.addSound(zipEntry.getName().replaceFirst("sounds/", ""), base64);
//	                	}
//                	}
//                }
              response.setStatus(HttpServletResponse.SC_CREATED);
              response.getWriter().print("The file was created successfully.");
              response.flushBuffer();
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }  
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException 
	{       
	 String name = req.getParameter("name");
		HttpSession session = req.getSession();
		ProjectData pd = (ProjectData) session.getAttribute("projectdata");
		String fs = pd.getImage(name);
		if(fs == null)
		{
			fs = pd.getSound(name);
		}
		if(fs == null)
		{
			res.getWriter().print("File not found!");
            res.flushBuffer();
		}
		String type  = fs.substring(5, fs.indexOf(";base64,"));
		fs = fs.substring(fs.indexOf(',')+1);//fs.replace("data:image/png;base64,", "");
		byte[] buffer = Base64.decodeBase64(fs.getBytes());
		res.setContentType(type);
	 res.getOutputStream().write(buffer);
	}
	

	
	
}

