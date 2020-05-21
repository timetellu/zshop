package cn.timetell.zshop.service;

import cn.timetell.zshop.common.exception.LoginErrorException;
import cn.timetell.zshop.common.exception.PhoneNotRegistException;
import cn.timetell.zshop.pojo.Customer;

/**
 * Created by Timetellu on 2020/4/28.
 */
public interface CustomerService {

    public Customer login(String loginName, String password) throws LoginErrorException;

    Customer findByPhone(String phone) throws PhoneNotRegistException;
}
