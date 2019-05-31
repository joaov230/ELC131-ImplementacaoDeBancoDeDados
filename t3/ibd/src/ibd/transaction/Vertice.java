/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.transaction;

import java.util.ArrayList;

/**
 *
 * @author João Vitor Forgearini Beltrame (30.05.2019)
 */
public class Vertice {
    private Integer verticeId;
    public ArrayList<Integer> adjacentes;
    public Boolean visitado;
    
    public Vertice (int verticeId) {
        this.verticeId = (Integer) verticeId;
        adjacentes = new ArrayList<Integer>();
    }
    
    ///////////////////
    // Vertice
    
    public int getVerticeId () {
        return (int) verticeId;
    }
    
    ///////////////////
    // Adjacentes
    
    public void addAdjacente (int idAdjacente) {
        adjacentes.add(idAdjacente);
    }
    
    /*
    public int findAdjacente (Integer adj) {
        for (int i = 0; i < adjacentes.size(); i++) {
            if (adjacentes.get(i) == adj) {
                return i;
            }
        }
        return -1;
    }
    */
    
    public int removeAdjacente (int idAdjacente) {
        for (Integer adj : adjacentes) {
            if (adj == idAdjacente) {
                adjacentes.remove(adj); // Remove o objeto adjacente
            }
        }
        
        return idAdjacente;
    }
    
    public void unlinkTudo () {
        adjacentes.clear();
    }
    
    public int getAdjacente (int iterador) {
        return adjacentes.get(iterador);
    }
    
    public boolean isAdjacente (int id) {
        for (Integer i : adjacentes) {
            if (i == id) {
                return true;
            }
        }
        return false;
    }
}
