package com.xyz.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xyz.entity.UserFiles;
import com.xyz.repository.FilesRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private FilesRepo fr;
	
	@GetMapping(path = "/")
	private String getIndex(Model m) {
		// TODO Auto-generated method stub
		List<UserFiles>ll= fr.findAll();
		m.addAttribute("ll", ll);
       return "index";
	}
	
	@PostMapping(path = "/upload")
	private String upload(@RequestParam("userFiles") MultipartFile p,HttpSession hs) {
		// TODO Auto-generated method stub
		System.out.println(p.getOriginalFilename());
        UserFiles uf = new UserFiles();
        uf.setFileName(p.getOriginalFilename());
		UserFiles ufob= fr.save(uf);
		if(ufob!=null) {
			try {
				File f = new ClassPathResource("/static/imgs").getFile();
			   Path pt = Paths.get(f.getAbsolutePath()+File.separator+p.getOriginalFilename());                   //java.nio.file package  
			   System.out.println(pt);
			   Files.copy(p.getInputStream(), pt, StandardCopyOption.REPLACE_EXISTING);
			   
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			hs.setAttribute("msg", "Something Wrong!!!");
		}
		
       return "redirect:/";
	}
	
}
