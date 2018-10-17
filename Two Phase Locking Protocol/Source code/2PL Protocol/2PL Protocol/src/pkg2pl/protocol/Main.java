package pkg2pl.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * CSE 5330 - DBMS Model and Implementation Project-1 Implementing 2PL Protocol
 * using Wait-Die deadlock prevention
 *
 * @author RIDHAM KOTHARI (1001446086)
 * @author YASH SHAH (1001563334)
 */
public class Main {

    private static int timestamp = 0;
    public static Connection conn;

    public static void main(String[] args) throws SQLException {

        conn = Database.getConnection();
        try {
            File file = new File("C:\\Users\\RIDHS\\Desktop\\input7.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\n");

                // System.out.println(line);
                parse(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parse(String operation) throws SQLException {

        char[] array = operation.toCharArray();
        char tranOperation = array[0];
        char tranNum = array[1];
        char dataItem = 0;
        if (tranOperation == 'b') {

            timestamp = 1 + timestamp;
            beginTransaction(String.valueOf(array[1]), timestamp);
        } else {

            String query = "Select count(*) as count from transaction where tranNum = '" + tranNum + "'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                count = rs.getInt("count");
            }
            if (count == 1) {

                String que = "Select state from transaction where tranNum='" + tranNum + "'";
                Statement stmt = conn.createStatement();
                ResultSet resultset = stmt.executeQuery(que);
                String status = null;
                while (resultset.next()) {
                    status = resultset.getString("state");
                }

                //System.out.println(status);
                if (status.equals("active")) {
                    //   System.out.println("T" + tranNum + " is active");
                    if (tranOperation == 'r') {
                        dataItem = array[3];

                        readItem(dataItem, tranNum);
                    } else if (tranOperation == 'w') {
                        dataItem = array[3];
                        //System.out.println("T" + tranNum + " begning write operation");
                        writeItem(dataItem, tranNum);
                    } else if (tranOperation == 'e') {
                        //System.out.println("Transaction " + tranNum + " begning end operation");
                        endTransaction(tranNum);
                    }
                } else if (status == "blocked") {
                    System.out.println("Transaction " + tranNum + " status is blocked");
                    dataItem = array[3];
                    System.out.println("Operation " + tranOperation + tranNum + "(" + dataItem + ")" + "is being added to waitlist of transaction " + tranNum);
                    addOperationToWaitList(tranNum, dataItem, tranOperation);
                } else if (status == "aborted") {
                    System.out.println("Transaction " + tranNum + " is aborted already. So ignor the operation");
                }

            } else {
                System.out.println("Transaction is not being started yet");
            }

        }
    }

    public static void beginTransaction(String tranNum, int timestamp) throws SQLException {

        System.out.println("Request to begin T" + tranNum);
        String SQL = "Insert into transaction (tranNum, timestamp, state) values (?,?,'active')";
        PreparedStatement stmt = conn.prepareStatement(SQL);
        stmt.setString(1, tranNum);
        stmt.setInt(2, timestamp);
        stmt.executeUpdate();
        System.out.println("T" + tranNum + " is started");
    }

    public static void readItem(char dataItem, char tranNum) throws SQLException {
        System.out.println("T" + tranNum + " wants to read " + dataItem);
        System.out.println("T" + tranNum + " requests read lock on " + dataItem);
        String itemlist = null;
        String que2 = "Select listOfItemsLocked  from transaction where tranNum='" + tranNum + "'";
        Statement stmt2 = conn.createStatement();
        ResultSet resultset2 = stmt2.executeQuery(que2);
        while (resultset2.next()) {
            itemlist = resultset2.getString("listOfItemsLocked");
        }

        String que = "Select count(*) as count from locktable where dataItem='" + dataItem + "'";
        Statement stmt = conn.createStatement();
        ResultSet resultset = stmt.executeQuery(que);
        int count = 0;
        while (resultset.next()) {
            count = resultset.getInt("count");
        }
        if (count == 1) {
            String query = "Select * from locktable where dataItem = '" + dataItem + "'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            String lockState = null;
            String tranNumHolding = null;
            while (rs.next()) {
                lockState = rs.getString("State");
                tranNumHolding = rs.getString("tranNumHolding");
            }
            System.out.println(lockState);
            if (lockState.equals("read")) {
                tranNumHolding = tranNumHolding + "$" + tranNum;
                String query2 = "update locktable set tranNumHolding = '" + tranNumHolding + "' where dataItem = '" + dataItem + "'";
                Statement statement2 = conn.createStatement();
                statement2.executeUpdate(query2);
                if (itemlist == null) {
                    itemlist = String.valueOf(dataItem);
                } else {
                    itemlist = itemlist + '$' + dataItem;
                }
                String query3 = "update transaction set listOfItemsLocked = '" + itemlist + "' where tranNum = '" + tranNum + "'";
                Statement statement3 = conn.createStatement();
                statement3.executeUpdate(query3);
                System.out.println("T" + tranNum + "shares read lock on " + dataItem);

            } else if (lockState.equals("write")) {
                waitDie(tranNum, dataItem, 'r');
            }
        } else {
            String SQL = "Insert into locktable (dataItem, State, tranNumHolding) values (?,'read',?)";
            PreparedStatement stmt4 = conn.prepareStatement(SQL);
            stmt4.setString(1, String.valueOf(dataItem));
            stmt4.setString(2, String.valueOf(tranNum));
            stmt4.executeUpdate();
            System.out.println("T" + tranNum + " succesfully read locked " + dataItem);

            if (itemlist == null) {
                itemlist = String.valueOf(dataItem);
            } else {
                itemlist = itemlist + '$' + dataItem;
            }
            String query4 = "update transaction set listOfItemsLocked = '" + itemlist + "' where tranNum = '" + tranNum + "'";
            Statement statement4 = conn.createStatement();
            statement4.executeUpdate(query4);

        }
    }

    public static void writeItem(char dataItem, char tranNum) throws SQLException {
        System.out.println("T" + tranNum + " wants to write " + dataItem);
        System.out.println("T" + tranNum + " requests write lock on " + dataItem);
        String itemlist2 = null;
        String que = "Select listOfItemsLocked  from transaction where tranNum='" + tranNum + "'";
        Statement stmt = conn.createStatement();
        ResultSet resultset = stmt.executeQuery(que);
        while (resultset.next()) {
            itemlist2 = resultset.getString("listOfItemsLocked");
        }

        String que2 = "Select count(*) as count from locktable where dataItem='" + dataItem + "'";
        Statement stmt2 = conn.createStatement();
        ResultSet resrultset2 = stmt2.executeQuery(que2);
        int count = 0;
        while (resrultset2.next()) {
            count = resrultset2.getInt("count");
        }
        if (count == 1) {

            String que4 = "Select tranNumHolding from locktable where dataItem='" + dataItem + "'";
            Statement stmt4 = conn.createStatement();
            ResultSet resultset4 = stmt4.executeQuery(que4);
            String tranNumHolding = null;
            while (resultset4.next()) {
                tranNumHolding = resultset4.getString("tranNumHolding");
            }
            String[] tranNumHoldingArray = tranNumHolding.split("\\$");
            //System.out.println(tranNumHoldingArray.length);
            if (tranNumHoldingArray.length > 1) {
                //System.out.println("Performing wait die for dataItem: " + dataItem + " with transaction: " + tranNum + " for operation write");
                waitDie(tranNum, dataItem, 'w');
            } else {
                String que3 = "Select * from locktable where dataItem='" + dataItem + "'";
                Statement stmt3 = conn.createStatement();
                ResultSet resultset3 = stmt3.executeQuery(que3);
                String lockstate = null;
                while (resultset3.next()) {
                    lockstate = resultset3.getString("State");
                }
                //System.out.println(lockstate + Arrays.toString(tranNumHoldingArray)+ tranNum);
                //System.out.println(String.valueOf("read".equals(lockstate) && Arrays.toString(tranNumHoldingArray).contains(String.valueOf(tranNum))));
                
                if ("read".equals(lockstate) && (Arrays.toString(tranNumHoldingArray).contains(String.valueOf(tranNum)))) {
                    System.out.println("T" + tranNum + " upgrades lock from read -> write on " + dataItem);
                    String query2 = "update locktable set state = 'write' where dataItem = '" + dataItem + "'";
                    Statement statement2 = conn.createStatement();
                    statement2.executeUpdate(query2);
                } else {
                    waitDie(tranNum, dataItem, 'w');
                }
            }

        } else {
            String SQL = "Insert into locktable (dataItem, State, tranNumHolding) values (?,'write',?)";
            PreparedStatement stmt3 = conn.prepareStatement(SQL);
            stmt3.setString(1, String.valueOf(dataItem));
            stmt3.setString(2, String.valueOf(tranNum));
            stmt3.executeUpdate();
            System.out.println("T" + tranNum + " succesfully write locked " + dataItem);

            if (itemlist2 == null) {
                itemlist2 = String.valueOf(dataItem);
            } else {
                itemlist2 = itemlist2 + '$' + dataItem;
            }
            String query3 = "update transaction set listOfItemsLocked = '" + itemlist2 + "' where tranNum = '" + tranNum + "'";

            Statement statement3 = conn.createStatement();
            statement3.executeUpdate(query3);
        }

    }

    public static void endTransaction(char tranNum) throws SQLException {
        System.out.println("T" + tranNum + " begins commit");

        String query2 = "update transaction set state = 'commit' where tranNum = '" + tranNum + "'";
        Statement statement2 = conn.createStatement();
        statement2.executeUpdate(query2);
        releaseLock(tranNum);

        String query3 = "update transaction set listOfItemsLocked = NULL where tranNum = '" + tranNum + "'";
        Statement statement3 = conn.createStatement();
        statement3.executeUpdate(query3);
        System.out.println("T" + tranNum + " ended successfully ");
    }

    public static void waitDie(char tranNum, char dataItem, char opName) throws SQLException {

        String que3 = "Select timestamp from transaction where tranNum='" + tranNum + "'";
        Statement stmt3 = conn.createStatement();
        ResultSet resultset3 = stmt3.executeQuery(que3);
        int requestingTimestamp = 0;
        while (resultset3.next()) {
            requestingTimestamp = resultset3.getInt("timestamp");
        }

        String que = "Select tranNumHolding from locktable where dataItem='" + dataItem + "'";
        Statement stmt = conn.createStatement();
        ResultSet resultset = stmt.executeQuery(que);
        String lockstate = null;
        String tranNumHolding = null;
        while (resultset.next()) {
            tranNumHolding = resultset.getString("tranNumHolding");
        }
        String[] tranNumHoldingArray = tranNumHolding.split("\\$");

        boolean abortFlag = false;

        for (String item : tranNumHoldingArray) {
            String que2 = "Select timestamp from transaction where tranNum='" + item + "'";
            Statement stmt2 = conn.createStatement();
            ResultSet resultset2 = stmt.executeQuery(que2);
            int holdingTimestamp = 0;
            while (resultset2.next()) {
                holdingTimestamp = resultset2.getInt("timestamp");
            }
            // System.out.println(requestingTimestamp);

            //System.out.println(holdingTimestamp);
            if (requestingTimestamp < holdingTimestamp) {
                abortFlag = false;
            } else {
                abortFlag = true;
            }
        }

        if (abortFlag) {
            abortTransaction(tranNum);
        } else {
            System.out.println("T" + tranNum + " will wait");

            String query4 = "update transaction set state = 'blocked' where tranNum ='" + tranNum + "'";
            Statement statement4 = conn.createStatement();
            statement4.executeUpdate(query4);

            String que4 = "Select tranNumWaiting from locktable where dataItem='" + dataItem + "'";
            Statement stmt4 = conn.createStatement();
            ResultSet resultset4 = stmt.executeQuery(que4);
            String itemlist = null;
            while (resultset4.next()) {
                itemlist = resultset4.getString("tranNumWaiting");
            }
            if (itemlist == null) {
                itemlist = Character.toString(tranNum);
            } else {
                itemlist = itemlist + "$" + tranNum;
            }
            String query2 = "update locktable set tranNumWaiting = '" + itemlist + "' where dataItem = '" + dataItem + "'";
            Statement statement2 = conn.createStatement();
            statement2.executeUpdate(query2);

            addOperationToWaitList(tranNum, dataItem, opName);

        }
    }

    public static void addOperationToWaitList(char tranNum, char dataItem, char opName) throws SQLException {

        String operation = Character.toString((char) opName) + tranNum + "(" + dataItem + ")";
        //System.out.println(opName);
        String que = "Select operationsInWaiting from transaction where tranNum='" + tranNum + "'";
        Statement stmt = conn.createStatement();
        ResultSet resultset = stmt.executeQuery(que);
        String operationlist = null;
        while (resultset.next()) {
            operationlist = resultset.getString("operationsInWaiting");
        }
        //System.out.println(operationlist);
        //System.out.println(operation);
        if (operationlist == null) {
            operationlist = operation;
        } else {
            operationlist = operationlist + "$" + operation;
        }
        String query2 = "update transaction set operationsInWaiting = '" + operationlist + "' where tranNum = '" + tranNum + "'";
        Statement statement2 = conn.createStatement();
        statement2.executeUpdate(query2);
        System.out.println("Operation " + operationlist + " added to T" + tranNum);

    }

    public static void releaseLock(char tranNum) throws SQLException {
        System.out.println("T" + tranNum + " is releasing locks");
        String itemlist = null;
        String que = "Select listOfItemsLocked from transaction where tranNum='" + tranNum + "'";
        Statement stmt = conn.createStatement();
        ResultSet resultset = stmt.executeQuery(que);
        while (resultset.next()) {
            itemlist = resultset.getString("listOfItemsLocked");
        }
        if (itemlist != null) {

            String[] splititem = itemlist.split("\\$");
            //return splititem;

            for (String item : splititem) {
                System.out.println("T" + tranNum + " releasing " + item);
                String tranNumlist = null;
                String query = "Select tranNumHolding from locktable where dataItem = '" + item + "'";
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    tranNumlist = rs.getString("tranNumHolding");
                }
                String[] tranNumListArray = null;
                if (tranNumlist != null) {
                    tranNumListArray = tranNumlist.split("\\$");
                    if (tranNumListArray.length > 1) {
                        for (int i = 0; i < tranNumListArray.length; i++) {
                            if (String.valueOf(tranNum).equals(tranNumListArray[i])) {
                                tranNumListArray[i] = null;
                            }
                        }
                        String str= "";
                        for (int i=0; i<tranNumListArray.length; i++){
                            if (tranNumListArray[i] != null){
                                str = str + tranNumListArray[i] + "$";
                            }
                        }
                        String query2 = "update locktable set tranNumHolding = '" + str + "' where dataItem = '" + item + "'";
                        Statement statement2 = conn.createStatement();
                        statement2.executeUpdate(query2);

                    } else {
                        String tranNumWaitinglist = null;
                        String activeTransactionId = null;
                        String operationList = null;
                        String query3 = "Select tranNumWaiting from locktable where dataItem = '" + item + "'";
                        Statement statement3 = conn.createStatement();
                        ResultSet rs3 = statement3.executeQuery(query3);
                        while (rs3.next()) {
                            tranNumWaitinglist = rs3.getString("tranNumWaiting");
                        }
                        String[] tranNumListWaitingArray = null;
                        if (tranNumWaitinglist != null) {
                            tranNumListWaitingArray = tranNumWaitinglist.split("\\$");
                            activeTransactionId = tranNumListWaitingArray[0];
                            String query2 = "update locktable set tranNumholding = '" + activeTransactionId + "' where dataItem = '" + item + "'";

                            Statement statement2 = conn.createStatement();
                            statement2.executeUpdate(query2);
                            String query4 = "update transaction set state = 'active' where tranNum ='" + activeTransactionId + "'";
                            Statement statement4 = conn.createStatement();
                            statement4.executeUpdate(query4);
                            String que2 = "Select operationsInWaiting from transaction where tranNum='" + activeTransactionId + "'";
                            Statement stmt2 = conn.createStatement();
                            ResultSet resultset2 = stmt2.executeQuery(que2);
                            while (resultset2.next()) {
                                operationList = resultset2.getString("operationsInWaiting");
                            }
                            if (operationList != null) {
                                String[] operationListArray = operationList.split("\\$");
                                for (String operation : operationListArray) {
                                    parse(operation);
                                }
                            }
                        } else {
                            String query4 = "delete from locktable where dataItem ='" + item + "'";
                            Statement statement4 = conn.createStatement();
                            statement4.executeUpdate(query4);
                            
                        }
                    }
                }
                System.out.println("T" + tranNum + " Released " + item);
            }
        }
    }

    public static void abortTransaction(char tranNum) throws SQLException {
        System.out.println("T" + tranNum + " begins abort");
        String query2 = "update transaction set state = 'aborted' where tranNum = '" + tranNum + "'";
        Statement statement2 = conn.createStatement();
        statement2.executeUpdate(query2);
        releaseLock(tranNum);
        System.out.println("T" + tranNum + " aborted successfully");

    }
}
