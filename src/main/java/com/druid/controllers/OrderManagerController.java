package com.druid.controllers;

import com.druid.enums.OrderStatus;
import com.druid.models.Order;
import com.druid.services.OrderService;
import com.druid.utils.DBConnection;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderManagerController implements Initializable {

  OrderService ors = new OrderService();
  List<Order> orders = ors.getOrders();
  Order or = new Order();
  ObservableList<Order> orderModelSearch = FXCollections.observableArrayList();
  Connection con = DBConnection.getInstance().getConnection();
  @FXML private TextField tfsearch;
  @FXML private TableView<Order> Ordertable;
  @FXML private TableColumn<Order, Integer> tab_id;
  @FXML private TableColumn<Order, Integer> tab_offer_ref;
  @FXML private TableColumn<Order, Integer> tab_plan;
  @FXML private TableColumn<Order, Float> tab_total;
  @FXML private TableColumn<Order, Timestamp> order_date;
  @FXML private TableColumn<Order, OrderStatus> status;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    loadTable();
  }

  public void loadTable() {
    String query = "SELECT * FROM `order`";

    try {
      Statement stmt = con.createStatement();
      ResultSet result = stmt.executeQuery(query);
      while (result.next()) {

        Integer querryID = result.getInt("id");
        Integer querryOfferID = result.getInt("offer_id");
        Integer querryPlan = result.getInt("plan");
        Float querryTotal = result.getFloat("total");
        Timestamp querryOrderdate = result.getTimestamp("orderdate");
        OrderStatus querryStatus = OrderStatus.fromString(result.getString("status"));

        orderModelSearch.add(
            new Order(
                querryID, querryOfferID, querryPlan, querryTotal, querryOrderdate, querryStatus));
      }

      tab_id.setCellValueFactory(new PropertyValueFactory<>("id"));
      tab_offer_ref.setCellValueFactory(new PropertyValueFactory<>("offerId"));
      tab_plan.setCellValueFactory(new PropertyValueFactory<>("plan"));
      tab_total.setCellValueFactory(new PropertyValueFactory<>("total"));
      order_date.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
      status.setCellValueFactory(new PropertyValueFactory<>("status"));

      Ordertable.setItems(orderModelSearch);

      FilteredList<Order> filteredData = new FilteredList<>(orderModelSearch, b -> true);
      tfsearch
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredData.setPredicate(
                    or -> {
                      if (newValue.isEmpty() || newValue == null) {
                        return true;
                      }
                      String searchKeyword = newValue.toLowerCase();
                      if (or.getStatus().toString().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                      } else if (or.getOrderDate().toString().toLowerCase().indexOf(searchKeyword)
                          > -1) {
                        return true;
                      } else if (String.valueOf(or.getPlan()).toLowerCase().indexOf(searchKeyword)
                          > -1) {
                        return true;
                      } else if (String.valueOf(or.getTotal()).toLowerCase().indexOf(searchKeyword)
                          > -1) {
                        return true;
                      } else if (String.valueOf(or.getId()).toLowerCase().indexOf(searchKeyword)
                          > -1) {
                        return true;
                      } else if (String.valueOf(or.getofferId())
                              .toLowerCase()
                              .indexOf(searchKeyword)
                          > -1) {
                        return true;
                      } else return false;
                    });
              });
      SortedList<Order> sortedData = new SortedList<>(filteredData);
      sortedData.comparatorProperty().bind(Ordertable.comparatorProperty());
      Ordertable.setItems(sortedData);

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  void btnmarkCanceled(ActionEvent event) {
    Order o1 = Ordertable.getSelectionModel().getSelectedItem();
    ors.updateOrderStatus(OrderStatus.CANCELED, o1.getId());
    Ordertable.refresh();
  }

  @FXML
  void btnmarkCompleted(ActionEvent event) {
    Order o1 = Ordertable.getSelectionModel().getSelectedItem();
    ors.updateOrderStatus(OrderStatus.COMPLETED, o1.getId());
    loadTable();
  }
}
