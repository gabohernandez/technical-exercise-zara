package com.maurogabrielhernandez.technicalexercisezara.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Product {
	
	private String id;
	private String name;
	private BigDecimal price;
	private boolean availability;

}
