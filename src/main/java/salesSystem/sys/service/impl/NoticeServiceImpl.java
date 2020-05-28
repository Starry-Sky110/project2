package salesSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.sys.domain.Notice;
import salesSystem.sys.mapper.NoticeMapper;
import salesSystem.sys.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.NoticeVO;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-08
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public DataGridView loadAllNotice(NoticeVO noticeVO) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVO.getTitle()), "title", noticeVO.getTitle());
        queryWrapper.ge(noticeVO.getStartTime() != null, "createtime", noticeVO.getStartTime());
        queryWrapper.le(noticeVO.getEndTime() != null, "createtime", noticeVO.getEndTime());
        queryWrapper.orderByDesc("createtime");
        // select * from sys_login where loginname like "#{loginname}" and logintime > #{logintime} and logintime < #{logintime}
        //分页
        Page page = new Page<>(noticeVO.getPage(), noticeVO.getLimit());
        //查询数据库
        noticeMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deleteNotice(Integer id) {

        noticeMapper.deleteById(id);

    }

    @Override
    public void batchDeleteNotice(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            idList.add(i);
        }
        noticeMapper.deleteBatchIds(idList);
    }

    @Override
    public void addNotice(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Transactional
    @Override
    public void updateNotice(Notice notice) {
        noticeMapper.updateById(notice);
    }


}
