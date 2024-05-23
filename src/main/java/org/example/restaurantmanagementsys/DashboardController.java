package org.example.restaurantmanagementsys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.chart.BarChart;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import javax.xml.transform.Result;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.*;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class DashboardController implements Initializable {
    @FXML
    private Button availableFD_addBtn;

    @FXML
    private Button availableFD_btn;

    @FXML
    private Button availableFD_clearBtn;

    @FXML
    private TableView<categories> availableFD_tableView;


    @FXML
    private TableColumn< categories,String > availableFD_col_price;

    @FXML
    private TableColumn< categories,String > availableFD_col_productName;

    @FXML
    private TableColumn<categories,String> availableFD_col_productid;

    @FXML
    private TableColumn<categories,String> availableFD_col_status;

    @FXML
    private TableColumn<categories,String> availableFD_col_type;

    @FXML
    private Button availableFD_deleteBtn;

    @FXML
    private AnchorPane availableFD_form;

    @FXML
    private TextField availableFD_productID;

    @FXML
    private TextField availableFD_productName;

    @FXML
    private TextField availableFD_productPrice;

    @FXML
    private ComboBox<?> availableFD_productStatus;

    @FXML
    private ComboBox<?> availableFD_productType;

    @FXML
    private TextField availableFD_search;

    @FXML
    private Button availableFD_updateBtn;

    @FXML
    private Button close;


    @FXML
    private Label dashboard_NC;

    @FXML
    private BarChart<String, Number> dashboard_NOCChart;

    @FXML
    private AreaChart<String, Double> dashboard_ICChart;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_Tincome;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button order_addBtn;

    @FXML
    private TextField order_amount;
    @FXML
    private Label order_balance;

    @FXML
    private Button order_btn;

    @FXML
    private TableColumn<product, String> order_col_price;

    @FXML
    private TableColumn<product, String> order_col_productID;

    @FXML
    private TableColumn<product, String> order_col_productName;

    @FXML
    private TableColumn<product, String> order_col_quantity;

    @FXML
    private TableColumn<product, String> order_col_type;

    @FXML
    private AnchorPane order_form;

    @FXML
    private Button order_payBtn;

    @FXML
    private ComboBox<String> order_productID;

    @FXML
    private ComboBox<String> order_productName;

    @FXML
    private Spinner<Integer> order_quantity;

    @FXML
    private Button order_receiptBtn;

    @FXML
    private Button order_removeBtn;

    @FXML
    private TableView<product> order_tableView;

    @FXML
    private Label order_total;

    @FXML
    private Label username;

    @FXML
    void availableFDSearch(KeyEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private Result result;



//    BEHAVIORS

    public void dashboardNC() {
        String sql = "SELECT COUNT(id) AS count_id FROM product_info";

        int nc = 0;

        Connection connect = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            // Assuming database is a class that has a method to connect to the database
            connect = database.connectDb();
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                nc = result.getInt("count_id");
            }

            dashboard_NC.setText(String.valueOf(nc));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void dashboardTI() {

        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) AS sum_total FROM product_info WHERE date = '" + sqlDate + "'";

        Connection connect = null;
        Statement statement = null;
        ResultSet result = null;
        double ti = 0;

        try {
            connect = database.connectDb();
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("sum_total");
            }

            dashboard_TI.setText("$" + String.valueOf(ti));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void dashboardTIncome() {
        String sql = "SELECT SUM(total) AS sum_total FROM product_info";

        Connection connect = null;
        Statement statement = null;
        ResultSet result = null;
        double ti = 0;

        try {
            connect = database.connectDb();
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("sum_total");
            }
            dashboard_Tincome.setText("$" + String.valueOf(ti));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources to prevent memory leaks
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public void Dashboard_NOCChart() {
        // Membersihkan data dari chart
        dashboard_NOCChart.getData().clear();

        // Query SQL untuk mendapatkan data yang diperlukan
        String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY date DESC LIMIT 5";

        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            // Menghubungkan ke database
            connect = database.connectDb();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            // Membuat Series untuk BarChart
            XYChart.Series<String, Number> series = new XYChart.Series<>();

            // Menambahkan data ke dalam series
            while (result.next()) {
                String date = result.getString(1);
                int count = result.getInt(2);

                // Pastikan data tidak null sebelum menambahkannya ke series
                if (date != null && !date.isEmpty()) {
                    series.getData().add(new XYChart.Data<>(date, count));
                }
            }

            // Menambahkan series ke chart jika tidak kosong
            if (!series.getData().isEmpty()) {
                dashboard_NOCChart.getData().add(series);
            } else {
                System.out.println("No data available to display in the chart.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Menutup resources
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Setter untuk dashboard_NOCChart
    public void setDashboard_NOCChart(BarChart<String, Number> dashboard_NOCChart) {
        this.dashboard_NOCChart = dashboard_NOCChart;
    }


    public void Dashboard_ICC() {
        dashboard_ICChart.getData().clear();

        // Memperbaiki query SQL
        String sql = "SELECT date, SUM(total) FROM product_info GROUP BY date ORDER BY date ASC LIMIT 7";

        Connection connect = null;
        PreparedStatement prepare = null;
        ResultSet result = null;

        try {
            connect = database.connectDb();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            // Menggunakan Series<String, Double> untuk AreaChart<String, Double>
            XYChart.Series<String, Double> series = new XYChart.Series<>();

            while (result.next()) {
                String date = result.getString(1);
                double total = result.getDouble(2);

                // Pastikan data tidak null sebelum menambahkannya ke series
                if (date != null && !date.isEmpty()) {
                    series.getData().add(new XYChart.Data<>(date, total));
                }
            }

            // Tambahkan series ke chart jika tidak kosong
            if (!series.getData().isEmpty()) {
                dashboard_ICChart.getData().add(series);
            } else {
                System.out.println("No data available to display in the chart.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Menutup resources
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void availableFDAdd() {
        String sql = "INSERT INTO category (product_id, product_name, type, price, status) VALUES (?, ?, ?, ?, ?)";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableFD_productID.getText());
            prepare.setString(2, availableFD_productName.getText());
            prepare.setString(3, (String) availableFD_productType.getSelectionModel().getSelectedItem());
            prepare.setString(4, availableFD_productPrice.getText());
            prepare.setString(5, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());

            Alert alert;

            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_productType.getSelectionModel().getSelectedItem() == null
                    || availableFD_productPrice.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();

            } else {
                String checkData = "SELECT product_id FROM category WHERE product_id = ?";

                prepare = connect.prepareStatement(checkData);
                prepare.setString(1, availableFD_productID.getText());
                ResultSet result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + availableFD_productID.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, availableFD_productID.getText());
                    prepare.setString(2, availableFD_productName.getText());
                    prepare.setString(3, (String) availableFD_productType.getSelectionModel().getSelectedItem());
                    prepare.setString(4, availableFD_productPrice.getText());
                    prepare.setString(5, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
//                  SHOW DATA
                    availableFDShowData();
//                  CLEAR THE FIELDS
                    availableFDClear();
                }

                result.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void availableFDUpdate(){

        String sql = "UPDATE category SET product_name = '"
                +availableFD_productName.getText()+"', type = '"
                +availableFD_productType.getSelectionModel().getSelectedItem()+"',price = '"
                +availableFD_productPrice.getText()+"', status = '"
                +availableFD_productStatus.getSelectionModel().getSelectedItem()
                +"' WHERE product_id = '"+availableFD_productID.getText()+"'";

        connect = database.connectDb();

        try{

            Alert alert;

            if(availableFD_productID.getText() .isEmpty()
                        ||availableFD_productName.getText().isEmpty()
                        ||availableFD_productType.getSelectionModel().getSelectedItem() == null
                        ||availableFD_productPrice.getText().isEmpty()
                        ||availableFD_productStatus.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill the blank fields");
                alert.showAndWait();
            }else{

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to UPDATE Product ID: "
                        +availableFD_productID.getText() + "?");


                Optional<ButtonType> option =alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Update!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

//                    SHOW DATA
                    availableFDShowData();
//                  CLEAR THE FIELDS
                    availableFDClear();

                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }

//                statement = connect.createStatement();

            }


        }catch (Exception e) {e.printStackTrace();}


    }

    public void availableFDDelete(){

        String sql = "DELETE FROM category WHERE product_id = '"
                +availableFD_productID.getText()+"'";
        connect = database.connectDb();

        try{
            Alert alert;

            if(availableFD_productID.getText() .isEmpty()
                    ||availableFD_productName.getText().isEmpty()
                    ||availableFD_productType.getSelectionModel().getSelectedItem() == null
                    ||availableFD_productPrice.getText().isEmpty()
                    ||availableFD_productStatus.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill the blank fields");
                alert.showAndWait();
            }else{

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to DELETE Product ID: "
                        +availableFD_productID.getText() + "?");


                Optional<ButtonType> option =alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

//                    SHOW DATA
                    availableFDShowData();
//                  CLEAR THE FIELDS
                    availableFDClear();

                }else{
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            }

        }catch (Exception e) {e.printStackTrace();}
    }

    public void availableFDClear(){
        availableFD_productID.setText("");
        availableFD_productName.setText("");
        availableFD_productType.getSelectionModel().clearSelection();
        availableFD_productPrice.setText("");
        availableFD_productStatus.getSelectionModel().clearSelection();
    }


    public ObservableList<categories> availableFDListData() {
        ObservableList<categories> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM category";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                categories cat = new categories(
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getDouble("price"),
                        result.getString("status")
                );

                listData.add(cat);
            }

            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listData;
    }

    public void availableFDSearch(){
        FilteredList<categories> filter = new FilteredList<>(availableFDList, e -> true);

        availableFD_search.textProperty().addListener((observabl, newValue, oldValue) -> {
            filter.setPredicate(predicateCategories ->{

                if(newValue.isEmpty() || newValue == null){
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if(predicateCategories.getProductId().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCategories.getName().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCategories.getType().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateCategories.getPrice().toString().contains(searchKey)){
                    return true;
                } else if (predicateCategories.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                }else{
                return false;}
            });
        });

        SortedList<categories> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(availableFD_tableView.comparatorProperty());
        availableFD_tableView.setItems(sortList);

    }


private ObservableList<categories> availableFDList;
public void availableFDShowData(){
    availableFDList = availableFDListData();

    availableFD_col_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
    availableFD_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
    availableFD_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
    availableFD_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    availableFD_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

    availableFD_tableView.setItems(availableFDList);
}

public void availableFDSelect(){
    categories catData = availableFD_tableView.getSelectionModel().getSelectedItem();

    int num = availableFD_tableView.getSelectionModel().getSelectedIndex();

    if ((num - 1) < -1) {
        return;
    }
    availableFD_productID.setText(catData.getProductId());
    availableFD_productName.setText(catData.getName());
    availableFD_productPrice.setText(String.valueOf(catData.getPrice()));


}

//    AVAILABLE FOODS/DRINKS

    private String[] categories = {"Meals", "Drinks"};
    public void availableFDType(){
        List<String> ListCat = new ArrayList<>();

        for(String data: categories){
            ListCat.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(ListCat);
        availableFD_productType.setItems(listData);

    }

    private String[] status = {"Available", "Not Available"};
    public void availableFDStatus(){
        List<String> listStatus = new ArrayList<>();

        for(String data: status){
            listStatus.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availableFD_productStatus.setItems(listData);
    }

    public void orderAdd() {
        orderCustomerId();
        orderTotal();

        String sql = "INSERT INTO product" +
                " (customer_id, product_id, product_name, type, price, quantity, date)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        java.sql.Date sqlDate = null;

        try (Connection connect = database.connectDb()) {
            String orderType = "";
            double orderPrice = 0;

            // Prepare the statement to check product type and price from category table
            String checkData = "SELECT type, price FROM category WHERE product_id = ?";
            try (PreparedStatement checkStmt = connect.prepareStatement(checkData)) {
                checkStmt.setString(1, (String) order_productID.getSelectionModel().getSelectedItem());
                try (ResultSet result = checkStmt.executeQuery()) {
                    if (result.next()) {
                        orderType = result.getString("type");
                        orderPrice = result.getDouble("price");
                    } else {
                        System.out.println("No data found for product_id: " + order_productID.getSelectionModel().getSelectedItem());
                        return;
                    }
                }
            }

            // Convert java.util.Date to java.sql.Date
            java.util.Date date = new java.util.Date();
            sqlDate = new java.sql.Date(date.getTime());

            // Prepare the statement to insert the new order into the product table
            try (PreparedStatement prepare = connect.prepareStatement(sql)) {
                prepare.setInt(1, customerId);
                prepare.setString(2, (String) order_productID.getSelectionModel().getSelectedItem());
                prepare.setString(3, (String) order_productName.getSelectionModel().getSelectedItem());
                prepare.setString(4, orderType);

                double  totalPrice = orderPrice * qty;

                prepare.setDouble(5, totalPrice);
                prepare.setInt(6, qty);
                prepare.setDate(7, sqlDate);

                int rowsInserted = prepare.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new product was inserted successfully!");
                } else {
                    System.out.println("Product insertion failed.");
                }
            }
            orderDisplayTotal();
            orderDisplayData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void orderPay() {
        orderCustomerId();
        orderTotal();

        // Mendapatkan tanggal saat ini
        Date currentDate = Date.valueOf(LocalDate.now());

        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES (?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            // Mengecek apakah amount yang diinput adalah valid
            if (order_amount.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Amount cannot be empty.");
                alert.showAndWait();
                return;
            }

            double amount = Double.parseDouble(order_amount.getText());

            // Mengecek jika balance 0 tetapi total tidak 0 dan amount yang diinput tidak sama dengan total
            if (balance == 0 && totalP != 0 && amount != totalP) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid input: Amount must match the total when balance is 0.");
                alert.showAndWait();
            } else if (balance != 0 && totalP != 0 && amount < totalP) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid: Amount must be equal to or greater than total.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, customerId);
                    prepare.setDouble(2, totalP);
                    prepare.setDate(3, currentDate);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                    order_total.setText("$0.0");
                    order_balance.setText("$0.0");
                    order_amount.setText("");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid amount format.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private double totalP = 0;

    public void orderTotal() {
        orderCustomerId();

        String sql = "SELECT SUM(price) AS total_price FROM product WHERE customer_id = ?";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setInt(1, customerId);
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    totalP = result.getDouble("total_price");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private double amount;
    private double balance;
    public void orderAmount(){
        orderTotal();

        Alert alert;

        if(order_amount.getText().isEmpty() || order_amount.getText() == null
                || order_amount.getText() == ""){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Type the amount!");
            alert.showAndWait();
        }else{
            amount = Double.parseDouble(order_amount.getText());

            if(amount < totalP){
                order_amount.setText("");
            }else{
                balance = (amount - totalP);
                order_balance.setText("$"+String.valueOf(balance));
            }
        }

    }

    public void orderDisplayTotal(){
        orderTotal();
        order_total.setText("$"+String.valueOf(totalP));

    }



    public ObservableList<product> orderListData() {
        orderCustomerId();
        ObservableList<product> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product WHERE customer_id = ?";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setInt(1, customerId);
            try (ResultSet result = prepare.executeQuery()) {

                while (result.next()) {
                    product prod = new product(result.getInt("id"),
                            result.getString("product_id"),
                            result.getString("product_name"),
                            result.getString("type"),
                            result.getDouble("price"),
                            result.getInt("quantity"));

                    listData.add(prod);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }
//    RECEIPTS
public void orderReceipt() {
//    orderSpinner();
//    orderCustomerId();
//    orderProductId();
    HashMap<String, Object> hash = new HashMap<>();
    hash.put("customer_id", customerId);  // Pastikan ini adalah nama parameter yang benar

    try {
        Alert alert;
        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            InputStream reportStream = getClass().getResourceAsStream("/reports/reportFIX.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("File laporan Jasper tidak ditemukan di classpath: /reports/reportFIX.jrxml");
            }

            JasperDesign jDesign = JRXmlLoader.load(reportStream);
            JasperReport jReport = JasperCompileManager.compileReport(jDesign);
            JasperPrint jPrint = JasperFillManager.fillReport(jReport, hash, connect);

            JasperViewer.viewReport(jPrint, false);
        }
    } catch (FileNotFoundException e) {
        System.err.println("File laporan tidak ditemukan: " + e.getMessage());
    } catch (JRException e) {
        System.err.println("Kesalahan JasperReports: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void orderRemove(){

        String sql = "DELETE FROM product WHERE id = " + item;

        connect = database.connectDb();

        try{
            Alert alert;

            if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == ""){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid Select the Item First");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to remove Item : " + item +"?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get(). equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Removed!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();

                    order_amount.setText("");
                    order_balance.setText("$0.0");

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }
        }catch (Exception e ) {e.printStackTrace();}

    }

    private int  item;
    public void orderSelectData(){
        product prod = order_tableView.getSelectionModel().getSelectedItem();
        int num = order_tableView.getSelectionModel().getSelectedIndex();


        if((num -1 ) < -1) return;

        item = prod.getId();
    }


    private ObservableList<product> orderData;
    public void orderDisplayData(){
        orderData = orderListData();

        order_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        order_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        order_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        order_tableView.setItems(orderData);
    }


    private int customerId;
    public void orderCustomerId() {
        String sql = "SELECT customer_id FROM product";

        try (Connection connect = database.connectDb();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            while (result.next()) {
                customerId = result.getInt("customer_id");
            }

            String checkData = "SELECT customer_id FROM product_info";
            try (Statement statement = connect.createStatement();
                 ResultSet resultCheck = statement.executeQuery(checkData)) {

                int customerInfoId = 0;

                while (resultCheck.next()) {
                    customerInfoId = resultCheck.getInt("customer_id");
                }

                if (customerId == 0) {
                    customerId += 1;
                } else if (customerId == customerInfoId) {
                    customerId += 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void orderProductId() {
        String sql = "SELECT product_id FROM category WHERE status = 'Available'";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();

            ObservableList<String> listData = FXCollections.observableArrayList();

            while (resultSet.next()) {
                listData.add(resultSet.getString("product_id"));
            }

            order_productID.setItems(listData);

            orderProductName();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void orderProductName(){
        String sql = "SELECT product_name FROM category WHERE product_id = ?";

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, order_productID.getValue()); // Mengambil product_id dari ComboBox

            ResultSet result = prepare.executeQuery();

            ObservableList<String> listData = FXCollections.observableArrayList();

            while (result.next()){
                String productName = result.getString("product_name");
                listData.add(productName);
            }

            order_productName.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SpinnerValueFactory<Integer>spinner;

    public void orderSpinner(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,50,0);

        order_quantity.setValueFactory(spinner);

    }

    private int qty;
    public void orderQuantity(){
        qty = order_quantity.getValue();
        System.out.println(qty);
    }


    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            availableFD_form.setVisible(false);
            order_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #283048;-fx-text-fill: #fff;  -fx-border-width: 0px;");
            availableFD_btn.setStyle("-fx-background-color: transparent;-fx-border-width: 1px;-fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent;-fx-border-width: 1px;-fx-text-fill: #000;");

            dashboardNC();
            dashboardTI();
            dashboardTIncome();
            Dashboard_NOCChart();
            Dashboard_ICC();

        } else if (event.getSource() == availableFD_btn) {
            dashboard_form.setVisible(false);
            availableFD_form.setVisible(true);
            order_form.setVisible(false);

            availableFD_btn.setStyle("-fx-background-color: #283048;-fx-text-fill: #fff;  -fx-border-width: 0px;");
            dashboard_btn.setStyle("-fx-background-color: transparent;-fx-border-width: 1px;-fx-text-fill: #000;");
            order_btn.setStyle("-fx-background-color: transparent;-fx-border-width: 1px;-fx-text-fill: #000;");

            availableFDShowData();
            availableFDSearch();

        } else if (event.getSource() == order_btn) {
            dashboard_form.setVisible(false);
            availableFD_form.setVisible(false);
            order_form.setVisible(true);

            order_btn.setStyle("-fx-background-color: #283048;-fx-text-fill: #fff;  -fx-border-width: 0px;");
            availableFD_btn.setStyle("-fx-background-color: transparent;-fx-border-width: 1px;-fx-text-fill: #000;");
            dashboard_btn.setStyle("-fx-background-color: transparent;-fx-border-width: 1px;-fx-text-fill: #000;");

            orderProductId();
            orderProductName();
            orderSpinner();
            orderDisplayData();
            orderDisplayTotal();
        }
    }

    private double x = 0;
    private double y = 0;
    public void logout(){
        try{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are yout sure want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) ->{
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() -y);

                    stage.setOpacity(.8f);
                });

                root.setOnMouseReleased((MouseEvent event) ->{
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){e.printStackTrace();}
    }
    public void displayUsername(){
        String user = data.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);
        username.setText(user);
    }
    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dashboardNC();
        dashboardTI();
        dashboardTIncome();
        Dashboard_NOCChart();
        Dashboard_ICC();

        displayUsername();
        availableFDStatus();
        availableFDType();

        availableFDShowData();

        availableFDSearch();

        orderProductId();
        orderProductName();
        orderDisplayData();
        orderDisplayTotal();


    }
}
