package com.rabobank.assignment.controller;

import com.rabobank.assignment.*;
import com.rabobank.assignment.repository.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.mock.web.*;
import org.springframework.test.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;
import org.springframework.test.web.servlet.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AssignmentApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    InputStream resource;


    @BeforeEach
    public void initEach() throws IOException {
        resource = new ClassPathResource(
                "issues.csv").getInputStream();

    }


    @Test
    @DisplayName("Import csv")
    void importUsers() throws Exception {
        MockMultipartFile mpFile = getFile("text/csv");
        mockMvc
                .perform(multipart("/users/import")
                        .file(mpFile)
                )

                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(3, userRepository.count());
    }

    @Test
    @DisplayName("Import csv and has wrong format")
    void importUsersWrongFormat() throws Exception {
        MockMultipartFile mpFile = getFile("application/pdf");
        mockMvc
                .perform(multipart("/users/import")
                        .file(mpFile)
                )

                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Get User by Id and get Age")
    void getUser() throws Exception {

        userRepository.save(new User(88101015, "Hanna", "Smith", 6, "1989-12-01"));
        mockMvc
                .perform(get("/users")
                        .queryParam("id", ("88101015"))
                )

                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.age").value(32));


    }

    private MockMultipartFile getFile(String contentType) throws IOException {
        return new MockMultipartFile(
                "issues",
                "issues.csv",
                contentType,
                resource);
    }

}