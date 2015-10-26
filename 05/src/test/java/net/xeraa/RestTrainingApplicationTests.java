package net.xeraa;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.xeraa.controller.Book2Controller;
import net.xeraa.model.Book2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestTrainingApplication.class)
@WebAppConfiguration
public class RestTrainingApplicationTests {

  @Rule
  public final RestDocumentation
      restDocumentation = new RestDocumentation("build/generated-snippets");

  @Autowired
  private WebApplicationContext context;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
	.apply(documentationConfiguration(this.restDocumentation))
	.build();
  }

  @Test
  public void emptyStart() throws Exception {
    mockMvc.perform(get(Book2Controller.URL).accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$", hasSize(0)))
        .andDo(document("index"));
  }

  @Test
  public void create() throws Exception {
    Book2 book = new Book2();
    book.setTitle("Ein");
    book.setAuthor("Philipp");
    book.setIsbn("1234");
    book.setPages(100);
    byte[] bookJson = toJson(book);

    //CREATE
    MvcResult result = mockMvc.perform(post(Book2Controller.URL)
                               .content(bookJson)
                               .contentType(MediaType.APPLICATION_JSON)
                               .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andReturn();
    long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

    //RETRIEVE
    mockMvc.perform(get(Book2Controller.URL + "/" + id).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is((int) id)))
        .andExpect(jsonPath("$.title", is(book.getTitle())))
        .andExpect(jsonPath("$.author", is(book.getAuthor())))
        .andExpect(jsonPath("$.isbn", is(book.getIsbn())))
        .andExpect(jsonPath("$.pages", is(book.getPages())));

    //DELETE
    mockMvc.perform(delete(Book2Controller.URL + "/" + id))
        .andExpect(status().isNoContent());
  }

  private long getResourceIdFromUrl(String locationUrl) {
    String[] parts = locationUrl.split("/");
    return Long.valueOf(parts[parts.length - 1]);
  }

  private byte[] toJson(Object r) throws Exception {
    ObjectMapper map = new ObjectMapper();
    return map.writeValueAsString(r).getBytes();
  }

}
