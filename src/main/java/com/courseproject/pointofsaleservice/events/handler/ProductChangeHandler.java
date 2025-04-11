package com.courseproject.pointofsaleservice.events.handler;

import com.courseproject.pointofsaleservice.events.models.ProductChangeModel;
import com.courseproject.pointofsaleservice.services.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductChangeHandler {

	@Autowired
	ProductService productService;

	private static final Logger logger = LoggerFactory.getLogger(ProductChangeHandler.class);

	public void loggerSink(ProductChangeModel product) {

		logger.debug("Received a message of type " + product.getType());

		switch (product.getAction()) {
			case "GET":
				logger.debug("Received a GET event from the inventory service for inventory id {}",
						product.getProductId());
				break;
			case "SAVE":
				logger.debug("Received a SAVE event from the inventory service for inventory id {}",
						product.getProductId());
				break;
			case "UPDATE":
				logger.debug("Received a UPDATE event from the inventory service for inventory id {}",
						product.getProductId());
				purgeProduct(product.getProductId());
				break;
			case "DELETE":
				logger.debug("Received a DELETE event from the inventory service for inventory id {}",
						product.getProductId());
				purgeProduct(product.getProductId());
				break;
			default:
				logger.error("Received an UNKNOWN event from the inventory service of type {}",
						product.getType());
				break;
		}
	}

	@CacheEvict(value = "product", key = "#productId")
	private void purgeProduct(Long productId) {
		logger.debug("Purging: " + productId);
		productService.deleteProduct(productId);
	}
}
