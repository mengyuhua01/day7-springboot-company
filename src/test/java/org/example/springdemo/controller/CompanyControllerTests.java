package org.example.springdemo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springdemo.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        companyRepository.clear();
    }

    @Test
    void should_create_company_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                  {
                      "name": "Java"
                   }
                """;
        String response = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        long id = jsonNode.get("id").asLong();
        mockMvc.perform(get("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void should_return_company_when_get_given_a_valid_id() throws Exception {
        String requestBody = """
                  {
                       "name": "Spring"
                   }
                """;
        String response = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        long id = jsonNode.get("id").asLong();
        mockMvc.perform(get("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Spring"));
    }

    @Test
    void should_return_all_companies_when_get_given_no_parameters() throws Exception {
        String requestBody = """
                  {
                       "name": "Spring"
                   }
                """;
        String response1 = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode1 = objectMapper.readTree(response1);
        long id1 = jsonNode1.get("id").asLong();
        String requestBody2 = """
                  {
                       "name": "JAVA"
                   }
                """;
        String response2 = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody2))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode2 = objectMapper.readTree(response2);
        long id2 = jsonNode2.get("id").asLong();
        mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_update_company_when_put_given_valid_id_and_body() throws Exception {
        String createRequestBody = """
                  {
                       "name": "OOP"
                   }
                """;
        String response = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(createRequestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        long id = jsonNode.get("id").asLong();
        String updateRequestBody = String.format("""
                  {
                       "id": %d, 
                       "name": "OOCL"
                   }
                """,id);
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("OOCL"));
    }

    @Test
    void should_delete_company_when_delete_given_valid_id() throws Exception {
        String createRequestBody = """
                  {
                       "name": "Casco"
                   }
                """;
        String response = mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(createRequestBody))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        long id = jsonNode.get("id").asLong();

        mockMvc.perform(delete("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_paginated_companies_when_get_given_valid_page_and_size() throws Exception {
        for (int i = 1; i <= 5; i++) {
            String requestBody = String.format("""
                      {
                           "name": "Company%d"
                       }
                    """, i);
            mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();
        }

        mockMvc.perform(get("/companies/page?page=1&size=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].name").value("Company1"))
                .andExpect(jsonPath("$[1].name").value("Company2"))
                .andExpect(jsonPath("$[2].name").value("Company3"))
                .andExpect(jsonPath("$[3].name").value("Company4"))
                .andExpect(jsonPath("$[4].name").value("Company5"));
    }

}
