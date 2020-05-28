package salesSystem.bus.service;

import salesSystem.bus.domain.Outport;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.OutPortVO;
import salesSystem.sys.utils.DataGridView;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-23
 */
public interface OutportService extends IService<Outport> {

    void addOutPort(Outport outport);

    DataGridView loadAllOutport(OutPortVO outPortVO);

}
