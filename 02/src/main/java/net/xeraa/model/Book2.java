package net.xeraa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book2 {

  private Long id;
  private String title;
  private String isbn;
  private String author;
  private int pages;

}

