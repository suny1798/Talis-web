package com.example.controller;

import com.example.pojo.Dept;
import com.example.pojo.Result;
import com.example.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
public class DeptController {

//    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;


//    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping("/depts")
    public Result list(){
        log.info("查询全部部门信息");
//        System.out.println("查询全部部门信息");
        List<Dept> list = deptService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/depts")
    public Result deleteById(Integer id){
        log.info("删除部门信息 id:" + id);
//        System.out.println("删除部门信息 id:" + id);
        deptService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/depts")
    public  Result insert(@RequestBody Dept dept){
        log.info("添加部门信息:" + dept);
//        System.out.println("添加部门信息:" + dept);
        deptService.insert(dept);
        return Result.success();
    }

    @GetMapping("/depts/{id}") //路径参数
    public Result findById(@PathVariable Integer id){
        log.info("查询部门信息 id:" + id);
//        System.out.println("查询部门信息 id:" + id);
        Dept dept = deptService.findById(id);
        return Result.success(dept);
    }

    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
        log.info("修改部门信息:" + dept);
//        System.out.println("修改部门信息:" + dept);
        deptService.update(dept);
        return Result.success();
    }

}
