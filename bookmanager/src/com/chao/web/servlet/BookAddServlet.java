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

		// Ҫִ���ļ��ϴ��Ĳ���,�����жϱ��Ƿ�֧���ļ��ϴ�������enctype="multipart/form-data"
		boolean multipartContent = ServletFileUpload.isMultipartContent(req);
		if (!multipartContent) {
			System.out.println("�뽫��enctypeֵ��Ϊ��enctype=multipart/form-data");
		}

		// ����һ��DiskFileItemfactory������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// ָ����ʱ�ļ��Ĵ洢Ŀ¼
		factory.setRepository(new File("F:\\"));
		// ����һ��ServletFileUpload���Ķ���
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// ����ϴ�������������
		sfu.setHeaderEncoding("UTF-8");
		//����request���󣬷������б���
		List<FileItem> fileItems = new ArrayList<FileItem>(0);
		//���ڷ�װ��ͨ���������
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		try {
			// ����request���󣬲��õ�һ������ļ���
			fileItems = sfu.parseRequest(req);
			// ������������
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					// ��ͨ����
					processFormField(fileItem,map);
				} else {
					// �ϴ�����
					processUploadField(fileItem,map);
				}
			}
			
			Book book = new Book();
			//��ȡ������
			try {
				BeanUtils.populate(book, map);
				book.setId(UUIDUtil.getUUID());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
			//ʵ��ҵ���߼�
			BookServiceImpl service = new BookServiceImpl();
			try {
				service.addBook(book);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//�ַ�ת��
			req.getRequestDispatcher("bookListServlet").forward(req, resp);

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
	}
	
	private void processUploadField(FileItem fileItem,Map<String, String[]> map) {
		try {
			// �õ��ļ�������
			InputStream input = fileItem.getInputStream();
			//�õ��ϴ��ļ���
			String fileName = fileItem.getName();
			//�õ��ļ�����չ������ֹ�����ļ�
			String extension = FilenameUtils.getExtension(fileName);
			if(!("jsp".equals(extension)||"exe".equals(extension))){
				// ����һ���ļ����̵�Ŀ¼
				String directoryRealPath = this.getServletContext().getRealPath("/upload");
				System.out.println(directoryRealPath);
				// �ȴ����ļ��ִ���Ŀ¼
				File storeDirectory = new File(directoryRealPath);
				//�жϵ�ǰĿ¼�Ƿ����
				if(!storeDirectory.exists()){
					storeDirectory.mkdirs(); // �����ļ���
				}
				// �����ļ���
				if(fileName!=null){
					// filename.substring(filename.lastIndexOf(File.separator)+1);
					fileName = FilenameUtils.getName(fileName);
				}
				// ����ļ�ͬ��������
				fileName = UUID.randomUUID() + "_" + fileName;
				// Ŀ¼��ɢ,��ֹͬһ���ļ������ļ�����
				String childDirectory = makeChildDirectory(storeDirectory, fileName);
				System.out.println(childDirectory);
				// ��storeDirectory�£���������Ŀ¼�µ��ļ�
				fileName = childDirectory + File.separator + fileName;
				File file = new File(storeDirectory,fileName);
				// ͨ���ļ���������ϴ����ļ����浽����
				OutputStream os = new FileOutputStream(file);
				int length = 0;
				byte[] b = new byte[1024];
				while((length = input.read(b))!=-1){
					os.write(b,0,length);
				}
				input.close();
				os.close();
				
				//ɾ����ʱ�ļ�
				fileItem.delete();
			}
			map.put(fileItem.getFieldName(), new String[]{fileName});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Ŀ¼��ɢ
	private String makeChildDirectory(File storeDirectory, String filename) {
		//��õ����ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		
		int hashcode = filename.hashCode();// �����ַ�ת����32λhashcode��
		String code = Integer.toHexString(hashcode); // ��hashcodeת��Ϊ16���Ƶ��ַ�
		String childDirectory = date + File.separator + code.charAt(0) + File.separator + code.charAt(1);
		//����ָ��Ŀ¼
		File file = new File(storeDirectory, childDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
		return childDirectory;
	}

	// ������ͨ����
	private void processFormField(FileItem fileItem,Map<String, String[]> map) {
		try {
			String name = fileItem.getFieldName();// �����ͨ����name����
			String value = fileItem.getString("UTF-8"); // �����ͨ����valueֵ
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
