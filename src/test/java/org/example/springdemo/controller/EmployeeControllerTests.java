package org.example.springdemo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springdemo.repository.EmployeeRepository;
import org.example.springdemo.repository.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.clear();
    }


    @Test
    void should_create_employee_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(20));

    }

    @Test
    void should_return_employee_when_get_given_a_valid_id() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        long id = createEmployee(requestBody);
        mockMvc.perform(get("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.salary").value(5000.0))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @Test
    void should_get_employees_when_get_given_valid_gender() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        long id = createEmployee(requestBody);
        Employee expect = new Employee(id, "male", 20, "Tom", 5000.0);
        mockMvc.perform(get("/employees?gender=male").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expect.getId()))
                .andExpect(jsonPath("$[0].name").value(expect.getName()))
                .andExpect(jsonPath("$[0].age").value(expect.getAge()))
                .andExpect(jsonPath("$[0].salary").value(expect.getSalary()))
                .andExpect(jsonPath("$[0].gender").value(expect.getGender()));

    }

    @Test
    void should_return_all_employees_when_get_given_no_parameters() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        createEmployee(requestBody);
        String requestBody1 = """
                  {
                       "name": "Sara",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "female"
                   }
                """;
        createEmployee(requestBody1);
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_update_employee_when_put_given_valid_id_and_body() throws Exception {
        String createRequestBody = """
                  {
                       "name": "Alice",
                       "age": 25,
                       "salary": 7000.0,
                       "gender": "female"
                   }
                """;
        long id = createEmployee(createRequestBody);

        String updateRequestBody = String.format("""
                   {
                       "id": %d,
                       "name": "Alice",
                       "age": 30,
                       "salary": 8000.0,
                       "gender": "female",
                   }
                """, id);
        mockMvc.perform(put("/employees").contentType(MediaType.APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.salary").value(8000.0))
                .andExpect(jsonPath("$.gender").value("female"));
    }

    @Test
    void should_delete_employee_when_delete_given_valid_id() throws Exception {
        String createRequestBody = """
                  {
                       "name": "Bob",
                       "age": 28,
                       "salary": 6000.0,
                       "gender": "male"
                   }
                """;
        long id = createEmployee(createRequestBody);

        mockMvc.perform(delete("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activeStatus").value(false));
    }

    @Test
    void should_return_paginated_employees_when_get_given_valid_page_and_size() throws Exception {
        for (int i = 1; i <= 6; i++) {
            String requestBody = String.format("""
                      {
                           "name": "Employee%d",
                           "age": %d,
                           "salary": %d,
                           "gender": "male"
                       }
                    """, i, 20 + i, 5000 + i * 100);
            createEmployee(requestBody);
        }

        mockMvc.perform(get("/employees/page?page=1&size=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Employee6"));
    }

    @Test
    public void should_not_create_employee_when_post_given_invalid_employee_age() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 17,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void should_not_return_employee_when_post_given_not_existed_employee_id() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        long id = createEmployee(requestBody);
        mockMvc.perform(get("/employees/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void should_not_create_employee_when_post_given_employee_age_over_inclusive_30_and_salary_below_200000() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 31,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());

    }

    @Test
    void should_create_employee_and_status_is_true_when_post_given_a_valid_body() throws Exception {
        String requestBody = """
                  {
                       "name": "Tom",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "male"
                   }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.activeStatus").value(true));
    }

    @Test
    void should_return_error_when_put_given_inactive_employee() throws Exception {
        String createRequestBody = """
                  {
                       "name": "Bob",
                       "age": 28,
                       "salary": 6000.0,
                       "gender": "male"
                   }
                """;
        long id = createEmployee(createRequestBody);

        mockMvc.perform(delete("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        String updateRequestBody = """
                   {
                       "name": "Bob Updated",
                       "age": 30,
                       "salary": 6500.0,
                       "gender": "male"
                   }
                """;
        mockMvc.perform(put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("you can't update inactive employee"));
    }

    private long createEmployee(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }


}
