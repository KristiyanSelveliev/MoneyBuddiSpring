package com.example.s9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.ProductDao;
import model.Product;

@RestController
public class MagicRestController {


	@Autowired
	private ProductDao productDao;
	
	@RequestMapping(value = "/s9/products/json", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> getProduct(){
		return productDao.getAll();
	}
	
}
