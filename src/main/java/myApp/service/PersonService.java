package myApp.service;

import java.util.List;
import java.util.Optional;

import myApp.entity.PersonEntity;
import org.springframework.stereotype.Service;

@Service
public interface PersonService {

	/**
	 * List person with even id
	 * 
	 * @return List<PersonEntity>
	 */
	List<PersonEntity> findUsers();

	Optional<PersonEntity> findUser(Integer id);

	PersonEntity save(PersonEntity personEntity);

	void delete(PersonEntity personEntity);
}
