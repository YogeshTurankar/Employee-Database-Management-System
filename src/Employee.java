import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class Employee{

	private JFrame frame;
	private JTable table;
	private JTextField jtextEmployeeID;
	private JTextField jtextNINumber;
	private JTextField jtextFirstName;
	private JTextField jtextLastName;
	private JTextField jtextGender;
	private JTextField jtextDOB;
	private JTextField jtextAge;
	private JTextField jtextSalary;

	public static String birth = "";
	String searchBoxSelectedItem;
	String deleteBoxSelectedItem;
	public static String DOB="";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	DefaultTableModel model = new DefaultTableModel();
	private JTextField textSearch;
	private JTextField textDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Employee() {
		initialize();

		con = EmployeeData.connectDB();

		Object col[] = { "EmpID", "NINumber", "Firstname", "Lastname", "Gender", "DOB", "Age", "Salary" };
		model.setColumnIdentifiers(col);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Employee.class.getResource("/Images/book.png")));
		frame.getContentPane().setFocusTraversalPolicyProvider(true);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBounds(0, 0, 1450, 800);
		frame.setExtendedState(6);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(527, 70, 733, 480);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmployeeID", "NINumber", "First Name", "Last Name", "Gender", "DOB", "Age", "Salary"
			}
		));
		table.getColumnModel().getColumn(2).setPreferredWidth(90);
		table.getColumnModel().getColumn(3).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(65);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("EmployeeID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(40, 80, 111, 22);
		frame.getContentPane().add(lblNewLabel);

		jtextEmployeeID = new JTextField();
		jtextEmployeeID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int eID = Utils.empID() + 1;

				jtextEmployeeID.setText("ID" + eID);

			}
		});
		jtextEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextEmployeeID.setBounds(180, 70, 243, 36);
		frame.getContentPane().add(jtextEmployeeID);
		jtextEmployeeID.setColumns(10);

		JButton btnNewButton = new JButton("Add Data");
		btnNewButton.setToolTipText("Enter Data & Press Add");
		btnNewButton.setBackground(new Color(106, 176, 76));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] id = jtextEmployeeID.getText().toCharArray();
				char[] ni = jtextNINumber.getText().toCharArray();
				char[] fname = jtextFirstName.getText().toCharArray();
				char[] lname = jtextLastName.getText().toCharArray();
				if (id.length != 0 && ni.length != 0 && fname.length != 0 && lname.length != 0) {
					String sql = "INSERT INTO EmployeeDetails(EmployeeID,NINumber,Firstname,Lastname,Gender,DOB,Age,Salary) VALUES(?,?,?,?,?,?,?,?)";

					try {
						pst = con.prepareStatement(sql);

						pst.setString(1, jtextEmployeeID.getText().toUpperCase());
						pst.setString(2, jtextNINumber.getText().toUpperCase());
						pst.setString(3, jtextFirstName.getText().toUpperCase());
						pst.setString(4, jtextLastName.getText().toUpperCase());
						pst.setString(5, jtextGender.getText().toUpperCase());
						pst.setString(6, jtextDOB.getText());
						pst.setString(7, jtextAge.getText());
						pst.setString(8, jtextSalary.getText());

						pst.execute();
						try {
							String sql1 = "SELECT * FROM EmployeeDetails";

							try {
								pst = con.prepareStatement(sql1);
								rs = pst.executeQuery();
								DefaultTableModel md = (DefaultTableModel) table.getModel();
								md.setRowCount(0);
								Object[] columnDats = new Object[8];

								while (rs.next()) {
									columnDats[0] = rs.getString("EmployeeID");
									columnDats[1] = rs.getString("NINumber");
									columnDats[2] = rs.getString("Firstname");
									columnDats[3] = rs.getString("Lastname");
									columnDats[4] = rs.getString("Gender");
									columnDats[5] = rs.getString("DOB");
									columnDats[6] = rs.getString("Age");
									columnDats[7] = rs.getString("Salary");

									md.addRow(columnDats);
								}
							} catch (Exception ec) {
								JOptionPane.showInternalMessageDialog(null, ec);
							}
							rs.close();
							pst.close();
							JOptionPane.showMessageDialog(null, "Data added to database");
						} catch (Exception e1) {
						e1.printStackTrace();
						}

					} catch (Exception ev) {
						JOptionPane.showMessageDialog(null, ev);
					}

//					DefaultTableModel model = (DefaultTableModel) table.getModel();
//					model.addRow(new Object[] { jtextEmployeeID.getText(), jtextNINumber.getText(),
//							jtextFirstName.getText(), jtextLastName.getText(), jtextGender.getText(),
//							jtextDOB.getText(), jtextAge.getText(), jtextSalary.getText() });
//
//					if (table.getRowCount() == -1) {
//						if (table.getRowCount() == 0) {
//							JOptionPane.showMessageDialog(null, "Membership update confirmed",
//									"Employee database system", JOptionPane.OK_OPTION);
//						}
//					}
				} else {
					JOptionPane.showMessageDialog(null, "Please Fill all Fields");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(40, 420, 150, 50);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("NI Number");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(40, 120, 101, 22);
		frame.getContentPane().add(lblNewLabel_1);

		jtextNINumber = new JTextField();
		jtextNINumber.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int NInum = Utils.NIID() + 1;

				jtextNINumber.setText("NI" + NInum);

			}
		});
		jtextNINumber.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextNINumber.setColumns(10);
		jtextNINumber.setBounds(180, 110, 243, 36);
		frame.getContentPane().add(jtextNINumber);

		JLabel lblNewLabel_1_1 = new JLabel("First Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1.setBounds(40, 160, 99, 22);
		frame.getContentPane().add(lblNewLabel_1_1);

		jtextFirstName = new JTextField();
		jtextFirstName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (Utils.name(jtextFirstName.getText()).matches()) {

				} else {
					JOptionPane.showMessageDialog(null, "Invalid Name");
					jtextFirstName.setText(null);
				}
			}
		});

		jtextFirstName.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextFirstName.setColumns(10);
		jtextFirstName.setBounds(180, 150, 243, 36);
		frame.getContentPane().add(jtextFirstName);

		JLabel lblNewLabel_1_1_1 = new JLabel("Last Name");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1.setBounds(40, 200, 95, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1);

		jtextLastName = new JTextField();
		jtextLastName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (Utils.name(jtextLastName.getText()).matches()) {

				} else {
					JOptionPane.showMessageDialog(null, "Invalid Name");
					jtextLastName.setText(null);
				}
			}
		});

		jtextLastName.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextLastName.setColumns(10);
		jtextLastName.setBounds(180, 190, 243, 36);
		frame.getContentPane().add(jtextLastName);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Gender");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1.setBounds(40, 240, 65, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1);

		jtextGender = new JTextField();
		jtextGender.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextGender.setColumns(10);
		jtextGender.setBounds(180, 230, 243, 36);
		frame.getContentPane().add(jtextGender);

		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("DOB");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1.setBounds(40, 280, 53, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1);

		jtextDOB = new JTextField();
		jtextDOB.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				DOB=jtextDOB.getText();
				if(Utils.dateOfBirth()) {
					
				}else {
					JOptionPane.showMessageDialog(null, "Invalid Date Format");
				}
			}
		});
		jtextDOB.setText("YYYY-MM-DD");
		jtextDOB.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextDOB.setColumns(10);
		jtextDOB.setBounds(180, 270, 243, 36);
		frame.getContentPane().add(jtextDOB);

		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Age");
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1.setBounds(40, 320, 53, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1);

		jtextAge = new JTextField();
		jtextAge.setToolTipText("Age will added automatically.");
		jtextAge.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					birth = jtextDOB.getText();
					jtextAge.setText(Utils.age());
					}catch(Exception ec) {
						JOptionPane.showMessageDialog(null, "Please first enter DOB");
					}
			}
		});

		jtextAge.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextAge.setColumns(10);
		jtextAge.setBounds(180, 310, 243, 36);
		frame.getContentPane().add(jtextAge);

		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Salary");
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1_1_1_1_1_1_1.setBounds(40, 360, 57, 22);
		frame.getContentPane().add(lblNewLabel_1_1_1_1_1_1_1);

		jtextSalary = new JTextField();
		jtextSalary.setFont(new Font("Tahoma", Font.BOLD, 18));
		jtextSalary.setColumns(10);
		jtextSalary.setBounds(180, 350, 243, 36);
		frame.getContentPane().add(jtextSalary);

		JButton btnPrint = new JButton("Print");
		btnPrint.setBackground(new Color(104, 109, 224));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat("Printing in progress");
				MessageFormat footer = new MessageFormat("Page {0, number, integer}");

				try {
					table.print();

				} catch (java.awt.print.PrinterException EV) {
					System.err.format("No printer found", EV.getMessage());
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnPrint.setBounds(40, 500, 150, 50);
		frame.getContentPane().add(btnPrint);

		JButton btnNewButton_1_1 = new JButton("Reset");
		btnNewButton_1_1.setBackground(new Color(249, 202, 36));
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtextEmployeeID.setText(null);
				jtextNINumber.setText(null);
				jtextFirstName.setText(null);
				jtextLastName.setText(null);
				jtextGender.setText(null);
				jtextDOB.setText(null);
				jtextAge.setText(null);
				jtextSalary.setText(null);
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1_1.setBounds(273, 500, 150, 50);
		frame.getContentPane().add(btnNewButton_1_1);

		JButton btnNewButton_1_1_1 = new JButton("Exit");
		btnNewButton_1_1_1.setBackground(new Color(235, 77, 75));
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit", "Employee Database System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnNewButton_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1_1_1.setBounds(1110, 590, 150, 50);
		frame.getContentPane().add(btnNewButton_1_1_1);

		JLabel lblNewLabel_2 = new JLabel("Employee Database Management System");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(26, 188, 156));
		lblNewLabel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_2.setBounds(0, 0, 1283, 50);
		frame.getContentPane().add(lblNewLabel_2);

		JButton btnShowData = new JButton("Show Data");
		btnShowData.setBackground(new Color(34, 166, 179));
		btnShowData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				con = EmployeeData.connectDB();

				String sql = "SELECT * FROM EmployeeDetails";

				try {
					pst = con.prepareStatement(sql);
					rs = pst.executeQuery();
					DefaultTableModel md = (DefaultTableModel) table.getModel();
					md.setRowCount(0);
					Object[] columnDats = new Object[8];

					while (rs.next()) {
						columnDats[0] = rs.getString("EmployeeID");
						columnDats[1] = rs.getString("NINumber");
						columnDats[2] = rs.getString("Firstname");
						columnDats[3] = rs.getString("Lastname");
						columnDats[4] = rs.getString("Gender");
						columnDats[5] = rs.getString("DOB");
						columnDats[6] = rs.getString("Age");
						columnDats[7] = rs.getString("Salary");

						md.addRow(columnDats);
					}
				} catch (Exception ec) {
					JOptionPane.showInternalMessageDialog(null, ec);
				}

			}
		});
		btnShowData.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnShowData.setBounds(273, 420, 150, 50);
		frame.getContentPane().add(btnShowData);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(39, 174, 96));
		panel.setBounds(10, 580, 460, 70);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JComboBox<String> comboBoxSearch = new JComboBox<String>();
		comboBoxSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchBoxSelectedItem = comboBoxSearch.getSelectedItem().toString();
			}
		});
		comboBoxSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBoxSearch.setModel(
				new DefaultComboBoxModel(new String[] { "Select Item", "EmployeeID", "Firstname", "Lastname" }));
		comboBoxSearch.setBounds(10, 16, 114, 40);
		panel.add(comboBoxSearch);

		textSearch = new JTextField();
		textSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		textSearch.setBounds(140, 16, 150, 40);
		panel.add(textSearch);
		textSearch.setColumns(10);

		JButton btnSearchData = new JButton("Search Data");
		btnSearchData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String searchbox = textSearch.getText().toUpperCase();

				if (searchbox.toCharArray().length != 0) {
					con = EmployeeData.connectDB();

					String sql = "SELECT * FROM EmployeeDetails where " + searchBoxSelectedItem + "=" + '"' + searchbox + '"';

					try {
						pst = con.prepareStatement(sql);
						rs = pst.executeQuery();
						DefaultTableModel md = (DefaultTableModel) table.getModel();
						md.setRowCount(0);
						Object[] columnDats = new Object[8];

						while (rs.next()) {
							columnDats[0] = rs.getString("EmployeeID");
							columnDats[1] = rs.getString("NINumber");
							columnDats[2] = rs.getString("Firstname");
							columnDats[3] = rs.getString("Lastname");
							columnDats[4] = rs.getString("Gender");
							columnDats[5] = rs.getString("DOB");
							columnDats[6] = rs.getString("Age");
							columnDats[7] = rs.getString("Salary");

							md.addRow(columnDats);
						}
					} catch (Exception ec) {
						JOptionPane.showInternalMessageDialog(null, ec);
					}

				} else {
					JOptionPane.showInternalMessageDialog(null, "Please Enter Search Data");
				}
			}
		});
		btnSearchData.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSearchData.setBounds(320, 16, 129, 40);
		panel.add(btnSearchData);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 57, 43));
		panel_1.setLayout(null);
		panel_1.setBounds(527, 580, 460, 70);
		frame.getContentPane().add(panel_1);

		JComboBox<String> comboBoxDelete = new JComboBox<String>();
		comboBoxDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBoxSelectedItem = comboBoxDelete.getSelectedItem().toString();
			}
		});
		comboBoxDelete.setModel(
				new DefaultComboBoxModel(new String[] { "Select Item", "EmployeeID", "Firstname", "Lastname" }));
		comboBoxDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBoxDelete.setBounds(10, 16, 114, 40);
		panel_1.add(comboBoxDelete);

		textDelete = new JTextField();
		textDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
		textDelete.setColumns(10);
		textDelete.setBounds(140, 16, 150, 40);
		panel_1.add(textDelete);

		JButton btnDeleteData = new JButton("Delete Data");
		btnDeleteData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String deletebox = textDelete.getText().toUpperCase();
				if (deletebox.toCharArray().length != 0) {
					con = EmployeeData.connectDB();
					String sql = "DELETE FROM EmployeeDetails WHERE " + deleteBoxSelectedItem + "=" + "'" + deletebox + "'";

					try {
						pst = con.prepareStatement(sql);
						int deleted = pst.executeUpdate();
						if (deleted == 0) {
							JOptionPane.showInternalMessageDialog(null, "Nothing to Delete");
						} else {
							JOptionPane.showInternalMessageDialog(null, "Data Deleted");
							String sql1 = "SELECT * FROM EmployeeDetails";

							try {
								pst = con.prepareStatement(sql1);
								rs = pst.executeQuery();
								DefaultTableModel md = (DefaultTableModel) table.getModel();
								md.setRowCount(0);
								Object[] columnDats = new Object[8];

								while (rs.next()) {
									columnDats[0] = rs.getString("EmployeeID");
									columnDats[1] = rs.getString("NINumber");
									columnDats[2] = rs.getString("Firstname");
									columnDats[3] = rs.getString("Lastname");
									columnDats[4] = rs.getString("Gender");
									columnDats[5] = rs.getString("DOB");
									columnDats[6] = rs.getString("Age");
									columnDats[7] = rs.getString("Salary");

									md.addRow(columnDats);
								}
							} catch (Exception ec) {
								JOptionPane.showInternalMessageDialog(null, ec);
							}
							
						}
					} catch (Exception ec) {
						JOptionPane.showInternalMessageDialog(null, ec);
					}

				} else {
					JOptionPane.showInternalMessageDialog(null, "Please Enter Valid Data");
				}
			}
		});
		btnDeleteData.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDeleteData.setBounds(320, 16, 129, 40);
		panel_1.add(btnDeleteData);
	}
}
