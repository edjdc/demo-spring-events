package edjdc.demo.springevents.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edjdc.demo.springevents.assembler.PersonAssembler;
import edjdc.demo.springevents.dto.CreatedPersonDTO;
import edjdc.demo.springevents.dto.PersonDTO;
import edjdc.demo.springevents.event.type.EntityCreatedEvent;
import edjdc.demo.springevents.exception.PersonNotFoundException;
import edjdc.demo.springevents.model.Person;
import edjdc.demo.springevents.repository.PersonRepository;
import edjdc.demo.springevents.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonAssembler personAssembler;
	private PersonRepository personRepository;
	private ApplicationEventPublisher applicationEventPublisher;

	public PersonServiceImpl(PersonAssembler personAssembler, PersonRepository personRepository,
			ApplicationEventPublisher applicationEventPublisher) {
		this.personAssembler = personAssembler;
		this.personRepository = personRepository;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	@Transactional
	public Long create(CreatedPersonDTO dto) {
		Person person = personAssembler.toPerson(dto);
		personRepository.save(person);
		applicationEventPublisher.publishEvent(new EntityCreatedEvent<Person>(person));
		return person.getId();
	}

	@Override
	public PersonDTO findOne(Long id) {
		Optional<Person> person = personRepository.findById(id);
		return person.map((p) -> personAssembler.toPersonDTO(p)).orElseThrow(PersonNotFoundException::new);
	}

	@Override
	public List<PersonDTO> findAll() {
		List<Person> persons = personRepository.findAll();
		return persons.stream().map((p) -> personAssembler.toPersonDTO(p)).collect(Collectors.toList());
	}

}
