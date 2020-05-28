package salesSystem.bus.mapper;

import salesSystem.bus.domain.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    void updateGoodsNumberById(Integer goodsid, Integer number);

}
