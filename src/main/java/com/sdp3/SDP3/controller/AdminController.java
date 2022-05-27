package com.sdp3.SDP3.controller;

import com.sdp3.SDP3.entites.*;
import com.sdp3.SDP3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private FavouriteService favouriteService;

    @RequestMapping(value = "/a/updateOrderState",method = RequestMethod.POST)
    public String updateOrderState(@ModelAttribute("orders") Orders orders, Model model, HttpSession session){
        Orders orders1=orderService.getOrderById(orders.getOrderId());
        System.out.println(orders1.getOrderAddress());
        orders1.setOrderState(orders.getOrderState());
        orderService.saveOrder(orders1);
        model.addAttribute("title","Admin - Wood & Yarn");
        List<Orders> orders3=orderService.getAllOrders();
        model.addAttribute("orders",orders3);
        return "admin";
    }


    @RequestMapping(value = "/a/addmtm/{id}",method = RequestMethod.POST)
    public String addMoneyToMerchant(@ModelAttribute("wallet") Wallet w, Model model, HttpSession session,@PathVariable(value = "id") String pid){
        model.addAttribute("title","Admin - Wood & Yarn");
        Wallet wallet=walletService.findWalletByStoreId(w.getStore().getStoreId());
        System.out.println();
        Long walbal=wallet.getWalletBalance();
        walbal= walbal+Long.parseLong(pid);
        wallet.setWalletBalance(walbal);
        walletService.createWallet(wallet);
        List<Orders> orders3=orderService.getAllOrders();
        model.addAttribute("orders",orders3);
        return "admin";
    }
}
