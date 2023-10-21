package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {
    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //添加员工
    void save(EmployeeDTO employeeDTO);

    //分页查询员工
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //更改员工状态
    void setStatus(Integer status, long id);

    //根据id查找员工
    Employee getById(Long id);

    //修改员工信息
    void updateEmp(EmployeeDTO employeeDTO);
}
