package com.maurogabrielhernandez.technicalexercisezara.exception;

public class ProductNotFoundException extends Exception{
	
	public ProductNotFoundException(String productId) {
		super("There isn't product with id: ".concat(productId));
	}


}
