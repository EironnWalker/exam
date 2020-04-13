package com.wuxl.interview.exam.rest;

import com.wuxl.interview.exam.constant.Result;
import com.wuxl.interview.exam.dao.BookRepository;
import com.wuxl.interview.exam.entity.param.DelBookParam;
import com.wuxl.interview.exam.entity.param.UpdateBookParam;
import com.wuxl.interview.exam.entity.po.Book;
import com.wuxl.interview.exam.service.ICalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info")
@Slf4j
public class ExamController {

    @Autowired
    private BookRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ICalService calService;


    @PostMapping("/create")
    public String create(@RequestBody Book param) {
        param.setTimestamp(System.currentTimeMillis() / 1000);
        repository.save(param);
        return Result.SUCCESS;

    }

    @PostMapping("/delete")
    public String delete(@RequestBody DelBookParam param) {
        Query query = new Query();
        Pageable pageRequest = PageRequest.of(0, 1, Sort.Direction.DESC, "timestamp");
        query.limit(1);
        query.addCriteria(Criteria.where("name").is(param.getName()));
        query.with(pageRequest);
        mongoTemplate.findAndRemove(query, Book.class);
        return Result.SUCCESS;
    }

    @PostMapping("/update")
    public String update(@RequestBody UpdateBookParam param) {
        Query query = Query.query(Criteria.where("name").is(param.getName()));
        Update updateValue = Update.update("name", param.getUpdate());
        mongoTemplate.updateMulti(query, updateValue, Book.class);
        return Result.SUCCESS;
    }

    @PostMapping("/group/{name}")
    public String groupName(@PathVariable("name") String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<Book> books = mongoTemplate.find(query, Book.class);
        return String.valueOf(books.size());
    }

    @PostMapping("/group/cal")
    public String calFibSeq() {
        try {
            calService.calFibSeq();
            return Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAILED;
        }
    }

    @PostMapping("/group/permutation")
    public String permutationLetter() {
        return calService.permutationLetter();
    }
}
