package com.sdp3.SDP3.controller;

import com.sdp3.SDP3.entites.*;
import com.sdp3.SDP3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
<<<<<<< HEAD

import static org.springframework.util.StringUtils.*;
=======
>>>>>>> Working

@Controller
public class MerchantController {
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

<<<<<<< HEAD
    @Autowired
    private S3service s3service;

    @Autowired
    private ProductService productService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private OrderService orderService;
=======
>>>>>>> Working

    @RequestMapping("/merchantRegister")
    public String merchant(Model model){
        model.addAttribute("title","Be a Merchant - Wood & Yarn");
        return "merch";
    }

    @RequestMapping(value="/merchantRegiste",
            method = RequestMethod.POST)
    public String merchantRegiste(@ModelAttribute("store") Store store, Model model, HttpSession session,
                                   @RequestParam("storeimage") MultipartFile file){
        try{
            model.addAttribute("title","Be a Merchant - Wood & Yarn");
            Long uid=(Long) session.getAttribute("id");
            if(file.isEmpty()){
                store.setStoreImage("test.jpg");
            }else{
                String fileName = cleanPath(file.getOriginalFilename());
                store.setStoreImage(fileName);
                File f=new ClassPathResource("static/img").getFile();
                Path p= Paths.get(f.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),p, StandardCopyOption.REPLACE_EXISTING);
            }
            Users users=usersService.getUserByUserId(uid);
            store.setUsers(users);
            storeService.registerMerchant(store);
            Wallet wallet=new Wallet();
            wallet.setWalletBalance(Long.valueOf(0));
            wallet.setStore(store);
            walletService.createWallet(wallet);


//            setting storeid in the session


            Store s=storeService.getUserByUserId(uid);
            Long storeid=s.getUsers().getUserId();
            session.setAttribute("storeid",storeid);

            Long uid11=(Long) session.getAttribute("storeid");
            Store s11=storeService.getUserByUserId(uid11);
            model.addAttribute("store",s11);
            List<Product> products=productService.getProductsByStoreId(s11.getStoreId());
            model.addAttribute("stprod",products);
            List<Blog> blogs=blogService.getBlogByStoreId(s11.getStoreId());
            model.addAttribute("stblog",blogs);
            List<Orders> o=orderService.getOrdersByStoreId(s11.getStoreId());
            model.addAttribute("storders",o);
            Wallet wal=walletService.findWalletByStoreId(uid11);
            model.addAttribute("wallet",wal);
<<<<<<< HEAD

=======
>>>>>>> Working
        }
        catch (Exception e){

        }

        return "store";
    }


    @RequestMapping(value="/merchantRegister",
            method = RequestMethod.POST)
    public String merchantRegister(@ModelAttribute("store") Store store, Model model, HttpSession session,
                                   @RequestParam("storeimage") MultipartFile file){
        try{
            model.addAttribute("title","Be a Merchant - Wood & Yarn");

            Long uid=(Long) session.getAttribute("id");

            String fname=s3service.uploadFile(file);

            store.setStoreImage(fname);
            Users users=usersService.getUserByUserId(uid);
            store.setUsers(users);
            storeService.registerMerchant(store);

            Wallet wallet=new Wallet();
            wallet.setWalletBalance(Long.valueOf(0));
            wallet.setStore(store);
            walletService.createWallet(wallet);

            Store s=storeService.getUserByUserId(uid);
            Long storeid=s.getUsers().getUserId();
            session.setAttribute("storeid",storeid);

            Long uid11=(Long) session.getAttribute("storeid");
            Store s11=storeService.getUserByUserId(uid11);
            model.addAttribute("store",s11);
            List<Product> products=productService.getProductsByStoreId(s11.getStoreId());
            model.addAttribute("stprod",products);
            List<Blog> blogs=blogService.getBlogByStoreId(s11.getStoreId());
            model.addAttribute("stblog",blogs);
            List<Orders> o=orderService.getOrdersByStoreId(s11.getStoreId());
            model.addAttribute("storders",o);
            Wallet wal=walletService.findWalletByStoreId(uid11);
            model.addAttribute("wallet",wal);
            return "store";
        }
        catch (Exception e){
            return "error";
        }

    }



    @GetMapping(value = "/storage/**")
    public void thumbnail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filename = request.getRequestURI();
        filename = filename.substring(9);
        byte[] bytes  = this.s3service.getFile(filename);
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
        String mimeType = URLConnection.guessContentTypeFromStream(is);
        response.setContentType(mimeType);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }


}
