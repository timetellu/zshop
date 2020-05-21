package cn.timetell.zshop.dao;

import cn.timetell.zshop.pojo.Customer;
import org.apache.ibatis.annotations.Param;


public interface CustomerDao {

    public Customer selectByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password, @Param("isValid") Integer isValid);

    Customer selectByPhone(String phone);
}
