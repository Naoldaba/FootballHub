package com.premier_league.PremierLeague.player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class PlayerService {
    private final IPlayerRepository _playerRepository;

    @Autowired
    public PlayerService(IPlayerRepository playerRepository) {
        this._playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return _playerRepository.findAll();
    }

    public List<Player> getPlayerByName(String name) {
        return _playerRepository.findAll().stream()
                .filter(player -> player.getName().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPosition(String pos) {
        return _playerRepository.findAll().stream()
                .filter(player -> player.getPos().contains(pos.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String nation) {
        return _playerRepository.findAll().stream()
                .filter(player -> player.getNation().contains(nation.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPos(String team, String pos) {
        return _playerRepository.findAll().stream()
                .filter(player -> team.equals(player.getTeam()) && pos.equals(player.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer (Player player) {
        _playerRepository.save(player);
        return player;
    }

    public Player updatePlayer (Player player) {
        Optional<Player> existingPlayer = _playerRepository.findByName(player.getName());
        if (existingPlayer.isPresent()) {
            Player updatedPlayer = existingPlayer.get();
            updatedPlayer.setName(player.getName());
            updatedPlayer.setNation(player.getNation());
            updatedPlayer.setAge(player.getAge());
            updatedPlayer.setPos(player.getPos());
            updatedPlayer.setTeam(player.getTeam());

            _playerRepository.save(player);
            return updatedPlayer;
        }

        return null;
    }

    @Transactional
    public void deletePlayer(String name) {
        _playerRepository.deleteByName(name);
    }
}
