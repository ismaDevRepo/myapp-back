package myApp.controller;

import myApp.entity.PersonEntity;
import myApp.exception.PersonNotFoundException;
import myApp.service.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    @Test
    public void listAllPersonEntityWithEvenId() throws Exception {
        // Create new persons entities
        PersonEntity personEntity1 = new PersonEntity(100, "Nom 1", "Prenom 1"),
                personEntity2 = new PersonEntity(101, "Nom 2", "Prenom 2");

        when(personService.findUsers()).thenReturn(Arrays.asList(personEntity1, personEntity2));

        mockMvc.perform(get("/persons")).andExpect(status().isOk()).andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(personEntity1.getId())))
                .andExpect(jsonPath("$[0].lastName", equalTo(personEntity1.getLastName())))
                .andExpect(jsonPath("$[0].firstName", equalTo(personEntity1.getFirstName())))
                .andExpect(jsonPath("$[1].id", equalTo(personEntity2.getId())))
                .andExpect(jsonPath("$[1].lastName", equalTo(personEntity2.getLastName())))
                .andExpect(jsonPath("$[1].firstName", equalTo(personEntity2.getFirstName())));
    }

    @Test
    public void getOnePersonEntity() throws Exception {
        // Create new persons entities
        PersonEntity personEntity = new PersonEntity(100, "Nom 1", "Prenom 1");

        when(personService.findUser(personEntity.getId())).thenReturn(Optional.of(personEntity));

        mockMvc.perform(get("/persons/{id}", personEntity.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(personEntity.getId())))
                .andExpect(jsonPath("$.lastName", equalTo(personEntity.getLastName())))
                .andExpect(jsonPath("$.firstName", equalTo(personEntity.getFirstName())));
    }

    @Test
    public void getOnePersonEntityNotFound() throws Exception {
        // Create new persons entities
        PersonEntity personEntity = new PersonEntity(100, "Nom 1", "Prenom 1");

        when(personService.findUser(personEntity.getId())).thenThrow(new PersonNotFoundException(personEntity.getId()));

        mockMvc.perform(get("/persons/{id}", personEntity.getId())).andExpect(status().isNotFound());
    }

    @Test
    public void createPersonEntity() throws Exception {
        // Create new person entity
        PersonEntity personEntity = new PersonEntity(100, "Nom 1", "Prenom 1");

        RequestBuilder request = post("/persons").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(personEntity));

        mockMvc.perform(request).andExpect(status().isCreated());

        // Check returned data
        ArgumentCaptor<PersonEntity> requestDtoArgumentCaptor = ArgumentCaptor.forClass(PersonEntity.class);
        verify(personService).save(requestDtoArgumentCaptor.capture());

        PersonEntity capturedRequestDto = requestDtoArgumentCaptor.getValue();
        assertThat(capturedRequestDto.getId(), equalTo(personEntity.getId()));
        assertThat(capturedRequestDto.getLastName(), equalTo(personEntity.getLastName()));
        assertThat(capturedRequestDto.getFirstName(), equalTo(personEntity.getFirstName()));
    }


}
