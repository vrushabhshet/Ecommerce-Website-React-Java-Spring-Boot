package ecommerce.dinedesign.dto;

import java.util.List;

public class ProductSearchFilterDto {
    private List<Integer> prices;
    private List<String> manufacturers;
    private List<String> categories;
    private String productCategory;
    private String manufacturer;

    public ProductSearchFilterDto() {}

    public List<Integer> getPrices() { return prices; }
    public void setPrices(List<Integer> prices) { this.prices = prices; }
    public List<String> getManufacturers() { return manufacturers; }
    public void setManufacturers(List<String> manufacturers) { this.manufacturers = manufacturers; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
}