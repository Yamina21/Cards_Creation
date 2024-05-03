import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Highlighter;
import java.awt.*;
 import javax.swing.table.*;
 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.*;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.text.*;
public class MainFrame extends JFrame  implements ActionListener {
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);

       JFrame table =new JFrame();    
    JButton addButton= new JButton("اضافة عضوية");
    JButton edit= new JButton("تصحيح");
    JButton refresh= new JButton("تحديث");
    JButton delete= new JButton("الغاء العضوية");

    JPanel southBtnPanel = new JPanel(new GridLayout(1, 4, 4, 4));
   
 
    
    public void initialize(ArrayList<Person> person) {
    
        /*************** Info Panel ***************/
     
     
        
     
        Map attributes = (new Font("Serif", Font.PLAIN, 12)).getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        Font newFont = new Font(attributes); 


        addButton.setFocusable(false);
        edit.setFocusable(false);
        addButton.setPreferredSize(new Dimension(40, 40));
        edit.setPreferredSize(new Dimension(40, 40));
        refresh.setPreferredSize(new Dimension(40, 40));
        delete.setPreferredSize(new Dimension(40, 40));



      
     


         String column[]={"رقم البطاقة","رقم وطني","الاسم","اللقب","المدعو","ت.الميلاد","مكان.الميلاد","اسم الأب","اسم الأم","الصفة"};   
          DefaultTableModel model = new DefaultTableModel(column,0); 
         JTable jt = new JTable(model);
         jt.setBounds(100,200,800,800);   
         ArrayList<Person> persons = person;
int i;
 
         for(i = 0; i < person.size(); i++)
        {   model.addRow(new Object[] { 
            persons.get(i).getId(),
            persons.get(i).getIdn(),
            persons.get(i).getName(),
            persons.get(i).getSurname(),
            persons.get(i).getNickname(),
            persons.get(i).getBirthdate(),
            persons.get(i).getPlacebirth(),
            persons.get(i).getFathername(),
            persons.get(i).getMothername(),
            persons.get(i).getProperty()

           });
            
        }
        jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
             public Component getTableCellRendererComponent(JTable table,
                                          Object value,
                                          boolean isSelected,
                                          boolean hasFocus,
                                          int row,
                                          int column) {
                                         
                 Component comp = super.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
                        if(persons.get(row).getProperty().equals("ملغي") ){
                    comp.setForeground(java.awt.Color.RED);
                    comp.setFont(newFont);
                }else{
               
                    comp.setForeground(java.awt.Color.white);
                 }
        
         
                return comp;
            }
        });
         jt.setShowGrid(true);
        jt.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)jt.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment( JLabel.RIGHT );
        
        JScrollPane sp=new JScrollPane(jt);   
        table.setSize(800,600);    
 
    table.add(sp);          
 table.add(southBtnPanel, BorderLayout.SOUTH);
 southBtnPanel.add(delete, BorderLayout.SOUTH);

 southBtnPanel.add(edit, BorderLayout.SOUTH);
 southBtnPanel.add(refresh, BorderLayout.SOUTH);

 
 southBtnPanel.add(addButton, BorderLayout.SOUTH);

        table.setVisible(true);       
        edit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";
                int rowIndex = jt.getSelectedRow();
                int colIndex = jt.getSelectedColumn();
                 String value = jt.getModel().getValueAt(rowIndex, colIndex).toString();
                 int id_value = Integer.parseInt(jt.getModel().getValueAt(rowIndex, 0).toString());
                 String namep = jt.getModel().getValueAt(rowIndex, 2).toString();
                 int id_numberp = Integer.parseInt(jt.getModel().getValueAt(rowIndex, 1).toString());
                 String sur_namep = jt.getModel().getValueAt(rowIndex, 3).toString();
                 String nicknamep = jt.getModel().getValueAt(rowIndex, 4).toString();
                 String birthdatep = jt.getModel().getValueAt(rowIndex, 5).toString();
                 String placebirthp = jt.getModel().getValueAt(rowIndex, 6).toString();
                 String fathernamep = jt.getModel().getValueAt(rowIndex, 7).toString();
                 String mothernamep = jt.getModel().getValueAt(rowIndex, 8).toString();
                 String propertyp = jt.getModel().getValueAt(rowIndex, 9).toString();



                 

 
                jt.setValueAt(value, rowIndex, colIndex);
                 try{
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String query = "UPDATE  persons SET id_national= ?, namep=?, sur_name= ?, nickname= ?, birth_date= ?, place_birth= ?, father_name = ?, mother_name= ?, property= ? WHERE ID =" + id_value;
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
            
                     preparedStatement.executeUpdate();

                     JOptionPane.showMessageDialog(null,
                     "تم تصحيح المعلومات بنجاح!","Alert",JOptionPane.WARNING_MESSAGE);   

                //do stuff  with value          
                conn.close();
   

  }catch(Exception event){
      System.out.println("Database connexion failed!");
      JOptionPane.showMessageDialog(
        null,
        "يرجى اعادة محاولة التصحيح لاحقا!  ",
        "خطأ في  تصحيح المعلومات!",JOptionPane.ERROR_MESSAGE);
  }

 
             
                 }
        
            
        });
    
        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";
                int rowIndex = jt.getSelectedRow();
                int colIndex = jt.getSelectedColumn();

                int Id= Integer.parseInt(jt.getModel().getValueAt(rowIndex, 0).toString());
                System.out.println(Id);
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String query = "UPDATE  persons SET property= ? WHERE ID =" + Id;
                    PreparedStatement preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setString(1, "ملغي");
                    preparedStatement.executeUpdate();

                     jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
 
                        public Component getTableCellRendererComponent(JTable table,
                                                      Object value,
                                                      boolean isSelected,
                                                      boolean hasFocus,
                                                      int row,
                                                      int column) {
                                                    
                             Component comp = super.getTableCellRendererComponent(table,
                                    value, isSelected, hasFocus, row, column);
                                     if(row == rowIndex){
                                comp.setForeground(java.awt.Color.RED);
                                comp.setFont(newFont);
                           
                                     }
                     
                            return comp;
                        }
                    });                              
                    conn.close();
   

  }catch(Exception event){
      System.out.println("Database connexion failed!");
      JOptionPane.showMessageDialog(
        null,
        "يرجى اعادة محاولة التصحيح لاحقا!  ",
        "خطأ في  تصحيح المعلومات!",JOptionPane.ERROR_MESSAGE);
  }



}});
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                NewMember member = new NewMember();
                member.initialize();
                model.fireTableDataChanged();
    
            }});
        refresh.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int m = persons.size()-1;
                ArrayList<Person> personList = new ArrayList<>();
                Person person= null;
         
                final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
                final String USERNAME = "root";
                final String PASSWORD = "";
        
             
                    try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                        String sql = "SELECT * FROM persons";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        
      
                        ResultSet resultSet = preparedStatement.executeQuery();
      
                        while(resultSet.next()) {
        
                            person  = new Person();
                            person.id = resultSet.getInt("id");
                            person.id_national = resultSet.getInt("id_national");
                            person.namep = resultSet.getString("namep");
                            person.sur_name = resultSet.getString("sur_name");
                            person.nickname = resultSet.getString("nickname");
                            person.birth_date = resultSet.getDate("birth_date").toLocalDate();;
                            person.place_birth = resultSet.getString("place_birth");
                            person.father_name = resultSet.getString("father_name");
                            person.mother_name = resultSet.getString("mother_name");
                            person.property = resultSet.getString("property");
                            personList.add(person);

      
                        }
                        ArrayList<Person> persons = personList;
                        DefaultTableModel model = (DefaultTableModel) jt.getModel();
                        int rows = model.getRowCount();
                        int i=  personList.size()-1;
                        System.out.println(rows);
                        System.out.println(i);
                       if(i > m && rows== i){
                       

                         
                         model.addRow(new Object[] { 
                             persons.get(i).getId(),
                             persons.get(i).getIdn(),
                             persons.get(i).getName(),
                             persons.get(i).getSurname(),
                             persons.get(i).getNickname(),
                             persons.get(i).getBirthdate(),
                             persons.get(i).getPlacebirth(),
                             persons.get(i).getFathername(),
                             persons.get(i).getMothername(),
                             persons.get(i).getProperty()
                 
                            });
                        
                            JOptionPane.showMessageDialog(null,
                            "تمت عملية التحديث بنجاح!","Alert",JOptionPane.WARNING_MESSAGE);   
                         }else{

                            JOptionPane.showMessageDialog(
                                null,
                                "يرجى اضافة معلومات جديدة لاجراء عملية التحديث.  ",
                                " لا توجد معلومات جديدة لتحديثها!",JOptionPane.ERROR_MESSAGE);
                          
                         }
                        preparedStatement.close();
                        conn.close();
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    
                 
            }});

 
            
    }
  
    

    private void setForeground(Color red) {
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        // TODO Auto-generated method stub
       
 
    }
   

    
}
