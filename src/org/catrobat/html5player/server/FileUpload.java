package org.catrobat.html5player.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                String name = item.getFieldName();
                InputStream stream = item.openStream();

                //ByteArrayOutputStream out = new ByteArrayOutputStream();
                ZipInputStream zip = new ZipInputStream(stream);
                ZipEntry zipEntry;
                while((zipEntry = zip.getNextEntry())!=null)
                {
                	System.out.println(zipEntry.getName());
                	
                }
              response.setStatus(HttpServletResponse.SC_CREATED);
              response.getWriter().print("The file was created successfully.");
              response.flushBuffer();
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        
        
//        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        System.out.println("Status : "+isMultipart);
//        if (isMultipart) {
//            FileItemFactory factory = new DiskFileItemFactory();
//            ServletFileUpload upload = new ServletFileUpload(factory);
//
//            try {
//                @SuppressWarnings("unchecked")
//				List<FileItem> items = upload.parseRequest(request);
//                Iterator<FileItem> iterator = items.iterator();
//                System.out.println("Test1 : " + items.size());
//                while (iterator.hasNext()) {
//                    FileItem item = (FileItem) iterator.next();
//                    System.out.println("Test2");
//                    if (!item.isFormField()) {
//                        String fileName = item.getName();
//                        System.out.println("Test3");
//                        String root = getServletContext().getRealPath("/");
//                        File path = new File(root + UPLOAD_DIRECTORY);
//                        if (!path.exists()) {
//                            boolean result = path.mkdir();  
//                            if(!result){    
//                          	  throw new IOException("Could not create directory.");
//                             }
//                        }
//                        
//                      
//
//                        File uploadedFile = new File(path + "/" + fileName);
//                        System.out.println(uploadedFile.getAbsolutePath());
//                        item.write(uploadedFile);
//                        
//                        
//                        response.setStatus(HttpServletResponse.SC_CREATED);
//                        response.getWriter().print("The file was created successfully.");
//                        response.flushBuffer();
//                        System.out.println("Test5");
//                    }
//                }
//            } catch (FileUploadException e) {
//            	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,  
//            			"An error occurred while creating the file : " + e.getMessage());
//                e.printStackTrace();
//            } catch (Exception e) {
//            	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,  
//            			"An error occurred while creating the file : " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        System.out.println("Test6");
    }
}

