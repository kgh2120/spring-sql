package com.kk.springsql.insert;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class InsertServiceTest {
    /*
        기준  100000;
        EntityManager Persist :  cost 2775ms
        DataJpa Save :  cost 3448ms
        DataJpa SaveAll :  cost 3177ms
        Jdbc Insert :  cost 650ms
        Jdbc Bulk Insert :  cost 503ms

        기준  500000;
        EntityManager Persist :  cost 9483ms
        DataJpa Save :  cost 11682ms
        DataJpa SaveAll :  cost 11191ms
        Jdbc Insert :  cost 1855ms
        Jdbc Bulk Insert :  cost 1434ms
     */

    @Autowired
    InsertService insertService;
    @DisplayName("EntityManager Persist")
    @Test
    void entityManagerPersistTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertEntityManagerPersist();
        long end = System.currentTimeMillis();
        log.info("EntityManager Persist :  cost {}ms ", end-start);
        // EntityManager Persist :  cost 2775ms
    }

    @DisplayName("DataJpa Save")
    @Test
    void insertDataJpaSaveTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertDataJpaSave();
        long end = System.currentTimeMillis();
        log.info("DataJpa Save :  cost {}ms ", end-start);
        //DataJpa Save :  cost 3448ms
    }

    @DisplayName("DataJpa SaveAll")
    @Test
    void insertDataJpaSaveAllTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertDataJpaSaveAll();
        long end = System.currentTimeMillis();
        log.info("DataJpa SaveAll :  cost {}ms ", end-start);
        //DataJpa SaveAll :  cost 3177ms
    }

    @DisplayName("Jdbc Insert")
    @Test
    void insertJdbcTemplateTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertJdbcTemplate();
        long end = System.currentTimeMillis();
        log.info("Jdbc Insert :  cost {}ms ", end-start);
        // Jdbc Insert :  cost 650ms
    }

    @DisplayName("Jdbc Bulk Insert")
    @Test
    void insertBulkTest() throws Exception{
        long start = System.currentTimeMillis();
        insertService.insertBulk();
        long end = System.currentTimeMillis();
        log.info("Jdbc Bulk Insert :  cost {}ms ", end-start);
        // Jdbc Bulk Insert :  cost 503ms
    }

}