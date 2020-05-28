package salesSystem.sys.service;

import salesSystem.sys.domain.Loginfo;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.LogInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-07
 */
public interface LoginfoService extends IService<Loginfo> {

    DataGridView loadAllLogInfo(LogInfoVO logInfoVO);

    void deleteloginfo(Integer id);

    void batchDeleteLogInfo(Integer[] ids);
}
