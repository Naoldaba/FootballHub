package com.premier_league.PremierLeague.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {
    private final PlayerService _playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this._playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayers(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String pos,
        @RequestParam(required = false) String nation,  
        @RequestParam(required = false) String team) {

        if (team != null && pos != null) {
            return _playerService.getPlayersByTeamAndPos(team, pos);
        } else if (name != null) {
            return _playerService.getPlayerByName(name);
        } else if (pos != null) {
            return _playerService.getPlayersByPosition(pos);
        } else if (nation != null) {
            return _playerService.getPlayersByNation(nation);
        } else {
            return _playerService.getAllPlayers();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player newPlayer = _playerService.addPlayer(player);
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        Player resPlayer = _playerService.updatePlayer(player);
        if (resPlayer != null) {
            return new ResponseEntity<>(resPlayer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{playerName}")
    public ResponseEntity<String> deletePlayer(@PathVariable String name) {
        _playerService.deletePlayer(name);
        return new ResponseEntity<>("Player Deleted Successfully", HttpStatus.OK);
    }
}
