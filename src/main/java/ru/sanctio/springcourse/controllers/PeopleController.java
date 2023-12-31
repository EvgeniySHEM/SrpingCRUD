package ru.sanctio.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sanctio.springcourse.dao.PersonDAO;
import ru.sanctio.springcourse.models.Person;
import ru.sanctio.springcourse.services.ItemServise;
import ru.sanctio.springcourse.services.PersonService;
import ru.sanctio.springcourse.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;
    private final PersonValidator personValidator;
    private final PersonDAO personDAO;
    private final ItemServise itemServise;

    public PeopleController(PersonService personService, PersonValidator personValidator, PersonDAO personDAO, ItemServise itemServise) {
        this.personService = personService;
        this.personValidator = personValidator;
        this.personDAO = personDAO;
        this.itemServise = itemServise;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());

        itemServise.findByItemName("Airpods");
        itemServise.findByOwner(personService.findAll().get(0));
        personService.test();
//        personDAO.testNPlus1();

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) { //@ModelAttribute("person") Person person - создаст персону с дефолтными полями и положит в модель
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.update(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }

}
