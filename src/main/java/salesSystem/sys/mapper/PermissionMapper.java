package salesSystem.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import salesSystem.sys.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    Integer queryPermissionCountByPid(@Param("id") Integer id, @Param("type") String type);

    Integer queryPermissionMaxOrderNum();

    List<Integer> queryPermissionIdsByRoleId(Integer rid);

    List<Integer> queryPermissionIdsByRoleIds(@Param("rids") List<Integer> rids);

}