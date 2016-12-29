package org.sm.lab.mybooks.test.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.SystemRole;
import org.sm.lab.mybooks.test.web.util.WithMockCustomUser;

@WithMockCustomUser(systemRole = SystemRole.ADMIN)
public class ReaderRestControllerTest extends BaseRestControllerTest {

    private final String RESOURCE_PATH = "/rest/readers/";

    @Test
    public void list() throws Exception {
        List<Reader> readers = loadReaders(12);

        mockMvc.perform(get(RESOURCE_PATH + "?pageNumber=2&pageSize=5")).andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))

                .andExpect(jsonPath("$.number", is(2))).andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.size", is(5))).andExpect(jsonPath("$.numberOfElements", is(2)))
                .andExpect(jsonPath("$.totalPages", is(3))).andExpect(jsonPath("$.first", is(false)))
                .andExpect(jsonPath("$.totalElements", is(12))).andExpect(jsonPath("$.content", hasSize(2)))

                .andExpect(jsonPath("$.content[0].id", is(readers.get(10).getId().intValue())))
                .andExpect(jsonPath("$.content[0].username", is(readers.get(10).getUsername())))
                .andExpect(jsonPath("$.content[0].email", is(readers.get(10).getEmail())))

                .andExpect(jsonPath("$.content[1].id", is(readers.get(11).getId().intValue())))
                .andExpect(jsonPath("$.content[1].username", is(readers.get(11).getUsername())))
                .andExpect(jsonPath("$.content[1].email", is(readers.get(11).getEmail())));

    }

    @Test
    public void getOne() throws Exception {
        Reader reader = loadOneReader();

        mockMvc.perform(get(RESOURCE_PATH + reader.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))

                .andExpect(jsonPath("$.id", is(reader.getId().intValue())))
                .andExpect(jsonPath("$.username", is(reader.getUsername())))
                .andExpect(jsonPath("$.email", is(reader.getEmail())))
                .andExpect(jsonPath("$.password", is(reader.getPassword())));
    }

    @Test
    public void search() throws Exception {
        loadReaders(7);

        mockMvc.perform(get(RESOURCE_PATH + "?search=rac3&pageNumber=0&pageSize=5")).andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))

                .andExpect(jsonPath("$.number", is(0))).andExpect(jsonPath("$.last", is(true)))
                .andExpect(jsonPath("$.size", is(5))).andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1))).andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.totalElements", is(1))).andExpect(jsonPath("$.content", hasSize(1)))

                .andExpect(jsonPath("$.content[0].email", is("agarf.rac3@example.com")));

    }

    @Test
    public void update() throws Exception {
        Reader reader = loadOneReader();
        String usernameUpdated = reader.getUsername() + "-updated";
        reader.setUsername(usernameUpdated);
        String readerJson = json(reader);

        mockMvc.perform(put(RESOURCE_PATH + reader.getId()).with(csrf().asHeader()).contentType(jsonContentType)
                .content(readerJson)).andExpect(status().isOk()).andExpect(content().contentType(jsonContentType))

                .andExpect(jsonPath("$.id", is(reader.getId().intValue())))
                .andExpect(jsonPath("$.username", is(usernameUpdated)));
    }

    @Test
    public void deleteOne() throws Exception {
        Reader reader = loadOneReader();

        mockMvc.perform(delete(RESOURCE_PATH + reader.getId()).with(csrf().asHeader())).andExpect(status().isOk());
    }

}
