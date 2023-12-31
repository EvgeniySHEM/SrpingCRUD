package ru.sanctio.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sanctio.springcourse.dao.PersonDAO;
import ru.sanctio.springcourse.models.Person;
import ru.sanctio.springcourse.services.PersonService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonService personService;

    @Autowired
    public AdminController(PersonService personService) {
        this.personService = personService;
        ;
    }

    @GetMapping()
    public String adminPage(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personService.findAll());

        return "admin/adminPage";
    }

    @PatchMapping("/add")
    public String makeAdmin(@ModelAttribute("person") Person person) {
        System.out.println(person.getId());
        return "redirect:/people";
    }

}
