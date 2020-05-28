package salesSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.sys.domain.Loginfo;
import salesSystem.sys.mapper.LoginfoMapper;
import salesSystem.sys.service.LoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.LogInfoVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-07
 */
@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

    @Autowired
    private LoginfoMapper loginfoMapper;

    @Override
    public DataGridView loadAllLogInfo(LogInfoVO logInfoVO) {
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(logInfoVO.getLoginname()), "loginname", logInfoVO.getLoginname());
        queryWrapper.ge(logInfoVO.getStartTime() != null, "logintime", logInfoVO.getStartTime());
        queryWrapper.le(logInfoVO.getEndTime() != null, "logintime", logInfoVO.getEndTime());
        queryWrapper.orderByDesc("logintime");
        // select * from sys_login where loginname like "#{loginname}" and logintime > #{logintime} and logintime < #{logintime}
        //分页
        Page page = new Page<>(logInfoVO.getPage(), logInfoVO.getLimit());
        //查询数据库
        loginfoMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deleteloginfo(Integer id) {

        loginfoMapper.deleteById(id);

    }

    @Override
    public void batchDeleteLogInfo(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            idList.add(i);
        }
        loginfoMapper.deleteBatchIds(idList);
    }
}
