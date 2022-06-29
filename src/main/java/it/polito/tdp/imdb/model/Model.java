package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
private Graph<Director, DefaultWeightedEdge> grafo; 
private ImdbDAO dao; 
private Map<Integer, Director> idMap; 
private Map<Integer, Actor> idMapAttori; 
private List<Actor> attori; 
private List<Director> provvisoria;
private List<Director> vertici= new ArrayList<Director>(); ;

public Model() {
	dao=new ImdbDAO(); 
	idMap= new HashMap<Integer, Director>(); 
	dao.loadAllDirectors(idMap);
}

public void creaGrafo(int anno) {
	grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	//aggiungere i vertici
	Graphs.addAllVertices(this.grafo, dao.getdirectorsByYear(anno, idMap));
	vertici=dao.getdirectorsByYear(anno, idMap); 
	
	//aggiungere gli archi 
	
	attori= new ArrayList <Actor>(this.dao.listAllActors()); 
	for(Actor a: attori) {
		provvisoria = new ArrayList<Director>(); 
		provvisoria=dao.getRelazioneDirettoreAttore(idMap, a, anno); 
		for (int i=0; i<provvisoria.size(); i++) {
			if (this.grafo.containsVertex(provvisoria.get(i))) {
			for (int j=i+1; j<provvisoria.size(); j++) {
				if (this.grafo.containsVertex(provvisoria.get(j))) {
				DefaultWeightedEdge edge = this.grafo.getEdge(provvisoria.get(i), provvisoria.get(j));
				if((edge == null) &&( !provvisoria.get(i).equals(provvisoria.get(j)))){
					Graphs.addEdgeWithVertices(this.grafo, provvisoria.get(i), provvisoria.get(j), 1);
				} else if ( !provvisoria.get(i).equals(provvisoria.get(j))){
					double pesoVecchio = this.grafo.getEdgeWeight(edge);
					double pesoNuovo = pesoVecchio + 1;
					this.grafo.setEdgeWeight(edge, pesoNuovo);
				}
			}
		}
	  }
	
     }

    }
}

public int nVertici() {
	return this.grafo.vertexSet().size();
}

public int nArchi() {
	return this.grafo.edgeSet().size();
}

public List <Director> getVertici() {
	return this.vertici; 
}

public List <Adiacenti> getAdiacenti(Director vertice) {
	List <Director> adiacenti= new ArrayList <Director>(Graphs.neighborListOf(this.grafo, vertice));
	List <Adiacenti> result= new ArrayList <Adiacenti>(); 
	for (Director d: adiacenti) {
		DefaultWeightedEdge edge = this.grafo.getEdge(vertice, d); 
		result.add(new Adiacenti(d, this.grafo.getEdgeWeight(edge))); 
	}
	return result; 
}
}
