package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.*;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish= new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);
        dishMapper.insert(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(id);
            }
            dishFlavorMapper.insert(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);
        long total=page.getTotal();
        List<DishVO> records=page.getResult();
        return new PageResult(total,records);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            Dish dish=dishMapper.getById(id);
            if(dish.getStatus()==StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        List<Long> setmealIds=setmealDishMapper.getIdByDishId(ids);
        if(setmealIds!=null && setmealIds.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        for (Long id : ids) {
            dishFlavorMapper.delete(id);
            dishMapper.delete(id);
        }

    }

    @Override
    public DishVO getById(Long id) {
         Dish dish=dishMapper.getById(id);
         DishVO dishVO=new DishVO();
         BeanUtils.copyProperties(dish,dishVO);
         List<DishFlavor> dishFlavors=dishFlavorMapper.getByDishId(id);
         dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public List<Dish> list(Dish dish) {
        return dishMapper.list(dish);
    }


    @Override
    public void setStatus(Integer status, long id) {
        Dish dish=new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
        if(status==StatusConstant.DISABLE){
            List<SetmealDish> setmealDishes=setmealDishMapper.getBydishId(id);
            if(setmealDishes!=null&&setmealDishes.size()>0) {
                for (SetmealDish setmealDish : setmealDishes) {
                    Setmeal setmeal = setmealMapper.getById(setmealDish.getSetmealId());
                    setmeal.setStatus(status);
                    setmealMapper.update(setmeal);
                }
            }
        }
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        dishFlavorMapper.delete(dishDTO.getId());
        dishFlavorMapper.insert(dishDTO.getFlavors());
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }
}
