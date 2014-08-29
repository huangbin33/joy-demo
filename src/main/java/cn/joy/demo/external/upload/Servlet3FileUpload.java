package cn.joy.demo.external.upload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringUtils;

@MultipartConfig(location = "D:/upload", maxFileSize = 1024 * 1024 * 10)
@WebServlet("/upload/s3")
public class Servlet3FileUpload extends HttpServlet{
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

		Part part = null;
		try {
			// <input name="file" size="50" type="file" />
			part = request.getPart("file");
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

			forwardErrorPage(request, response, "上传文件过大，请检查输入是否有误！");
			return;
		} catch (IOException ieo) {
			ieo.printStackTrace();
			// 在接收数据时出现问题
			System.err.println("I/O error occurred during the retrieval of the requested Part");
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}

		if (part == null) {
			forwardErrorPage(request, response, "上传文件出现异常，请检查输入是否有误！");
			return;
		}

		// 得到文件的原始名称，eg ：测试文档.pdf
		String fileName = getFileName(part);

		System.out.println("contentType : " + part.getContentType());
		System.out.println("fileName : " + fileName);
		System.out.println("fileSize : " + part.getSize());
		System.out.println("header names : ");
		for (String headerName : part.getHeaderNames()) {
			System.out.println(headerName + " : " + part.getHeader(headerName));
		}

		String saveName = System.currentTimeMillis() + "." + getExtension(fileName);

		System.out.println("save the file with new name : " + saveName);

		// 因在注解中指定了路径，这里可以指定要写入的文件名
		// 在未执行write方法之前，将会在注解指定location路径下生成一临时文件
		part.write(saveName);

		request.setAttribute("fileName", fileName);
		request.getRequestDispatcher("/uploadResult.jsp").forward(request, response);
	}

	private void forwardErrorPage(HttpServletRequest request, HttpServletResponse response, String errMsg)
			throws ServletException, IOException {
		request.setAttribute("errMsg", errMsg);

		request.getRequestDispatcher("/upload.jsp").forward(request, response);
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
