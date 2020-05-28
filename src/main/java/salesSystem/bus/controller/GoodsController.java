package salesSystem.bus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import salesSystem.bus.domain.Goods;
import salesSystem.bus.service.GoodsService;
import salesSystem.bus.vo.GoodsVO;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-21
 */
@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("loadAllGoods")
    public DataGridView loadAllgoods(GoodsVO goodsVO) {

        return goodsService.loadAllGoods(goodsVO);
    }

    @RequestMapping("deleteGoods")
    public ResultObj deletegoods(Integer id) {
        try {
            goodsService.deleteGoods(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    @Transactional
    @RequestMapping("batchDeleteGoods")
    public ResultObj batchDeletegoods(Integer[] ids) {
        try {
            goodsService.batchDeleteGoods(ids);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_FAIL;
        }
    }

    //添加公告
    @RequestMapping("addGoods")
    public ResultObj addGoods(Goods goods, HttpSession session) {
        try {
            //存入数据库
            goodsService.addGoods(goods);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_FAIL;
        }
    }

    @RequestMapping("updateGoods")
    public ResultObj updateGoods(Goods goods) {
        try {
            //存入数据库
            goodsService.updateGoods(goods);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_FAIL;
        }
    }

    //loadGoodsById
    @RequestMapping("loadGoodsById")
    public DataGridView loadGoodsById(Integer id) {

        return new DataGridView(goodsService.getById(id));
    }

    //loadAllAvailableGoods
    @RequestMapping("loadAllAvailableGoods")
    public DataGridView loadAllAvailableGoods() {
        return goodsService.loadAllAvailableGoods();
    }

    //loadGoodsByProviderId
    @RequestMapping("loadGoodsByProviderId")
    public DataGridView loadGoodsByProviderId(Integer providerId) {
        System.out.println(providerId);
        return goodsService.loadGoodsByProviderId(providerId);
    }


}

