package productcrudapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import productcrudapp.dao.ProductDao;
import productcrudapp.model.Product;

@Controller
public class MainController {
	
	@Autowired
	private ProductDao productdao;
	
	@RequestMapping("/")
	public String home(Model m) {
		List<Product> products = this.productdao.getProducts();
		System.out.println(products);
		m.addAttribute("products",products);
		return "home";
	}
	
	@RequestMapping("/add-product")
	public String addProduct(Model m) {
		m.addAttribute("title", "Add Product");
		return "addproduct_form";
	}
	
	@RequestMapping(value ="/handle-product", method = RequestMethod.POST)
	public RedirectView handleAddProduct(@ModelAttribute Product product, HttpServletRequest request) {
		System.out.println(product);
		
		this.productdao.createProduct(product);
		
		RedirectView rd = new RedirectView();
		rd.setUrl(request.getContextPath()+"/");
		return rd;
	}
	
	@RequestMapping("/delete/{productID}")
	public RedirectView addProduct(@PathVariable("productID") int productID,HttpServletRequest request) {
		this.productdao.deleteProduct(productID);
		RedirectView rd = new RedirectView();
		rd.setUrl(request.getContextPath()+"/");
		return rd;
	}
	
	@RequestMapping("/edit/{productID}")
	public String editProduct(@PathVariable("productID") int productID,Model m,HttpServletRequest request) {
		Product product = this.productdao.getProduct(productID);
		System.out.println("product: "+product);
		m.addAttribute("product",product);
		//RedirectView rd = new RedirectView();
		//rd.setUrl(request.getContextPath()+"/edit");
		return "edit";
	}

}
