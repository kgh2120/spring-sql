package com.kk.jpaqueryperformance.insert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/insert")
@RequiredArgsConstructor
@RestController
public class InsertController {

    private final InsertService insertService;

    @PostMapping("/em/persist")
    public void insertEntityManagerPersist(){
        long start = System.currentTimeMillis();
        insertService.insertEntityManagerPersist();
        long end = System.currentTimeMillis();
        log.info("em.persist cost {}ms", end-start);
    }
    @PostMapping("/data/save")
    public void insertDataJpaSave(){
        long start = System.currentTimeMillis();
        insertService.insertDataJpaSave();
        long end = System.currentTimeMillis();
        log.info("em.persist cost {}ms", end-start);
    }
    @PostMapping("/data/save-all")
    public void insertDataJpaSaveAll(){
        long start = System.currentTimeMillis();
        insertService.insertDataJpaSaveAll();
        long end = System.currentTimeMillis();
        log.info("/data/save-all cost {}ms", end-start);
    }

    @PostMapping("/jdbc/insert")
    public void insertJdbcTemplate(){
        long start = System.currentTimeMillis();
        insertService.insertJdbcTemplate();
        long end = System.currentTimeMillis();
        log.info("/jdbc/insert cost {}ms", end-start);
    }

    @PostMapping("/jdbc/bulk")
    public void insertBulk(){
        long start = System.currentTimeMillis();
        insertService.insertBulk();
        long end = System.currentTimeMillis();
        log.info("/jdbc/bulk cost {} ms", end-start);
    }
}
