package cn.timetell.zshop.service.impl;

import cn.timetell.zshop.common.constant.CustomerConstant;
import cn.timetell.zshop.common.exception.LoginErrorException;
import cn.timetell.zshop.common.exception.PhoneNotRegistException;
import cn.timetell.zshop.dao.CustomerDao;
import cn.timetell.zshop.pojo.Customer;
import cn.timetell.zshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Timetellu on 2020/4/28.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Customer login(String loginName, String password) throws LoginErrorException {
        Customer customer = customerDao.selectByLoginNameAndPassword(loginName, password, CustomerConstant.CUSTOMER_VALID);
        if (customer == null) {
            throw new LoginErrorException("登陆失败，用户名或密码不正确");
        }
        return customer;
    }


    @Override
    public Customer findByPhone(String phone) throws PhoneNotRegistException {
        Customer customer = customerDao.selectByPhone(phone);
        if (customer == null) {
            throw new PhoneNotRegistException("该手机号码尚未注册");
        }
        return customer;
    }

}
