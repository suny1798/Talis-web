package com.example.service;


import com.example.pojo.Emp;
import com.example.pojo.PageResult;
import org.springframework.stereotype.Service;

@Service
public interface EmpService {

    PageResult<Emp> findAll(Integer page, Integer pageSize);
}
