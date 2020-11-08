

package com.example.demo.site.controllers.Admin;

import com.example.demo.site.entities.Brand;
import com.example.demo.site.exceptions.BrandCantBeDeletedException;
import com.example.demo.site.exceptions.BrandNotFoundException;
import com.example.demo.site.forms.BrandForm;
import com.example.demo.site.forms.BrandFormFiltred;
import com.example.demo.site.services.BrandService;

import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.StringUtils;


@RequestMapping("/admin/znacky")
@Controller
public class BrandController
{
    
    @Inject
    private BrandService brandService;
    
    //administrace značek
    
    @GetMapping("/zobrazit")
    public String showAll(Model model,Pageable pageable, @RequestParam(required = false) String name)
    {
        // priznak zda zobrazit resetovaci tlacitko - alespon jeden filtr byl pouzity
        boolean showResetButton =
                !StringUtils.isEmpty(name);
        model.addAttribute("showResetButton", showResetButton);
        
        
        // vytvoreni, naplneni a predani formulare do modelu
        BrandFormFiltred brandFormFiltred = new BrandFormFiltred();
        brandFormFiltred.setName(name);
        
        model.addAttribute("brandFormFiltred", brandFormFiltred);
        
        // nacteni jedne filtrovane a razene stranky navstevniku
        Page<Brand> page = brandService.findFiltered(name, pageable);
        model.addAttribute("page", page);
        
        // nasledujici kus kodu v vytahne z objektu tridy Page strankovani ve formatu "sloupec,poradi" nebo null
        // jsou pouzity streamy a funkce vyssich radu, ktere jsme nebrali, bylo by mozne to udelat i pomoci cyklu
        // nenasel jsem prozatim lepsi (cistci) zpusob, jak to udelat
        
        model.addAttribute("sort", page
                .getSort()
                .get()
                .map(s -> s.getProperty() + "," + s.getDirection().name().toLowerCase())
                .findFirst()
                .orElse(null));
       
        return "admin/brand/interface";
    }
    
    @PostMapping("/zobrazit")
    public RedirectView showAll(BrandFormFiltred brandFormFiltred, RedirectAttributes redirectAttributes)
    {
        String name = brandFormFiltred.getName();
        if (!StringUtils.isEmpty(name))
            redirectAttributes.addAttribute("name", name);
       
        return new RedirectView("/admin/znacky/zobrazit", true);
    }
    
    @RequestMapping(value="pridat-znacku", method=RequestMethod.GET)
    public String addBrand(Model model)
    {
        model.addAttribute("brandForm", new BrandForm());
                
        return "admin/brand/add";
    }
    
    @RequestMapping(value="pridat-znacku", method=RequestMethod.POST)
    public String addBrand(@Valid BrandForm brandForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){
            return "admin/brand/add";
        }
        
        
        Brand brand = new Brand();
        brand.setName(brandForm.getName());
        
             
        try
        {
            Brand newBrand = brandService.save(brand);
            redirectAttributes.addFlashAttribute("message", "Značka "+ newBrand.getName() +" byla úspěšně přidána");
            return "redirect:/admin/znacky/zobrazit";
        }
        catch(Exception e)
        {
            //error hláška zatím nefunguje, ale už to nepadá :)
            redirectAttributes.addFlashAttribute("error", "Značku nelze přidat");
            return "redirect:/admin/znacky/zobrazit";
        }
        
    }
    
    @RequestMapping(value="editace-znacek/{id}", method=RequestMethod.GET)
    public String editBrand(Model model, @PathVariable Long id)
    {
        Optional<Brand> brand = brandService.findById(id);
        if(!brand.isPresent()) 
            throw new BrandNotFoundException();
        
        model.addAttribute("brandId", brand.get().getId());
        
        BrandForm brandForm = new BrandForm();
        brandForm.setName(brand.get().getName());
        model.addAttribute("brandForm", brandForm);
        
        return "admin/brand/edit";
    }
    
    
    @RequestMapping(value="editace-znacek/{id}", method=RequestMethod.POST)
    public String editBrand(@Valid BrandForm brandForm, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){
            return "admin/brand/edit";
        }
        
        Optional<Brand> brand = brandService.findById(id);
        if(!brand.isPresent()) 
            throw new BrandNotFoundException();
        
        Brand brandEdit = brand.get();
        brandEdit.setName(brandForm.getName());
        brandService.save(brandEdit);   
        
        redirectAttributes.addFlashAttribute("message", "Značka "+ brand.get().getName() +" byla úspěšně upravena");
        
        return "redirect:/admin/znacky/zobrazit";
    }
    
    
    @RequestMapping(value="smazani-znacky/{id}")
    public String deleteBrand(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Optional<Brand> brand = brandService.findById(id);
        if(!brand.isPresent())
        {
            throw new BrandNotFoundException();
        }

        try
        {
          brandService.delete(brand.get());  
        }
        catch(BrandCantBeDeletedException e)
        {
            redirectAttributes.addFlashAttribute("error", "Značka "+ brand.get().getName() +" nemohla byt smazana");
            return "redirect:/admin/znacky/zobrazit";
        }        
        
        redirectAttributes.addFlashAttribute("message", "Značka "+ brand.get().getName() +" byla úspěšně smazana");
        
        return "redirect:/admin/znacky/zobrazit";
    }
}
