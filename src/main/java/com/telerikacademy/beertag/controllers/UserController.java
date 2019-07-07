package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.security.JwtProvider;
import com.telerikacademy.beertag.services.BeerService;
import com.telerikacademy.beertag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
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

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable final int id) {
        return userService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody final User user) {
        return userService.save(user)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable final int id, @RequestBody final User user) {
        return userService.update(id, user)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final int id) {
        userService.deleteById(id);
    }

    @PutMapping("/rateBeer")
    public ResponseEntity<Beer> rateBeer(@RequestParam(name = "user_id") int userId, @RequestParam("beer_id") int beerId, @RequestParam("rating") int rating) {

        return beerService.rate(userId, beerId, rating)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/tagBeer")
    public ResponseEntity<Beer> tagBeer(@RequestParam("beer_id") final int beerId,
                                        @RequestParam("tag") final String tag,
                                        final HttpServletRequest request) {
        int userId = getUserId(request);
        return beerService.tag(userId, beerId, tag)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    private int getUserId(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        if (!jwtProvider.validateToken(token))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Token");

        return jwtProvider.getId(token);
    }

//
//    @PutMapping("/{id}/wishBeer")
//    public Beer wishBeer(@PathVariable(name = "id") int userId, @RequestParam int beerId) {
//        try {
//            return userService.addToWishList(userId, beerId);
//        } catch (IllegalArgumentException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }

//    @PutMapping("/{id}/unWishBeer")
//    public void unWishBeer(@PathVariable(name = "id") int userId, @RequestParam int beerId) {
//        try {
//            userService.removeFromWishList(userId, beerId);
//        } catch (IllegalArgumentException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//        }
//    }
}
