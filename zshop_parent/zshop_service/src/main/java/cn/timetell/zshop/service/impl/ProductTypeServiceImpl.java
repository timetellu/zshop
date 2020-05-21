package cn.timetell.zshop.service.impl;

import cn.timetell.zshop.common.constant.PaginationConstant;
import cn.timetell.zshop.common.constant.ProductTypeConstant;
import cn.timetell.zshop.common.exception.ProductTypeExistException;
import cn.timetell.zshop.dao.ProductTypeDao;
import cn.timetell.zshop.pojo.ProductType;
import cn.timetell.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeDao productTypeDao;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<ProductType> findAll() {
        return productTypeDao.selectAll();
    }

    @Override
    public void add(String name) throws ProductTypeExistException {
        ProductType productType = productTypeDao.selectByName(name);
        if(null != productType){
            throw new ProductTypeExistException("您添加的商品类型已存在！");
        }
        productTypeDao.insert(name, ProductTypeConstant.PRODUCT_TYPE_ENABLE);

    }

    @Override
    public ProductType findById(Integer id) {
        return productTypeDao.selectById(id);
    }

    @Override
    public void modifyName(Integer id, String name)  throws  ProductTypeExistException {
        productTypeDao.updateName(id,name);
    }

    @Override
    public void removeById(int id) {
        productTypeDao.deleteById(id);
    }

    @Override
    public List<ProductType> findEnable() {
        return productTypeDao.selectByStatus(ProductTypeConstant.PRODUCT_TYPE_ENABLE);
    }

    @Override
    public void modifyStatus(Integer id) {
        ProductType productType = productTypeDao.selectById(id);
        Integer status = productType.getStatus();
        if(status == 1){
            status = 0;
        }else{
            status = 1;
        }
        productTypeDao.updateStatus(id,status);
    }
}
