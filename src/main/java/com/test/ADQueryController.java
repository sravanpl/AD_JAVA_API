package com.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.DAO.UserDaoImpl;
import com.test.DTO.ADUserDetails;

@RestController
public class ADQueryController {
	@Autowired
	UserDaoImpl userDaoImpl;

	@RequestMapping("/getADUsers")
	public List<ADUserDetails> getUserList(@RequestParam(value="base",defaultValue="DC=sravan,DC=com") String base) {
		System.out.println("test "+base);
		return userDaoImpl.getAllUsers(base);
	}
}
