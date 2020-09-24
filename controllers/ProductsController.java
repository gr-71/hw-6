package com.rga.springwebapp.controllers;

import com.rga.springwebapp.service.InitInfoService;
import com.rga.springwebapp.domain.Product;
import com.rga.springwebapp.service.ProductsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private ProductsService productsService;
    List<Product> products = InitInfoService.getProducts();


    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
        initProducts();
    }

    private void initProducts() {
        for (Product product : products) {
            productsService.save (product);
        }
    }

    // http://localhost:8090/app/products - GET
    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/list")
    public String list(Model model){
        products = productsService.getProductJpaDAO ().findAll ();
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8090/app/products/1 - GET
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getById(Model model, @PathVariable("id") Long id){
        Product byId = productsService.findById (id);
        model.addAttribute("product",
                byId == null ? new Product(): byId);
        return "product";
    }

    // http://localhost:8090/app/products/1/price - GET
    @RequestMapping(value = "/{id}/price", method = RequestMethod.GET)
    @ResponseBody
    public String apiPrice(@PathVariable Long id){
        Product byId = productsService.findById (id);
        return String.valueOf(byId == null ? null : byId.getPrice());
    }

    // http://localhost:8090/app/products/new - GET
    @GetMapping("/new")
    public String getFormNewProduct(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    // http://localhost:8090/app/products/new - POST
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String addNewProduct(Product savedProduct){
        productsService.save (savedProduct);
        System.out.println(savedProduct);
        return "redirect:/products/" + savedProduct.getId();
    }

    // http://localhost:8080/app/filter {title:"asd"}
    @PostMapping("/filter")
    @ResponseBody
    public String filterByTitle(@RequestParam String title){
        return productsService.getAll().stream()
                .filter(product-> product.getTitle().contains(title))
                .map(product -> String.valueOf(product.getId()))
                .collect(Collectors.joining(","));
    }

    // http://localhost:8090/app/products?id=1 - GET
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getByIdLink(Model model, @RequestParam Long id){
        Product byId = productsService.findById (id);
        model.addAttribute("product",
                byId == null ? new Product(): byId);
        return "product";
    }

    // http://localhost:8090/app/products/update?id=3 - GET
    @GetMapping("/update")
    public String getFormUpdateProduct(Model model,@RequestParam(name = "id") long id){
        Product byId = productsService.findById (id);
        model.addAttribute("product",
                byId == null ? new Product(): byId);
        return "update-product";
    }

    // http://localhost:8090/app/products/update - POST
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProduct(Product updateProduct){
        productsService.update (updateProduct);
        System.out.println(updateProduct);
        return "redirect:/products/" + updateProduct.getId();
    }

    // http://localhost:8090/app/products/any
    @RequestMapping(value = "any")
    @ResponseBody
    public String anyRequest(){
        return "any request " + UUID.randomUUID().toString();
    }

    // http://localhost:8090/app/products/like?name=Product_7 {name:"asd"}
    @GetMapping("/like")
    public String filterByName(Model model,
                               @RequestParam String name){
        List<Product> products = productsService.getProductJpaDAO ().findAllByTitleLike (name);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8090/app/products?priceFrom=500&priceTo=700
    @GetMapping(params = {"priceFrom", "priceTo"})
    public String productsByPrice(Model model,
                                  @RequestParam double priceFrom,
                                  @RequestParam double priceTo){
        List<Product> products = productsService.getProductJpaDAO ().findAllByPriceBetween (priceFrom, priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8090/app/products/filter?priceFrom=500&priceTo=700
    @GetMapping("/filter")
    public String filterByPrice(Model model,
                                @RequestParam double priceFrom,
                                @RequestParam(required = false) Double priceTo){
        List<Product> products = productsService.getProductJpaDAO ().findAllByPriceBetween (priceFrom, priceTo == null ? Double.MAX_VALUE : priceTo);
        model.addAttribute("products", products);
        return "list";
    }

    // http://localhost:8090/app/products/delete?id=1
    @RequestMapping(value = "/delete")
    public String deleteById(Model model,
                             @RequestParam(name = "id") long id){
        products.remove (productsService.findById (id));
        productsService.delete (id);
        model.addAttribute("products", products);
        return "redirect:/products/";
    }

    // http://localhost:8090/app/products/maxprice - GET
    @RequestMapping("/maxprice")
    public String maxPriceProduct(Model model){
        Product maxProduct = productsService.getProductJpaDAO ().findAll (Sort.by("price").descending ()).get(0);
        model.addAttribute("product",
                maxProduct == null ? new Product(): maxProduct);
        return "product";
    }

    // http://localhost:8090/app/products/minprice - GET
    @RequestMapping("/minprice")
    public String minPriceProduct(Model model){
        Product minProduct = productsService.getProductJpaDAO ().findAll (Sort.by("price").ascending()).get(0);
        model.addAttribute("product",
                minProduct == null ? new Product(): minProduct);
        return "product";
    }
}
