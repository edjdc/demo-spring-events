package edjdc.demo.springevents.service.impl;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

import edjdc.demo.springevents.assembler.PersonAssembler;
import edjdc.demo.springevents.dto.PersonDTO;
import edjdc.demo.springevents.event.type.EntityCreatedEvent;
import edjdc.demo.springevents.exception.PersonNotFoundException;
import edjdc.demo.springevents.model.Person;
import edjdc.demo.springevents.repository.PersonRepository;

@RunWith(SpringRunner.class)
public class PersonServiceImplTest {

	private PersonServiceImpl personServiceImpl;

	@MockBean
	private PersonRepository personRepository;

	@MockBean
	private PersonAssembler personAssembler;
	
	@MockBean
	private ApplicationEventPublisher applicationEventPublisher;

	@Before
	public void setUp() {
		personServiceImpl = new PersonServiceImpl(personAssembler, personRepository, applicationEventPublisher);
	}

	@Test
	public void shouldFind() {
		Person person = new Person();
		person.setId(1L);
		person.setFirstName("Edivilson");
		person.setLastName("Dalacosta");
		person.setAge(25);

		PersonDTO personDTO = new PersonDTO();
		personDTO.setId(person.getId());
		personDTO.setFirstName(person.getFirstName());
		personDTO.setLastName(person.getLastName());
		personDTO.setAge(person.getAge());

		when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		when(personAssembler.toPersonDTO(person)).thenReturn(personDTO);
		doNothing().when(applicationEventPublisher).publishEvent(any(EntityCreatedEvent.class));

		PersonDTO dto = personServiceImpl.findOne(1L);

		assertThat(dto.getId(), equalTo(personDTO.getId()));
		assertThat(dto.getFirstName(), equalTo(personDTO.getFirstName()));
		assertThat(dto.getLastName(), equalTo(personDTO.getLastName()));
		assertThat(dto.getAge(), equalTo(personDTO.getAge()));
	}

	@Test(expected = PersonNotFoundException.class)
	public void shoulThrowPersonNotFoundExceptionOnFind() {
		when(personRepository.findById(1L)).thenReturn(Optional.empty());
		personServiceImpl.findOne(1L);
	}

}
