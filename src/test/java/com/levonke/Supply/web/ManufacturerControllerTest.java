package com.levonke.Supply.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.repository.ManufacturerRepository;
import com.levonke.Supply.service.ManufacturerServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levonke.Supply.web.model.ManufacturerRequest;
import com.levonke.Supply.web.model.ManufacturerResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ManufacturerController Test")
class ManufacturerControllerTest {
	
	private final Manufacturer manufacturer = new Manufacturer()
		.setId(1)
		.setName("Name")
		.setWebsite("server.com");
	
	private final Component component = new Component()
		.setId(1)
		.setManufacturerPartNumber("ManufacturerPartNumber");
//		.setManufacturer(manufacturer);
//		.setUuid("uuid");
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private ManufacturerServiceImpl manufacturerService;
	
	@MockBean
	private ManufacturerRepository manufacturerRepositoryMock;
	
	@BeforeAll
	void setUpMockMvc(WebApplicationContext wac) {
		mockMvc = webAppContextSetup(wac)
			.build();
	}
	
	@Test
	@DisplayName("Get manufacturers")
	void getManufacturers() throws Exception {
		// Arrange
		List<Manufacturer> manufacturers = new ArrayList<Manufacturer>() {{
			add(manufacturer);
		}};
		
		PageRequest pr = PageRequest.of(0, 25);
		PageImpl<Manufacturer> manufacturerPage = new PageImpl<>(manufacturers, pr, 100);
		
		when(manufacturerRepositoryMock.findAll(any(Pageable.class))).thenReturn(manufacturerPage);
		
		List<ManufacturerResponse> expectedResponse = manufacturers
			.stream()
			.map(ManufacturerResponse::new)
			.collect(Collectors.toList());
		
		// Act
		MvcResult result = this.mockMvc.perform(
				get(ManufacturerController.manufacturerBaseURI + "/manufacturers")
					.param("page", "0")
					.param("size", "25")
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id").exists())
			.andReturn();
		
		List<ManufacturerResponse> actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ManufacturerResponse>>() { });
		
		// Assert
		assertEquals("Invalid manufacturer response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Create manufacturer")
	void createManufacturer() throws Exception {
		// Arrange
		Manufacturer manufacturerNoId = new Manufacturer()
			.setName("Name")
			.setWebsite("server.com");
		
		when(manufacturerRepositoryMock.save(manufacturerNoId)).thenReturn(manufacturer);
		
		ManufacturerRequest manufacturerRequest = new ManufacturerRequest()
			.setName("Name")
			.setWebsite("server.com");
		
		// Act
		MvcResult result = this.mockMvc.perform(
				post(ManufacturerController.manufacturerBaseURI + "/manufacturers")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(objectMapper.writeValueAsString(manufacturerRequest))
			)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", ManufacturerController.manufacturerBaseURI + "/manufacturers" + "/1"))
			.andReturn();
		
		ManufacturerResponse actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ManufacturerResponse>() { });
		ManufacturerResponse expectedResponse = new ManufacturerResponse(manufacturer);
		
		// Assert
		verify(manufacturerRepositoryMock, times(1)).save(manufacturerNoId);
		assertEquals("Invalid manufacturer response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Get manufacturer")
	void getManufacturer() throws Exception {
		// Arrange
		Optional<Manufacturer> manufacturerOptional = Optional.of(manufacturer);
		
		when(manufacturerRepositoryMock.findById(1)).thenReturn(manufacturerOptional);
		
		// Act
		MvcResult result = this.mockMvc.perform(
				get(ManufacturerController.manufacturerBaseURI + "/manufacturers" + "/1")
			)
			.andExpect(status().is2xxSuccessful())
			.andReturn();
		
		ManufacturerResponse expectedResponse = new ManufacturerResponse(manufacturer);
		ManufacturerResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<ManufacturerResponse>() { });
		
		// Assert
		verify(manufacturerRepositoryMock, times(1)).findById(1);
		assertEquals("Invalid manufacturer response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Update manufacturer")
	void updateManufacturer() throws Exception {
		// Arrange
		Manufacturer manufacturerUpdated = new Manufacturer()
			.setId(1)
			.setName("NAME")
			.setWebsite("server.com");
		
		Optional<Manufacturer> manufacturerOptional = Optional.of(manufacturer);
		
		when(manufacturerRepositoryMock.findById(1)).thenReturn(manufacturerOptional);
		when(manufacturerRepositoryMock.save(any(Manufacturer.class))).thenReturn(manufacturerUpdated);
		
		ManufacturerRequest manufacturerRequest = new ManufacturerRequest()
			.setName("NAME");
		
		// Act
		MvcResult result = this.mockMvc.perform(
				patch(ManufacturerController.manufacturerBaseURI + "/manufacturers" + "/1")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(objectMapper.writeValueAsString(manufacturerRequest))
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id").exists())
			.andReturn();
		
		ManufacturerResponse expectedResponse = new ManufacturerResponse(manufacturer);
		expectedResponse.setName("NAME");
		ManufacturerResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<ManufacturerResponse>() { });
		
		// Assert
		verify(manufacturerRepositoryMock, times(1)).findById(1);
		verify(manufacturerRepositoryMock, times(1)).save(manufacturerUpdated);
		assertEquals("Invalid manufacturer response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Delete manufacturer")
	void deleteManufacturer() throws Exception {
		// Act
		this.mockMvc.perform(
				delete(ManufacturerController.manufacturerBaseURI + "/manufacturers" + "/1")
			)
			.andExpect(status().isNoContent());
		
		// Assert
		verify(manufacturerRepositoryMock, times(1)).deleteById(1);
	}
	
}
