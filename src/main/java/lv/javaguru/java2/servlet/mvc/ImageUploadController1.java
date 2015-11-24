package lv.javaguru.java2.servlet.mvc;

import org.springframework.stereotype.Controller;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Optional;

@WebServlet("/ImageUploadController1")
@MultipartConfig(fileSizeThreshold=1024*1024,
            maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
@Controller
public class ImageUploadController1 implements MVCController {


    private static final String SAVE_DIR = "PropertyPhotos";
    public MVCModel execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {


            Collection<Part> parts = request.getParts();
            Optional<Part> p1 = parts.stream().findFirst();
            Part filePart = request.getPart("description");
            String fileName1 = getFilename(filePart);

            session.removeAttribute("fullPath");
            session.setAttribute("fullPath", fileName1);
            String appPath = request.getServletContext().getRealPath("");
            // constructs path of the directory to save uploaded file
            String savePath = appPath + File.separator + SAVE_DIR;
            session.removeAttribute("path");
            //session.setAttribute("path", savePath);
            // creates the save directory if it does not exists

            for (Part part : parts) {

                session.setAttribute("path", part.getName());
            }

            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

        if (filePart != null && filePart.getSize() > 0) {

            InputStream inputStream = filePart.getInputStream();
            String destination = request.getServletContext().getRealPath("");
            String fileName = getFilename(filePart);
            String saveDir=savePath+File.separator+fileName;
            File attachedFile = new File(saveDir);

            FileOutputStream outputStream = new FileOutputStream(attachedFile);
            final byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1) {
                outputStream.write(bytes);
            }
            outputStream.flush();


        }


               /* for (Part part : request.getParts()) {

                InputStream is = request.getPart(part.getName()).getInputStream();
                int i = is.available();
                byte[] b = new byte[i];
                is.read(b);
                String fileName = getFileName(part);
                String saveDir=savePath+File.separator+fileName;
                //session.setAttribute("fullPath", "cuuka");
                FileOutputStream os = new FileOutputStream(saveDir);
                os.write(b);
                is.close();
            }*/
            return new MVCModel("kkk", "/clientLoggedInFirstPage1.jsp");
        } catch (Exception e) {
            System.out.println("Fuck It!");
        }
        return new MVCModel("model", "/index.jsp");
    }
    private static String getFilename(Part part) {
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
    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");

        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;


    }
}


