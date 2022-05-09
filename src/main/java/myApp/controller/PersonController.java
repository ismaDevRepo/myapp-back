package myApp.controller;

import myApp.entity.PersonEntity;
import myApp.exception.PersonNotFoundException;
import myApp.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {


    private PersonServiceImpl personServiceImpl;

    @Autowired
    public PersonController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @GetMapping
    public List<PersonEntity> list() {
        return personServiceImpl.findUsers();
    }

    @GetMapping("/{id}")
    public PersonEntity getOne(@PathVariable("id") Integer id) {
        Optional<PersonEntity> oPersonneEntity = personServiceImpl.findUser(id);

        return oPersonneEntity.orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<PersonEntity> insert(@RequestBody PersonEntity personEntity) {
        PersonEntity person = personServiceImpl.save(personEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(person);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<PersonEntity> patch(@PathVariable("id") Integer id, @RequestBody PersonEntity personEntity) {
        PersonEntity personneEntity = personServiceImpl.findUser(id).orElseThrow(() -> new PersonNotFoundException(id));

        personneEntity.setLastName(personEntity.getLastName());
        personneEntity.setFirstName(personEntity.getFirstName());

        return ResponseEntity.accepted().body(personServiceImpl.save(personneEntity));
    }
}
