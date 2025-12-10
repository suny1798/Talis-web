package com.example.service.impl;

import com.example.Utils.JwtUtil;
import com.example.mapper.EmpExprMapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.*;
import com.example.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class EmpServiceimpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public PageResult<Emp> findAll(EmpQueryParam empQueryParam) {
        Page<Object> objects = PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        List<Emp> rows = empMapper.list(empQueryParam);

        return new PageResult<Emp>(objects.getTotal(), rows);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        //1.补全基础属性
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        //2.保存员工基本信息
        empMapper.insert(emp);

        //3. 保存员工的工作经历信息 - 批量
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        //1 删除基本信息
        empMapper.deleteByIds(ids);

        //2 删除工作信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        //1. 修改员工信息
        empMapper.updateById(emp);
        //2. 修改员工工作信息   --先删除后添加
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }

    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public List<Emp> list() {
        return empMapper.listAll();
    }

    @Override
    public Logininfo login(Emp emp) {

        Emp loginEmp = empMapper.login(emp);

        if (loginEmp != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginEmp.getId());
            claims.put("username", loginEmp.getUsername());
            String jwt = JwtUtil.generateJwt(claims);
            return new Logininfo(loginEmp.getId(), loginEmp.getUsername(), loginEmp.getName(), jwt);
        }
        return null;
    }
}
