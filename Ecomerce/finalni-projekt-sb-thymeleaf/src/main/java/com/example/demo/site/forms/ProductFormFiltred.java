package com.example.demo.site.forms;




import javax.validation.constraints.NotBlank;

/**
 *
 * @author Setir
 */



public class ProductFormFiltred
{
    
    private String name;
    private long price;
    private String description; 
    private Long brandId;
    private Long categoryId;
    
    
    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    
}
