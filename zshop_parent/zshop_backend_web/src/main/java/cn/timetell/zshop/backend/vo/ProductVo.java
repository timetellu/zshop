package cn.timetell.zshop.backend.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by Timetellu on 2020/4/28.
 * 视图对象
 * 通过前端的  name属性进行传值
 */
public class ProductVo {

    private Integer id;
    private String name;
    private Double price;
    private CommonsMultipartFile file;
    private Integer productTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }
}
