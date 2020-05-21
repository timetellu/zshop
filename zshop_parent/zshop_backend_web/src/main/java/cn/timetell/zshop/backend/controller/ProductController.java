package cn.timetell.zshop.backend.controller;

import cn.timetell.zshop.backend.vo.ProductVo;
import cn.timetell.zshop.common.constant.PaginationConstant;
import cn.timetell.zshop.common.util.ResponseResult;
import cn.timetell.zshop.dto.ProductDto;
import cn.timetell.zshop.pojo.Product;
import cn.timetell.zshop.pojo.ProductType;
import cn.timetell.zshop.service.ProductService;
import cn.timetell.zshop.service.ProductTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Timetellu on 2020/4/28.
 */
@Controller
@RequestMapping("/backend/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @ModelAttribute("enableProductTypes")
    public List<ProductType> findEnable(){
        List<ProductType> enableProductTypes = productTypeService.findEnable();
        return enableProductTypes;
    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum, Model model) {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = PaginationConstant.PAGE_NUM;
        }

        PageHelper.startPage(pageNum, PaginationConstant.PAGE_SIZE);
        List<Product> products = productService.findAll();
        PageInfo<Product> pageInfo = new PageInfo<Product>(products);
        model.addAttribute("pageInfo", pageInfo);

        return "productManager";
    }

    @RequestMapping("/add")
    public String add(ProductVo productVo,Integer pageNum, HttpSession session, Model model) {
        String uploadPath = session.getServletContext().getRealPath("/WEB-INF/upload");
        System.out.println(uploadPath);

        try {
            //将VO转换为DTO
            ProductDto productDto = new ProductDto();
            //public static void copyProperties(Object dest, Object orig)
            PropertyUtils.copyProperties(productDto, productVo); //对象间属性的拷贝，将后边的copy到前边
            productDto.setInputStream(productVo.getFile().getInputStream());
            productDto.setFileName(productVo.getFile().getOriginalFilename());
            productDto.setUploadPath(uploadPath);

            productService.add(productDto);
            model.addAttribute("successMsg", "添加成功");
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }

        return "forward:findAll?pageNum=" + pageNum;
    }

    //ajax传输，所以要加@ResponseBody注解
    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String, Object> checkName(String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (productService.checkName(name)) { //不存在，可用
            map.put("valid", true);
        } else {
            map.put("valid", false);
            map.put("message", "商品（" + name + "）已存在");
        }
        return map;
    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(int id) {
        Product product = productService.findById(id);
        return ResponseResult.success(product);
    }

    @RequestMapping("/getImage")
    public void getImage(String path, OutputStream outputStream) {
        productService.getImage(path, outputStream);
    }

    @RequestMapping("/modify")
    public String modify(ProductVo productVo, Integer pageNum, HttpSession session, Model model) {
        String uploadPath = session.getServletContext().getRealPath("/WEB-INF/upload");

        try {
            //将VO转换为DTO
            ProductDto productDto = new ProductDto();
            PropertyUtils.copyProperties(productDto, productVo); //对象间属性的拷贝
            productDto.setInputStream(productVo.getFile().getInputStream());
            productDto.setFileName(productVo.getFile().getOriginalFilename());
            productDto.setUploadPath(uploadPath);

            productService.modify(productDto);
            model.addAttribute("successMsg", "修改成功");
        } catch (Exception e) {
            //e.printStackTrace();
            model.addAttribute("errorMsg", e.getMessage());
        }

        return "forward:findAll?pageNum=" + pageNum;
    }

    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult removeById(int id) {
        productService.removeById(id);
        return ResponseResult.success();
    }
}
