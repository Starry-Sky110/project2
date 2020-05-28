package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.bus.domain.Provider;
import salesSystem.bus.service.ProviderService;
import salesSystem.bus.vo.ProviderVO;
import salesSystem.sys.domain.User;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
@RestController
@RequestMapping("provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @RequestMapping("loadAllProvider")
    public DataGridView loadAllprovider(ProviderVO providerVO) {

        return providerService.loadAllProvider(providerVO);
    }

    @RequestMapping("deleteProvider")
    public ResultObj deleteprovider(Integer id) {
        try {
            providerService.deleteProvider(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteProvider")
    public ResultObj batchDeleteprovider(Integer[] ids) {
        try {
            providerService.batchDeleteProvider(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    //添加公告
    @RequestMapping("addProvider")
    public ResultObj addProvider(Provider provider, HttpSession session) {
        try {

            //存入数据库
            providerService.addProvider(provider);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateProvider")
    public ResultObj updateProvider(Provider provider){
        try {
//            provider.setCreatetime(TimeZoneInChina.ChangeTimeZoneInChina());
            //存入数据库
            providerService.updateProvider(provider);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_FAIL;
        }
    }
    //loadProviderById
    @RequestMapping("loadProviderById")
    public DataGridView loadProviderById(Integer id){

        return new DataGridView(providerService.getById(id));
    }

    @RequestMapping("loadAllAvailableProvider")
    public DataGridView loadAllAvailableProvider(){
        return providerService.loadAllAvailableProvider();
    }

}

