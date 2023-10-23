package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    //新增菜品
    public void saveWithFlavor(DishDTO dishDTO);

    //菜品分页查询
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //删除菜品一条龙
    void delete(List<Long> ids);

    //更改菜品状态
    void setStatus(Integer status, long id);

    //通过ID查找菜品
    DishVO getById(Long id);

    //修改菜品
    void updateDish(DishDTO dishDTO);

    //通过分类ID或菜品名字查找菜品
    List<Dish> getByCategoryIdOrName(Long categoryId,String name);

}
