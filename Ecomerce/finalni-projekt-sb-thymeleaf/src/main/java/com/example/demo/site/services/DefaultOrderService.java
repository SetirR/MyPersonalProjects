/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderProduct;
import com.example.demo.site.entities.OrderStatus;
import com.example.demo.site.entities.User;
import com.example.demo.site.exceptions.OrderStatusNotFoundException;
import com.example.demo.site.repositories.CartRepository;
import com.example.demo.site.repositories.OrderProductRepository;
import com.example.demo.site.repositories.OrderRepository;
import com.example.demo.site.repositories.OrderStatusRepository;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author Setir
 */

@Service
public class DefaultOrderService implements OrderService
{
    @Inject
    private OrderRepository orderRepository;
    
    @Inject
    private OrderStatusRepository orderStatusRepository;
    
    @Inject 
    private OrderProductRepository orderProductRepository;
    
    @Inject
    private CartRepository cartRepository;

    @Override
    public Page<Order> findAll(Pageable pageable) 
    {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
    
    @Override
    public List<Order> findByUser(User user)
    {
        return orderRepository.findByUser(user);
    }

    static Specification<Order> hasOrderStatusId(Long orderStatusId)
    {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderStatus").get("id"), orderStatusId);
    }
    
    static Specification<Order> hasUserId(Long userId)
    {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }
    
    @Override
    public Page<Order> findFiltred(Long orderStatusId, Long userId, Pageable pageable) 
    {
        Specification<Order> specification = null;
        
        
        
        if (!StringUtils.isEmpty(orderStatusId))
        {
            specification = (specification == null)
                    ? Specification.where(hasOrderStatusId(orderStatusId))
                    : specification.and(hasOrderStatusId(orderStatusId));
        }
        
        if (!StringUtils.isEmpty(userId))
        {
            specification = (specification == null)
                    ? Specification.where(hasUserId(userId))
                    : specification.and(hasUserId(userId));
        }

        return orderRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    @Transactional
    public Order createOrder(User user) 
    {
        Order createdOrder = new Order();
        Optional<OrderStatus> optOrderStatus = orderStatusRepository.findByName("Created");
        if(optOrderStatus.isEmpty())
        {
            throw new OrderStatusNotFoundException("Order status not found");
        }
        
        OrderStatus orderStatus = optOrderStatus.get();
        createdOrder.setOrderStatus(orderStatus);
        createdOrder.setUser(user);
        orderRepository.save(createdOrder);
        return createdOrder;
    }

    @Override
    @Transactional
    public void sentOrder(Order order, Cart cart) {
        
        for(CartProduct cartProduct : cart.getCardProducts())
        {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setAmount(cartProduct.getAmount());
            orderProduct.setPrice(cartProduct.getProduct().getPrice()*cartProduct.getAmount());
            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);
        }
        
        cartRepository.delete(cart); 
        
    }

    @Override
    public Order confirmOrder(Order order) {
        
        Optional<OrderStatus> optOrderStatus = orderStatusRepository.findByName("Confirmed");
        if(optOrderStatus.isEmpty())
        {
            throw new OrderStatusNotFoundException("Order status not found");
        }
        OrderStatus orderStatus = optOrderStatus.get();
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order cancelOrder(Order order) {
        
        Optional<OrderStatus> optOrderStatus = orderStatusRepository.findByName("Canceled");
        if(optOrderStatus.isEmpty())
        {
            throw new OrderStatusNotFoundException("Order status not found");
        }
        OrderStatus orderStatus = optOrderStatus.get();
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return order;
    }
    
    

    
    
    
}
