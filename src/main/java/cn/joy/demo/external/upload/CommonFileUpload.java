package cn.joy.demo.external.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload/cf")
public class CommonFileUpload extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 检查输入请求是否为multipart表单数据。
		if (isMultipart) {
			System.out.println(request.getParameter("datas"));	//不能直接获取
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<FileItem> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				// 检查当前项目是普通表单项目还是上传文件。
				if (item.isFormField()) {// 如果是普通表单项目，显示表单内容。
					String fieldName = item.getFieldName();
					if (fieldName.equals("datas")) 
						System.out.println("the datas is" + item.getString());// 显示表单内容。
				} else if(item.getName()!=null){// 如果是上传文件，显示文件名。
					System.out.println("the upload file name is " + item.getName());
					File fullFile = new File(item.getName());
					File savedFile = new File("D:/upload", fullFile.getName());
					try {
						item.write(savedFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("the enctype must be multipart/form-data");
		}
	}

}
