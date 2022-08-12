package laz.example.urlshortener;

import laz.example.urlshortener.controllers.CrumbController;
import laz.example.urlshortener.services.InMemoryHashMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CrumbController.class)
class CrumbNewControllerShould {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InMemoryHashMapService inMemoryHashMapService;

	@Test
	void whenGivenAURL_ReturnACrumb() throws Exception {
		when(inMemoryHashMapService.genAndStoreURLForCrumb(anyString())).thenReturn("ExampleCrumb");
		this.mockMvc.perform(get("/new").param("fullURL", "https://www.google.com"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("ExampleCrumb")));
	}

}