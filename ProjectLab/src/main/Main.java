package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Vector;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javafx.stage.Window;
import model.CartItem;
import model.User;
import util.Connect;

public class Main extends Application {
	//dummy user
	User existingUser = new User("CU001", "DummyName", "DummyPass", "Customer", "DummyAddress", "0812345678", "Male");
	Connect connect = Connect.getInstance();
	Connection connection = connect.getConnection();
	Stage window;
	
	MenuBar mbCS;
	Menu menuHomeCS,menuCartCS,menuAccountCS;
	MenuItem miHomePage,miMyCart,miPurchaseHistory,miLogout;
	
	
	//Cart 
	Stage popUpStage;
	Scene popUpScene;
	Scene CartScene;
	Label areYouSureLblC, titlePopUpLblC;
	HBox hbpopupC;
	VBox vbpopupC;
	Button yesBtnC,noBtnC;
	
	
	BorderPane bpC,bp2C;
	GridPane gpC;
	
	Label titleLblC;
	Label welcomeLblC;
	Label selectLblC;
	Label priceLblC;
	Label pNameLblC;
	Label quantityLblC;
	Label totalPriceLblC;
	Label OrderInfoLblC,userNameLblC,phoneNumberLblC,addressLblC;
	Label spinnerTotalLblC;
	Label noItemLblC,considerLblC;
	Text productDescC;
	TextFlow tfdescC;
	ArrayList<CartItem> ALCartItem=new ArrayList<>();
	ListView<String> cartLV;
	ObservableList<String> cartList;
	
	HBox  hb1C,hb2C,hb3C,hb4C,hb5C;
	Spinner<Integer> quantitySpinner;
	Button updateCartBtnC,removeCartBtnC, makePurchaseBtnC;
	
	private void menuBarCS() {
		//initiate
		mbCS = new MenuBar();
		menuHomeCS = new Menu("Home");
		menuCartCS= new Menu("Cart");
		menuAccountCS= new Menu("Account");
		miHomePage= new MenuItem("Home Page");
		miMyCart= new MenuItem("My Cart");
		miPurchaseHistory= new MenuItem("Purchase History");
		miLogout= new MenuItem("Log Out");
		//set
		menuHomeCS.getItems().setAll(miHomePage);
		menuCartCS.getItems().setAll(miMyCart);
		menuAccountCS.getItems().setAll(miLogout,miPurchaseHistory);
		mbCS.getMenus().addAll(menuHomeCS,menuCartCS,menuAccountCS);
	}
	
	private void cart() {
		getCartData();
		bpC= new BorderPane();	
		bp2C= new BorderPane();	
		gpC = new GridPane();
		
		
		
		quantityLblC = new Label();
		pNameLblC = new Label();
		titleLblC = new Label(existingUser.getUserName()+"'s Cart");
		welcomeLblC = new Label("Welcome, "+existingUser.getUserName());
		productDescC = new Text();
		priceLblC = new Label();
		spinnerTotalLblC = new Label();
		selectLblC = new Label("Select a product to add and remove");
		totalPriceLblC = new Label("Total : Rp. "+getTotalPriceCart());
		OrderInfoLblC = new Label("Order Information");
		userNameLblC= new Label("Username : "+existingUser.getUserName());
		phoneNumberLblC = new Label("Phone Number : "+existingUser.getPhone_num());
		addressLblC=new Label("Address : "+existingUser.getAddress());
		noItemLblC = new Label("No Item In Cart");
		considerLblC = new Label("Consider adding one!");
		
		updateCartBtnC = new Button("Update Cart");
		removeCartBtnC = new Button("Remove From Cart");
		makePurchaseBtnC = new Button("Make Purchase");
		
		cartList = FXCollections.observableArrayList();
		
		
		generateObservableList();
		
		cartLV = new ListView<String>(cartList);
		
		hb1C = new HBox(welcomeLblC);
		hb2C = new HBox(selectLblC);
		hb3C = new HBox();
		hb4C = new HBox();
		hb5C = new HBox();
		if(cartList.isEmpty()) {
			hb1C.getChildren().setAll(noItemLblC);
			hb2C.getChildren().setAll(considerLblC);
    		hb3C.getChildren().clear();
    		hb4C.getChildren().clear();
    		hb5C.getChildren().clear();
		 }
		
		//layout
		tfdescC= new TextFlow(productDescC);
		tfdescC.setPrefWidth(400);
		cartLV.setPrefSize(300, 170);
		tfdescC.setPrefHeight(65);
		//Pane Layout
		gpC.add(titleLblC, 0, 0);
		gpC.add(cartLV, 0, 1);
		gpC.add(hb1C, 1, 2);
		gpC.add(hb2C, 1, 3);
		gpC.add(hb3C, 1, 4);
		gpC.add(hb4C, 1, 5);
		gpC.add(hb5C, 1, 6);
		gpC.add(totalPriceLblC, 0, 7);
		gpC.add(OrderInfoLblC, 0, 8);
		gpC.add(userNameLblC, 0, 9);
		gpC.add(phoneNumberLblC, 0, 10);
		gpC.add(addressLblC, 0,11);
		gpC.add(makePurchaseBtnC, 0, 12);
		gpC.setRowSpan(cartLV, 6);
		
		bpC.setTop(mbCS);
		bpC.setCenter(gpC);
		
		// pop up
		titlePopUpLblC = new Label("Order Confirmation");
		areYouSureLblC = new Label("Are you sure you want to make purchase");
		yesBtnC = new Button("Yes");
		noBtnC = new Button("No");
		hbpopupC = new HBox(yesBtnC,noBtnC);
		vbpopupC= new VBox(areYouSureLblC,hbpopupC);
		
		bp2C.setTop(titlePopUpLblC);
		bp2C.setCenter(vbpopupC);
		popUpScene = new Scene(bp2C,400,300);
		popUpStage = new Stage();
		popUpStage.setTitle("Order Confirmation");
		popUpStage.setScene(popUpScene);
		
	}
	private void generateObservableList() {
		cartList.clear();
		try {
			for (CartItem c : ALCartItem) {
				String q =c.getQuantity();
				String pname = c.getProductName();
				String p= c.getProductPrice();
				Integer TotalPrice = Integer.parseInt(q) * Integer.parseInt(p);
				String listContent = q+"x "+pname+" (RP."+TotalPrice.toString()+")";
				cartList.add(listContent);
			}
		} catch (Exception e) {
			System.out.println("generate observable list failed:"+e);
		}
	}

