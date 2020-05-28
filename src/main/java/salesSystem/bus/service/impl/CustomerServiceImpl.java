package salesSystem.bus.service.impl;

import com.alibaba.druid.sql.visitor.functions.Concat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.bus.domain.Customer;
import salesSystem.bus.mapper.CustomerMapper;
import salesSystem.bus.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import salesSystem.bus.vo.CustomerVO;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.utils.DataGridView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {


    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public DataGridView loadAllCustomer(CustomerVO customerVO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(customerVO.getCustomername()), "customername", customerVO.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVO.getPhone()), "phone", customerVO.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(customerVO.getConnectionperson()), "connectionperson", customerVO.getConnectionperson());
        queryWrapper.orderByDesc("id");
        //分页
        Page page = new Page<>(customerVO.getPage(), customerVO.getLimit());
        //查询数据库
        customerMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deleteCustomer(Integer id) {

        customerMapper.deleteById(id);

    }

    @Override
    public void batchDeleteCustomer(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            idList.add(i);
        }
        customerMapper.deleteBatchIds(idList);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerMapper.insert(customer);
    }

    @Transactional
    @Override
    public void updateCustomer(Customer customer) {
        customerMapper.updateById(customer);
    }

    @Override
    public DataGridView loadAllAvailableCustomer() {

        QueryWrapper<Customer> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);

        return new DataGridView(customerMapper.selectList(qw));

    }


}
