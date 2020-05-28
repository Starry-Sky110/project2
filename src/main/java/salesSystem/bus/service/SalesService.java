package salesSystem.bus.service;

import salesSystem.bus.domain.Sales;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.SalesVO;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-25
 */
public interface SalesService extends IService<Sales> {

    DataGridView loadAllSales(SalesVO salesVO);

    ResultObj addSales(Sales sales);

    ResultObj updateSales(Sales sales);

    void deleteSales(Integer id, Integer num);
}
