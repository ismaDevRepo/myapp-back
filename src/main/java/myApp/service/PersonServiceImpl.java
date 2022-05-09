package myApp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import myApp.entity.PersonEntity;
import myApp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository personRepository;

	@Autowired
	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public List<PersonEntity> findUsers() {
		List<PersonEntity> lPerson = personRepository.findAll();

		return lPerson.stream()
				.filter(personEntity -> personEntity.getId() % 2 == 0)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<PersonEntity> findUser(Integer id) {
		return personRepository.findById(id);
	}

	@Override
	public PersonEntity save(PersonEntity personEntity) {
		return personRepository.save(personEntity);
	}

	@Override
	public void delete(PersonEntity personEntity) {
		personRepository.delete(personEntity);
	}
}
