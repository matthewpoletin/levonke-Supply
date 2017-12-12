package com.levonke.Supply.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.levonke.Supply.domain.Component;
import com.levonke.Supply.domain.Manufacturer;
import com.levonke.Supply.repository.ComponentRepository;
import com.levonke.Supply.service.ComponentServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levonke.Supply.web.model.ComponentRequest;
import com.levonke.Supply.web.model.ComponentResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ComponentController Test")
class ComponentControllerTest {
	
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
	private ComponentServiceImpl componentService;
	
	@MockBean
	private ComponentRepository componentRepositoryMock;
	
	@BeforeAll
	void setUpMockMvc(WebApplicationContext wac) {
		mockMvc = webAppContextSetup(wac)
			.build();
	}
	
	@Test
	@DisplayName("Get components")
	void getComponents() throws Exception {
		// Arrange
		List<Component> components = new ArrayList<Component>() {{
			add(component);
		}};
		
		PageRequest pr = PageRequest.of(0, 25);
		PageImpl<Component> componentPage = new PageImpl<>(components, pr, 100);
		
		when(componentRepositoryMock.findAll(any(Pageable.class))).thenReturn(componentPage);
		
		List<ComponentResponse> expectedResponse = components
			.stream()
			.map(ComponentResponse::new)
			.collect(Collectors.toList());
		
		// Act
		MvcResult result = this.mockMvc.perform(
				get(ComponentController.componentBaseURI + "/components")
					.param("page", "0")
					.param("size", "25")
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$[0].id").exists())
			.andReturn();
		
		List<ComponentResponse> actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<ComponentResponse>>() { });
		
		// Assert
		assertEquals("Invalid component response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Create component")
	void createComponent() throws Exception {
		// Arrange
		Component componentNoId = new Component()
			.setManufacturerPartNumber("ManufacturerPartNumber");
//			.setUuid();
//			.setManufacturer("Password");
		
		when(componentService.generateUUID()).thenReturn(null);
		when(componentRepositoryMock.save(componentNoId)).thenReturn(component);
		
		ComponentRequest componentRequest = new ComponentRequest()
			.setManufacturerPartNumber("ManufacturerPartNumber");
//			.setUuid();
//			.setManufacturer("Password");
		
		// Act
		MvcResult result = this.mockMvc.perform(
				post(ComponentController.componentBaseURI + "/components")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(objectMapper.writeValueAsString(componentRequest))
			)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", ComponentController.componentBaseURI + "/components" + "/1"))
			.andReturn();
		
		ComponentResponse actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ComponentResponse>() { });
		ComponentResponse expectedResponse = new ComponentResponse(component);
		
		// Assert
		verify(componentRepositoryMock, times(1)).save(componentNoId);
		assertEquals("Invalid component response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Get component")
	void getComponent() throws Exception {
		// Arrange
		Optional<Component> componentOptional = Optional.of(component);
		
		when(componentRepositoryMock.findById(1)).thenReturn(componentOptional);
		
		// Act
		MvcResult result = this.mockMvc.perform(
				get(ComponentController.componentBaseURI + "/components" + "/1")
			)
			.andExpect(status().is2xxSuccessful())
			.andReturn();
		
		ComponentResponse expectedResponse = new ComponentResponse(component);
		ComponentResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<ComponentResponse>() { });
		
		// Assert
		verify(componentRepositoryMock, times(1)).findById(1);
		assertEquals("Invalid component response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Update component")
	void updateComponent() throws Exception {
		// Arrange
		Component componentUpdated = new Component()
			.setId(1)
			.setManufacturerPartNumber("MANUFACTURERPARTNUMBER");
//			.setUuid();
//			.setManufacturer();
		
		Optional<Component> componentOptional = Optional.of(component);
		
		when(componentRepositoryMock.findById(1)).thenReturn(componentOptional);
		when(componentRepositoryMock.save(any(Component.class))).thenReturn(componentUpdated);
		
		ComponentRequest componentRequest = new ComponentRequest()
			.setManufacturerPartNumber("MANUFACTURERPARTNUMBER");
		
		// Act
		MvcResult result = this.mockMvc.perform(
				patch(ComponentController.componentBaseURI + "/components" + "/1")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(objectMapper.writeValueAsString(componentRequest))
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id").exists())
			.andReturn();
		
		ComponentResponse expectedResponse = new ComponentResponse(component);
		expectedResponse.setManufacturerPartNumber("MANUFACTURERPARTNUMBER");
		ComponentResponse actualResponse = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<ComponentResponse>() { });
		
		// Assert
		verify(componentRepositoryMock, times(1)).findById(1);
		verify(componentRepositoryMock, times(1)).save(componentUpdated);
		assertEquals("Invalid component response", expectedResponse, actualResponse);
	}
	
	@Test
	@DisplayName("Delete component")
	void deleteComponent() throws Exception {
		// Act
		this.mockMvc.perform(
				delete(ComponentController.componentBaseURI + "/components" + "/1")
			)
			.andExpect(status().isNoContent());
		
		// Assert
		verify(componentRepositoryMock, times(1)).deleteById(1);
	}
	
}
