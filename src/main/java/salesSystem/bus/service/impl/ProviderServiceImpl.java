package salesSystem.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.bus.domain.Provider;
import salesSystem.bus.domain.Provider;
import salesSystem.bus.mapper.ProviderMapper;
import salesSystem.bus.mapper.ProviderMapper;
import salesSystem.bus.service.ProviderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.bus.vo.ProviderVO;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.utils.DataGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
@Service
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {


    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public DataGridView loadAllProvider(ProviderVO providerVO) {
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(providerVO.getProvidername()), "providername", providerVO.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(providerVO.getPhone()), "phone", providerVO.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(providerVO.getConnectionperson()), "connectionperson", providerVO.getConnectionperson());
        queryWrapper.orderByDesc("id");
        //分页
        Page page = new Page<>(providerVO.getPage(), providerVO.getLimit());
        //查询数据库
        providerMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deleteProvider(Integer id) {

        providerMapper.deleteById(id);

    }

    @Override
    public void batchDeleteProvider(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            idList.add(i);
        }
        providerMapper.deleteBatchIds(idList);
    }

    @Override
    public void addProvider(Provider provider) {
        providerMapper.insert(provider);
    }

    @Transactional
    @Override
    public void updateProvider(Provider provider) {
        providerMapper.updateById(provider);
    }

    @Override
    public DataGridView loadAllAvailableProvider() {
        QueryWrapper<Provider> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);

        return new DataGridView(providerMapper.selectList(qw));
    }

}
