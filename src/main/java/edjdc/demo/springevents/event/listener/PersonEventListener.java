package edjdc.demo.springevents.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import edjdc.demo.springevents.event.type.EntityCreatedEvent;
import edjdc.demo.springevents.model.Person;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PersonEventListener {

	@EventListener
	public void onPersonCreatedSync(EntityCreatedEvent<Person> event) {
		log.info("sync listener: {0}", event.getEntity());
	}

	@EventListener
	@Async
	public void onPersonCreatedAsync(EntityCreatedEvent<Person> event) throws InterruptedException {
		Thread.sleep(1000L);
		log.info("async listener: {0}", event.getEntity());
	}

}
