package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Dancestyle;
import org.sid.ebankingbackend.services.Dancestyleservice;
import org.sid.ebankingbackend.services.IDancestyleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/dancestyle")
@CrossOrigin("*")
public class DancestyleController {
    @Autowired
    IDancestyleservice styleserv;

    @GetMapping("/retrieve_all_styles")
    public List<Dancestyle> getDancestyles() {
        List<Dancestyle> liststyle = styleserv.retrieveAllDancestyles();
        return liststyle;
    }

    @PostMapping("/add_style")
    public Dancestyle addDancestyle(@RequestBody Dancestyle s) {
        Dancestyle style = styleserv.addDancestyle(s);
        return style;
    }

    @PutMapping("/update_style/{id}")
    public Dancestyle  updateDancestyle(@PathVariable Long id, @RequestBody Dancestyle s) {

        s.setIdstyled(id);
        Dancestyle style = styleserv.updateDancestyle(s);
        return style;
    }
    @GetMapping("/retrieve_categ/{id}")
    public Dancestyle retrieveDancestyle(@PathVariable Long id) {
        Dancestyle style = styleserv.retrieveDancestyle(id);
        return style;
    }

    @DeleteMapping("/remove_categ/{id}")
    public void removeDancestyle(@PathVariable Long id) {
        styleserv.removeDancestyle(id);
    }

    @GetMapping("/dancestyles/category/{categoryId}")
    public Set<Dancestyle> getStylesByCategoryId(@PathVariable Long categoryId) {
        return styleserv.getStylesByCategoryId(categoryId);
    }


    @GetMapping("/distinctStyledNames")
    public List<String> getDistinctStyledNames() {
        return styleserv.getAllDistinctStyledNames();
    }

}
