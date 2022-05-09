package myApp.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import myApp.entity.PersonEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTests {

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void saveAllListAndDeleteAllPerson() throws Exception {
		PersonEntity person1 = new PersonEntity(1, "Nom 1", "Prenom 1");
		PersonEntity person2 = new PersonEntity(2, "Nom 2", "Prenom 1");
		PersonEntity person3 = new PersonEntity(3, "Nom 3", "Prenom 3");
		PersonEntity person4 = new PersonEntity(4, "Nom 4", "Prenom 4");

		List<PersonEntity> persons = Arrays.asList(person1, person2, person3, person4);

		personRepository.saveAll(persons);

		// Retrieve
		List<PersonEntity> lPers = personRepository.findAll();

		// Should not be null
		assertNotNull(lPers);
		assertEquals(persons.size(), lPers.size());

		// Get one
		PersonEntity person = personRepository.getOne(person1.getId());
		assertEquals(person.getId(), person1.getId());
		assertEquals(person.getLastName(), person1.getLastName());
		assertEquals(person.getFirstName(), person1.getFirstName());

		// Delete all
		personRepository.deleteAll(persons);

		// Check that database is empty
		lPers = personRepository.findAll();
		assertNotNull(lPers);
		assertTrue(lPers.isEmpty());
	}

	@Test
	public void saveGetAndDelete() {
		// Save new person entity
		PersonEntity personEntity = personRepository.save(new PersonEntity(100, "Nom 1", "Prenom 1"));

		// Search inserted person entity
		PersonEntity personEntityFound = personRepository.findById(personEntity.getId()).orElse(null);

		// Should not be null
		assertNotNull(personEntityFound);
		// And person should be equal to the inserted person entity
		assertEquals(personEntityFound.getId(), personEntity.getId());
		assertEquals(personEntityFound.getLastName(), personEntity.getLastName());
		assertEquals(personEntityFound.getFirstName(), personEntity.getFirstName());

		// Delete person entity
		personRepository.delete(personEntity);

		// Search for deleted person entity
		personEntityFound = personRepository.findById(personEntity.getId()).orElse(null);

		// Should return null
		assertNull(personEntityFound);
	}
}