package com.example.demo.site.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.example.demo.site.entities.Brand;
import com.example.demo.site.entities.Category;
import javax.persistence.Table;

/**
 *
 * @author Setir
 */

@Table(name="product")
@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private long id;
    
    @Column
    private String name;
    
    @Column
    private long price;
    
    @Column
    private String description; 
    
    @Column
    private boolean enabled;
    
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable=false)
    private Brand brand;
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable=false)
    private Category category;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
