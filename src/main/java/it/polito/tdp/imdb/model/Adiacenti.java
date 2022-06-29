package it.polito.tdp.imdb.model;

public class Adiacenti implements Comparable <Adiacenti> {

	Director adiacente; 
	double attoriCondivisi;
	
	public Adiacenti(Director adiacente, double attoriCondivisi) {
		super();
		this.adiacente = adiacente;
		this.attoriCondivisi = attoriCondivisi;
	}

	public Director getAdiacente() {
		return adiacente;
	}

	public double getAttoriCondivisi() {
		return attoriCondivisi;
	}

	@Override
	public int compareTo(Adiacenti other) {
		// TODO Auto-generated method stub
		return (int) (other.getAttoriCondivisi()-this.attoriCondivisi);
	}

	@Override
	public String toString() {
		return "Adiacenti [adiacente=" + adiacente + ", attoriCondivisi=" + attoriCondivisi + "]";
	}
	
	
	
	
	
 
	
	
}
