package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import salesSystem.bus.domain.Customer;
import salesSystem.bus.service.CustomerService;
import salesSystem.bus.vo.CustomerVO;
import salesSystem.sys.domain.User;

import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;
import salesSystem.sys.utils.TimeZoneInChina;


import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("loadAllCustomer")
    public DataGridView loadAllcustomer(CustomerVO customerVO) {

        return customerService.loadAllCustomer(customerVO);
    }

    @RequestMapping("deleteCustomer")
    public ResultObj deletecustomer(Integer id) {
        try {
            customerService.deleteCustomer(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteCustomer")
    public ResultObj batchDeletecustomer(Integer[] ids) {
        try {
            customerService.batchDeleteCustomer(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    //添加公告
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(Customer customer, HttpSession session) {
        try {
            //从session获得用户
            User user = (User) session.getAttribute("user");
            //设置发布人
//            customer.setOpername(user.getName());
//            customer.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            customerService.addCustomer(customer);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(Customer customer){
        try {
//            customer.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            customerService.updateCustomer(customer);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_FAIL;
        }
    }
    //loadCustomerById
    @RequestMapping("loadCustomerById")
    public DataGridView loadCustomerById(Integer id){

        return new DataGridView(customerService.getById(id));
    }

    //loadAllAvailableCustomer
    @RequestMapping("loadAllAvailableCustomer")
    public DataGridView loadAllAvailableCustomer(){
        return customerService.loadAllAvailableCustomer();
    }
}

