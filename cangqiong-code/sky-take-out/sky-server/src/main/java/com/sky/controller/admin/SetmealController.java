package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(value = "套餐管理")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("添加套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("添加套餐{}",setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){

        log.info("套餐分页查询");
        PageResult pageResult=setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查找套餐")
    public Result gteById(@PathVariable Long id){
        log.info("根据id{}查找套餐信息",id);
        SetmealVO setmealVO=setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delete(@RequestParam List<Long> ids){
        log.info("删除套餐");
        setmealService.delete(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("更改套餐状态")
    public Result startOrstop(@PathVariable Integer status,long id){
        log.info("更改套餐{}状态",id);
        setmealService.setStatus(status,id);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("修改套餐")
    public Result updateDish(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐{}",setmealDTO.getId());
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
}
