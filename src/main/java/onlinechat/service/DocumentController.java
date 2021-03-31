package onlinechat.service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import java.sql.Blob;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import onlinechat.entity.Document;
import onlinechat.util.Hibernateutil;
import onlinechat.dao.DocumentDAO;

@Controller
public class DocumentController {
	
	
	@RequestMapping("/index")
	public String index(Model m) {
		try {
			m.addAttribute("document", new Document());
			m.addAttribute("documentList", DocumentDAO.list());
		}catch(Exception e) {
			e.printStackTrace();
		}

		return "documents";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MultipartHttpServletRequest request,
			@ModelAttribute("document") Document document,
			@RequestParam("file") MultipartFile file) {
		
		
		System.out.println("Name:" + document.getName());
		System.out.println("Desc:" + document.getDescription());
		System.out.println("File:" + file.getName());
		System.out.println("ContentType:" + file.getContentType());
		
		try {
			Blob blob = Hibernate.getLobCreator( Hibernateutil.createSession()).createBlob(file.getInputStream(),-1);

			document.setFilename(file.getOriginalFilename());
			document.setContent(blob);
			document.setContentType(file.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			DocumentDAO.save(document);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "saved";
	}

	@RequestMapping("/download/{documentId}")
	public Document download(@PathVariable("documentId")
			Integer documentId, HttpServletResponse response) {
		
		Document doc = DocumentDAO.get(documentId);
		try {
			response.setHeader("Content-Disposition", "attachment;filename=\"" +doc.getFilename()+ "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			IOUtils.copy(doc.getContent().getBinaryStream(), out);
			out.flush();
			out.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return doc;
	}

	@RequestMapping("/remove/{documentId}")
	public String remove(@PathVariable("documentId")
			Integer documentId) {
		
		DocumentDAO.remove(documentId);
		
		return "redirect:/index.html";
	}
}