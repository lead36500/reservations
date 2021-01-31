package com.example.reservations;

import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationsApplication.class, args);
	}

}

@RepositoryRestResource
interface ReservationRespository extends JpaRepository<Reservation, Long>{
	
}

@Component
class SampleDataCLR implements CommandLineRunner{

	private final ReservationRespository reservationRespository;
	
	@Autowired
	public SampleDataCLR(ReservationRespository resRepo) {
		this.reservationRespository = resRepo;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Stream.of("Baber","Josh","Jurgen","aNDREW").forEach
		(name -> reservationRespository.save(new Reservation(name)));
		
		reservationRespository.findAll().forEach(System.out::println);
	}

	public ReservationRespository getReservationRespository() {
		return reservationRespository;
	}
	
}

// spring data jpa repository.. also cassandra mongodb available
@Entity
class Reservation{
	
	@Override
	public String toString() {
		
		return "Reservation{"
				+ "id="+id
				+", reservationName='"+reservationName+'\''
		+'}';
	}
	
	public Reservation() {
		
	}
	
	public Reservation(String reservationName) {
	
		this.reservationName = reservationName;
	
	}
	
	@Id 
	@GeneratedValue
	private Long id;
	
	private String reservationName ; //reservation_name
	
	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setReservationName(String value) {
		this.reservationName = value;
	}
	
	public String getReservationName() {
		return this.reservationName;
	}
}