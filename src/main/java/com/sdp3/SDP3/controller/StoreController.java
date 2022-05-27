package com.sdp3.SDP3.controller;

import com.sdp3.SDP3.entites.*;
import com.sdp3.SDP3.repository.OrderRepository;
import com.sdp3.SDP3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {
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
    private S3service s3service;

//    @RequestMapping(value = "/addProducts",method = RequestMethod.POST)
//    public String addProduct(@ModelAttribute("product") Product product, Model model, HttpSession session,
//                             @RequestParam("productimage1") MultipartFile file1,
//                             @RequestParam("productimage2") MultipartFile file2){
//        Long uid=(Long) session.getAttribute("storeid");
//        Store s=storeService.getUserByUserId(uid);
//        model.addAttribute("store",s);
//        try{
//            if(file1.isEmpty()){
//                product.setProductImage1("test.jpg");
//            }else{
//                String fileName = StringUtils.cleanPath(file1.getOriginalFilename());
//                product.setProductImage1(fileName);
//                File f=new ClassPathResource("static/img").getFile();
//                Path p= Paths.get(f.getAbsolutePath()+File.separator+file1.getOriginalFilename());
//                Files.copy(file1.getInputStream(),p, StandardCopyOption.REPLACE_EXISTING);
//            }
//            if(file2.isEmpty()){
//                product.setProductImage2("test.jpg");
//            }else{
//                String fileName = StringUtils.cleanPath(file2.getOriginalFilename());
//                product.setProductImage2(fileName);
//                File f=new ClassPathResource("static/img").getFile();
//                Path p= Paths.get(f.getAbsolutePath()+File.separator+file2.getOriginalFilename());
//                Files.copy(file2.getInputStream(),p, StandardCopyOption.REPLACE_EXISTING);
//            }
//            product.setStore(s);
//            productService.saveProduct(product);
//            Long uid1=(Long) session.getAttribute("storeid");
//            Store s1=storeService.getUserByUserId(uid1);
//            model.addAttribute("store",s1);
//            List<Product> products=productService.getProductsByStoreId(s1.getStoreId());
//            model.addAttribute("stprod",products);
//            List<Blog> blogs=blogService.getBlogByStoreId(s1.getStoreId());
//            model.addAttribute("stblog",blogs);
//            List<Orders> o=orderService.getOrdersByStoreId(s1.getStoreId());
//            model.addAttribute("storders",o);
//            Wallet wallet=walletService.findWalletByStoreId(uid1);
//            model.addAttribute("wallet",wallet);
//
//        }catch (Exception e){
//        }
//
//        return "store";
//    }



    @RequestMapping(value = "/addProduct",method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product, Model model, HttpSession session,
                             @RequestParam("productimage1") MultipartFile file1,
                             @RequestParam("productimage2") MultipartFile file2){

        Long uid=(Long) session.getAttribute("storeid");
        Store s=storeService.getUserByUserId(uid);
        model.addAttribute("store",s);

        try{
            String fname1=s3service.uploadFile(file1);
            product.setProductImage1(fname1);
            String fname2=s3service.uploadFile(file2);
            product.setProductImage2(fname2);
            product.setStore(s);
            productService.saveProduct(product);
            Long uid1=(Long) session.getAttribute("storeid");
            Store s1=storeService.getUserByUserId(uid1);
            model.addAttribute("store",s1);
            List<Product> products=productService.getProductsByStoreId(s1.getStoreId());
            model.addAttribute("stprod",products);
            List<Blog> blogs=blogService.getBlogByStoreId(s1.getStoreId());
            model.addAttribute("stblog",blogs);
            List<Orders> o=orderService.getOrdersByStoreId(s1.getStoreId());
            model.addAttribute("storders",o);
            Wallet wallet=walletService.findWalletByStoreId(uid1);
            model.addAttribute("wallet",wallet);

        }catch (Exception e){
        }

        return "store";
    }


//    @RequestMapping(value = "/addBlog",method = RequestMethod.POST)
//    public String addBlog(@ModelAttribute("blog") Blog blog, Model model, HttpSession session,
//                          @RequestParam("blogimage") MultipartFile file){
//
//        Long uid=(Long) session.getAttribute("storeid");
//        Store s=storeService.getUserByUserId(uid);
//        model.addAttribute("store",s);
//        try{
//            if(file.isEmpty()){
//                blog.setBlogImage("test.jpg");
//            }else{
//                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//                blog.setBlogImage(fileName);
//                File f=new ClassPathResource("static/img").getFile();
//                Path p= Paths.get(f.getAbsolutePath()+File.separator+file.getOriginalFilename());
//                Files.copy(file.getInputStream(),p, StandardCopyOption.REPLACE_EXISTING);
//            }
//            blog.setStore(s);
//            blogService.saveBlog(blog);
//            Long uid1=(Long) session.getAttribute("storeid");
//            Store s1=storeService.getUserByUserId(uid1);
//            model.addAttribute("store",s1);
//            List<Product> products=productService.getProductsByStoreId(s1.getStoreId());
//            model.addAttribute("stprod",products);
//            List<Blog> blogs=blogService.getBlogByStoreId(s1.getStoreId());
//            model.addAttribute("stblog",blogs);
//            List<Orders> o=orderService.getOrdersByStoreId(s1.getStoreId());
//            model.addAttribute("storders",o);
//            Wallet wallet=walletService.findWalletByStoreId(uid1);
//            model.addAttribute("wallet",wallet);
//
//        }catch (Exception e){
//
//        }
//        return "store";
//    }


    @RequestMapping(value = "/addBlog",method = RequestMethod.POST)
    public String addBlog(@ModelAttribute("blog") Blog blog, Model model, HttpSession session,
                          @RequestParam("blogimage") MultipartFile file){

        Long uid=(Long) session.getAttribute("storeid");
        Store s=storeService.getUserByUserId(uid);
        model.addAttribute("store",s);
        try{
            String fname=s3service.uploadFile(file);
            blog.setBlogImage(fname);
            blog.setStore(s);
            blogService.saveBlog(blog);
            Long uid1=(Long) session.getAttribute("storeid");
            Store s1=storeService.getUserByUserId(uid1);
            model.addAttribute("store",s1);
            List<Product> products=productService.getProductsByStoreId(s1.getStoreId());
            model.addAttribute("stprod",products);
            List<Blog> blogs=blogService.getBlogByStoreId(s1.getStoreId());
            model.addAttribute("stblog",blogs);
            List<Orders> o=orderService.getOrdersByStoreId(s1.getStoreId());
            model.addAttribute("storders",o);
            Wallet wallet=walletService.findWalletByStoreId(uid1);
            model.addAttribute("wallet",wallet);

        }catch (Exception e){

        }
        return "store";
    }


    @RequestMapping(value = "/updateOrderState",method = RequestMethod.POST)
    public String updateOrderState(@ModelAttribute("orders") Orders orders, Model model,HttpSession session){
        Orders orders1=orderService.getOrderById(orders.getOrderId());
        System.out.println(orders1.getOrderAddress());
        orders1.setOrderState(orders.getOrderState());
        orderService.saveOrder(orders1);
        Long uid=(Long) session.getAttribute("storeid");
        Store s=storeService.getUserByUserId(uid);
        model.addAttribute("store",s);
        List<Product> products=productService.getProductsByStoreId(s.getStoreId());
        model.addAttribute("stprod",products);
        List<Blog> blogs=blogService.getBlogByStoreId(s.getStoreId());
        model.addAttribute("stblog",blogs);
        List<Orders> o=orderService.getOrdersByStoreId(s.getStoreId());
        model.addAttribute("storders",o);
        Wallet wallet=walletService.findWalletByStoreId(uid);
        model.addAttribute("wallet",wallet);
        return "store";
    }

    @RequestMapping(value = "/regiEvent")
    public String eventRegister(){

        return "store";
    }
}
