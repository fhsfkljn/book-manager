package com.chao.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.chao.domain.Book;
import com.chao.service.BookServiceImpl;
import com.chao.util.UUIDUtil;

public class BookAddServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 要执行文件上传的操作,首先判断表单是否支持文件上传。即：enctype="multipart/form-data"
		boolean multipartContent = ServletFileUpload.isMultipartContent(req);
		if (!multipartContent) {
			System.out.println("请将表单enctype值改为：enctype=multipart/form-data");
		}

		// 创建一个DiskFileItemfactory工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 指定临时文件的存储目录
		factory.setRepository(new File("F:\\"));
		// 创建一个ServletFileUpload核心对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解决上传表单项乱码问题
		sfu.setHeaderEncoding("UTF-8");
		//解析request对象，返回所有表单项
		List<FileItem> fileItems = new ArrayList<FileItem>(0);
		//用于封装普通表单项的数据
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		try {
			// 解析request对象，并得到一个表单项的集合
			fileItems = sfu.parseRequest(req);
			// 遍历表单项数据
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					// 普通表单项
					processFormField(fileItem,map);
				} else {
					// 上传表单项
					processUploadField(fileItem,map);
				}
			}
			
			Book book = new Book();
			//获取表单数据
			try {
				BeanUtils.populate(book, map);
				book.setId(UUIDUtil.getUUID());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
			//实现业务逻辑
			BookServiceImpl service = new BookServiceImpl();
			try {
				service.addBook(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//分发转向
			req.getRequestDispatcher("bookListServlet").forward(req, resp);

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	private void processUploadField(FileItem fileItem,Map<String, String[]> map) {
		try {
			// 得到文件输入流
			InputStream input = fileItem.getInputStream();
			//得到上传文件名
			String fileName = fileItem.getName();
			//得到文件的扩展名，防止恶意文件
			String extension = FilenameUtils.getExtension(fileName);
			if(!("jsp".equals(extension)||"exe".equals(extension))){
				// 创建一个文件存盘的目录
				String directoryRealPath = this.getServletContext().getRealPath("/upload");
				System.out.println(directoryRealPath);
				// 既代表文件又代表目录
				File storeDirectory = new File(directoryRealPath);
				//判断当前目录是否存在
				if(!storeDirectory.exists()){
					storeDirectory.mkdirs(); // 创建文件夹
				}
				// 处理文件名
				if(fileName!=null){
					// filename.substring(filename.lastIndexOf(File.separator)+1);
					fileName = FilenameUtils.getName(fileName);
				}
				// 解决文件同名的问题
				fileName = UUID.randomUUID() + "_" + fileName;
				// 目录打散,防止同一个文件夹内文件过多
				String childDirectory = makeChildDirectory(storeDirectory, fileName);
				System.out.println(childDirectory);
				// 在storeDirectory下，创建完整目录下的文件
				fileName = childDirectory + File.separator + fileName;
				File file = new File(storeDirectory,fileName);
				// 通过文件输出流将上传的文件保存到磁盘
				OutputStream os = new FileOutputStream(file);
				int length = 0;
				byte[] b = new byte[1024];
				while((length = input.read(b))!=-1){
					os.write(b,0,length);
				}
				input.close();
				os.close();
				
				//删除临时文件
				fileItem.delete();
			}
			map.put(fileItem.getFieldName(), new String[]{fileName});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 目录打散
	private String makeChildDirectory(File storeDirectory, String filename) {
		//获得当天的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		
		int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码
		String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符
		String childDirectory = date + File.separator + code.charAt(0) + File.separator + code.charAt(1);
		//创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
		return childDirectory;
	}

	// 处理普通表单项
	private void processFormField(FileItem fileItem,Map<String, String[]> map) {
		try {
			String name = fileItem.getFieldName();// 获得普通表单的name属性
			String value = fileItem.getString("UTF-8"); // 获得普通表单的value值
			map.put(name, new String[]{value});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
