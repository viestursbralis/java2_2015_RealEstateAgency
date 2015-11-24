package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;

//@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50,
        location="/")   // 50MB

@Controller
public class ImageUploadController2 implements MVCController {

    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";

    /**
     * handles file upload
     */
    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
try {
    for (Part part : request.getParts()) {
        if (part.getName().equalsIgnoreCase("file")){
            String fileName = extractFileName(part);

            session.setAttribute("fn", fileName);
            part.write(savePath + File.separator + fileName);
        }

    }
}catch (Exception e) {
    System.out.println("Sorry, me - dumb.....!");
}
        return new MVCModel("kkk", "/clientLoggedInFirstPage1.jsp");
    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private static String extractFileName0(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1)
                        .substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }



    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