	private Integer getTotalPriceCart() {
		Integer total=0;
		for (CartItem c : ALCartItem) {
			Integer price= Integer.parseInt(c.getProductPrice());
			Integer quantity =Integer.parseInt(c.getQuantity());
			Integer subtotal = price*quantity;
			System.out.println(subtotal);
			total +=subtotal;
		}
		return total;
	}
	private void getCartData() {
		String querygetcart="SELECT * FROM cart c\r\n" + 
				"JOIN product p on c.productID=p.productID\r\n" + 
				"WHERE userID='"+existingUser.getUserID()+"';";
		System.out.println(querygetcart);
		connect.rs = connect.execQuery(querygetcart);
		try {
			ALCartItem.clear();
			while(connect.rs.next()) {
				String productName = connect.rs.getString("product_name");
//				Integer q =  connect.rs.getInt("quantity");
//				String quantity = q.toString();
				String quantity= connect.rs.getString("quantity");
				String productID= connect.rs.getString("productID");
				String productPrice= connect.rs.getString("product_price");
				String description = connect.rs.getString("product_des");
				ALCartItem.add(new CartItem(productID, productName, productPrice, description, quantity));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void eventListenerCart() {
		yesBtnC.setOnAction(e->{
			if(cartList.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Failed to make transaction");
				alert.showAndWait();
				popUpStage.close();
				return;
			}
			else {
				System.out.println("Add Transac");
				String newTransId="";
				
				String query = "INSERT INTO `transaction_header` (`transactionID`, `userID`) "
						+ "VALUES ('"+newTransId+"', '"+existingUser.getUserID()+"');";
				System.out.println("Clear Cart");
			}
		});
		noBtnC.setOnAction(e->{
			popUpStage.close();
		});
		makePurchaseBtnC.setOnAction(e->{
			if( !popUpStage.isShowing()) {
				popUpStage.showAndWait();
			}
			else {
				popUpStage.close();
				popUpStage.showAndWait();
			}
		});
		cartLV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			 int selectedID = cartLV.getSelectionModel().getSelectedIndex();
			 if(selectedID<0) {
				 System.out.println("Out of Bound");
			 }
	        System.out.println("Selected Index: " + selectedID);
	        
	        if(selectedID>=0) {
	        	System.out.println(">0");
	        	productDescC.setText(ALCartItem.get(selectedID).getDescription());
		    	priceLblC.setText("Price: Rp."+ALCartItem.get(selectedID).getProductPrice());
		    	pNameLblC.setText(ALCartItem.get(selectedID).getProductName());
		    	spinnerTotalLblC.setText("Total: ");
		    	String q= ALCartItem.get(selectedID).getQuantity();
		    	Integer qty = Integer.parseInt(q);
		    	quantityLblC.setText("Quantity: ");
		    	quantitySpinner= new Spinner<>(-999,999,0);
		    	
		    	hb1C.getChildren().setAll(pNameLblC);
		    	hb2C.getChildren().setAll(tfdescC);
		    	hb3C.getChildren().setAll(priceLblC);
		    	hb4C.getChildren().setAll(quantityLblC,quantitySpinner,spinnerTotalLblC);
		    	hb5C.getChildren().setAll(updateCartBtnC,removeCartBtnC);
		    	
		    	quantitySpinner.setOnMouseClicked(e->{
		    		Integer qspinner = quantitySpinner.getValue();
		    		Integer p = Integer.parseInt(ALCartItem.get(selectedID).getProductPrice());
		    		Integer subtotal = qspinner * p ;
		    		spinnerTotalLblC.setText("Total : "+subtotal);
		    	});
		    	removeCartBtnC.setOnAction(e->{
		    		System.out.println("remove");
	    			String queryDelete="DELETE FROM `cart` "
	    					+ "WHERE `cart`.`productID` = '"+ALCartItem.get(selectedID).getProductID()+"' "
	    					+ "AND `cart`.`userID` = '"+existingUser.getUserID()+"'";
	    			hb1C.getChildren().clear();
	    			hb2C.getChildren().clear();
	    			hb3C.getChildren().clear();
	    			hb4C.getChildren().clear();
	    			hb5C.getChildren().clear();
	    			connect.execUpdate(queryDelete);
	    			getCartData();
	    			generateObservableList();
	    			totalPriceLblC.setText("Total : Rp. "+getTotalPriceCart());
	    			cartLV.refresh();
	    			if(cartList.isEmpty()) {
	    				System.out.println("Cart Is Empty (1)");
	    				hb1C.getChildren().setAll(noItemLblC);
	    				hb2C.getChildren().setAll(considerLblC);
	    				hb3C.getChildren().clear();
	    				hb4C.getChildren().clear();
	    				hb5C.getChildren().clear();
	    			 }
	    			Alert alert = new Alert(AlertType.INFORMATION);
	    			alert.setHeaderText("Deleted From Cart");
					alert.showAndWait();
					
		    	});
		    	
		    	updateCartBtnC.setOnAction(e->{
		    		Integer qspinner = quantitySpinner.getValue();
		    		Integer newQty= qty + qspinner ;
//		    		System.out.println(newQty);
		    		if(qspinner==0) {
		    			Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Not a Valid Amount");
						alert.showAndWait();
		    		}
		    		else if(newQty==0) {
		    			System.out.println("remove(update)");
		    			hb1C.getChildren().clear();
		    			hb2C.getChildren().clear();
		    			hb3C.getChildren().clear();
		    			hb4C.getChildren().clear();
		    			hb5C.getChildren().clear();
		    			String queryDelete="DELETE FROM `cart` "
		    					+ "WHERE `cart`.`productID` = '"+ALCartItem.get(selectedID).getProductID()+"' "
		    					+ "AND `cart`.`userID` = '"+existingUser.getUserID()+"'";
		    			connect.execUpdate(queryDelete);
		    			getCartData();
		    			generateObservableList();
		    			cartLV.refresh();
		    			totalPriceLblC.setText("Total : Rp. "+getTotalPriceCart());
		    			if(cartList.isEmpty()) {
		    				System.out.println("Cart Is Empty (2)");
		    				hb1C.getChildren().setAll(noItemLblC);
		    				hb2C.getChildren().setAll(considerLblC);
		    				hb3C.getChildren().clear();
		    				hb4C.getChildren().clear();
		    				hb5C.getChildren().clear();
		    			}
		    			Alert alert = new Alert(AlertType.INFORMATION);
		    			alert.setHeaderText("Deleted From Cart");
						alert.showAndWait();
		    		}
		    		
		    		else if(newQty<0) {
		    			Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Not a Valid Amount");
						alert.showAndWait();
		    		}
		    		else {
		    			System.out.println("update qty");
		    			Connect connect = Connect.getInstance();
						Connection connection = connect.getConnection();
		    			String query= "UPDATE `cart` "
		    					+ "SET `quantity` = '"+newQty+"' "
		    					+ "WHERE `cart`.`productID` = '"+ALCartItem.get(selectedID).getProductID()+"' "
		    					+ "AND `cart`.`userID` = '"+existingUser.getUserID()+"';";
		    			hb1C.getChildren().clear();
		    			hb2C.getChildren().clear();
		    			hb3C.getChildren().clear();
		    			hb4C.getChildren().clear();
		    			hb5C.getChildren().clear();
		    			connect.execUpdate(query);
		    			getCartData();
		    			generateObservableList();
		    			cartLV.refresh();
		    			totalPriceLblC.setText("Total : Rp. "+getTotalPriceCart());
		    			Alert alert = new Alert(AlertType.INFORMATION);
		    			alert.setHeaderText("Updated Cart");
						alert.showAndWait();
		    		}
		    	});
	        }
	    	
	    	
		});
		

	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		menuBarCS();
		cart();
		eventListenerCart();
		
		CartScene = new Scene(bpC,800,600);
		
		
		primaryStage.setScene(CartScene);
		window.show();
		window.setTitle("Login");
		
	}


	public static void main(String[] args) {
		launch(args);
	}

}

