package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String events(Model model) {
        ArrayList events = new ArrayList<>();
        events.add("Masqurade Ball");
        events.add("Renfest");
        events.add("Breaking Benjamin Concert");
        model.addAttribute("events", events);

        return "events/index";
    }

    // lives at /events/create
    @GetMapping("create")
    public String renderCreateForm() {
        return "events/create";
    }
}
