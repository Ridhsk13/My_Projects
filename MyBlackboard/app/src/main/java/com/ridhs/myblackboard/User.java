package com.ridhs.myblackboard;

/**
 * Created by RIDHS on 4/14/2017.
 */

public class User {

    public String user_name;
    public String user_email;
    public String user_contact_no;
    public String user_type;
    public UserAddress user_address;

    public User() {
    }

    public User(String user_name, String user_email, String user_contact_no, String user_type/*, UserAddress user_address*/) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_contact_no = user_contact_no;
        this.user_type = user_type;
        //this.user_address = user_address;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_contact_no() {
        return user_contact_no;
    }

    public void setUser_contact_no(String user_contact_no) {
        this.user_contact_no = user_contact_no;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public UserAddress getUser_address() {
        return user_address;
    }

    public void setUser_address(UserAddress user_address) {
        this.user_address = user_address;
    }

    public static class UserAddress {
        public String address_line_1;
        public String address_line_2;
        public String city;
        public String pincode;
        public String state;

        public UserAddress() {
        }

        public UserAddress(String address_line_1, String address_line_2, String city, String pincode, String state) {
            this.address_line_1 = address_line_1;
            this.address_line_2 = address_line_2;
            this.city = city;
            this.pincode = pincode;
            this.state = state;
        }

        public String getAddress_line_1() {
            return address_line_1;
        }

        public void setAddress_line_1(String address_line_1) {
            this.address_line_1 = address_line_1;
        }

        public String getAddress_line_2() {
            return address_line_2;
        }

        public void setAddress_line_2(String address_line_2) {
            this.address_line_2 = address_line_2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }


}
