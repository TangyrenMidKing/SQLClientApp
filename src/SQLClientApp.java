import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



public class SQLClientApp
{
	private JButton ConnectButton, ClearQuery, ExecuteButton, ClearWindow;
	private JLabel QueryLabel, dbInfoLabel, jdbcLabel, UrlLabel, UserLabel, PasswordLabel;
	private JTextArea TextQuery;
	private JComboBox DriverCombo;
	private JComboBox UrlCombo;
	private JTextField UserText;
	private JPasswordField PasswordText;
	private JLabel StatusLabel, WindowLabel;

	private ResultSetTable tableModel;
	private Connection connect;
	private TableModel Empty;
	private JTable resultTable;

	private JFrame frame;
	private JPanel panel;


	public SQLClientApp()
	{
		// The Window
		frame = new JFrame("Project2 - SQL Client App (MJL - CNT 4714 - Summer 2021)");
		panel = new JPanel();

		// Initial drop down menu.
		String[] DriverItems = { "com.mysql.cj.jdbc.Driver", "oracle.jdbc.driver.OracleDriver",
								"com.ibm.db2.jdbc.net.DB2Driver", "com.jdbc.odbc.JdbcOdbcDriver" };
		String[] UrlItems = {"jdbc:mysql://localhost:3306/project2?useTimezone=true&serverTimezone=UTC",
							"jdbc:mysql://localhost:3306/bikedb?useTimezone=true&serverTimezone=UTC",
							"jdbc:mysql://localhost:3306/test?useTimezone=true&serverTimezone=UTC"};

		// Initial buttons, texts, and labels, etc
		ConnectButton = new JButton("Connect to Database");
		ConnectButton.setFont(new Font("Arial", Font.BOLD, 12));
		ConnectButton.setBounds(20, 187, 165, 25);
		ConnectButton.setBackground(Color.blue);
		ConnectButton.setForeground(Color.yellow);
		ConnectButton.setBorderPainted(false);
		ConnectButton.setOpaque(true);

		ClearQuery = new JButton("Clear SQL Command");
		ClearQuery.setFont(new Font("Arial", Font.BOLD, 12));
		ClearQuery.setBounds(480,150,175,25);
		ClearQuery.setForeground(Color.red);
		ClearQuery.setBackground(Color.white);
		ClearQuery.setBorderPainted(false);
		ClearQuery.setOpaque(true);

		ExecuteButton = new JButton("Execute SQL Command");
		ExecuteButton.setFont(new Font("Arial", Font.BOLD, 11));
		ExecuteButton.setBounds(695,150,175,25);
		ExecuteButton.setBackground(Color.green);
		ExecuteButton.setBorderPainted(false);
		ExecuteButton.setOpaque(true);

		ClearWindow = new JButton("Clear Reslut Window");
		ClearWindow.setFont(new Font("Arial", Font.BOLD, 12));
		ClearWindow.setBounds(25, 480, 168, 25);
		ClearWindow.setBackground(Color.yellow);
		ClearWindow.setBorderPainted(false);
		ClearWindow.setOpaque(true);

		dbInfoLabel = new JLabel();
		dbInfoLabel.setFont(new Font("Arial", Font.BOLD, 14));
		dbInfoLabel.setText("Enter Database Information");
		dbInfoLabel.setBounds(10, 0, 300, 25);
		dbInfoLabel.setForeground(Color.blue);

		jdbcLabel = new JLabel(" JDBC Driver");
		jdbcLabel.setBackground(Color.lightGray);
		jdbcLabel.setForeground(Color.black);
		jdbcLabel.setBounds(10, 21, 125, 25);
		jdbcLabel.setOpaque(true);

		UrlLabel = new JLabel(" Database URL");
		UrlLabel.setBackground(Color.lightGray);
		UrlLabel.setForeground(Color.black);
		UrlLabel.setOpaque(true);
		UrlLabel.setBounds(10, 49, 125, 25);

		UserLabel = new JLabel(" Username");
		UserLabel.setBackground(Color.lightGray);
		UserLabel.setForeground(Color.black);
		UserLabel.setOpaque(true);
		UserLabel.setBounds(10, 78, 125, 24);

		PasswordLabel = new JLabel(" Password");
		PasswordLabel.setBackground(Color.lightGray);
		PasswordLabel.setForeground(Color.black);
		PasswordLabel.setOpaque(true);
		PasswordLabel.setBounds(10, 107, 125, 24);

		TextQuery = new JTextArea(5, 5);

		StatusLabel = new JLabel("    No Connection Now");
		StatusLabel.setBounds(200, 187, 690, 25);
		StatusLabel.setBackground(Color.white);
		StatusLabel.setForeground(Color.black);
		StatusLabel.setOpaque(true);

		WindowLabel = new JLabel();
		WindowLabel.setBounds(45, 231, 220, 25);
		WindowLabel.setFont(new Font("Arial", Font.BOLD, 14));
		WindowLabel.setForeground(Color.blue);
		WindowLabel.setText("SQL Executeion Result Window");

		QueryLabel = new JLabel();
		QueryLabel.setBounds(450, 0, 215, 25);
		QueryLabel.setFont(new Font("Arial", Font.BOLD, 14));
		QueryLabel.setForeground(Color.blue);
		QueryLabel.setText("Enter SQL Command");

		resultTable = new JTable();

		Empty = new DefaultTableModel();

		DriverCombo = new JComboBox(DriverItems);
		DriverCombo.setBounds(135, 21, 290, 25);

		UrlCombo = new JComboBox(UrlItems);
		UrlCombo.setBounds(135, 49, 290, 25);

		UserText = new JTextField("", 10);
		UserText.setBounds(135, 78, 290, 25);

		PasswordText = new JPasswordField("", 10);
		PasswordText.setBounds(135, 106, 290, 25);

		// Windows size and layout
		panel.setPreferredSize(new Dimension(1000, 520));
		panel.setLayout(null);
		final Box square = Box.createHorizontalBox();
		square.add(new JScrollPane(resultTable));
		square.setBounds(45, 254, 841, 220);
		Box sqlSquare = Box.createHorizontalBox();
		sqlSquare.add(new JScrollPane(TextQuery));
		sqlSquare.setBounds(450, 22, 438, 125);
		resultTable.setEnabled(false);
		resultTable.setGridColor(Color.black);

		// Add buttons, text ,and labels, etc
		panel.add(ConnectButton);
		panel.add(ClearQuery);
		panel.add(ExecuteButton);
		panel.add(ClearWindow);
		panel.add(QueryLabel);
		panel.add(sqlSquare);
		panel.add(dbInfoLabel);
		panel.add(jdbcLabel);
		panel.add(UrlLabel);
		panel.add(UserLabel);
		panel.add(PasswordLabel);
		panel.add(DriverCombo);
		panel.add(UrlCombo);
		panel.add(UserText);
		panel.add(PasswordText);
		panel.add(StatusLabel);
		panel.add(WindowLabel);
		panel.add(square);
		ClearWindow.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				resultTable.setModel(Empty);;
			}
		});

		ClearQuery.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				TextQuery.setText("");
			}
		});

		ConnectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				 try
				 {
					 
					 Class.forName(DriverCombo.getSelectedItem().toString());
					 
					 connect = DriverManager.getConnection(UrlCombo.getSelectedItem().toString(), UserText.getText(), PasswordText.getText());

					 StatusLabel.setText("   Succsefully connect to: " + UrlCombo.getSelectedItem().toString());
				 }

				 catch (Exception e)
				 {
					 StatusLabel.setText(e.getMessage());
				 }
			}
		});

		ExecuteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				try {
					resultTable.setEnabled(true);
					resultTable.setAutoscrolls(true);
					tableModel = new ResultSetTable(connect, TextQuery.getText());

					if (TextQuery.getText().toUpperCase().contains("SELECT"))
					{
						tableModel.setQuery(TextQuery.getText());
						resultTable.setModel(tableModel);
					}
					else
					{
						tableModel.setUpdate(TextQuery.getText());
					}
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
				}
				catch (ClassNotFoundException NotFound)
				{
					JOptionPane.showMessageDialog(null, "MySQL driver not found", "Driver not found", JOptionPane.ERROR_MESSAGE);
				}
			}
		});


		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		new SQLClientApp();
	}
}
