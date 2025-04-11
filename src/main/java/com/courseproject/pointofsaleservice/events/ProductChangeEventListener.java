package com.courseproject.pointofsaleservice.events;

import com.courseproject.pointofsaleservice.events.handler.ProductChangeHandler;
import com.courseproject.pointofsaleservice.events.models.ProductChangeModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.function.Consumer;

@AllArgsConstructor
@Slf4j
@Configuration
public class ProductChangeEventListener {

	@Autowired
	private ProductChangeHandler productChangeHandler;

	@Bean
	public MessageConverter kafkaMessageConverter() {
		return new StringJsonMessageConverter();
	}

	@Bean
	public Consumer<ProductChangeModel> productChangeModelConsumer() {
		return productChangeModel -> {
			log.debug("Received a new productChangeModel: {}",
					productChangeModel.getProductId());
			productChangeHandler.loggerSink(productChangeModel);
		};
	}

	@KafkaListener(topics = "productChangeModel", groupId = "productGroup", containerFactory = "kafkaListenerContainerFactory")
	public void listen(ProductChangeModel productChangeModel) {
		log.debug("Received a new productChangeModel: {}", productChangeModel.getProductId());
		productChangeHandler.loggerSink(productChangeModel);
	}
}
