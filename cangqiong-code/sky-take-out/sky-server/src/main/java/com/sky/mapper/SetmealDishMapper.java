package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {



    List<Long> getIdByDishId(List<Long> ids);


    void insert(List<SetmealDish> setmealDishes);

    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> getById(Long id);

    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void delete(Long id);

    @Select("select * from setmeal_dish where dish_id=#{id}")
    List<SetmealDish> getBydishId(long id);
}
