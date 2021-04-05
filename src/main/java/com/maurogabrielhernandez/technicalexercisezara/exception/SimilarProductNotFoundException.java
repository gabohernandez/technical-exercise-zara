package com.maurogabrielhernandez.technicalexercisezara.exception;

public class SimilarProductNotFoundException extends Exception{
	
	public SimilarProductNotFoundException() {
		super("There aren't similar products");
	}

}
