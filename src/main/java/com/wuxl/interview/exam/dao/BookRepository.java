package com.wuxl.interview.exam.dao;

import com.wuxl.interview.exam.entity.po.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

}
