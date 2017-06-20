package com.wjl.action;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.io.PrintWriter;  
  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;  
  
import org.apache.struts2.ServletActionContext;  
  
import com.opensymphony.xwork2.ActionSupport;  
  
public class UploadAction extends ActionSupport {  
  
    private File upload;  
    private String uploadContentType;  
    private String uploadFileName;  
  
  
    public File getUpload() {  
        return upload;  
    }  
  
    public void setUpload(File upload) {  
          
        this.upload = upload;  
    }  
  
    public String getUploadContentType() {  
        return uploadContentType;  
    }  
  
    public void setUploadContentType(String uploadContentType) {  
        this.uploadContentType = uploadContentType;  
    }  
  
    public String getUploadFileName() {  
        return uploadFileName;  
    }  
  
    public void setUploadFileName(String uploadFileName) {  
        this.uploadFileName = uploadFileName;  
    }  
  
    public String execute() throws Exception {  
  
        HttpServletResponse response = ServletActionContext.getResponse();  
        response.setCharacterEncoding("utf-8");  
        PrintWriter out = response.getWriter();  
  
       
        String callback = ServletActionContext.getRequest().getParameter("CKEditorFuncNum");   
          
        String expandedName = "";   
        if (uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg")) {  
            
            expandedName = ".jpg";  
        }else if(uploadContentType.equals("image/png") || uploadContentType.equals("image/x-png")){  
             
            expandedName = ".png";  
        }else if(uploadContentType.equals("image/gif")){  
            expandedName = ".gif";  
        }else if(uploadContentType.equals("image/bmp")){  
            expandedName = ".bmp";  
        }else{  
            out.println("<script type=\"text/javascript\">");    
            
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件格式不正确（必须为.jpg/.gif/.bmp/.png文件）');"); 
            out.println("</script>");  
            return null;  
        }  
          
        if(upload.length() > 1024*1024){ //here you can change upload img size 在这里改变图片的大小, 1024*1024==1MB 类推  
            out.println("<script type=\"text/javascript\">");    
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'上传图片的资源不能大于1MB');");   
            out.println("</script>");  
            return null;  
        }  
          
          
        InputStream is = new FileInputStream(upload); 
        HttpServletRequest request=ServletActionContext.getRequest();
       // String path = request.getContextPath();
        //String uploadPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/WebRoot/img";
        
        String uploadPath = ServletActionContext.getServletContext()     
                .getRealPath("/uploadImg");
        File file = new File(uploadPath);
        if(!file.exists()||!file.isDirectory()){
        	file.mkdir();
        }
//        System.out.println(uploadPath);
        String fileName = java.util.UUID.randomUUID().toString();  
        fileName += expandedName;  
        File toFile = new File(uploadPath, fileName);  
        OutputStream os = new FileOutputStream(toFile);     
        byte[] buffer = new byte[1024];     
        int length = 0;  
        while ((length = is.read(buffer)) > 0) {     
            os.write(buffer, 0, length);     
        }   
        is.close();  
        os.close();  
          
       
        out.println("<script type=\"text/javascript\">");    
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + "uploadImg/" + fileName + "','')");    
        out.println("</script>");  
        return null;  
    }  
}  