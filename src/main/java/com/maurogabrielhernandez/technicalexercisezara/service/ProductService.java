package com.maurogabrielhernandez.technicalexercisezara.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.maurogabrielhernandez.technicalexercisezara.exception.ProductNotFoundException;
import com.maurogabrielhernandez.technicalexercisezara.exception.ServiceException;
import com.maurogabrielhernandez.technicalexercisezara.exception.SimilarProductNotFoundException;
import com.maurogabrielhernandez.technicalexercisezara.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ProductService {
	
	@Autowired
	private WebClient client;
	
	private static final String PRODUCT_PATH = "/product/{id}";
	private static final String PRODUCT_SIMILAR_PATH = PRODUCT_PATH.concat("/similarids");
	
	public Flux<Product> getSimilarProductsForId(String productId){
		return this.getSimilarIds(productId)
				.parallel()
				.runOn(Schedulers.boundedElastic())
				.flatMap(id -> getSimilarProduct(String.valueOf(id)))
				.ordered((p1, p2) -> p2.getId().compareTo(p1.getId()));
	}

	private Flux<Integer> getSimilarIds(String productId) {
		return client
				.get()
				.uri(PRODUCT_SIMILAR_PATH, productId)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new SimilarProductNotFoundException()))
			    .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ServiceException()))				
			    .bodyToFlux(Integer.class)
			    .onErrorResume(Exception.class, (e) -> Flux.empty());
	}

	public Mono<Product> getSimilarProduct(String productId){
		return client
				.get()
				.uri(PRODUCT_PATH, productId)
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, response ->  Mono.error(new ProductNotFoundException(productId)))
				.onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ServiceException()))
				.bodyToMono(Product.class)
				.onErrorResume(Exception.class, (e) -> Mono.empty());
	}
	
}