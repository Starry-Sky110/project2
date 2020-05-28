package salesSystem.bus.mapper;

import salesSystem.bus.domain.Sales;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-25
 */
public interface SalesMapper extends BaseMapper<Sales> {

    void updateSalesNumberById(Integer id, int number);
}
