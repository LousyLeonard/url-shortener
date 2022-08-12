package laz.example.urlshortener.controllers;

import laz.example.urlshortener.services.InMemoryHashMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/new")
public class CrumbController {

    final InMemoryHashMapService hashMapService;

    @Autowired
    public CrumbController(final InMemoryHashMapService hashMapService) {
        this.hashMapService = hashMapService;
    }

    @GetMapping()
    public ResponseEntity<String> getNewCrumb(@RequestParam final String fullURL, final HttpServletRequest request) {
        final String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .path("/")
                .path(this.hashMapService.genAndStoreURLForCrumb(fullURL))
                .build()
                .toUriString();

        return ResponseEntity.ok(baseUrl);
    }
}