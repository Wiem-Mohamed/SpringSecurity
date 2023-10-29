package com.global.hr.repository;

import com.global.hr.entity.Book;
import com.global.hr.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    List<Book> getAllBooks();
    Book add(Book book);
    Optional<Book> findById(Long bookId) throws BookNotFoundException;
    void delete(Long bookId) throws BookNotFoundException;
    Book update(Book book);
}