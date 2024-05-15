package com.urbangear.ecommercecars.controller;

import com.urbangear.ecommercecars.domain.order;
import com.urbangear.ecommercecars.domain.car;
import com.urbangear.ecommercecars.service.contactFormService;
import com.urbangear.ecommercecars.service.carService;
import com.urbangear.ecommercecars.service.orderService;
import com.urbangear.ecommercecars.domain.contactForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/web")
public class webController {
    @Autowired
    private contactFormService contactFormService;
    @Autowired
    private carService carService;
    @Autowired
    private orderService orderService;


    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        // Check if the user is logged in
        if (session.getAttribute("session") == null) {
            // Redirect to the login page if the user is not logged in
            return "redirect:/web/error";
        }

        // Fetch cars data based on categories
        List<car> crossoverCars = carService.findByCategory("crossover");
        List<car> suvCars = carService.findByCategory("suv");
        List<car> sedanCars = carService.findByCategory("sedan");
        List<car> hatchbackCars = carService.findByCategory("hatchback");
        List<car> allCars = carService.getAllCars();

        // Decode and convert images for each category
        List<String> crossoverCarsImages = decodeImages(crossoverCars);
        List<String> suvCarsImages = decodeImages(suvCars);
        List<String> sedanCarsImages = decodeImages(sedanCars);
        List<String> hatchbackCarsImages = decodeImages(hatchbackCars);
        List<String> allCarsImages = decodeImages(allCars);

        // Add the car lists and corresponding Base64-encoded images to the model
        model.addAttribute("crossoverCars", crossoverCars);
        model.addAttribute("suvCars", suvCars);
        model.addAttribute("sedanCars", sedanCars);
        model.addAttribute("hatchbackCars", hatchbackCars);
        model.addAttribute("allCars", allCars);

        model.addAttribute("crossoverCarsImages", crossoverCarsImages);
        model.addAttribute("suvCarsImages", suvCarsImages);
        model.addAttribute("sedanCarsImages", sedanCarsImages);
        model.addAttribute("hatchbackCarsImages", hatchbackCarsImages);
        model.addAttribute("allCarsImages", allCarsImages);

        return "home";
    }


    private List<String> decodeImages(List<car> cars) {
        List<String> base64Images = new ArrayList<>();
        for (car car : cars) {
            base64Images.add(decodeImage(car.getImage()));
        }
        return base64Images;
    }

    private String decodeImage(byte[] imageData) {
        try {
            // Check if imageData is null or empty
            if (imageData == null || imageData.length == 0) {
                return "";
            }

            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageData);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Return an empty string or handle appropriately
        }
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@ModelAttribute("contactForm") contactForm contactForm) {
        // Save the form data using the service
        contactFormService.saveContactForm(contactForm);

        // Redirect the user to the contact page
        return "redirect:/web/contact";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "errorPage";
    }



    @GetMapping("/contact")
    public String showContactForm(Model model, HttpSession session) {
        // Check if the user is logged in
        if (session.getAttribute("session") == null) {
            // Redirect to the login page if the user is not logged in
            return "redirect:/web/error";
        }
        // Add the contactForm object to the model
        model.addAttribute("contactForm", new contactForm());

        return "contact";
    }


    @GetMapping("/checkout")
    public String showCheckout(HttpSession session) {
        // Check if the user is logged in
        if (session.getAttribute("session") == null) {
            // Redirect to the login page if the user is not logged in
            return "redirect:/web/error";
        }

        return "checkout";
    }

    @GetMapping("/payment")
    public String showPayment(HttpSession session) {
        // Check if the user is logged in
        if (session.getAttribute("session") == null) {
            // Redirect to the login page if the user is not logged in
            return "redirect:/web/error";
        }
        return "payment";
    }


    @PostMapping("/saveOrder")
    public String saveOrder(@RequestParam String itemsName, @RequestParam Integer items, @RequestParam Double total_price, HttpSession session) {
        try {
            String username = (String) session.getAttribute("username");

            order orderItem = new order();
            orderItem.setItemsName(itemsName);
            orderItem.setItems(items);
            orderItem.setTotal_price(total_price);
            orderItem.setUser(username);

            orderService.saveOrder(orderItem);

            return "redirect:/web/success";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/web/error";
        }
    }



    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        // Clear session attributes
        session.removeAttribute("session");

        // Invalidate the user session
        session.invalidate();

        // Expire any existing cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
            }
        }
        return "redirect:/api/users/login";
    }

    @RequestMapping("/error")
    public String handleError() {
        // Provide a custom error page or redirect to a specific page
        return "orderErrorPage";
    }

    @RequestMapping("/success")
    public String handleSuccess() {
        // Provide a custom error page or redirect to a specific page
        return "orderSuccessPage";
    }


}
