package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.DTO.UserBeerDetails;
import com.telerikacademy.beertag.models.DTO.UserUpdateDTO;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.models.constants.UserRole;
import com.telerikacademy.beertag.security.JwtProvider;
import com.telerikacademy.beertag.services.BeerService;
import com.telerikacademy.beertag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE})
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final BeerService beerService;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService userService, BeerService beerService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.beerService = beerService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable final int id) {
        return userService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<User> save(@RequestBody final User user) {
        return userService.save(user)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    /*@PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable final int id, @RequestBody final User user) {
        return userService.update(id, user)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }*/

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final int id) {
        userService.deleteById(id);
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PutMapping("/rateBeer")
    public ResponseEntity<Beer> rateBeer(@RequestParam("beer_id") final int beerId,
                                         @RequestParam("rating") final int rating,
                                         final HttpServletRequest request) {
        int userId = getUserId(request);

        return beerService.rate(userId, beerId, rating)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PutMapping("/tagBeer")
    public ResponseEntity<Beer> tagBeer(@RequestParam("beer_id") final int beerId,
                                        @RequestParam("tag") final String tag,
                                        final HttpServletRequest request) {
        int userId = getUserId(request);
        return beerService.tag(userId, beerId, tag)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    private int getUserId(final HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        if (!jwtProvider.validateToken(token))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token");

        return jwtProvider.getId(token);
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PutMapping("/wish")
    public ResponseEntity<User> wishBeer(@RequestParam("beer_id") final int beerId,
                                         final HttpServletRequest request) {
        int userId = getUserId(request);

        return userService.wish(userId, beerId)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PutMapping("/drink")
    public ResponseEntity<User> drinkBeer(@RequestParam("beer_id") final int beerId,
                                          final HttpServletRequest request) {
        int userId = getUserId(request);

        return userService.drink(userId, beerId)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @GetMapping("/beerDetails")
    public ResponseEntity<UserBeerDetails> beerDetails(@RequestParam("beer_id") final int beerId,
                                                       final HttpServletRequest request) {
        int userId = getUserId(request);

        return userService.beerDetails(userId, beerId)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @GetMapping("/{id}/wishedBeers")
    public List<Beer> wishedBeers(@PathVariable("id") final int userId) {
        return userService.findWishedBeers(userId);
    }


    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @GetMapping("/{id}/createdBeers")
    public List<Beer> createdBeers(@PathVariable("id") final int userId) {
        return userService.findCreatedBeers(userId);
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @GetMapping("/{id}/topDrankBeers")
    public List<Beer> topDrankBeers(@PathVariable("id") final int userId) {
        return userService.findTopDrank(userId);
    }

    @PreAuthorize("hasRole('Member') or hasRole('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") final int userId,
                                       @RequestBody final UserUpdateDTO userUpdateDTO,
                                       final HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        Optional<User> user = userService.findById(jwtProvider.getId(token));

        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        if (user.get().getUserRole() != UserRole.Admin && userId != getUserId(request))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return userService.update(userId, userUpdateDTO)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }
}
