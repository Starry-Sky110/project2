package salesSystem.bus.service;

import salesSystem.bus.domain.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.SalesBackVO;
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
public interface SalesbackService extends IService<Salesback> {

    void addSalesBack(Salesback salesback);

    DataGridView loadAllSalesback(SalesBackVO salesBackVO);
}
