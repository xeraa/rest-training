package net.xeraa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Book {

  private final Long id;
  private String title;
  private String isbn;
  private String author;
  private int pages;

}
