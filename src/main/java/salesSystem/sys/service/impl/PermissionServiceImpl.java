package salesSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.sys.constant.Constant;
import salesSystem.sys.domain.Permission;
import salesSystem.sys.domain.Permission;
import salesSystem.sys.domain.User;
import salesSystem.sys.mapper.PermissionMapper;
import salesSystem.sys.mapper.RoleMapper;
import salesSystem.sys.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.sys.utils.ActiveUser;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.TreeNode;
import salesSystem.sys.vo.PermissionVO;
import salesSystem.sys.vo.PermissionVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Permission> queryAllPermissionList(PermissionVO permissionVO) {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        if (permissionVO != null) {
            // where type=#{type} not null
            permissionQueryWrapper.eq(StringUtils.isNotBlank(permissionVO.getType()),
                    "type", permissionVO.getType());
            // where available = #{available} not null
            permissionQueryWrapper.eq(permissionVO.getAvailable() != null,
                    "available", permissionVO.getAvailable());
        }
        permissionQueryWrapper.orderByAsc("ordernum");
        List<Permission> permissionList = permissionMapper.selectList(permissionQueryWrapper);
        return permissionList;
    }

    @Override
    public DataGridView loadAllPermission(PermissionVO permissionVO) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(permissionVO.getTitle()), "title", permissionVO.getTitle());

        queryWrapper.eq(StringUtils.isNotBlank(permissionVO.getType()), "type", permissionVO.getType());
        queryWrapper.eq(StringUtils.isNotBlank(permissionVO.getPercode()), "percode", permissionVO.getPercode());
        queryWrapper.eq(permissionVO.getAvailable() != null, "available", permissionVO.getAvailable());

        queryWrapper.and(permissionVO.getId() != null, new Consumer<QueryWrapper<Permission>>() {
            @Override
            public void accept(QueryWrapper<Permission> qw) {
                qw.eq(permissionVO.getId() != null, "id", permissionVO.getId())
                        .or().eq(permissionVO.getId() != null, "pid", permissionVO.getId());
            }
        });
        queryWrapper.orderByDesc("ordernum");
        // select * from sys_login where loginname like "#{loginname}" and logintime > #{logintime} and logintime < #{logintime}
        //分页
        Page page = new Page<>(permissionVO.getPage(), permissionVO.getLimit());
        //查询数据库
        permissionMapper.selectPage(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public void deletePermission(Integer id) {
        permissionMapper.deleteById(id);
    }


    @Override
    public void addPermission(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Transactional
    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateById(permission);
    }

    @Override
    public Integer queryPermissionCountByPid(Integer id, String type) {
        return permissionMapper.queryPermissionCountByPid(id, type);
    }

    @Override
    public Integer queryPermissionMaxOrderNum() {

        return permissionMapper.queryPermissionMaxOrderNum();
    }

    @Override
    public DataGridView queryPermissionByRoleId(Integer roleId) {
        QueryWrapper<Permission> qw = new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Permission> allPermission = permissionMapper.selectList(qw);
        // 从中间表查询role所拥有的pid
        List<Integer> role_permissionId = permissionMapper.queryPermissionIdsByRoleId(roleId);
        //当前用户所拥有的权限
        List<Permission> roleHasPermissions = null;
        if (role_permissionId == null || role_permissionId.size() == 0) {
            roleHasPermissions = new ArrayList<>();
        } else {
            qw.in("id", role_permissionId);
            roleHasPermissions = permissionMapper.selectList(qw);
        }

        //数据格式转换
        List<TreeNode> nodes = new ArrayList<>();
        for (Permission p1 : allPermission) {
            String checkArr = "0";
            for (Permission p2 : roleHasPermissions) {
                if (p1.getId() == p2.getId()) {
                    checkArr = "1";
                    break;
                }
            }
            //是否展开
            Boolean spread = p1.getOpen() == null ? false : p1.getOpen() == 1 ? true : false;
            TreeNode t1 = new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr);
            nodes.add(t1);
        }
        return new DataGridView(nodes);

    }

    @Override
    public List<String> queryPermissionsByUserId(Integer id) {
        //根据用户id查询角色id
        List<Integer> rids = roleMapper.currentUserHasRole(id);
        if (null == rids && rids.size() == 0) {
            return null;
        } else {

            List<Integer> pids = permissionMapper.queryPermissionIdsByRoleIds(rids);
            if (null == pids || pids.size() == 0) {
                return null;
            } else {
                QueryWrapper<Permission> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.eq("type", Constant.TYPE_PERMISSION);
                qw.in("id", pids);
                //所有权限
                List<Permission> permissionList = this.permissionMapper.selectList(qw);

                //权限名
                ArrayList<String> permissions = new ArrayList<>();
                for (Permission p : permissionList) {
                    permissions.add(p.getPercode());
                }
                return permissions;
            }

        }

    }

    @Override
    public List<Permission> queryPermissionByUserIdForList(User user) {
        //根据用户id查询角色id
        List<Integer> rids = roleMapper.currentUserHasRole(user.getId());
//        List<String> roles = activeUser.getRoles();
        if (null == rids && rids.size() == 0) {
            return null;
        } else {

            List<Integer> pids = permissionMapper.queryPermissionIdsByRoleIds(rids);
            if (null == pids || pids.size() == 0) {
                return null;
            } else {
                QueryWrapper<Permission> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.eq("type", Constant.TYPE_MENU);
                qw.in("id", pids);
                //所有权限
                List<Permission> permissionList = this.permissionMapper.selectList(qw);

                return permissionList;
            }

        }
    }

}
