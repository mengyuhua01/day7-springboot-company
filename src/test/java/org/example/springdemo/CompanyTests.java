package org.example.springdemo;

import org.example.springdemo.Controller.CompanyController;
import org.example.springdemo.dao.CompanyRepository;
import org.example.springdemo.service.CompanyService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp(){
      companyRepository.clear();
    }

    @Test
    void should_create_company_when_post_given_a_valid_body() throws Exception{
        String requestBody = """
                  {
                      "name": "Java"
                   }
                """;
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void should_return_company_when_get_given_a_valid_id() throws Exception{
        String requestBody = """
                  {
                       "name": "Spring"
                   }
                """;
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));


        mockMvc.perform(get("/companies/{id}",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Spring"));
    }
    @Test
    void should_return_all_companies_when_get_given_no_parameters() throws Exception {
        String requestBody = """
                  {
                       "name": "Spring"
                   }
                """;
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
        String requestBody1 = """
                  {
                       "name": "JAVA"
                   }
                """;
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
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
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(createRequestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        String updateRequestBody = """
                  {
                       "name": "OOCL"
                   }
                """;
        mockMvc.perform(put("/companies/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("OOCL"));
    }
    @Test
    void should_delete_company_when_delete_given_valid_id() throws Exception {
        String createRequestBody = """
          {
               "name": "Casco"
           }
        """;
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(createRequestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(delete("/companies/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/companies/{id}", 1).contentType(MediaType.APPLICATION_JSON))
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
                    .andExpect(jsonPath("$.id").value(i));
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
