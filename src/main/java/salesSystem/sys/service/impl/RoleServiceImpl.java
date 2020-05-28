package salesSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.Role;
import salesSystem.sys.domain.Role;
import salesSystem.sys.mapper.RoleMapper;
import salesSystem.sys.mapper.RoleMapper;
import salesSystem.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.TreeNode;
import salesSystem.sys.vo.RoleVO;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public DataGridView loadAllRole(RoleVO roleVO) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVO.getName()), "name", roleVO.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVO.getRemark()), "remark", roleVO.getRemark());

        queryWrapper.orderByDesc("createtime");
        //分页
        Page page = new Page<>(roleVO.getPage(), roleVO.getLimit());
        //查询数据库
        roleMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deleteRole(Integer id) {
        //先删除中间表
        roleMapper.deleteRolePermissionByRid(id);
        roleMapper.deleteRoleUserByRid(id);

        roleMapper.deleteById(id);

    }

    @Override
    public void batchDeleteRole(Integer[] ids) {
        Collection<Serializable> idList = new ArrayList<>();
        for (Integer i : ids) {
            //先删除中间表
            roleMapper.deleteRolePermissionByRid(i);
            roleMapper.deleteRoleUserByRid(i);
            idList.add(i);
        }
        roleMapper.deleteBatchIds(idList);
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    @Transactional
    @Override
    public void updateRole(Role role) {
        roleMapper.updateById(role);
    }

    @Override
    public void saveRolePermission(Integer roleId, Integer[] pids) {
        roleMapper.deleteRolePermissionByRid(roleId);

        for (Integer i : pids) {
            roleMapper.saveRolePermission(roleId, i);
        }
    }

    @Override
    public DataGridView loadUserRoleByUserId(Integer userId) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        //查询所有可用的角色
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Role> roles = roleMapper.selectList(qw);
        //查询当前用户所拥有的角色id
        List<Integer> u_roleId = roleMapper.currentUserHasRole(userId);
        //当前用户所拥有的角色
        List<Role> userHasRole = null;
        if (u_roleId == null || u_roleId.size() == 0) {
            userHasRole = new ArrayList<>();
        } else {
            qw.in("id", u_roleId);
            userHasRole = roleMapper.selectList(qw);
        }

        //数据格式转换
        //判断用户角色是不是在所有可用的角色里面，是为true
        List<Map<String, Object>> res = new ArrayList<>();
        for (Role r1 : roles) {
            Boolean LAY_CHECKED = false;
            for (Role r2 : userHasRole) {
                if (r1.getId() == r2.getId()) {
                    LAY_CHECKED = true;
                    break;
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", r1.getId());
            map.put("name", r1.getName());
            map.put("remark", r1.getRemark());
            map.put("createtime", r1.getCreatetime());
            map.put("LAY_CHECKED", LAY_CHECKED); //表格checkbox是否选中

            res.add(map);
        }

        return new DataGridView(res);
    }

    @Override
    public List<String> queryRoleNameByUserId(Integer uid) {
        //在user_role表查询roleId
        List<Integer> rids = roleMapper.currentUserHasRole(uid);
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Role> currentUserRoles = new ArrayList<>();
        if (rids != null && rids.size() != 0) {
            qw.in("id", rids);
            currentUserRoles = roleMapper.selectList(qw);
        }
        ArrayList<String> roleNames = new ArrayList<>();
        for (Role r : currentUserRoles) {
            roleNames.add(r.getName());
        }
        return roleNames;
    }


}
