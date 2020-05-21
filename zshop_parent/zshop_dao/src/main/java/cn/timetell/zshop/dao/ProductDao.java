package cn.timetell.zshop.dao;

import cn.timetell.zshop.params.ProductParam;
import cn.timetell.zshop.pojo.Product;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
public interface ProductDao {

    public void insert(Product product);

    Product selectByName(String name);

    List<Product> selectAll();

    Product selectById(int id);

    void update(Product product);

    void deleteById(int id);

    List<Product> selectByParams(ProductParam productParam);

}
