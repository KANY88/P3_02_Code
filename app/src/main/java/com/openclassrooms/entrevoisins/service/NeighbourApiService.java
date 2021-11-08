package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();
    List<Neighbour> getFavoriteNeighbour();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);
    /**
     * add a neighbour to Favorite
     * @param neighbour
     * @return
     */

    void addNeighbourFavorite(Neighbour neighbour);
    void removeFavoriteNeighbour(Neighbour neighbour);
    //Boolean favorite(Neighbour neighbour);


    void setFavorite(Neighbour neighbour, boolean favorite);
}
