package cn.joy.demo.external.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringUtils;

@MultipartConfig(location = "D:/upload", maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024L * 1024L * 10L)
@WebServlet("/upload/ss3")
public class Servlet3FilesUpload extends HttpServlet{
	private static final MultipartConfig config;

	static {
		config = Servlet3FileUpload.class.getAnnotation(MultipartConfig.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/upload.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// 为避免获取文件名称时出现乱码
		request.setCharacterEncoding("UTF-8");
		
		System.out.println(request.getParameter("datas"));	//能直接获取

		Collection<Part> parts = null;
		try {
			parts = request.getParts();
		} catch (IllegalStateException ise) {
			ise.printStackTrace();
			// 上传文件超过注解所标注的maxRequestSize或maxFileSize值
			if (config.maxRequestSize() == -1L) {
				System.out.println("the Part in the request is larger than maxFileSize");
			} else if (config.maxFileSize() == -1L) {
				System.out.println("the request body is larger than maxRequestSize");
			} else {
				System.out
						.println("the request body is larger than maxRequestSize, or any Part in the request is larger than maxFileSize");
			}

			doError(request, response, "上传文件过大，请检查输入是否有误！");
			return;
		} catch (IOException ieo) {
			ieo.printStackTrace();
			// 在接收数据时出现问题
			System.err.println("I/O error occurred during the retrieval of the requested Part");
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}

		if (parts == null || parts.isEmpty()) {
			doError(request, response, "上传文件为空！");
			return;
		}

		// 前端具有几个file组件，这里会对应几个Part对象
		List fileNames = new ArrayList();
		for (Part part : parts) {
			if (part == null) {
				continue;
			}
			// 这里直接以源文件名保存
			String fileName = getFileName(part);

			if (StringUtils.isBlank(fileName)) {
				continue;
			}

			part.write(fileName);
			fileNames.add(fileName);
		}

		request.setAttribute("fileNames", fileNames);
		request.getRequestDispatcher("/uploadsResult.jsp").forward(request, response);
	}

	private void doError(HttpServletRequest request, HttpServletResponse response, String errMsg)
			throws ServletException, IOException {
		request.setAttribute("errMsg", errMsg);

		this.doGet(request, response);
	}

	private String getFileName(Part part) {
		if (part == null)
			return null;

		String fileName = part.getHeader("content-disposition");
		if (StringUtils.isBlank(fileName)) {
			return null;
		}

		return StringUtils.substringBetween(fileName, "filename=\"", "\"");
	}

	private String getExtension(String fileName) {
		int idx = fileName.lastIndexOf(".");
		if (idx != -1)
			return fileName.substring(idx + 1);
		return "";
	}
}
