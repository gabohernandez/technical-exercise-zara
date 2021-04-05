package com.maurogabrielhernandez.technicalexercisezara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maurogabrielhernandez.technicalexercisezara.model.Product;
import com.maurogabrielhernandez.technicalexercisezara.service.ProductService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("{productId}/similar")
	public ResponseEntity<Flux<Product>> getSimilarProducts(@PathVariable String productId){
		return ResponseEntity.ok(productService.getSimilarProductsForId(productId));
	}

}
