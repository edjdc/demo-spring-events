package edjdc.demo.springevents.event.listener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edjdc.demo.springevents.dto.CreatedPersonDTO;
import edjdc.demo.springevents.service.PersonService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BoostrapPerson {

	private PersonService personService;

	public BoostrapPerson(PersonService personService) {
		this.personService = personService;
	}

	@EventListener
	public void onContextRefreshedEvent(ContextRefreshedEvent event) {
		log.info("Start bootstrap person");
		
		CreatedPersonDTO p1 = new CreatedPersonDTO();
		p1.setFirstName("Pedro");
		p1.setLastName("Silva");
		p1.setAge(70);
		
		CreatedPersonDTO p2 = new CreatedPersonDTO();
		p2.setFirstName("João");
		p2.setLastName("Santos");
		p2.setAge(30);
		
		CreatedPersonDTO p3 = new CreatedPersonDTO();
		p3.setFirstName("Maria");
		p3.setLastName("Pereira");
		p3.setAge(20);
		
		personService.create(p1);
		personService.create(p2);
		personService.create(p3);
		
		log.info("End bootstrap person");
	}
}
