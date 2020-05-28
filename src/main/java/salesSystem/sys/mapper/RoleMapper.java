package salesSystem.sys.mapper;

import org.apache.ibatis.annotations.Param;
import salesSystem.sys.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-14
 */
public interface RoleMapper extends BaseMapper<Role> {

    void deleteRolePermissionByRid(@Param("id") Integer id);

    void deleteRoleUserByRid(@Param("id")Integer id);

    //insert into sys_role_permission(rid,pid) values(#{rid},#{pid})
    void saveRolePermission(Integer rid, Integer pid);

    List<Integer> currentUserHasRole(Integer userId);
}
