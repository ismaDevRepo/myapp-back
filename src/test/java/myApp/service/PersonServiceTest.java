package myApp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import myApp.entity.PersonEntity;
import myApp.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

	@InjectMocks
	private PersonServiceImpl personService;

	@Mock
	private PersonRepository repository;

	@Test
	public void listAllPersonEvenId() {
		// Create new persons entities
		PersonEntity personEntity1 = new PersonEntity(100, "Nom 1", "Prenom 1");
		PersonEntity personEntity2 = new PersonEntity(101, "Nom 2", "Prenom 2");
		PersonEntity personEntity3 = new PersonEntity(102, "Nom 3", "Prenom 3");
		PersonEntity personEntity4 = new PersonEntity(103, "Nom 4", "Prenom 4");

		when(repository.findAll())
				.thenReturn(Arrays.asList(personEntity1, personEntity2, personEntity3, personEntity4));

		List<PersonEntity> lPersonEntity = personService.findUsers();

		assertEquals(Integer.valueOf(100), lPersonEntity.get(0).getId());
		assertEquals(Integer.valueOf(102), lPersonEntity.get(1).getId());
	}

	@Test
	public void getOnePerson() {
		// Create new persons entities
		PersonEntity personEntity1 = new PersonEntity(100, "Nom 1", "Prenom 1");

		when(repository.findById(personEntity1.getId())).thenReturn(Optional.of(personEntity1));

		Optional<PersonEntity> oPersonEntity = personService.findUser(personEntity1.getId());

		assertEquals(personEntity1.getId(), oPersonEntity.get().getId());
	}

	@Test
	public void savePerson() {
		// Create new persons entities
		PersonEntity personEntity1 = new PersonEntity(100, "Nom 1", "Prenom 1");

		when(repository.save(personEntity1)).thenReturn(personEntity1);

		PersonEntity personEntity = personService.save(personEntity1);

		assertEquals(Integer.valueOf(100), personEntity.getId());
	}

	@Test
	public void deletePerson() {
		// Create new persons entities
		PersonEntity personEntity1 = new PersonEntity(100, "Nom 1", "Prenom 1");

		doNothing().when(repository).delete(personEntity1);

		personService.delete(personEntity1);

		verify(repository, times(1)).delete(personEntity1);
		verifyNoMoreInteractions(repository);
	}
}
