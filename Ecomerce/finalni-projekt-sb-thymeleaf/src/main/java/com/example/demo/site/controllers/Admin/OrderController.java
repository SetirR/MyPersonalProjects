/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers.Admin;

import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderStatus;
import com.example.demo.site.entities.User;
import com.example.demo.site.exceptions.OrderNotFoundException;
import com.example.demo.site.forms.OrderFormFiltred;
import com.example.demo.site.services.OrderService;
import com.example.demo.site.services.OrderStatusService;
import com.example.demo.site.services.UserService;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/admin/objednavky")
@Controller
public class OrderController
{
    @Inject
    private OrderService orderService;
    
    @Inject 
    private OrderStatusService orderStatusService;
    
    @Inject
    private UserService userService;
    
    
    @GetMapping("/zobrazit")
    public String showAll(Model model, Pageable pageable, @RequestParam(required = false) Long orderStatusId, Long userId)
    {
        boolean showResetButton =
                orderStatusId != null
                || userId != null;
        model.addAttribute("showResetButton", showResetButton);

        List<OrderStatus> orderStatus = orderStatusService.findAll();
        List<User> users = userService.findAll();

        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("users", users);
        
        OrderFormFiltred orderFormFiltred = new OrderFormFiltred();
        orderFormFiltred.setOrderStatusId(orderStatusId);
        orderFormFiltred.setUserId(userId);

        model.addAttribute("orderFormFiltred" , orderFormFiltred);

        Page<Order> page = orderService.findFiltred(orderStatusId, userId, pageable);

        model.addAttribute("page", page);

        model.addAttribute("sort", page
                .getSort()
                .get()
                .map(s -> s.getProperty() + "," + s.getDirection().name().toLowerCase())
                .findFirst()
                .orElse(null));
        
        return "admin/order/interface";
    }
    
    
    @PostMapping("/zobrazit")
    public RedirectView showAll(OrderFormFiltred orderFormFiltred, RedirectAttributes redirectAttributes)
    {
        Long orderStatusId = orderFormFiltred.getOrderStatusId();
        if (orderStatusId != null)
            redirectAttributes.addAttribute("orderStatusId", orderStatusId);

        Long userId = orderFormFiltred.getUserId();
        if (userId != null)
            redirectAttributes.addAttribute("userId", userId);


        return new RedirectView("/admin/objednavky/zobrazit", true);
    }
    
    @RequestMapping("/potvrdit/{id}")
    public String confirm(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Optional<Order> optOrder = orderService.findById(id);
        if(optOrder.isEmpty())
        {
            throw new OrderNotFoundException("Objednavka nenalezena");
        }
        Order order = optOrder.get();
        
        orderService.confirmOrder(order);
        redirectAttributes.addFlashAttribute("message", "Objednavka č." + order.getId() + " byla úspěšně potvrzena");
        return "redirect:/admin/objednavky/zobrazit";
    }
    
    @RequestMapping("/zrusit/{id}")
    public String cancel(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Optional<Order> optOrder = orderService.findById(id);
        if(optOrder.isEmpty())
        {
            throw new OrderNotFoundException("Objednavka nenalezena");
        }
        Order order = optOrder.get();
        
        orderService.cancelOrder(order);
        redirectAttributes.addFlashAttribute("message", "Objednavka č." + order.getId() + " byla úspěšně zrušena");
        return "redirect:/admin/objednavky/zobrazit";
    }
    
    @RequestMapping("/smazat/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Optional<Order> optOrder = orderService.findById(id);
        if(optOrder.isEmpty())
        {
            throw new OrderNotFoundException("Objednavka nenalezena");
        }
        
        Order order = optOrder.get();
        redirectAttributes.addFlashAttribute("message", "Objednavka č." + order.getId() + " byla úspěšně smazana");
        orderService.delete(order);
        return "redirect:/admin/objednavky/zobrazit";
    }
    
}
