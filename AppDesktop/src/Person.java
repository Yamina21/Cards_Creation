import java.sql.Date;
import java.time.LocalDate;

public class Person {

        public int id;
        public int id_national;
        public String namep;
        public String sur_name;
        public String nickname;
        public LocalDate birth_date;
        public String place_birth;
        public String father_name;
        public String mother_name;
        public String property;


        public int getId() {
            return id;
        }
        public int getIdn() {
            return id_national;
        }
   
        public String getName() {
            return namep;
        }
  
        public String getSurname() {
            return sur_name;
        }
        public String getNickname() {
            return nickname;
                }
        public LocalDate  getBirthdate() {
            return birth_date;
        }
       
        public String  getPlacebirth() {
            return place_birth;
        }
        public String  getFathername() {
            return father_name;
        }
        public String  getMothername() {
            return mother_name;
        }
        public String  getProperty() {
            return property;
        }
    
    
    
}
