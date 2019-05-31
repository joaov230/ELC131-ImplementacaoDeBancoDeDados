/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ibd.transaction;

import java.util.ArrayList;

/**
 *
 * @author João Vitor Forgearini Beltrame (30.05.2019) key 1615 64449805
 */
public class Vertice {
    private Integer verticeId;
    public ArrayList<Integer> adjacentes;
    public Boolean visitado;
    
    public Vertice (int verticeId) {
        this.verticeId = (Integer) verticeId;
        adjacentes = new ArrayList<Integer>();
        visitado = false;
    }
    
    ///////////////////
    // Vertice
    
    public int getVerticeId () {
        return verticeId.intValue();
    }
    
    ///////////////////
    // Adjacentes
    
    public void addAdjacente (int idAdjacente) {
        adjacentes.add(new Integer(idAdjacente));
    }
    
    public int removeAdjacente (int idAdjacente) {
        for (int i = 0; i < adjacentes.size(); i++) {
            if (adjacentes.get(0).intValue() == idAdjacente) {
                adjacentes.remove(i); // Remove adjacente que deve ser retirado
            }
        }
        
        return idAdjacente;
    }
    
    public void unlinkTudo () {
        adjacentes.clear();
    }
    
    public int getAdjacente (int iterador) {
        return adjacentes.get(iterador).intValue();
    }
    
    public boolean isAdjacente (int id) {
        for (Integer i : adjacentes) {
            if (i.intValue() == id) {
                return true;
            }
        }
        return false;
    }
    
    
    public void printAdjacentes () {
        for (Integer adj : adjacentes) {
            System.out.print(" -> " + adj.intValue());
        }
    }
}
