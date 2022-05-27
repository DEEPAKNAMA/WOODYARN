package com.sdp3.SDP3.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.sdp3.SDP3.entites.*;
import com.sdp3.SDP3.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private FavouriteService favouriteService;

    @RequestMapping("/buy/{id}")
    public String buyProduct(@PathVariable(value = "id") String pid, HttpSession session, Model model){
        Product p=productService.findProductById(pid);
        Long uid=(Long) session.getAttribute("id");
        if(uid==null){
            model.addAttribute("bsorl",1);
            return "home";
        }
        Users u=usersService.getUserByUserId(uid);
        Store s=p.getStore();

        model.addAttribute("store",s);
        model.addAttribute("product",p);
        model.addAttribute("user",u);
        session.setAttribute("buysid",s.getStoreId());
        session.setAttribute("buypid",p.getProductId());
        return "checkout";
    }
    @PostMapping("/buy/orderc")
    @ResponseBody
    public String orderC(@RequestBody Map<String,Object> data,HttpSession session)throws Exception{
        System.out.println(data);
        int amt=Integer.parseInt(data.get("amount").toString());
        String address=data.get("address").toString();
        System.out.println(address);
        var client=new RazorpayClient("*************","****************");
        JSONObject options = new JSONObject();
        options.put("amount", amt*100);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        Order order=client.Orders.create(options);
        System.out.println(order);
        Orders o=new Orders();
        o.setOrderAmount(order.get("amount")+"");
        o.setOrderId(order.get("id"));
        o.setOrderAddress(address);
        o.setPaymentId(null);
        o.setOrderStatus("created");
        Long uid=(Long) session.getAttribute("id");
        o.setOrderedUserId(usersService.getUserByUserId(uid));
        Long sid=(Long) session.getAttribute("buysid");
        o.setOrderedStoreId(storeService.getStoreByStoreId(sid));
        Long pid=(Long) session.getAttribute("buypid");
        o.setOrderedProductId(productService.getByProductById(pid));
        o.setOrderReceipt(order.get("receipt"));
        o.setOrderState("SellerAtToConfirm");
        orderService.saveOrder(o);
        return order.toString();
    }


    @PostMapping("/corder")
    @ResponseBody
    public ResponseEntity<?> orders(@RequestBody Map<String,Object> data, HttpSession session, HttpServletResponse httpResponse) throws Exception {
        Orders o=orderService.getOrderById(data.get("order_id").toString());
        o.setPaymentId(data.get("payment_id").toString());
        o.setOrderStatus(data.get("order_status").toString());
        orderService.saveOrder(o);
        return ResponseEntity.ok(Map.of("msg","updated"));
    }



    @RequestMapping("/fav/{id}")
    public String favProduct(@PathVariable(value = "id") String pid, HttpSession session, Model model){
        Product p=productService.findProductById(pid);
        Long uid=(Long) session.getAttribute("id");
        if(uid==null){
            model.addAttribute("bsorll",1);
            return "home";
        }
        Users u=usersService.getUserByUserId(uid);
        Store s=p.getStore();
        Favourite favourite=new Favourite();
        favourite.setUsers(u);
        favourite.setProduct(p);
        favouriteService.saveFavourite(favourite);
        List<Favourite> products=favouriteService.getFavProductsByUserId(u);
        model.addAttribute("product",products);
        return "fav";
    }


}
