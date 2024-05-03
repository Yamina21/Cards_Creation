import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatDarculaLaf;

public class LoginForm extends JFrame {


    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    JTextField tfEmail;
    JPasswordField pfPassword;

    public void initialize() {
        /*************** Form Panel ***************/
        JLabel lbLoginForm = new JLabel("تسجيل الدخول", SwingConstants.CENTER);
        
         lbLoginForm.setFont(mainFont);
        lbLoginForm.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  

        JLabel lbEmail = new JLabel("الايمايل");
        lbEmail.setFont(mainFont);
        lbEmail.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbPassword = new JLabel("كلمة السر");
        lbPassword.setFont(mainFont);
        lbPassword.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  


        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  

        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbLoginForm);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        /*************** Buttons Panel ***************/
        JButton btnLogin = new JButton("دخول");
        btnLogin.setFont(mainFont);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());
                 ArrayList<Person> person= getPerson();
                User user = getAuthenticatedUser(email, password);

                if (user != null) {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialize(person);
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        JButton btnCancel = new JButton("الغاء");
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        buttonsPanel.add(btnCancel);

        buttonsPanel.add(btnLogin);



        /*************** Initialise the frame ***************/
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("تسجيل الدخول");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }



    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            preparedStatement.close();
            conn.close();

        }catch(Exception e){
            System.out.println("Database connexion failed!");
        }


        return user;
    }
    private   ArrayList<Person> getPerson() {
        ArrayList<Person> personList = new ArrayList<>();
        Person person= null;
 
        final String DB_URL = "jdbc:mysql://localhost/desktopapp?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

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

            preparedStatement.close();
            conn.close();

        }catch(Exception e){
            System.out.println("Database connexion failed!");
        }
        return personList;


    }
    public static void main(String[] args) {
        System.setProperty("prism.text", "t2k");
        System.setProperty("prism.lcdtext", "false");
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        

        LoginForm loginForm = new LoginForm();
        loginForm.initialize();

    }



    public Font getMainFont() {
        return mainFont;
    }



    public JTextField getTfEmail() {
        return tfEmail;
    }



    public void setTfEmail(JTextField tfEmail) {
        this.tfEmail = tfEmail;
    }



    public JPasswordField getPfPassword() {
        return pfPassword;
    }



    public void setPfPassword(JPasswordField pfPassword) {
        this.pfPassword = pfPassword;
    }

    
   
}

