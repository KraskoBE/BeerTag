package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.DTO.BeerCreateDTO;
import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.security.JwtProvider;
import com.telerikacademy.beertag.services.BeerService;
import com.telerikacademy.beertag.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/api/beers")
public class BeerController {
    private final BeerService beerService;
    private final JwtProvider jwtProvider;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public BeerController(BeerService beerService, JwtProvider jwtProvider, ImageService imageService, ModelMapper modelMapper) {
        this.beerService = beerService;
        this.jwtProvider = jwtProvider;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    //    @GetMapping
//    @PreAuthorize("permitAll()")
//    public List<Beer> findAll() {
//        return beerService.findAll();
//    }

    @GetMapping
    public Page<Beer> findBy(@RequestParam final int page,
                             @RequestParam final String orderBy) {
        return beerService.findBy(page,orderBy);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Member')")
    public ResponseEntity<Beer> findById(@PathVariable final int id) {
        return beerService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('Member')")
    public ResponseEntity<Beer> save(@RequestBody final BeerCreateDTO beer,
                                     final HttpServletRequest request) {

        String token = jwtProvider.resolveToken(request);
        if (!jwtProvider.validateToken(token))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token");

        int creatorId = jwtProvider.getId(token);

        byte[] imageBytes = Base64.getDecoder().decode(beer.getImageBase64());

        Optional<Image> beerImage = imageService.save(imageBytes);
        if (!beerImage.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image couldn't be saved");

        return beerService.save(creatorId, modelMapper.map(beer, Beer.class), beerImage.get())
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beer> update(@PathVariable final int id, @RequestBody final Beer beer) {
        return beerService.update(id, beer)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final int id) {
        beerService.deleteById(id);
    }
}
