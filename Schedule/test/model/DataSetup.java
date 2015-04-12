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

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();	
		}		
	}
	
	@Test
	public void check() {
		System.out.println("test");
	}
	
}