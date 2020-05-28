package salesSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import salesSystem.sys.domain.Dept;
import salesSystem.sys.mapper.DeptMapper;
import salesSystem.sys.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.DeptVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-10
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> queryAllDeptForList(DeptVO deptVO) {
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.eq(deptVO.getAvailable() != null, "available", deptVO.getAvailable());
        // select * from sys_dept where available = 1
        return deptMapper.selectList(qw);
    }


    @Override
    public DataGridView loadAllDept(DeptVO deptVO) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVO.getTitle()), "title", deptVO.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVO.getAddress()), "address", deptVO.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVO.getRemark()), "remark", deptVO.getRemark());
        queryWrapper.eq(deptVO.getId() != null, "id", deptVO.getId())
                .or().eq(deptVO.getId() != null, "pid", deptVO.getId());
        queryWrapper.orderByDesc("createtime");
        // select * from sys_login where loginname like "#{loginname}" and logintime > #{logintime} and logintime < #{logintime}
        //分页
        Page page = new Page<>(deptVO.getPage(), deptVO.getLimit());
        //查询数据库
        deptMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deleteDept(Integer id) {

        deptMapper.deleteById(id);

    }

//    @Override
//    public void batchDeleteDept(Integer[] ids) {
//        Collection<Serializable> idList = new ArrayList<>();
//        for (Integer i : ids) {
//            idList.add(i);
//        }
//        deptMapper.deleteBatchIds(idList);
//    }

    @Override
    public void addDept(Dept dept) {
        deptMapper.insert(dept);
    }

    @Transactional
    @Override
    public void updateDept(Dept dept) {
        deptMapper.updateById(dept);
    }

    @Override
    public Integer queryDeptCountByPid(Integer id) {
        return deptMapper.queryDeptCountByPid(id);
    }

    @Override
    public Integer queryDeptMaxOrderNum() {

        return deptMapper.queryDeptMaxOrderNum();
    }


}
