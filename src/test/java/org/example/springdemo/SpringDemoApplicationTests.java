package org.example.springdemo;



import org.example.springdemo.dao.entity.Employee;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
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
    @Order(2)
    void should_return_employee_when_get_given_a_valid_id() throws Exception{
        String requestBody = """
                  {
                       "name": "Sara",
                       "age": 20,
                       "salary": 5000.0,
                       "gender": "female"
                   }
                """;
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));


        mockMvc.perform(get("/employees/{id}",1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.salary").value(5000.0))
                .andExpect(jsonPath("$.gender").value("male"));
    }

    @Test
    @Order(3)
    void should_get_employees_when_get_given_valid_gender() throws Exception{
        Employee expect = new Employee(1, "male", 20, "Tom", 5000.0);
        mockMvc.perform(get("/employees/gender?gender=male").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expect.getId()))
                .andExpect(jsonPath("$[0].name").value(expect.getName()))
                .andExpect(jsonPath("$[0].age").value(expect.getAge()))
                .andExpect(jsonPath("$[0].salary").value(expect.getSalary()))
                .andExpect(jsonPath("$[0].gender").value(expect.getGender()));

    }

    @Test
    @Order(4)
    void should_return_all_employees_when_get_given_no_parameters() throws Exception {
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

}
