package org.example.springdemo;



import org.junit.jupiter.api.Test;
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
class SpringDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

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

}
