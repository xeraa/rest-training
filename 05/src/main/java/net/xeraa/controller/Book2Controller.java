package net.xeraa.controller;

import net.xeraa.model.Book2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(Book2Controller.URL)
public class Book2Controller {

  public static final String URL = "/v2/book";

  private Map<Long, Book2> books = new HashMap<>();
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public void createBook(@RequestBody Book2 book, HttpServletRequest request,
                         HttpServletResponse response) {
    book.setId(counter.incrementAndGet());
    books.put(book.getId(), book);

    response.setHeader("Location", request.getRequestURL().append("/")
        .append(book.getId()).toString());
  }

  @RequestMapping(
      method = RequestMethod.GET,
      value="/{bookId}",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody Book2 getBook(@PathVariable("bookId") Long bookId){
    return books.get(bookId);
  }

  @RequestMapping(
      method = RequestMethod.PUT,
      value="/{bookId}",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book2 book){
    book.setId(bookId);
    books.replace(book.getId(), book);
  }

  @RequestMapping(
      method = RequestMethod.DELETE,
      value="/{bookId}",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBook(@PathVariable("bookId") Long bookId){
    books.remove(bookId);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody List<Book2> getAllBooks(
      @RequestParam(value = "page", required = true, defaultValue = "0") Integer page) {
    int i = 0;
    final int SIZE = 3;
    final int LOW = page * SIZE;
    final int HIGH = LOW + SIZE;
    List<Book2> bookPage = new ArrayList<>();
    for (Map.Entry<Long, Book2> book : books.entrySet()) {
      if (i >= LOW && i < HIGH) {
        bookPage.add(book.getValue());
      }
      if (bookPage.size() >= SIZE) {
        break;
      }
      i++;
    }
    return bookPage;
  }

}
