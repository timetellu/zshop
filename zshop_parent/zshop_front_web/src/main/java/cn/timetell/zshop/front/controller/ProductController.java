package cn.timetell.zshop.front.controller;

import cn.timetell.zshop.common.constant.PaginationConstant;
import cn.timetell.zshop.params.ProductParam;
import cn.timetell.zshop.pojo.Product;
import cn.timetell.zshop.pojo.ProductType;
import cn.timetell.zshop.service.ProductService;
import cn.timetell.zshop.service.ProductTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/front/product")
public class ProductController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;


    @RequestMapping("/search")
    public String search(ProductParam productParam,Integer pageNum,Model model){
        if (ObjectUtils.isEmpty(pageNum)){
            pageNum= PaginationConstant.PAGE_NUM;
        }

        PageHelper.startPage(pageNum,PaginationConstant.FRONT_PAGE_SIZE);
        List<Product> products = productService.findByParams(productParam);
        PageInfo<Product> pageInfo = new PageInfo<Product>(products);
        model.addAttribute("pageInfo",pageInfo);

        return "main";
    }

    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){
        List<ProductType> productTypes = productTypeService.findEnable();
        return productTypes;
    }
}
