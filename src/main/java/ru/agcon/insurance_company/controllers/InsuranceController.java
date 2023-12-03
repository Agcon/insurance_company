package ru.agcon.insurance_company.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.agcon.insurance_company.dto.InsuranceDTO;
import ru.agcon.insurance_company.dto.TypeDTO;
import ru.agcon.insurance_company.models.Insurance;
import ru.agcon.insurance_company.models.TypeOfInsurance;
import ru.agcon.insurance_company.services.InsuranceService;
import ru.agcon.insurance_company.services.TypeService;

import java.util.List;

@Controller
@RequestMapping("/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;
    private final TypeService typeService;
    private final ModelMapper modelMapper;

    @Autowired
    public InsuranceController(InsuranceService insuranceService, TypeService typeService, ModelMapper modelMapper) {
        this.insuranceService = insuranceService;
        this.typeService = typeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public String mainPage(Model model){
        List<TypeOfInsurance> types = typeService.getAll();
        model.addAttribute("types", types);
        return "insurance/index";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createTypePage(Model model){
        model.addAttribute("typeDTO", new TypeDTO());
        model.addAttribute("typeExists", false);
        return "insurance/create_type";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createType(@ModelAttribute("typeDTO") TypeDTO typeDTO, Model model){
        TypeOfInsurance type = convertToType(typeDTO);
        if (typeService.create(type)) return "redirect:/insurance";
        model.addAttribute("typeExists", true);
        return "insurance/create_type";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateTypePage(@PathVariable("id") int id, Model model){
        TypeOfInsurance type = typeService.getOne(id);
        model.addAttribute("type", type);
        return "insurance/update_type";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateType(@PathVariable("id") int id, @ModelAttribute("type") TypeDTO typeDTO) {
        TypeOfInsurance type = typeService.getOne(id);
        type.setName(typeDTO.getName());
        typeService.create(type);
        return "redirect:/insurance";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteType(@PathVariable("id") int id){
        typeService.delete(id);
        return "redirect:/insurance";
    }

    @GetMapping("/{id}")
    public String insurancePage(@PathVariable("id") int id, Model model){
        TypeOfInsurance type = typeService.getOne(id);
        model.addAttribute("type", type);
        model.addAttribute("insurances", type.getInsurances());
        return "insurance/insurance";
    }

    @GetMapping("/{id}/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createInsurancePage(@PathVariable("id") int id, Model model){
        model.addAttribute("type", typeService.getOne(id));
        model.addAttribute("typeExists", false);
        return "insurance/create_insurance";
    }

    @PostMapping("/{id}/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createInsurance(@PathVariable("id") int id, @ModelAttribute("insuranceDTO") InsuranceDTO insuranceDTO, Model model){
        TypeOfInsurance type = typeService.getOne(id);
        model.addAttribute("type", type);
        Insurance insurance = convertToInsurance(insuranceDTO);
        insurance.setType(type);
        type.getInsurances().add(insurance);
        if (insuranceService.create(insurance)) {
            typeService.create(type);
            return "redirect:/insurance/{id}";
        }
        model.addAttribute("typeExists", true);
        return "insurance/create_insurance";
    }

    @GetMapping("/{id}/update/{id_insurance}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateInsurancePage(@PathVariable("id") int id, @PathVariable("id_insurance") int idInsurance, Model model){
        Insurance insurance = insuranceService.getOne(idInsurance);
        model.addAttribute("insurance", insurance);
        TypeOfInsurance type = typeService.getOne(id);
        model.addAttribute("type", type);
        model.addAttribute("insuranceDTO", new InsuranceDTO());
        return "insurance/update_insurance";
    }

    @PostMapping("/{id}/update/{id_insurance}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateInsurance(@PathVariable("id") int id, @PathVariable("id_insurance") int idInsurance, @ModelAttribute("insuranceDTO") InsuranceDTO insuranceDTO) {
        TypeOfInsurance type = typeService.getOne(id);
        type.getInsurances().remove(insuranceService.getOne(idInsurance));
        Insurance insurance = convertToInsurance(insuranceDTO);
        insurance.setId(idInsurance);
        insurance.setType(type);
        type.getInsurances().add(insurance);
        insuranceService.update(insurance);
        typeService.create(type);
        return "redirect:/insurance/{id}";
    }

    @PostMapping("/{id}/delete/{id_insurance}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteInsurance(@PathVariable("id") int id, @PathVariable("id_insurance") int idInsurance){
        TypeOfInsurance type = typeService.getOne(id);
        Insurance insurance = insuranceService.getOne(idInsurance);
        type.getInsurances().remove(insurance);
        insuranceService.delete(idInsurance);
        typeService.create(type);
        return "redirect:/insurance/{id}";
    }

/*    @GetMapping("/search")
    public String searchPage(){
        return "search";
    }*/

/*    @PostMapping("/search")
    public String doSearch(@RequestParam("name") String name, Model model){
        Insurance insurance = insuranceService.getByName(name);
        model.addAttribute("insurance", insurance);
        return "search";
    }*/

    public Insurance convertToInsurance(InsuranceDTO insuranceDTO){
        return modelMapper.map(insuranceDTO, Insurance.class);
    }

    public TypeOfInsurance convertToType(TypeDTO typeDTO){
        return modelMapper.map(typeDTO, TypeOfInsurance.class);
    }
}
