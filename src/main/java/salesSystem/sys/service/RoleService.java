package salesSystem.sys.service;

import salesSystem.sys.domain.Role;
import salesSystem.sys.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.vo.RoleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-14
 */
public interface RoleService extends IService<Role> {

    DataGridView loadAllRole(RoleVO roleVO);

    void deleteRole(Integer id);

    void batchDeleteRole(Integer[] ids);

    void addRole(Role role);

    void updateRole(Role role);

    void saveRolePermission(Integer roleId, Integer[] pids);

    DataGridView loadUserRoleByUserId(Integer userId);

    List<String> queryRoleNameByUserId(Integer id);
}
