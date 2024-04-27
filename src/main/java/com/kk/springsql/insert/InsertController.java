package com.kk.springsql.insert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/insert")
@RequiredArgsConstructor
@RestController
public class InsertController {

    private final InsertService insertService;


    @PostMapping("/em")
    public void insertEntityManagerPersist(@RequestParam int n){
        insertService.insertEntityManagerPersist(n);
    }
    @PostMapping("/jpa/save")
    public void insertDataJpaSave(@RequestParam int n){
        insertService.insertDataJpaSave(n);
    }
    @PostMapping("/jpa/save-all")
    public void insertDataJpaSaveAll(@RequestParam int n){
        insertService.insertDataJpaSaveAll(n);
    }
    @PostMapping("/jpa/save-flush")
    public void insertDataJpaSaveAndFlush(@RequestParam int n){
        insertService.insertDataJpaSaveAndFlush(n);
    }
    @PostMapping("/jpa/save-all-flush")
    public void insertDataJpaSaveAllAndFlush(@RequestParam int n){
        insertService.insertDataJpaSaveAllAndFlush(n);
    }
    @PostMapping("/dsl")
    public void insertQueryDslInsert(@RequestParam int n){
        insertService.insertQueryDslInsert(n);
    }
    @PostMapping("/my/xml")
    public void insertMybatisXML(@RequestParam int n){
        insertService.insertMybatisXML(n);
    }
    @PostMapping("/my/ano")
    public void insertMybatisAnnotation(@RequestParam int n){
        insertService.insertMybatisAnnotation(n);
    }

    @PostMapping("/my/pro")
    public void insertMybatisProvider(@RequestParam int n){
        insertService.insertMybatisProvider(n);
    }
    @PostMapping("/jdbc/normal")
    public void insertJdbcTemplateUpdate(@RequestParam int n){
        insertService.insertJdbcTemplateUpdate(n);
    }
    @PostMapping("/jdbc/simple")
    public void insertSimpleJdbcInsertExecute(@RequestParam int n){
        insertService.insertSimpleJdbcInsertExecute(n);
    }
    @PostMapping("/jdbc/pre")
    public void insertPreparedStatementCreator(@RequestParam int n){
        insertService.insertPreparedStatementCreator(n);
    }

    @PostMapping("/jdbc/named/map")
    public void insertNamedParameterJdbcTemplateHashMap(@RequestParam int n){
        insertService.insertNamedParameterJdbcTemplateHashMap(n);
    }
    @PostMapping("/jdbc/named/bean")
    public void insertNamedParameterJdbcTemplateBeanPropertySqlParameterSource(@RequestParam int n){
        insertService.insertNamedParameterJdbcTemplateBeanPropertySqlParameterSource(n);
    }
    @PostMapping("/jdbc/bulk")
    public void insertJdbcTemplateBulkUpdate(@RequestParam int n){
        insertService.insertJdbcTemplateBulkUpdate(n);
    }
    @PostMapping("/jdbc/named/bulk")
    public void insertNamedParameterJdbcTemplateBulkUpdate(@RequestParam int n){
        insertService.insertNamedParameterJdbcTemplateBulkUpdate(n);
    }
}
