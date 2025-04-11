package it.exercise.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.exercise.spring_la_mia_pizzeria_crud.model.Pizza;
import it.exercise.spring_la_mia_pizzeria_crud.repository.PizzaRepository;


@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "keyword", required = false) String name) {
        List<Pizza> result;
        if (name != null && !name.isBlank()) {
            result = pizzaRepository.findByNameContainingIgnoreCase(name);
        } else {
            result = pizzaRepository.findAll();
        }
        model.addAttribute("list", result);
        return "pizzas/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> optPizza = pizzaRepository.findById(id);
        if (optPizza.isPresent()) {
            model.addAttribute("pizza", optPizza.get());
            return "/pizzas/show";
        }

        model.addAttribute("errorCause", "Nessuna pizza trovata con questo id: " + id);
        model.addAttribute("errorMessage", "Errore di ricerca della pizza");
        return "/error_pages/generalError";
    } 

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/pizzas/create";
    }
}
