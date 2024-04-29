package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Dancestyle;
import org.sid.ebankingbackend.services.IDancecategservice;
import org.sid.ebankingbackend.services.IDancestyleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/dancecateg")
@CrossOrigin("*")
public class DancecategoryController {
    @Autowired
    IDancecategservice categserv;
    @Autowired
    IDancestyleservice styleserv;
    @GetMapping("/retrieve_all_categs")
    public List<Dancecategory> getDancecategories() {
        List<Dancecategory> listcategs = categserv.retrieveAllDancecategories();
        return listcategs;
    }

    @PostMapping("/add_categ")
    public Dancecategory addDancecategory(@RequestBody Dancecategory c) {
        Dancecategory categ = categserv.addDancecategory(c);
        return categ;
    }

    @PutMapping("/update_categ/{id}")
    public Dancecategory  updateDancecategory(@PathVariable Long id, @RequestBody Dancecategory c) {

        c.setIdcategd(id);
        Dancecategory categ = categserv.updateDancecategory(c);
        return categ;
    }
    @GetMapping("/retrieve_categ/{id}")
    public Dancecategory retrieveDancecategory(@PathVariable Long id) {
        Dancecategory categ = categserv.retrieveDancecategory(id);
        return categ;
    }

    @DeleteMapping("/remove_categ/{id}")
    public void removeDancecategory(@PathVariable Long id) {
        categserv.removeDancecategory(id);
    }



    @PutMapping("/dancecategories/{id}/dancestyles")
    public Dancecategory addDanceStyleToCategory(@PathVariable Long id,
                                                 @RequestBody Dancestyle dancestyle) {
        return styleserv.addDanceStyleToCategory(id, dancestyle);
    }
    @DeleteMapping("/{categoryId}/styles/{styleId}")
    public Dancecategory removeDanceStyleFromCategory(@PathVariable Long categoryId,
                                                      @PathVariable Long styleId) {
        return styleserv.removeDanceStyleFromCategory(categoryId, styleId);
    }


}
