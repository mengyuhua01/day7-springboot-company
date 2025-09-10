package org.example.springdemo;



import org.example.springdemo.Controller.CompanyController;
import org.example.springdemo.Controller.EmployeeController;
import org.example.springdemo.dao.entity.Employee;
import org.example.springdemo.service.EmployeeService;
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
class EmployeeTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    void setUp(){
       employeeService.clear();
    }


    @Test
    void should_create_employee_when_post_given_a_valid_body() throws Exception{
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
                .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    void should_return_employee_when_get_given_a_valid_id() throws Exception{
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
                .andExpect(jsonPath("$.id").value(1));


        mockMvc.perform(get("/employees/{id}",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.salary").value(5000.0))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @Test
    void should_get_employees_when_get_given_valid_gender() throws Exception{
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
                .andExpect(jsonPath("$.id").value(1));
        Employee expect = new Employee(1, "male", 20, "Tom", 5000.0);
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
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
        String requestBody1 = """
                  {
                       "name": "Sara",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "female"
                   }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody1))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
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
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(createRequestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        String updateRequestBody = """
               {
                   "name": "Alice",
                   "age": 30,
                   "salary": 8000.0,
                   "gender": "female"
               }
            """;
        mockMvc.perform(put("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(updateRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
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
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(createRequestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mockMvc.perform(delete("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
            mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(i));
        }

        mockMvc.perform(get("/employees/page?page=2&size=5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Employee6"));
    }





}
