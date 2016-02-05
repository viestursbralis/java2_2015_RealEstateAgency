package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
//@WebServlet("/ImageUploadController")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)   // 50MB
@Controller
public class ImageUploadController implements MVCController {

    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("fn");
        String fN=request.getParameter("fileName");

        session.setAttribute("fn", fN);
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String fileName = "";
        try {
            for (Part part : request.getParts()) {


                for (String s : part.getHeader("content-disposition").split(";")) {
                    if (s.trim().startsWith("filename")) {
                        fileName = s.split("=")[1].replaceAll("\"", "");
                    }
                }
                part.write(fileName);

            }

        }catch (Exception e) {
            System.out.println("Error!");

        }


       /* try {
            // get access to file that is uploaded from client
            Part p1 = request.getPart("file");
            InputStream is = p1.getInputStream();

            // read filename which is sent as a part
            Part p2  = request.getPart("photoname");
            Scanner s = new Scanner(p2.getInputStream());
            String filename = s.nextLine();    // read filename from stream

            // get filename to use on the server
            String outputfile = request.getServletContext().getRealPath(filename);  // get path on the server
            FileOutputStream os = new FileOutputStream (outputfile);

            // write bytes taken from uploaded file to target file
            int ch = is.read();
            while (ch != -1) {
                os.write(ch);
                ch = is.read();
            }
            os.close();
            //out.println("<h3>File uploaded successfully!</h3>");
        }
        catch(Exception ex) {
            //out.println("Exception -->" + ex.getMessage());
        }*/
        return new MVCModel("description", "/clientLoggedInFirstPage1.jsp");

    }



    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "PropertyPhotos";



    /**
     * Extracts file name from HTTP header content-disposition
     */
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
