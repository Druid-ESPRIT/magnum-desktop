package com.druid.interfaces;

import com.druid.models.Order;

import java.util.List;

public interface IOrder {
    public void addOrder(Order or);
    public List<Order> getOrders();
    public void updateOrder(Order or);
    public void deleteOrder(Order or);
}
