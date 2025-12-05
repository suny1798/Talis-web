package com.example.service;

import com.example.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeptService {

    List<Dept> findAll();

    void deleteById(Integer id);

    void insert(Dept dept);

    void update(Dept dept);

    Dept findById(Integer id);
}
