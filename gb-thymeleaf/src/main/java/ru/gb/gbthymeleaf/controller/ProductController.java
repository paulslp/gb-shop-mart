package ru.gb.gbthymeleaf.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbapi.product.api.ProductGateway;
import ru.gb.gbapi.product.dto.ProductDto;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductGateway productGateway;

    public ProductController(
            @Qualifier("productExtGateway") ProductGateway productGateway
    ) {
        this.productGateway = productGateway;
    }

    @GetMapping("/all")
    public String getProductList(Model model) {

        model.addAttribute("products", productGateway.getProductList());
        return "product-list";
    }

    @GetMapping("/{productId}")
    public String info(Model model, @PathVariable(name = "productId") Long id) {
        ProductDto product;
        if (id != null) {
            product = productGateway.getProduct(id).getBody();
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", product);
        return "product-info";
    }

    @GetMapping
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        ProductDto product;

        if (id != null) {
            product = productGateway.getProduct(id).getBody();
        } else {
            product = new ProductDto();
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping
    public String saveProduct(ProductDto product) {
        productGateway.handlePost(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        productGateway.deleteById(id);
        return "redirect:/product/all";
    }

}
