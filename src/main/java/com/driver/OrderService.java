package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class OrderService {
   // @Autowired
    OrderRepository or=new OrderRepository();
    public void addOrder(Order order)
    {
        or.addOrder(order);
    }
    public void addPartner(String deliveryPartnerid)
    {
        or.addPartner(deliveryPartnerid);
    }
    public void addOrderPartnerPair(String orderid,String deliveryPartnerid)
    {
        or.addOrderPartnerPair(orderid,deliveryPartnerid);
    }
    public Order getOrderbyOrderId(String orderid)
    {
        return or.getOrderbyOrderId(orderid);
    }
    public DeliveryPartner getPartnerbyPartnerId(String dpid)
    {
        return or.getPartnerbyPartnerId(dpid);
    }
    public int OrderCount(String dpid)
    {
        return or.getOrderCount(dpid);
    }
    public List<String> OrdersbyPartnerId(String dpid)
    {
        return or.getOrdersbyPartnerId(dpid);
    }
    public List<String>getallOrders()
    {
        return or.getAllOrders();
    }
    public int getCountOfUnassignedorders()
    {
       return or.countofordersnotassignedtopartner();
    }
    public int getOrdersLeftAfterGivenTimeBypartnerId(String time,String partnerid)
    {
        return or.getOrdersLeftAfterGivenTimeByPartnerid(time,partnerid);
    }
    public String getLastDeliveryTimeBypartnerId(String partnerid)
    {
        return or.getLastDeliveryTimeByPartnerid(partnerid);
    }
    public void deletepartnerById(String partnerid)
    {
        or.deletePartnerByid(partnerid);
    }
    public void  deleteOrderByid(String orderid)
    {
        or.deleteOrderById(orderid);
    }
}
