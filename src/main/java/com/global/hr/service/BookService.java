package com.global.hr.service;

import com.global.hr.entity.Book;
import com.global.hr.exception.BookNotFoundException;
import com.global.hr.repository.BookRepository;
import com.global.hr.repository.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> findById(Long bookId) throws BookNotFoundException {
        return Optional.ofNullable(bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException ("No book found with the id : " + bookId)));
    }

    @Override
    public void delete(Long bookId) throws BookNotFoundException {
        Optional<Book> theBook = findById(bookId);
        theBook.ifPresent(book -> bookRepository.deleteById(book.getId()));
    }

    @Override
    public Book update(Book book) {
        return bookRepository.save(book);
    }
}