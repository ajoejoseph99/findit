import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.*;

class Login extends JFrame implements ActionListener
{
    static JTextField username,password;
	JButton loginbtn;
    JLabel usernamelbl,passwordlbl;
	static String uname,bid;
	
	Login() throws SQLException, ClassNotFoundException
	{	
		setTitle("Login Page");
		this.getContentPane().setBackground(Color.GRAY);
        usernamelbl = new JLabel("Username");
        passwordlbl = new JLabel("Password");
        username = new JTextField(2000);
        password = new JTextField(2000);
		loginbtn = new JButton("Login");
		setLayout(null);
		setVisible(true);
        usernamelbl.setBounds(15,80,250,30);
        username.setBounds(15,120,250,30);
        passwordlbl.setBounds(15,160,250,30);
        password.setBounds(15,200,250,30);
		loginbtn.setBounds(85,250,100,30);
        add(usernamelbl);
        add(passwordlbl);
		add(loginbtn);
        add(username);
        add(password);
		setSize(300,370);
		loginbtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object source= ae.getSource();
		
		if(source==loginbtn)
		{
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
				//System.out.println("Driver Loaded");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				//System.out.println("Connection Established");
				Statement stm = con.createStatement();
				Statement stm1 = con.createStatement();
				//System.out.println("Statement created");
				String u,p;
				u=username.getText();
				p=password.getText();
				String qry = "select password,type from users where username = '"+u+"'";
				String findbid = "select businessid from users where username = '"+u+"'";
				
				ResultSet rs = stm.executeQuery(qry);
				ResultSet rs1 = stm1.executeQuery(findbid);
				//System.out.println("Query executed");
				String fetchpassword=" ";
				String type=" ";
				
				while(rs.next())
				{
					fetchpassword = rs.getString(1);
					type = rs.getString(2);
				}
				if(fetchpassword.equals(p))
				{
					if(type.equals("admin"))
					{
						AdminHomePanel ap = new AdminHomePanel();
						setVisible(false);
					}
					else if(type.equals("business"))
					{
						BusinessHomePanel bhp = new BusinessHomePanel();
						setVisible(false);
					}
					else
						System.out.println("Failed");
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Invalid Credentials!","ERROR", JOptionPane.ERROR_MESSAGE);
					username.setText("");
					password.setText("");
				}
				while(rs1.next())
				{
					bid=rs1.getString(1);
				}
				rs.close();
				rs1.close();
				stm.close();
				stm1.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
	}

}
//-------------------------------------------------------------------------------------
//ADMIN PANEL DEFINITIONS START HERE
//-------------------------------------------------------------------------------------

class AdminHomePanel extends JFrame implements ActionListener
{
	JButton addbusiness,removebusiness,editbusiness,logoutbtn,showallbusinesses;
    JPanel adminhomepanel;
	AdminHomePanel()
	{
		setTitle("AdminHomePanel");
		addbusiness = new JButton("Add a business");
		removebusiness= new JButton("Remove a business");
		editbusiness = new JButton("Edit business details");
		showallbusinesses = new JButton("Show all businesses");
		logoutbtn = new JButton("Logout");
        adminhomepanel = new JPanel();
        add(adminhomepanel);
		setVisible(true);
		setDefaultLookAndFeelDecorated(true);
        adminhomepanel.setLayout(new GridLayout(5,1));
        adminhomepanel.add(addbusiness);
        adminhomepanel.add(removebusiness);
		adminhomepanel.add(editbusiness);
		adminhomepanel.add(showallbusinesses);
		adminhomepanel.add(logoutbtn);
		setSize(500,500);
		addbusiness.addActionListener(this);
		removebusiness.addActionListener(this);
		editbusiness.addActionListener(this);
		logoutbtn.addActionListener(this);
		showallbusinesses.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source==addbusiness)
		{
			AddBusinessPanel obj = new AddBusinessPanel();
		}
		else if(source==removebusiness)
		{
			RemoveBusinessPanel obj = new RemoveBusinessPanel();
		}
		else if(source==editbusiness)
		{
			EditBusinessPanel obj = new EditBusinessPanel();
		}
		else if(source==showallbusinesses)
		{
			ShowBusinesses obj = new ShowBusinesses();
		}
		else if(source==logoutbtn)
		{
			try {
				Login obj = new Login();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setVisible(false);
		}
		
	}
    
    
}

class AddBusinessPanel extends JFrame implements ActionListener
{
	JLabel businessnamelbl,businessaddresslbl,businessnumberlbl,businesstypelbl,businessusernamelbl,businesspasswordlbl,businessidlbl;
	JTextField businessname,businessaddress,businessnumber,businessusername,businesspassword,businessid;
	JPanel addbusinesspanel;
	JButton submit,cancel;
	JComboBox list;
	AddBusinessPanel()
	{
		String[] options= {"Business","Service"};
		list = new JComboBox(options);
		setTitle("Add Business");
		businessidlbl = new JLabel("Business ID");
		businessid = new JTextField(1000);
		businessnamelbl = new JLabel("Business Name");
		businessname = new JTextField(1000);
		businessaddresslbl = new JLabel("Business Address");
		businessaddress = new JTextField(2000);
		businessnumberlbl = new JLabel("Business Number");
		businessnumber = new JTextField(1000);
		businesstypelbl = new JLabel("Business Type");
		businessusernamelbl = new JLabel("Username");
		businessusername = new JTextField(1000);
		businesspasswordlbl = new JLabel("Password");
		businesspassword = new JTextField(1000);
		cancel = new JButton("Cancel");
		submit = new JButton("Submit");
		setVisible(true);
		addbusinesspanel = new JPanel();
		addbusinesspanel.setLayout(new GridLayout(8,2,2,15));
		add(addbusinesspanel);
		addbusinesspanel.add(businessidlbl);
		addbusinesspanel.add(businessid);
		addbusinesspanel.add(businessnamelbl);
		addbusinesspanel.add(businessname);
		addbusinesspanel.add(businessaddresslbl);
		addbusinesspanel.add(businessaddress);
		addbusinesspanel.add(businessnumberlbl);
		addbusinesspanel.add(businessnumber);
		addbusinesspanel.add(businesstypelbl);
		addbusinesspanel.add(list);
		addbusinesspanel.add(businessusernamelbl);
		addbusinesspanel.add(businessusername);
		addbusinesspanel.add(businesspasswordlbl);
		addbusinesspanel.add(businesspassword);
		addbusinesspanel.add(cancel);
		addbusinesspanel.add(submit);
		
		setSize(600,500);
		submit.addActionListener(this);
		cancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if(source==submit)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm1 = con.createStatement();
				Statement stm2 = con.createStatement();
				String bid,bname,baddress,bnumber;
				String buname,bpass;

				bid=businessid.getText();
				bname=businessname.getText();
				baddress=businessaddress.getText();
				bnumber=businessnumber.getText();
				buname=businessusername.getText();
				bpass=businesspassword.getText();

				String qry1 = "insert into businesses(businessid,businessname,businessaddress,businessnumber,businesstype) values('" +bid+ "','" +bname+ "','" +baddress+ "','" +bnumber+ "','" +list.getSelectedItem()+ "')";
				String qry2="insert into users(username,password,type,businessid) values('" +buname+ "','" +bpass+ "','" +list.getSelectedItem()+ "','" +bid+ "')";
				stm1.executeUpdate(qry1);
				stm2.executeUpdate(qry2);
				JOptionPane.showMessageDialog(this, "Business added!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				stm1.close();
				stm2.close();
				con.close();

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(source == cancel)
		{
			setVisible(false);
		}
	}
	
}

class RemoveBusinessPanel extends JFrame implements ActionListener
{
	JLabel businessnamelbl,businessaddresslbl,businessnumberlbl,businesstypelbl,businessusernamelbl,businesspasswordlbl,businessidlbl;
	JTextField businessname,businessaddress,businessnumber,businessusername,businesspassword,businessid;
	JPanel addbusinesspanel,panel1;
	JButton delete,cancel,find;
	JComboBox list;
	RemoveBusinessPanel()
	{
		String[] options= {"Business","Service"};
		list = new JComboBox(options);
		setTitle("Remove Business");
		businessidlbl = new JLabel("Business ID");
		businessid = new JTextField(1000);
		businessnamelbl = new JLabel("Business Name");
		businessname = new JTextField(1000);
		businessaddresslbl = new JLabel("Business Address");
		businessaddress = new JTextField(2000);
		businessnumberlbl = new JLabel("Business Number");
		businessnumber = new JTextField(1000);
		businesstypelbl = new JLabel("Business Type");
		businessusernamelbl = new JLabel("Username");
		businessusername = new JTextField(1000);
		businesspasswordlbl = new JLabel("Password");
		businesspassword = new JTextField(1000);
		cancel = new JButton("Cancel");
		delete = new JButton("Delete");
		find = new JButton("Find");
		setVisible(true);
		addbusinesspanel = new JPanel();
		panel1 = new JPanel();
		addbusinesspanel.setLayout(new GridLayout(7,2,2,15));
		panel1.setLayout(new GridLayout(1,3,15,0));
		add(addbusinesspanel);
		addbusinesspanel.add(businessidlbl);
		addbusinesspanel.add(businessid);
		addbusinesspanel.add(businessnamelbl);
		addbusinesspanel.add(businessname);
		businessname.setEditable(false);
		addbusinesspanel.add(businessaddresslbl);
		addbusinesspanel.add(businessaddress);
		businessaddress.setEditable(false);
		addbusinesspanel.add(businessnumberlbl);
		addbusinesspanel.add(businessnumber);
		businessnumber.setEditable(false);
		addbusinesspanel.add(businesstypelbl);
		addbusinesspanel.add(list);
		list.setEnabled(false);
		addbusinesspanel.add(businessusernamelbl);
		addbusinesspanel.add(businessusername);
		businessusername.setEditable(false);
		addbusinesspanel.add(businesspasswordlbl);
		addbusinesspanel.add(businesspassword);
		businesspassword.setEditable(false);
		add(panel1,BorderLayout.SOUTH);
		panel1.add(cancel);
		panel1.add(find);
		panel1.add(delete);
		setSize(600,400);
		delete.addActionListener(this);
		cancel.addActionListener(this);
		find.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if(source == find)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm1 = con.createStatement();
				Statement stm2 = con.createStatement();
				String qry1 = "select * from businesses where businessid= '" +businessid.getText()+ "'";
				String qry2 = "select * from users where businessid= '" +businessid.getText()+ "'";
				ResultSet rs1,rs2;
				rs1=stm1.executeQuery(qry1);
				rs2=stm2.executeQuery(qry2);
				String nm,ad,no,ty,u,p;
				while(rs1.next())
				{
					nm=rs1.getString(2);
					ad=rs1.getString(3);
					no=rs1.getString(4);
					ty=rs1.getString(5);
					businessname.setText(nm);
					businessaddress.setText(ad);
					businessnumber.setText(no);
					list.setSelectedItem(ty);
					businessname.setEditable(true);
					businessaddress.setEditable(true);
					businessnumber.setEditable(true);
					list.setEnabled(true);
				}
				while(rs2.next())
				{
					u=rs2.getString(1);
					p=rs2.getString(2);
					businessusername.setText(u);
					businesspassword.setText(p);
					businessusername.setEditable(true);
					businesspassword.setEditable(true);
				}
				stm1.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if(source==delete)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm1 = con.createStatement();
				Statement stm2 = con.createStatement();
				String qry1 = "delete from businesses where businessid= '" +businessid.getText()+ "'";
				String qry2 = "delete from users where businessid= '" +businessid.getText()+ "'";
				int dialoguebutton = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this business listing?","WARNING",JOptionPane.YES_OPTION);
				if(dialoguebutton==JOptionPane.YES_OPTION)
				{
					stm1.executeUpdate(qry1);
					stm2.executeUpdate(qry2);
					JOptionPane.showMessageDialog(this, "Business details deleted!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
					
				else if(dialoguebutton==JOptionPane.NO_OPTION)
				{
					JOptionPane.showMessageDialog(this, "No changes were made!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				stm1.close();
				stm2.close();
				con.close();

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(source == cancel)
		{
			setVisible(false);
		}
	}
	
}

class EditBusinessPanel extends JFrame implements ActionListener
{
	JLabel businessnamelbl,businessaddresslbl,businessnumberlbl,businesstypelbl,businessusernamelbl,businesspasswordlbl,businessidlbl;
	JTextField businessname,businessaddress,businessnumber,businessusername,businesspassword,businessid;
	JPanel addbusinesspanel,panel1;
	JButton update,cancel,find;
	JComboBox list;
	EditBusinessPanel()
	{
		String[] options= {"Business","Service"};
		list = new JComboBox(options);
		setTitle("Edit Business");
		businessidlbl = new JLabel("Business ID");
		businessid = new JTextField(1000);
		businessnamelbl = new JLabel("Business Name");
		businessname = new JTextField(1000);
		businessaddresslbl = new JLabel("Business Address");
		businessaddress = new JTextField(2000);
		businessnumberlbl = new JLabel("Business Number");
		businessnumber = new JTextField(1000);
		businesstypelbl = new JLabel("Business Type");
		businessusernamelbl = new JLabel("Username");
		businessusername = new JTextField(1000);
		businesspasswordlbl = new JLabel("Password");
		businesspassword = new JTextField(1000);
		cancel = new JButton("Cancel");
		update = new JButton("Update");
		find = new JButton("Find");
		setVisible(true);
		addbusinesspanel = new JPanel();
		panel1 = new JPanel();
		addbusinesspanel.setLayout(new GridLayout(7,2,2,15));
		panel1.setLayout(new GridLayout(1,3,15,0));
		add(addbusinesspanel);
		addbusinesspanel.add(businessidlbl);
		addbusinesspanel.add(businessid);
		addbusinesspanel.add(businessnamelbl);
		addbusinesspanel.add(businessname);
		businessname.setEditable(false);
		addbusinesspanel.add(businessaddresslbl);
		addbusinesspanel.add(businessaddress);
		businessaddress.setEditable(false);
		addbusinesspanel.add(businessnumberlbl);
		addbusinesspanel.add(businessnumber);
		businessnumber.setEditable(false);
		addbusinesspanel.add(businesstypelbl);
		addbusinesspanel.add(list);
		list.setEnabled(false);
		addbusinesspanel.add(businessusernamelbl);
		addbusinesspanel.add(businessusername);
		businessusername.setEditable(false);
		addbusinesspanel.add(businesspasswordlbl);
		addbusinesspanel.add(businesspassword);
		businesspassword.setEditable(false);
		add(panel1,BorderLayout.SOUTH);
		panel1.add(cancel);
		panel1.add(find);
		panel1.add(update);
		setSize(600,400);
		update.addActionListener(this);
		cancel.addActionListener(this);
		find.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if(source == find)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm1 = con.createStatement();
				Statement stm2 = con.createStatement();
				String qry1 = "select * from businesses where businessid= '" +businessid.getText()+ "'";
				String qry2 = "select * from users where businessid= '" +businessid.getText()+ "'";
				ResultSet rs1,rs2;
				rs1=stm1.executeQuery(qry1);
				rs2=stm2.executeQuery(qry2);
				String nm,ad,no,ty,u,p;
				while(rs1.next())
				{
					nm=rs1.getString(2);
					ad=rs1.getString(3);
					no=rs1.getString(4);
					ty=rs1.getString(5);
					businessname.setText(nm);
					businessaddress.setText(ad);
					businessnumber.setText(no);
					list.setSelectedItem(ty);
					businessname.setEditable(true);
					businessaddress.setEditable(true);
					businessnumber.setEditable(true);
					list.setEnabled(true);
				}
				while(rs2.next())
				{
					u=rs2.getString(1);
					p=rs2.getString(2);
					businessusername.setText(u);
					businesspassword.setText(p);
					businessusername.setEditable(true);
					businesspassword.setEditable(true);
				}
				stm1.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if(source==update)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm1 = con.createStatement();
				Statement stm2 = con.createStatement();
				String qry1 = "update businesses set businessname='" +businessname.getText()+ "',businessaddress='" +businessaddress.getText()+ "',businessnumber= '" +businessnumber.getText()+ "',businesstype= '" +String.valueOf(list.getSelectedItem())+ "' where businessid= '" +businessid.getText()+ "'";
				String qry2 = "update users set username= '" +businessusername.getText()+ "',password='" +businesspassword.getText()+ "' where businessid= '" +businessid.getText()+ "'";
				int dialoguebutton = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this business listing?","WARNING",JOptionPane.YES_OPTION);
				if(dialoguebutton==JOptionPane.YES_OPTION)
				{
					stm1.executeUpdate(qry1);
					stm2.executeUpdate(qry2);
					JOptionPane.showMessageDialog(this, "Business details updated!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
					
				else if(dialoguebutton==JOptionPane.NO_OPTION)
				{
					JOptionPane.showMessageDialog(this, "No changes were made!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				stm1.close();
				stm2.close();
				con.close();

			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(source == cancel)
		{
			setVisible(false);
		}
	}
	
}

//-------------------------------------------------------------------------------------
//ADMIN PANEL DEFINITIONS END HERE
//-------------------------------------------------------------------------------------

//-------------------------------------------------------------------------------------
//BUSINESS PANEL DEFINITIONS START HERE
//-------------------------------------------------------------------------------------

class BusinessHomePanel extends JFrame implements ActionListener
{
	JLabel businessdetailslbl,productdetailslbl;
	JButton addproduct,removeproduct,updateproduct,viewinventory,editdetails,logout;
	JPanel panel1,panel2,panel3;
	BusinessHomePanel()
	{
		setTitle("Business Home Panel");
		panel1 = new JPanel();
		//panel2 = new JPanel();
		//panel3 = new JPanel();
		businessdetailslbl = new JLabel("Business Details");
		productdetailslbl = new JLabel("Product Details");
		addproduct = new JButton("Add Product");
		removeproduct = new JButton("Remove Product");
		updateproduct = new JButton("Update Product");
		viewinventory = new JButton("View Inventory");
		editdetails = new JButton("Edit Details");
		logout = new JButton("Logout");
		panel1.setLayout(new GridLayout(7,1,0,5));
		//panel2.setLayout(new FlowLayout());
		//panel3.setLayout(new FlowLayout());
		add(panel1);
		panel1.add(productdetailslbl);
		//panel1.add(panel2);
		panel1.add(addproduct);
		panel1.add(removeproduct);
		panel1.add(updateproduct);
		panel1.add(businessdetailslbl);
		//panel1.add(panel3,BorderLayout.WEST);
		panel1.add(editdetails);
		panel1.add(logout);
		setVisible(true);
		setSize(300,300);
		//pack();
		addproduct.addActionListener(this);
		removeproduct.addActionListener(this);
		updateproduct.addActionListener(this);
		editdetails.addActionListener(this);
		logout.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if(source==addproduct)
		{
			AddProduct obj = new AddProduct();
		}
		else if(source == removeproduct)
		{
			RemoveProduct obj = new RemoveProduct();
		}
		else if(source == updateproduct)
		{
			UpdateProduct obj = new UpdateProduct();
		}
		else if(source == editdetails)
		{
			EditBusinessDetailsPanel obj = new EditBusinessDetailsPanel();
		}
		else if(source == logout)
		{
			try {
				Login obj = new Login();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setVisible(false);
		}
	}
}


class AddProduct extends JFrame implements ActionListener
{
	JLabel productidlbl,productnamelbl,mrplbl,pricelbl;
	JTextField productid,productname,mrp,price;
	JButton add,cancel;
	JPanel panel1,panel2;
	AddProduct()
	{
		setTitle("Add Product");
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel1.setLayout(new GridLayout(4,2,0,10));
		add(panel1,BorderLayout.NORTH);
		productidlbl = new JLabel("Product ID");
		productid = new JTextField(1000);
		productnamelbl = new JLabel("Product Name");
		productname = new JTextField(2000);
		mrplbl = new JLabel("MRP");
		mrp = new JTextField(1000);
		pricelbl = new JLabel("Price");
		price = new JTextField(1000);
		add(panel2,BorderLayout.SOUTH);
		panel2.setLayout(new FlowLayout());
		add = new JButton("Add");
		cancel = new JButton("Cancel");
		panel1.add(productidlbl);
		panel1.add(productid);
		panel1.add(productnamelbl);
		panel1.add(productname);
		panel1.add(mrplbl);
		panel1.add(mrp);
		panel1.add(pricelbl);
		panel1.add(price);
		panel2.add(cancel);
		panel2.add(add);
		setSize(400,200);
		setVisible(true);
		add.addActionListener(this);
		cancel.addActionListener(this);

	}
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(source==add)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm = con.createStatement();

				String strpid,pname,strpmrp,strpprice;
				Integer pid;
				Float pmrp,pprice;

				strpid=productid.getText();
				strpmrp=mrp.getText();
				strpprice=price.getText();
				pname=productname.getText();
				
				pid=Integer.parseInt(strpid);
				pmrp=Float.parseFloat(strpmrp);
				pprice=Float.parseFloat(strpprice);

				String busid=Login.bid;
				String qry1 = "insert into products(productid,productname,mrp,price,businessid) values(" +pid+ ", '" +pname+ "' ," +pmrp+ "," +pprice+ "," +busid+ ")";
				stm.executeUpdate(qry1);
			
				JOptionPane.showMessageDialog(this, "1 product added!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				stm.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if(source==cancel)
		{
			setVisible(false);
		}
		
	}
}

class RemoveProduct extends JFrame implements ActionListener
{
	JLabel productidlbl,productnamelbl,mrplbl,pricelbl;
	JTextField productid,productname,mrp,price;
	JButton delete,find,cancel;
	JPanel panel1,panel2;
	RemoveProduct()
	{
		setTitle("Remove product");
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel1.setLayout(new GridLayout(5,2,0,10));
		add(panel1,BorderLayout.NORTH);
		productidlbl = new JLabel("Product ID");
		productid = new JTextField(1000);
		productnamelbl = new JLabel("Product Name");
		productname = new JTextField(2000);
		mrplbl = new JLabel("MRP");
		mrp = new JTextField(1000);
		pricelbl = new JLabel("Price");
		price = new JTextField(1000);
		find = new JButton("Find");
		add(panel2,BorderLayout.SOUTH);
		panel2.setLayout(new FlowLayout());
		delete = new JButton("Delete");
		cancel = new JButton("Cancel");
		panel1.add(productidlbl);
		panel1.add(productid);
		panel1.add(productnamelbl);
		panel1.add(productname);
		panel1.add(mrplbl);
		panel1.add(mrp);
		panel1.add(pricelbl);
		panel1.add(price);
		panel2.add(cancel);
		panel2.add(find);
		panel2.add(delete);
		productname.setEditable(false);
		mrp.setEditable(false);
		price.setEditable(false);
		setSize(400,200);
		setVisible(true);
		find.addActionListener(this);
		delete.addActionListener(this);
		cancel.addActionListener(this);


	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if(source==find)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm = con.createStatement();

				String qry = "select * from products where productid='" +productid.getText()+ "' AND businessid= '" +Login.bid+ "'";
				ResultSet rs = stm.executeQuery(qry);
				String name=" ";
				Double pmrp = 0.0;
				Double pprice = 0.0;
				while(rs.next())
				{
					name=rs.getString(2);
					pmrp=rs.getDouble(3);
					pprice=rs.getDouble(4);
				}
				productname.setText(name);
				mrp.setText(Double.toString(pmrp));
				price.setText(Double.toString(pprice));
				rs.close();
				stm.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(source == delete)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm = con.createStatement();
				String qry = "delete from products where productid='" +productid.getText()+ "' AND businessid= '" +Login.bid+ "'";	
				stm.executeUpdate(qry);	
				JOptionPane.showMessageDialog(this, "Product deleted!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				stm.close();
				con.close();
				setVisible(false);
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		if(source==cancel)
		{
			setVisible(false);
		}
		
	}
}

class UpdateProduct extends JFrame implements ActionListener
{
	JLabel productidlbl,productnamelbl,mrplbl,pricelbl;
	JTextField productid,productname,mrp,price;
	JButton update,find,cancel;
	JPanel panel1,panel2;
	UpdateProduct()
	{
		setTitle("Update product");
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel1.setLayout(new GridLayout(4,2,0,15));
		add(panel1,BorderLayout.NORTH);
		productidlbl = new JLabel("Product ID");
		productid = new JTextField(1000);
		productnamelbl = new JLabel("Product Name");
		productname = new JTextField(2000);
		mrplbl = new JLabel("MRP");
		mrp = new JTextField(1000);
		pricelbl = new JLabel("Price");
		price = new JTextField(1000);
		add(panel2,BorderLayout.SOUTH);
		panel2.setLayout(new FlowLayout());
		update = new JButton("Update");
		cancel = new JButton("Cancel");
		panel1.add(productidlbl);
		panel1.add(productid);
		panel1.add(productnamelbl);
		panel1.add(productname);
		panel1.add(mrplbl);
		panel1.add(mrp);
		panel1.add(pricelbl);
		panel1.add(price);
		find = new JButton("Find");
		panel2.add(cancel);
		panel2.add(find);
		panel2.add(update);
		setSize(400,200);
		setVisible(true);
		//pack();
		update.addActionListener(this);
		find.addActionListener(this);
		cancel.addActionListener(this);

	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if(source == find)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm = con.createStatement();
				String qry = "select * from products where productid='" +productid.getText()+ "' AND businessid= '" +Login.bid+ "'";
				ResultSet rs = stm.executeQuery(qry);
				String name=" ";
				Double pmrp = 0.0;
				Double pprice = 0.0;
				while(rs.next())
				{
					name=rs.getString(2);
					pmrp=rs.getDouble(3);
					pprice=rs.getDouble(4);
				}
				productname.setText(name);
				mrp.setText(Double.toString(pmrp));
				price.setText(Double.toString(pprice));
				rs.close();
				stm.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(source==update)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm = con.createStatement();
				String pname,stmrp,stprice;
				Double pmrp,pprice;
				pname=productname.getText();
				stmrp=mrp.getText();
				pmrp=Double.parseDouble(stmrp);
				stprice=price.getText();
				pprice=Double.parseDouble(stprice);
				String qry = "update products set productname='" +pname+ "',mrp= " +pmrp+ ",price= " +pprice+ " where productid= '" +productid.getText()+ "' AND businessid= '" +Login.bid+ "'";
				int dialoguebutton = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this product listing?","WARNING",JOptionPane.YES_OPTION);
				if(dialoguebutton==JOptionPane.YES_OPTION)
				{
					stm.executeUpdate(qry);
					JOptionPane.showMessageDialog(this, "Product details updated!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
				else if(dialoguebutton==JOptionPane.NO_OPTION)
				{
					JOptionPane.showMessageDialog(this, "No changes were made!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				stm.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(source==cancel)
		{
			setVisible(false);
		}

	}
		
}





class EditBusinessDetailsPanel extends JFrame implements ActionListener
{
	JLabel businessnamelbl,businessaddresslbl,businessnumberlbl,businesstypelbl;
	JTextField businessname,businessaddress,businessnumber;
	JPanel panel1;
	JButton edit,submit,cancel;
	JComboBox list;
	EditBusinessDetailsPanel()
	{
		String[] options= {"Business","Service"};
		list = new JComboBox(options);
		setTitle("Edit Business Details");
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(5,2,0,10));
		businessnamelbl = new JLabel("Business Name");
		businessname = new JTextField(1000);
		businessaddresslbl = new JLabel("Business Address");
		businessaddress = new JTextField(2000);
		businessnumberlbl = new JLabel("Business Number");
		businessnumber = new JTextField(1000);
		businesstypelbl = new JLabel("Business Type");
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		setVisible(true);
		add(panel1);
		panel1.add(businessnamelbl);
		panel1.add(businessname);
		panel1.add(businessaddresslbl);
		panel1.add(businessaddress);
		panel1.add(businessnumberlbl);
		panel1.add(businessnumber);
		panel1.add(businesstypelbl);
		panel1.add(list);
		panel1.add(cancel);
		panel1.add(submit);
		setSize(600,250);
		submit.addActionListener(this);
		cancel.addActionListener(this);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
			Statement stm = con.createStatement();
			String qry1 = "select * from businesses where businessid= '" +Login.bid+ "'";
			ResultSet rs1= stm.executeQuery(qry1);
			while(rs1.next())
			{
				String bname,baddress,bnumber,btype;
				bname=rs1.getString(2);
				baddress=rs1.getString(3);
				bnumber=rs1.getString(4);
				btype=rs1.getString(5);
				businessname.setText(bname);
				businessaddress.setText(baddress);
				businessnumber.setText(bnumber);
				list.setSelectedItem(btype);
			}
			rs1.close();
			stm.close();
			con.close();

		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
	
		if(source==submit)
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
				Statement stm = con.createStatement();
				String qry = "update businesses set businessname= '" +businessname.getText()+ "',businessaddress= '" +businessaddress.getText()+ "',businessnumber= '" +businessnumber.getText()+ "',businesstype= '" +list.getSelectedItem()+ "' where businessid= '" +Login.bid+ "'";
				int dialoguebutton = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this business listing?","WARNING",JOptionPane.YES_OPTION);
				if(dialoguebutton==JOptionPane.YES_OPTION)
				{
					stm.executeUpdate(qry);
					JOptionPane.showMessageDialog(this, "Business details updated!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
				else if(dialoguebutton==JOptionPane.NO_OPTION)
				{
					JOptionPane.showMessageDialog(this, "No changes were made!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				stm.close();
				con.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(source == cancel);
		{
			setVisible(false);
		}
	}
	
}


/*int dialoguebutton = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this business listing?","WARNING",JOptionPane.YES_OPTION);
				if(dialoguebutton==JOptionPane.YES_OPTION)
				{
					stm1.executeUpdate(qry1);
					stm2.executeUpdate(qry2);
					JOptionPane.showMessageDialog(this, "Business details updated!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
					
				else if(dialoguebutton==JOptionPane.NO_OPTION)
				{
					JOptionPane.showMessageDialog(this, "No changes were made!","Execution Successful",JOptionPane.INFORMATION_MESSAGE);
				}
*/
//-------------------------------------------------------------------------------------
//BUSINESS PANEL DEFINITIONS END HERE
//-------------------------------------------------------------------------------------


class ShowBusinesses extends JFrame
{
	JTable table;
	JButton button;
	ShowBusinesses()
	{
		
		//button = new JButton();
		
		//add(button);
		setVisible(true);
		//setLayout(null);
		setSize(400,200);
		//String columnNames[]={"Username","Password"};
		DefaultTableModel model= new DefaultTableModel(new String[] {"Username","Password"},0);
		//model.setColumnIdentifiers(columnNames);
		table= new JTable(model);
		model.addColumn("Username");
		model.addColumn("Password");
		add(table);
		//table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
		//pack();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/findit","Ajoe","1234");
			Statement stm = con.createStatement();
			String qry = "select * from users";
			ResultSet rs = stm.executeQuery(qry);
			String uname,pword;
			
			while(rs.next())
			{
				System.out.println("Entered loop");
				uname=rs.getString(1);
				pword=rs.getString(2);
				model.addRow(new Object[] {uname,pword});
			}
			rs.close();
			stm.close();
			con.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



public class Main
{
	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException
	{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		//UIManager.put("defaultFont", new Font("Helvetica", Font.BOLD, 14));
		Login lg= new Login();
        //AdminHomePanel obj = new AdminHomePanel();
		//EditBusinessPanel obj = new EditBusinessPanel();
		//RemoveBusinessPanel obj = new RemoveBusinessPanel();
		//BusinessHomePanel obj = new BusinessHomePanel();
		//RemoveProduct obj = new RemoveProduct();
		//EditBusinessDetailsPanel obj = new EditBusinessDetailsPanel();
		//ShowBusinesses sb = new ShowBusinesses();
	}
}
