package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    @Override
    public void createNeighbour(Neighbour neighbour) {

    }

    @Override
    public void addNeighbourFavorite(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(true);
    }




    @Override
    public List<Neighbour> getFavoriteNeighbour() {
        List<Neighbour> favoriteNeighbourList = new ArrayList<>();
        for (Neighbour n: neighbours) {
            if (n.getFavorite()){
                favoriteNeighbourList.add(n);
            }
        }
        return favoriteNeighbourList;
    }

    @Override
    public void removeFavoriteNeighbour(Neighbour neighbour) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(false);
    }

    @Override
    public void setFavorite(Neighbour neighbour, boolean favorite) {
        neighbours.get(neighbours.indexOf(neighbour)).setFavorite(favorite);
    }


}