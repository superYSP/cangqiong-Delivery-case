package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("添加菜品{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        String key="dish_"+dishDTO.getCategoryId();
        redisTemplate.delete(key);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){

        log.info("菜品分页查询");
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除菜品");
        dishService.delete(ids);
        cleanCache("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("更改菜品状态")
    public Result startOrstop(@PathVariable Integer status,long id){
        log.info("更改菜品{}状态",id);
        dishService.setStatus(status,id);
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找菜品")
    public Result gteById(@PathVariable Long id){
        log.info("根据id{}查找菜品信息",id);
        DishVO dishVO=dishService.getById(id);
        return Result.success(dishVO);
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id或菜品名字查找菜品")
    public Result gteByCategoryIdOrName( Long categoryId,String name) {
        log.info("根据分类id{}查找菜品信息", categoryId);
        Dish dish=new Dish();
        dish.setCategoryId(categoryId);
        dish.setName(name);
        List<Dish> dishes = dishService.list(dish);
        return Result.success(dishes);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品{}",dishDTO.getId());
        dishService.updateDish(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }

    private void cleanCache(String pattern){
        Set keys=redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}

