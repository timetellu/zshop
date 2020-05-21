package cn.timetell.zshop.service;

import cn.timetell.zshop.dto.ProductDto;
import cn.timetell.zshop.params.ProductParam;
import cn.timetell.zshop.pojo.Product;
import org.apache.commons.fileupload.FileUploadException;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
public interface ProductService {
    void add(ProductDto productDto) throws FileUploadException;

    boolean checkName(String name);

    List<Product> findAll();

    Product findById(int id);

    void getImage(String path, OutputStream outputStream);

    void modify(ProductDto productDto) throws FileUploadException;

    void removeById(int id);

    List<Product> findByParams(ProductParam productParam);

}
