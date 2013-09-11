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
import org.catrobat.html5player.client.CatrobatDebug;

public class FileUpload extends HttpServlet {
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ServletFileUpload upload = new ServletFileUpload();

    try {
      FileItemIterator iter = upload.getItemIterator(request);
      CatrobatDebug.debug("UploadServlet iter:" + iter);
      while (iter.hasNext()) {

        FileItemStream item = iter.next();
        CatrobatDebug.debug("Field Name: " + item.getFieldName());
        CatrobatDebug.debug("Name: " + item.getName());
        CatrobatDebug.debug("is form field: " + item.isFormField());
        // String name = item.getFieldName();
        InputStream stream = item.openStream();

        ZipInputStream zip = new ZipInputStream(stream);
        ProjectData pd = LoadUtils.loadDatafromZipStream(zip);
        HttpSession session = request.getSession();
        session.setAttribute("projectdata", pd);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().print("The file was created successfully.");
        response.flushBuffer();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
      IOException {
    String name = req.getParameter("name");
    HttpSession session = req.getSession();
    ProjectData pd = (ProjectData) session.getAttribute("projectdata");
    String fs = pd.getImage(name);
    CatrobatDebug.debug("Get File Name: " + name);
    if (fs == null) {
      fs = pd.getSound(name);
    }
    if (fs == null) {
      res.getWriter().print("File not found!");
      res.flushBuffer();
    }
    String type = fs.substring(5, fs.indexOf(";base64,"));
    fs = fs.substring(fs.indexOf(',') + 1);// fs.replace("data:image/png;base64,", "");
    byte[] buffer = Base64.decodeBase64(fs.getBytes());
    res.setContentType(type);
    res.getOutputStream().write(buffer);
  }



}
