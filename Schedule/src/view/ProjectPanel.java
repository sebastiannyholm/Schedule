package view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.ProjectController;
import model.Project;
import model.Schedule;

public class ProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Schedule schedule;
	
	private JLabel titleLabel, projectListTitleLabel;
	private JButton createProject, deleteProject, manageProject, back;
	private JList projectList;
	private JScrollPane scrollPane;
	
	private DefaultListModel<Project> projects = new DefaultListModel<Project>();
	
	public ProjectPanel(Schedule schedule) {
		this.schedule = schedule;
		this.titleLabel = new JLabel("My projects");
		this.projectListTitleLabel = new JLabel("All projects");
		this.projectList = new JList(projects);
		this.createProject = new JButton("Create project");
		this.deleteProject = new JButton("Delete project");
		this.manageProject = new JButton("Manage project");
		this.back = new JButton("Back");
		this.scrollPane = new JScrollPane();
		this.scrollPane.setViewportView(projectList);
		
		for (Project project : schedule.getUser().getProjects())
			projects.addElement(project);
		
		this.setLayout(null);
		
		titleLabel.setBounds(20, 20, 460, 40);
		projectListTitleLabel.setBounds(20, 80, 200, 40);
		scrollPane.setBounds(20, 120, 200, 300);
		
		createProject.setBounds(250, 120, 120, 40);
		deleteProject.setBounds(250, 180, 120, 40);
		manageProject.setBounds(250, 240, 120, 40);
		back.setBounds(250, 300, 120, 40);
		
		this.add(titleLabel);
		this.add(projectListTitleLabel);
		this.add(scrollPane);
		this.add(createProject);
		this.add(deleteProject);
		this.add(manageProject);
		this.add(back);
		
	}

	public void registerListener(ProjectController controller) {
		createProject.addActionListener(controller);
		deleteProject.addActionListener(controller);
		manageProject.addActionListener(controller);
		back.addActionListener(controller);
	}

//	public int getSelectedIndex() {
//		return getContactList().getSelectedIndex();
//	}
//	
//	public Contact getSelected() {
//		int index = getContactList().getSelectedIndex();
//		
//		Contact contact = listModel.get(index);
//		
//		return contact;
//	}
//	
//	public void updateContactBookList( ContactBook book ) {
//		
//		listModel.clear();
//		
//		for ( int i = 0; i < book.getSize(); i++ ) {
//			
//			listModel.addElement(book.getContact(i));
//
//		}
//		
//	}
	
}
