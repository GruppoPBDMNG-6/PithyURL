package GruppoPBDMNG_6.PithyURL.SparkServer.Entities;

public class Country implements Comparable<Country>{
	
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

	@Override
	public int compareTo(Country other) {
		return Integer.compare(other.getVisits(), this.getVisits());
	}
	
}
