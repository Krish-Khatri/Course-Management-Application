package pages;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
public class UploadServlet extends HttpServlet{
    private boolean multi_part;
    private String path;
    private final int max_file_size = 50 * 1024;
    private final int max_mem_size = 4 * 1024;
    private File file;


    public void __init__(){
        path = getServletContext().getInitParameter("file-upload");
    }

    public void doGet(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException{
        throw new ServletException("GET method is used with " + getClass().getName() + " but POST method is needed");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        multi_part = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        if(!multi_part){
            pw.print("<html>");
            pw.println("<head>");
            pw.println("<title>Upload</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<p>No file uploaded</p>");
            pw.println("</body>");
            pw.println("</html>");
            return;
        }

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

        // max size allowed to be stored
        diskFileItemFactory.setSizeThreshold(max_mem_size);
        // store here if size > max_mem_size
        diskFileItemFactory.setRepository(new File("c:\\temp"));

        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setSizeMax(max_file_size);

        try {
            List items = fileUpload.parseRequest(request);
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Upload Servlet</title>");
            pw.println("</head>");
            pw.println("<body>");

            Iterator iter = items.iterator();
            while (iter.hasNext()){
                FileItem fileItem = (FileItem) iter.next();
                if(!fileItem.isFormField()){
                    String field_name = fileItem.getFieldName();
                    String file_name = fileItem.getName();
                    String content_type = fileItem.getContentType();
                    boolean in_memory = fileItem.isInMemory();
                    long size = fileItem.getSize();

                    if(file_name.lastIndexOf("\\") >= 0){
                        file = new File(path + file_name.substring(file_name.lastIndexOf("\\")));
                    } else {
                        file = new File(path + file_name.substring(file_name.lastIndexOf("\\") + 1));
                    }

                    fileItem.write(file);
                    pw.println("Uploaded File " + file_name + "<br>");
                }
            }
            pw.println("</body>");
            pw.println("</html>");
        } catch (Exception e){
            e.printStackTrace(System.out);
        }
    }


}
