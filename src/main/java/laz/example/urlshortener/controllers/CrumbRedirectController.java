package laz.example.urlshortener.controllers;

import laz.example.urlshortener.services.InMemoryHashMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CrumbRedirectController {

    InMemoryHashMapService hashMapService;

    @Autowired
    public CrumbRedirectController(final InMemoryHashMapService hashMapService) {
        this.hashMapService = hashMapService;
    }

    @RequestMapping("/{crumb}")
    ResponseEntity<Void> redirect(@PathVariable String crumb) {
        return ResponseEntity.status(HttpStatus.FOUND)
                // TODO redirect to local not found page
                .location(URI.create(this.hashMapService.getCrumb(crumb).orElse("")))
                .build();
    }
}