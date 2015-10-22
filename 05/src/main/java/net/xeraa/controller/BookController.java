package net.xeraa.controller;

import net.xeraa.model.Book;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/book")
public class BookController {

  private Map<Long, Book> books = new HashMap<>();
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping(method = RequestMethod.POST)
  public Map<String, Object> createBook(@RequestBody Map<String, Object> bookMap) {
    Book book = Book.builder()
        .id(counter.incrementAndGet())
        .title(bookMap.get("title").toString())
        .isbn(bookMap.get("isbn").toString())
        .author(bookMap.get("author").toString())
        .pages(Integer.parseInt(bookMap.get("pages").toString()))
        .build();
    books.put(book.getId(), book);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Book created");
    response.put("book", book);
    return response;
  }

  @RequestMapping(method = RequestMethod.GET, value="/{bookId}")
  public Book getBook(@PathVariable("bookId") String bookId){
    return books.get(Long.parseLong(bookId));
  }

  @RequestMapping(method = RequestMethod.PUT, value="/{bookId}")
  public Map<String, Object> updateBook(@PathVariable("bookId") String bookId,
                                      @RequestBody Map<String, Object> bookMap){
    Book book = Book.builder()
        .id(Long.parseLong(bookId))
        .title(bookMap.get("title").toString())
        .isbn(bookMap.get("isbn").toString())
        .author(bookMap.get("author").toString())
        .pages(Integer.parseInt(bookMap.get("pages").toString()))
        .build();
    books.replace(book.getId(), book);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Book updated");
    response.put("book", book);
    return response;
  }

  @RequestMapping(method = RequestMethod.DELETE, value="/{bookId}")
  public Map<String, String> deleteBook(@PathVariable("bookId") String bookId){
    books.remove(Long.parseLong(bookId));
    Map<String, String> response = new HashMap<>();
    response.put("message", "Book deleted");
    return response;
  }

  @RequestMapping(method = RequestMethod.GET)
  public Map<String, Object> getAllBooks(){
    Map<String, Object> response = new HashMap<>();
    response.put("booksCount", books.size());
    response.put("books", books);
    return response;
  }

}
