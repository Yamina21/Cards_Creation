import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.*;

import javafx.stage.WindowEvent;

public class NewMember implements ActionListener {
    final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";
    

    JFrame frame = new JFrame();

    JLabel label = new JLabel("اضافة بطاقة عضوية ");
    JButton insert= new JButton("ادماج");


    JTextField id_number = new JTextField();
    JTextField name = new JTextField();
    JTextField sur_name = new JTextField();
    JTextField nickname = new JTextField();
    JTextField birth_date = new JTextField();
    JTextField place_birth = new JTextField();
    JTextField father_name = new JTextField();
    JTextField mother_name = new JTextField();
    JTextField property = new JTextField();








    
   
    public void initialize(){
   
        label.setBounds(100,50,100,50);

        id_number.setBounds(100,100,100,50);
        name.setBounds(100,150,100,50);
        sur_name.setBounds(100,200,100,50);
        nickname.setBounds(100,250,100,50);
        birth_date.setBounds(100,300,100,50);
        place_birth.setBounds(100,350,100,50);
        father_name.setBounds(100,400,100,50);
        mother_name.setBounds(100,450,100,50);
        property.setBounds(100,500,100,50);




         insert.setBounds(100,550,100,50);
 
         insert.addActionListener(this);
 
         id_number.setSize(350, 40);
         name.setSize(350, 40);
         sur_name.setSize(350, 40);
         nickname.setSize(350, 40);
         birth_date.setSize(350, 40);
         place_birth.setSize(350, 40);
         father_name.setSize(350, 40);
         mother_name.setSize(350, 40);       
         property.setSize(350, 40);
         label.setSize(350, 40);




         id_number.setFont(new Font("Arial",Font.PLAIN,20));
         name.setFont(new Font("Arial",Font.PLAIN,20));
         sur_name.setFont(new Font("Arial",Font.PLAIN,20));
         nickname.setFont(new Font("Arial",Font.PLAIN,20));
         birth_date.setFont(new Font("Arial",Font.PLAIN,20));
         place_birth.setFont(new Font("Arial",Font.PLAIN,20));
         father_name.setFont(new Font("Arial",Font.PLAIN,20));
         mother_name.setFont(new Font("Arial",Font.PLAIN,20));
         property.setFont(new Font("Arial",Font.PLAIN,20));
         label.setFont(new Font("Arial",Font.PLAIN,25));


         id_number.setText(" رقم البطاقة");
         name.setText("الاسم");
         sur_name.setText("اللفب");
         nickname.setText("المدعو");
         birth_date.setText("تاريخ الميلاد");
         place_birth.setText("مكان الميلاد");
         father_name.setText("اسم الأب ");
         mother_name.setText("اسم الأم");
         property.setText("الصفة");
         id_number.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         sur_name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         nickname.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         birth_date.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         place_birth.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         father_name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         mother_name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         property.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
         label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  


         frame.add(label);

         frame.add(id_number);
         frame.add(name);
         frame.add(sur_name);
         frame.add(nickname);
         frame.add(birth_date);
         frame.add(place_birth);
         frame.add(father_name);
         frame.add(mother_name);
         frame.add(property);




		 frame.add(insert);

         frame.setSize(600,700);
         frame.setLayout(null);
         frame.setVisible(true);
   
    
    frame.addWindowListener((WindowListener) new WindowAdapter(){
        public void windowClosing(WindowEvent w)
        {
        frame.dispose();
        }       
    });

}


@Override
public void actionPerformed(ActionEvent e) {
    final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";
    String namep = name.getText();
    int id_numberp = Integer.parseInt(id_number.getText());
    String sur_namep = sur_name.getText();
    String nicknamep = nickname.getText();
    String birthdatep = birth_date.getText();
    String placebirthp = place_birth.getText();
    String fathernamep = father_name.getText();
    String mothernamep = mother_name.getText();
    String propertyp = property.getText();




    





      //TODO Auto-generated method stub
    if(e.getSource()== insert) {
  
        try{
        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

        String query = "INSERT INTO persons  (id_national, namep, sur_name, nickname, birth_date, place_birth, father_name, mother_name, property) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id_numberp);
        preparedStatement.setString(2, namep);
        preparedStatement.setString(3, sur_namep);
        preparedStatement.setString(4, nicknamep);
        preparedStatement.setDate(5, Date.valueOf(birthdatep));       
        preparedStatement.setString(6, placebirthp);
        preparedStatement.setString(7, fathernamep);
        preparedStatement.setString(8, mothernamep);
        preparedStatement.setString(9, propertyp);


 
      // execute the preparedstatement
      preparedStatement.executeUpdate();
       conn.close();
       JOptionPane.showMessageDialog(null,
       "تم اضافة عضو جديد بنجاح!","Alert",JOptionPane.WARNING_MESSAGE);     
      

  }catch(Exception event){
      System.out.println("Database connexion failed!");
      JOptionPane.showMessageDialog(
        null,
        "يرجى مراجعة المعلومات المضافة",
        "خطأ في المعلومات!",JOptionPane.ERROR_MESSAGE);
  }
        
}
}
}