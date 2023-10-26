package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    //添加套餐
    void save(SetmealDTO setmealDTO);

    //分页查询套餐
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    //通过id查找套餐
    SetmealVO getById(Long id);

    //删除套餐
    void delete(List<Long> ids);

    //更改套餐状态
    void setStatus(Integer status, long id);

    //修改套餐
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
