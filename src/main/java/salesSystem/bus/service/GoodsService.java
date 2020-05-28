package salesSystem.bus.service;

import salesSystem.bus.domain.Goods;
import salesSystem.bus.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.GoodsVO;
import salesSystem.sys.utils.DataGridView;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
public interface GoodsService extends IService<Goods> {

    DataGridView loadAllGoods(GoodsVO goodsVO);

    void deleteGoods(Integer id);

    void batchDeleteGoods(Integer[] ids);

    void addGoods(Goods goods);

    void updateGoods(Goods goods);

    DataGridView loadAllAvailableGoods();

    DataGridView loadGoodsByProviderId(Integer providerId);
}
