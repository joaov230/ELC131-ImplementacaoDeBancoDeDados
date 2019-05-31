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
public class GrafoDoJoao {
   
    ArrayList<Vertice> vertices;

   
    public GrafoDoJoao () {
        vertices = new ArrayList<Vertice>();
    }
   
    public void addVertice (int id) {
        for (Vertice vert : vertices) {
            if (vert.getVerticeId() == id) {
                return;
            }
        }
        vertices.add(new Vertice(id));
    }
   
    // Retorna o vertice pelo Id dele
    public Vertice getVerticeById (int id) {
        for (Vertice vert : vertices) {
            if (id == vert.getVerticeId()) {
                return vert;
            }
        }
        return null;
    }
    
    // Conecta dois vertices dependente -> independente
    public void linkVertices (int dependente, int independente) {
        // Se não está na lista de adjacentes, adiciona
        Vertice vDependente = getVerticeById(dependente);
        Vertice vIndependente = getVerticeById(independente);
        
        if (vDependente.isAdjacente(independente)) {
            return;
        } else {
            vDependente.addAdjacente(independente);
        }
    }
   
    // É chamada quando tem que desconectar aquele vertice do resto do grafo
    // Remove todas as arestas de saída e todas de entrada
    public void unlinkVertice (int abortado) {
        // Desconecta todas as conexões de saída dessa transação
        getVerticeById(abortado).unlinkTudo();
        
        // Desconecta também todas as conexões de entrada do abortado
        for (Vertice vert : vertices) {
            if (vert.isAdjacente(abortado)) {
                vert.removeAdjacente(abortado);
            }
        }
    }
   
    // Retorna o true se tem ciclo, false se não
    public boolean temCiclo () {
        for (Vertice v : vertices) {
            v.visitado = false;
        }
        
        return verificaCicloRecursivo(vertices.get(0));
    }
    
    private boolean verificaCicloRecursivo (Vertice vert) {
        if (vert.visitado) {
            return true;
        } else {
            vert.visitado = true;
            
            for (Integer adj : vert.adjacentes) {
                if(verificaCicloRecursivo(getVerticeById(adj))) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    
    public void printGrafo () {
        for(Vertice vert : vertices) {
            System.out.print("Vertice " + vert.getVerticeId());
            vert.printAdjacentes();
            
            System.out.println();
        }
    }
}
