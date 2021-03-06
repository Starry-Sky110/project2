package salesSystem.bus.service;

import salesSystem.bus.domain.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.CustomerVO;
import salesSystem.sys.utils.DataGridView;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
public interface CustomerService extends IService<Customer> {

    DataGridView loadAllCustomer(CustomerVO customerVO);

    void deleteCustomer(Integer id);

    void batchDeleteCustomer(Integer[] ids);

    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    DataGridView loadAllAvailableCustomer();
}
