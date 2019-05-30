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
    private int BRANCOdoJoao = -1;
    private int CINZAdoJoao = 0;
    private int PRETOdoJoao = 1;
   
    ArrayList<Vertice> vertices;

   
    public GrafoDoJoao () {
        vertices = new ArrayList<Vertice>();
    }
   
    public void addVertice (int id) {
        vertices.add(new Vertice(id));
    }
   
    // Conecta dois vertices dependente -> independente
    public void linkVertices (int independente, int dependente) {
        // Se não está na lista de adjacentes, adiciona
        if (!vertices.get(dependente).isAdjacente(independente)) {
            vertices.get(dependente).addAdjacente(independente);
        }
    }
   
    // É chamada quando tem que desconectar aquele vertice do resto do grafo
    // Remove todas as arestas de saída e todas de entrada
    public void unlinkVertice (int abortado) {
        // Desconecta todas as conexões de saída dessa transação
        vertices.get(abortado).unlinkTudo();
       
        // Desconecta também todas as conexões de entrada do abortado
        for (Vertice vert : vertices) {
            if (vert.isAdjacente(abortado)) {
                vert.removeAdjacente(abortado);
            }
        }
    }
   
    // Retorna o id da transação que vai ser desconectada das demais e que deve ser abortada
    public int temCiclo () {
        // TO DO
        return 0;
    }
}
