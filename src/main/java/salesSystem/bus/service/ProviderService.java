package salesSystem.bus.service;

import salesSystem.bus.domain.Provider;
import salesSystem.bus.domain.Provider;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.bus.vo.ProviderVO;
import salesSystem.sys.utils.DataGridView;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
public interface ProviderService extends IService<Provider> {

    DataGridView loadAllProvider(ProviderVO providerVO);

    void deleteProvider(Integer id);

    void batchDeleteProvider(Integer[] ids);

    void addProvider(Provider provider);

    void updateProvider(Provider provider);

    DataGridView loadAllAvailableProvider();

}
