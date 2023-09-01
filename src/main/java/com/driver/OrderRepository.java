package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class OrderRepository {
    HashMap<String,Order> order=new HashMap<>();
    HashMap<String,DeliveryPartner> delivery=new HashMap<>();
    HashMap<String, List<String>> deliveryPartnerOrder=new HashMap<>();
    HashMap<String,String>orderpartner=new HashMap<>();
    public void addOrder(Order or)
    {
        String id=or.getId();
        order.put(id,or);
    }
    public void addPartner(String dpid)
    {
        DeliveryPartner dp=new DeliveryPartner(dpid);
        delivery.put(dpid,dp);

    }
    public void addOrderPartnerPair(String orid,String dpid)
    {
        List<String>orderlist;
       if(deliveryPartnerOrder.containsKey(dpid))
       {
           orderlist=deliveryPartnerOrder.get(dpid);
           orderlist.add(orid);
           if(delivery.containsKey(dpid))
           {
               DeliveryPartner dp=delivery.get(dpid);
               dp.setNumberOfOrders(orderlist.size());
           }
       }
       else
       {
           orderlist=new ArrayList<>();
           orderlist.add(orid);
           deliveryPartnerOrder.put(dpid,orderlist);
           if(delivery.containsKey(dpid))
           {
               DeliveryPartner dp=delivery.get(dpid);
               dp.setNumberOfOrders(1);
           }
       }
       if(!orderpartner.containsKey(orid))
           orderpartner.put(orid,dpid);

    }
    public Order getOrderbyOrderId(String orid)
    {
        return order.get(orid);
    }
    public DeliveryPartner getPartnerbyPartnerId(String dpid)
    {
        return delivery.get(dpid);
    }
    public int getOrderCount(String dpid)
    {
        if (deliveryPartnerOrder.containsKey(dpid))
        return delivery.get(dpid).getNumberOfOrders();
        else
            return 0;
    }
    public List<String> getOrdersbyPartnerId(String dpid)
    {
       // List<>
        if(deliveryPartnerOrder.containsKey(dpid))
        {
           List<String>orderidlist=deliveryPartnerOrder.get(dpid);
           return orderidlist;
        }
        else
        {
            return new ArrayList<>();
        }
    }
    public List<String> getAllOrders()
    {
        List<String>orderlist=new ArrayList<>();
        for(String orid:order.keySet())
        {
           orderlist.add(orid);
        }
        return orderlist;
    }
    public int countofordersnotassignedtopartner()
    {
        return order.size()-orderpartner.size();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerid(String t,String dpid)
    {
        int count=0;
       int val=Integer.valueOf(t.substring(0,2))*60+Integer.valueOf((t.substring(3,5)));
       List<String>orderlist=deliveryPartnerOrder.get(dpid);
       for(String orid:orderlist)
       {
          Order or=order.get(orid);
          int dt=or.getDeliveryTime();
          if(dt>val)
              count++;
       }
       return count;
    }
    public String getLastDeliveryTimeByPartnerid(String dpid)
    {
        List<String>orderlist=deliveryPartnerOrder.get(dpid);
        int max=0;
        for(String orid:orderlist)
        {
            Order or=order.get(orid);
            int time=or.getDeliveryTime();
            if(time>max)
            {
                max=time;
            }
        }
        int hour=max/60;
        int min=max%60;
        return String.valueOf(hour)+":"+String.valueOf(min);

    }
    public void deletePartnerByid(String dpid)
    {
         if(deliveryPartnerOrder.containsKey(dpid))
         {
             deliveryPartnerOrder.remove(dpid);
         }
         if(delivery.containsKey(dpid))
             delivery.remove(dpid);
         for(String or:orderpartner.keySet())
         {
             String dp=orderpartner.get(or);
             if(dp.equals(dpid)) {
                 orderpartner.remove(or);
                 order.remove(or);
             }
         }
    }
    public void deleteOrderById(String orid)
    {
        if(order.containsKey((orid)))
            order.remove(orid);
        if(orderpartner.containsKey(orid))
        {
            String dpid=orderpartner.get(orid);
            orderpartner.remove(orid);
            if(deliveryPartnerOrder.containsKey((dpid)))
            {
                List<String>orderlist=deliveryPartnerOrder.get(dpid);
                for(int i=0;i<orderlist.size();i++)
                {
                    if(orderlist.get(i).equals(orid))
                        orderlist.remove(i);
                }
            }
        }
    }

}
