package cn.timetell.zshop.service;

import cn.timetell.zshop.common.exception.ProductTypeExistException;
import cn.timetell.zshop.pojo.ProductType;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
public interface ProductTypeService {

    public List<ProductType> findAll();

    /**
     * 添加商品类型
     * 判断商品类型名称是否已存在
     *      如果已存在，抛出异常
     *      如果不存在，则保持
     * @param name
     */
    public void add(String name) throws ProductTypeExistException;

    ProductType findById(Integer id);

    void modifyName(Integer id, String name) throws ProductTypeExistException;

    /**
     * 根据id删除商品类型
     *      判断是否存在该商品类型的商品，如果存在，则抛出异常
     * @param id
     */
    void removeById(int id);

    void modifyStatus(Integer id);

    /**
     * 查找有效的商品类型信息
     * @return
     */
    List<ProductType> findEnable();
}
