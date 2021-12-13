package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class ClincEntity {
	String name;
	LocalDateTime open;
	LocalDateTime close;
	String address;
	String[] services;
}
