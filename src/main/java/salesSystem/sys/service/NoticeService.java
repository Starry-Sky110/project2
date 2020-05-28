package salesSystem.sys.service;

import salesSystem.sys.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.NoticeVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-08
 */
public interface NoticeService extends IService<Notice> {

    DataGridView loadAllNotice(NoticeVO noticeVO);

    void deleteNotice(Integer id);

    void batchDeleteNotice(Integer[] ids);

    void addNotice(Notice notice);

    void updateNotice(Notice notice);


}
