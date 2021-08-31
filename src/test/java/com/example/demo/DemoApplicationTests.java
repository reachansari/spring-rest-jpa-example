package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.controller.EmployeeController;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeJpaRespository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class DemoApplicationTests {
	private Employee emp;
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeJpaRespository empJpaRespository;
	
	@Before
	public void setUp()
	{
		emp = new Employee();
		emp.setId(1l);
		emp.setName("Virat");
		emp.setEmail("virat@gmail.com");
		emp.setTeamName("Testing");
		emp.setMobile("99880865432");
		emp.setSalary(15000);
	}

	@Test
	public void userCreationTest() throws Exception {
		when(empJpaRespository.findByName(emp.getName())).thenReturn(emp);
		ObjectMapper mapper = new ObjectMapper();
		String transactionString = mapper.writeValueAsString(emp);

		MvcResult result = mockMvc.perform(post("/employees/load").content(transactionString).contentType(MediaType.APPLICATION_JSON))
								  .andExpect(status().isOk()).andReturn();
		String responseUser = result.getResponse().getContentAsString();

		JSONAssert.assertEquals(responseUser, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void findAllUsersTest() throws Exception {
		List<Employee> users = new ArrayList<>();
		users.add(emp);

		when(empJpaRespository.findAll()).thenReturn(users);

		MvcResult result = mockMvc.perform(get("/employees/all")).andExpect(status().isOk()).andReturn();
		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(users),
								result.getResponse().getContentAsString(), false);
	}

	@Test
	public void findUserByNameTest() throws Exception {
		when(empJpaRespository.findByName(emp.getName())).thenReturn(emp);

		MvcResult result = mockMvc.perform(get("/employees/{name}", "Virat")).andExpect(status().isOk()).andReturn();

		JSONAssert.assertEquals(new ObjectMapper().writeValueAsString(emp),
								result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void findUserByNameWithWrongNameTest() throws Exception {
		when(empJpaRespository.findByName(emp.getName())).thenReturn(emp);

		MvcResult result = mockMvc.perform(get("/employees/{name}", "Rohit")).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse().getContentAsString().length(), is(0));
	}

}
