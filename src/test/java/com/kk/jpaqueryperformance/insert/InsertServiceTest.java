package com.kk.jpaqueryperformance.insert;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@Transactional
@SpringBootTest
class InsertServiceTest {

    @Autowired
    InsertService insertService;

    @BeforeAll
    static void init(){

    }

    @DisplayName("EntityManager Persist")
    @Test
    void entityManagerPersistTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertEntityManagerPersist();
        long end = System.currentTimeMillis();
        log.info("EntityManager Persist :  cost {}ms ", end-start);
    }

    @DisplayName("DataJpa Save")
    @Test
    void insertDataJpaSaveTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertDataJpaSave();
        long end = System.currentTimeMillis();
        log.info("DataJpa Save :  cost {}ms ", end-start);
    }

    @DisplayName("DataJpa SaveAll")
    @Test
    void insertDataJpaSaveAllTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertDataJpaSaveAll();
        long end = System.currentTimeMillis();
        log.info("DataJpa SaveAll :  cost {}ms ", end-start);
    }

    @DisplayName("Jdbc Insert")
    @Test
    void insertJdbcTemplateTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertJdbcTemplate();
        long end = System.currentTimeMillis();
        log.info("Jdbc Insert :  cost {}ms ", end-start);
    }

    @DisplayName("Jdbc Bulk Insert")
    @Test
    void insertBulkTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertBulk();
        long end = System.currentTimeMillis();
        log.info("Jdbc Bulk Insert :  cost {}ms ", end-start);
    }

}