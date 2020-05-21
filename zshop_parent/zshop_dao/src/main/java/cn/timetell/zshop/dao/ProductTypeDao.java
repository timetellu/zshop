package cn.timetell.zshop.dao;

import cn.timetell.zshop.pojo.ProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
public interface ProductTypeDao {

    /**
     * 查找所有商品类型
     * @return
     */
    public List<ProductType> selectAll();

    /**
     * 根据id查找商品类型
     * @param id
     * @return
     */
    public ProductType selectById(int id);

    /**
     * 根据名称查找商品类型
     * @param name
     * @return
     */
    public ProductType selectByName(String name);

    public void insert(@Param("name")String name,@Param("status")int status);

    public void updateName(@Param("id") int id, @Param("name") String name);

    public void updateStatus(@Param("id") int id, @Param("status") int status);

    public void deleteById(int id);

    public List<ProductType> selectByStatus(int status);
}
