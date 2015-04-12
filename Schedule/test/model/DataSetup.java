package model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;

import dtu.library.app.Address;
import dtu.library.app.Book;
import dtu.library.app.User;

public class DataSetup {
		
	@Before
	public void setUp(){
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("Som001","Software Engineering - 9","Ian Sommerville"));
		books.add(new Book("Sof001","XML for Dummies","Fred Software"));
		for (int i = 1; i <= 10; i++) {
			books.add(new Book("book"+i,"Book "+i,"Author "+i));
		}
		
		libApp.adminLogin("adminadmin");
		for (Book book : books) {
			libApp.addBook(book);
		}

		List<User> users = new ArrayList<User>();
		
		Address address = new Address("Kirkevej",2344,"Herlev");
		User user = new User("1234651234","User 1","user1@library.dk",address);
		users.add(user);
		address = new Address("Lyngby",2345,"Holte");
		user = new User("1212871234","User 2","user2@library.dk",address);
		users.add(user);
		
		for (User usr : users) {
			libApp.register(usr);
		}		
	}
	
	@Test
	public void check() {
		System.out.println("test");
		
		asdasdasd
	}
	
}