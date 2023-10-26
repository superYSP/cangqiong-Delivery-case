package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import com.sky.service.ShoppingCartService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    public List<ShoppingCart> list(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number=#{number} where id=#{id}")
    public void updateById(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time,number) values" +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime},#{number})")
    void insert(ShoppingCart shoppingCart);

    void delete(ShoppingCart shoppingCart);
}
