package GruppoPBDMNG_6.PithyURL.Entities;

public class Country {
	
	private String name;
	private int visits;
	
	public Country(String name, int visits){
		this.name = name;
		this.visits = visits;
	}
	
	public void setName(String name){this.name = name;}
	public void setvisits(int visits){this.visits = visits;}
	
	public String getName(){return name;}
	public int getVisits(){return visits;}
	
}
