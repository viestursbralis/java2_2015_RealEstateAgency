package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.JunctionDAO;
import lv.javaguru.java2.database.PropertyDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Iterator;
import java.util.List;

@Controller
public class ImageUploadController3 implements MVCController {

    /*************************************************************************/
    @Autowired
    private PropertyDAO propertyDao;
    @Autowired
    private JunctionDAO propertyPhotosDao;

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 1000 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
    private final String SAVE_DIR = "PropertyPhotos";
    /***************************************************************************/
    public MVCModel execute(HttpServletRequest request) {


        HttpSession session = request.getSession();
/**************************************************************/
        filePath =request.getServletContext().getRealPath("");
                //request.getServletContext().getInitParameter("uploadFiles");

        String savePath = filePath + "\\" + SAVE_DIR +"\\";
        session.removeAttribute("path");
        session.setAttribute("path", savePath);
        //session.removeAttribute("fullPath");
        //session.setAttribute("fullPath", savePath);

        isMultipart = ServletFileUpload.isMultipartContent(request);

        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );

        try{
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            while ( i.hasNext () )
            {
                FileItem fi = (FileItem)i.next();
                if ( !fi.isFormField () )
                {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
                    // Write the file
                    if( fileName.lastIndexOf("\\") >= 0 ){
                        String finalPath = savePath +
                                fileName.substring( fileName.lastIndexOf("\\"));
                        String fileNameToDatabase = fileName.substring( fileName.lastIndexOf("\\"));
                        file = new File(finalPath) ;
                        session.removeAttribute("fullPath");
                        session.setAttribute("fullPath",savePath +
                                fileName.substring( fileName.lastIndexOf("\\")) );


                    }else{
                        String finalPath = savePath +
                                fileName.substring(fileName.lastIndexOf("\\")+1);
                        String fileNameToDatabase = fileName.substring(fileName.lastIndexOf("\\")+1);
                        file = new File(finalPath) ;
                        session.removeAttribute("fn");
                        session.setAttribute("fn",savePath +
                                fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        Long lastInsertedPhotoID = propertyDao.insertPhoto(fileNameToDatabase);
                        Object attribute = request.getSession().getAttribute("lastPropertyID");
                        long lastInsertedPropertyID = Long.parseLong(String.valueOf(attribute));

                        propertyPhotosDao.propertyPhotosJunction(lastInsertedPropertyID, lastInsertedPhotoID);

                    }
                    fi.write( file ) ;

                }
            }

        }catch(Exception ex) {
            System.out.println(ex);
        }


        /***********************************************************/
        return new MVCModel("kkk", "/clientLoggedInFirstPage1.jsp");
    }

}


