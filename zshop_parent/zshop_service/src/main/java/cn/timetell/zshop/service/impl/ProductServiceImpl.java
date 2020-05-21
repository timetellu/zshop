package cn.timetell.zshop.service.impl;

import cn.timetell.zshop.common.util.StringUtils;
import cn.timetell.zshop.dao.ProductDao;
import cn.timetell.zshop.dto.ProductDto;
import cn.timetell.zshop.params.ProductParam;
import cn.timetell.zshop.pojo.Product;
import cn.timetell.zshop.pojo.ProductType;
import cn.timetell.zshop.service.ProductService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Timetellu on 2020/4/28.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public void add(ProductDto productDto) throws FileUploadException {
        // 1.文件上传到服务器磁盘
        String fileName = StringUtils.renameFileName(productDto.getFileName());
        String filePath = productDto.getUploadPath()+"\\"+fileName;

        try {
            StreamUtils.copy(productDto.getInputStream(), new FileOutputStream(filePath));
        } catch (IOException e) {
            throw new FileUploadException("文件上传失败"+e.getMessage());
        }

        //将文件上传到FTP服务器
//        String filePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        String fileName = StringUtils.renameFileName(productDto.getFileName());
//        boolean flag = FtpUtils.uploadFile(host, port, username, password, basePath, filePath, fileName, productDto.getInputStream());
//        System.out.println(baseUrl);
//        System.out.println(filePath);
//        System.out.println(fileName);
//        if (!flag){
//            throw new FileUploadException("文件上传失败");
//        }

        // 2.文件保存到数据库，将DTO转换为POJO
        Product product = new Product();
        try {
            PropertyUtils.copyProperties(product, productDto);
            product.setImage(filePath);
//            product.setImage(baseUrl+"/"+filePath+"/"+fileName);  //http://192.168.1.68/images/20180509/aasd234234234.JPG

            ProductType productType = new ProductType();
            productType.setId(productDto.getProductTypeId());

            product.setProductType(productType);

            productDao.insert(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkName(String name) {
        Product product = productDao.selectByName(name);
        if(product!=null){  //已存在，返回false
            return false;
        }
        return true;   //不存在，返回true
    }

    @Override
    public List<Product> findAll() {
        return productDao.selectAll();
    }

    @Override
    public Product findById(int id) {
        return productDao.selectById(id);
    }

    /**
     * 获取图片，写到输出流中
     * @param path
     * @param outputStream
     */
    @Override
    public void getImage(String path, OutputStream outputStream) {
        try {
            StreamUtils.copy(new FileInputStream(path),outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modify(ProductDto productDto) throws FileUploadException {
        // 1.文件上传
        String fileName = StringUtils.renameFileName(productDto.getFileName());
        String filePath=productDto.getUploadPath()+"\\"+fileName;

        try {
            StreamUtils.copy(productDto.getInputStream(),new FileOutputStream(filePath));
        } catch (IOException e) {
            throw new FileUploadException("文件上传失败"+e.getMessage());
        }


        // 2.保存到数据库，将DTO转换为PO
        Product product = new Product();
        try {
            PropertyUtils.copyProperties(product,productDto);
            product.setImage(filePath);

            ProductType productType = new ProductType();
            productType.setId(productDto.getProductTypeId());

            product.setProductType(productType);

            productDao.update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(int id) {
        productDao.deleteById(id);
    }

    @Override
    public List<Product> findByParams(ProductParam productParam) {
        return productDao.selectByParams(productParam);
    }
}
