package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
@Repository

public class OrderRepository {
    HashMap<String,Order> orderdB=new HashMap<>();
    HashMap<String,DeliveryPartner> deliverydB=new HashMap<>();
    HashMap<String, List<String>> partnerOrderdB=new HashMap<>();
    HashMap<String,String>orderpartnerdB=new HashMap<>();
    public void addOrder(Order or)
    {
        String id=or.getId();
        orderdB.put(id,or);
    }
    public void addPartner(String dpid)
    {
        DeliveryPartner dp=new DeliveryPartner(dpid);
        deliverydB.put(dpid,dp);

    }
    public void addOrderPartnerPair(String orid,String dpid)
    {

           // List<String> orderlist;
        if(!partnerOrderdB.containsKey(dpid)) {
            partnerOrderdB.put(dpid,new ArrayList<>());
        }

       partnerOrderdB.get(dpid).add(orid);
        orderpartnerdB.put(orid,dpid);
        DeliveryPartner db=deliverydB.get(dpid);
        db.setNumberOfOrders(db.getNumberOfOrders()+1);

            //if (!orderpartner.containsKey(orid))
            //    orderpartner.put(orid, dpid);
        }


    public Order getOrderbyOrderId(String orid)
    {
        return orderdB.get(orid);
    }
    public DeliveryPartner getPartnerbyPartnerId(String dpid)
    {
        return deliverydB.get(dpid);
    }
    public int getOrderCount(String dpid)
    {
        if (partnerOrderdB.containsKey(dpid))
        return deliverydB.get(dpid).getNumberOfOrders();
        else
            return 0;
    }
    public List<String> getOrdersbyPartnerId(String dpid)
    {
       // List<>
        if(partnerOrderdB.containsKey(dpid))
        {
           List<String>orderidlist=partnerOrderdB.get(dpid);
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
        for(String orid:orderdB.keySet())
        {
           orderlist.add(orid);
        }
        return orderlist;
    }
    public int countofordersnotassignedtopartner()
    {
        //if(orderdB.size()>0 && orderpartnerdB.size()>0)
        return orderdB.size()-orderpartnerdB.size();
         //  else
      //  return 0;
    }
    public int countofordersassignedtopartner()
    {
        return orderpartnerdB.size();
    }
    public int getOrdersLeftAfterGivenTimeByPartnerid(String t,String dpid)
    {
        int count=0;
       int val=Integer.valueOf(t.substring(0,2))*60+Integer.valueOf((t.substring(3,5)));
       List<String>orderlist=partnerOrderdB.get(dpid);
       for(String orid:orderlist)
       {
          Order or=orderdB.get(orid);
          int dt=or.getDeliveryTime();
          if(dt>val)
              count++;
       }
       return count;
    }
    public String getLastDeliveryTimeByPartnerid(String dpid)
    {
        List<String>orderlist=partnerOrderdB.get(dpid);
        int max=0;
        for(String orid:orderlist)
        {
            Order or=orderdB.get(orid);
            int time=or.getDeliveryTime();
            if(time>max)
            {
                max=time;
            }
        }
        String hour=String.valueOf(max/60);
        String min=String.valueOf(max%60);

        if(hour.length() == 1)
            hour= "0" + hour;
        if(min.length() == 1)
            min = "0" + min;
        return hour +":"+min;

    }
    public void deletePartnerByid(String dpid)
    {

         for(String or:orderpartnerdB.keySet())
         {
             String dp=orderpartnerdB.get(or);
             if(dp.equals(dpid)) {
                 orderpartnerdB.remove(or);
                 //.remove(or);

             }
         }
        if(partnerOrderdB.containsKey(dpid))
        {
            partnerOrderdB.remove(dpid);
        }
        if(deliverydB.containsKey(dpid))
            deliverydB.remove(dpid);
    }
    public void deleteOrderById(String orid)
    {


        if(orderpartnerdB.containsKey(orid))
        {
            String dpid=orderpartnerdB.get(orid);

            if(partnerOrderdB.containsKey((dpid)))
            {
                List<String>orderlist=partnerOrderdB.get(dpid);
                for(int i=0;i<orderlist.size();i++)
                {
                    if(orderlist.get(i).equals(orid))
                        orderlist.remove(i);
                }
            }
            deliverydB.get(dpid).setNumberOfOrders(deliverydB.get(dpid).getNumberOfOrders()-1);
            orderpartnerdB.remove(orid);
            orderdB.remove(orid);
        }
    }

}
