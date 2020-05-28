package salesSystem.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.bus.domain.Customer;
import salesSystem.bus.domain.Goods;
import salesSystem.bus.domain.Sales;
import salesSystem.bus.mapper.CustomerMapper;
import salesSystem.bus.mapper.GoodsMapper;
import salesSystem.bus.mapper.SalesMapper;
import salesSystem.bus.service.SalesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.bus.vo.SalesVO;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-25
 */
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {


    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public DataGridView loadAllSales(SalesVO salesVO) {
        QueryWrapper<Sales> qw = new QueryWrapper<>();
        qw.eq(salesVO.getCustomerid() != null, "customerid", salesVO.getCustomerid());
        qw.eq(salesVO.getGoodsid() != null, "goodsid", salesVO.getGoodsid());
        qw.ge(salesVO.getStartTime() != null, "salestime", salesVO.getStartTime());
        qw.le(salesVO.getEndTime() != null, "salestime", salesVO.getEndTime());

        qw.orderByDesc("salestime");
        Page page = new Page<>(salesVO.getPage(), salesVO.getLimit());
        List<Sales> sales = salesMapper.selectPage(page, qw).getRecords();

        for (Sales s : sales) {
            Integer customerid = s.getCustomerid();
            Integer goodsid = s.getGoodsid();
            Customer customer = customerMapper.selectById(customerid);
            //客户名
            s.setCustomername(customer.getCustomername());
            Goods goods = goodsMapper.selectById(goodsid);
            //商品名
            s.setGoodsname(goods.getGoodsname());
            //规格
            s.setSize(goods.getSize());
        }


        return new DataGridView(page.getTotal(), sales);
    }


    @Transactional
    @Override
    public ResultObj addSales(Sales sales) {

        if (sales.getNumber() < 0) {
            return ResultObj.ERROR_NUMBER;
        }

        //仓库商品剩余量
        Goods goods = goodsMapper.selectById(sales.getGoodsid());
        Integer surplusGoods = goods.getNumber();
        if (surplusGoods >= sales.getNumber()) {
            //减库存
            goodsMapper.updateGoodsNumberById(sales.getGoodsid(), -sales.getNumber());
            //添加订单
            salesMapper.insert(sales);
            return ResultObj.ADD_SUCCESS;
        } else {
            return ResultObj.SURPLUS_GOODS_NOT_ENOUGH;
        }

    }

    @Transactional
    @Override
    public ResultObj updateSales(Sales sales) {

        System.out.println(sales.toString());

        if (sales.getNumber() < 0) {
            return ResultObj.ERROR_NUMBER;
        }

        //原销售订单
        Sales originalSales = salesMapper.selectById(sales.getId());
        //原来的销售数量
        Integer oldNumber = originalSales.getNumber();
        //仓库的数量
        Goods goods = goodsMapper.selectById(originalSales.getGoodsid());
        Integer number = goods.getNumber();
        //现在数量  new 1000  old 500   new 400
        Integer newNumber = number + oldNumber - sales.getNumber();
        if (newNumber >= 0) {
            sales.setNumber(sales.getNumber());
            goods.setNumber(newNumber);
            //更新订单
            salesMapper.updateById(sales);
            //更新仓库
            goodsMapper.updateById(goods);
            return ResultObj.UPDATE_SUCCESS;
        } else {
            return ResultObj.SURPLUS_GOODS_NOT_ENOUGH;
        }
    }

    @Transactional
    @Override
    public void deleteSales(Integer id, Integer num) {

        //销售订单
        Sales sales = salesMapper.selectById(id);
        //对应的仓库中的商品
        Goods goods = goodsMapper.selectById(sales.getGoodsid());

        goodsMapper.updateGoodsNumberById(goods.getId(), num);
        salesMapper.deleteById(id);


    }
}
