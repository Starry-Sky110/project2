package salesSystem.bus.service;

import salesSystem.bus.domain.Inport;
import salesSystem.bus.domain.Inport;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.InportVO;
import salesSystem.sys.utils.DataGridView;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-23
 */
public interface InportService extends IService<Inport> {

    DataGridView loadAllInport(InportVO inportVO);

    void deleteInport(Integer id, Integer num);


    void addInport(Inport inport);

    void updateInport(Inport inport);

}
