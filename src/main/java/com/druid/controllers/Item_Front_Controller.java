package com.druid.controllers;

import com.druid.enums.EventStatus;
import com.druid.enums.EventType;
import com.druid.models.Event;
import com.druid.models.Review;
import com.druid.models.User;
import com.druid.services.EventService;
import com.druid.services.ReviewService;
import com.druid.utils.ConnectedUser;
import com.druid.utils.Mail;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Item_Front_Controller implements Initializable {

    Event event;
    EventService eventService = new EventService();
    ReviewService reviewService = new ReviewService();

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @FXML
    private Label event_date;

    @FXML
    private Label event_description;

    @FXML
    private Label event_location;

    @FXML
    private Label event_location_label;

    @FXML
    private Label event_name;

    @FXML
    private Label event_price;

    @FXML
    private Label event_status;

    @FXML
    private JFXButton participer;

    @FXML
    private ImageView event_image;

    @FXML
    private Label podcaster_name;

    @FXML
    private Hyperlink hyperlink;
    private ConnectedUser connectedUser = ConnectedUser.getInstance();



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



        if(userParticipating(connectedUser.getUser().getID(),this.event.getId()))
        {
            Event e = eventService.getEvent(this.event.getId());

            if (e.getStatus().equals(EventStatus.FINISHED)) {
                participer.setText("Give Review");
            }else{
                participer.setText("Annuler Participation");
            }
        }

        event_name.setText(this.event.getName());

        if(this.event.isPayant())
        {
            System.out.println(this.event.getPrix());
            event_price.setText(String.valueOf(this.event.getPrix())+" DT");
        }else{
            event_price.setText("Free");
        }

        event_date.setText(this.event.getDate().toString());

        if(this.event.getStatus()== EventStatus.NOT_FINISHED)
        {
            if(this.event.getDate().before(new java.sql.Date(System.currentTimeMillis()))){
                event_status.setText("Not Started");
            }

        }else
        {
            event_status.setText("Finished");
        }

        event_description.setText(this.event.getDescription());

        if(this.event.getType()== EventType.LIVE)
        {
            event_location_label.setText("Online Link");
            event_location.setVisible(false);
            hyperlink.setVisible(true);
            hyperlink.setText(this.event.getLocation());

            hyperlink.setOnAction(event1 -> {
                try {
                    Desktop.getDesktop().browse(new URL(this.event.getLocation()).toURI());
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            });


            // event_location.setText(this.event.getLocation());
        }else
        {
            event_location_label.setText("Address");
            event_location.setVisible(true);
            hyperlink.setVisible(false);
            event_location.setText("Adress : "+this.event.getLocation());
        }

        participer.setId(String.valueOf(this.event.getId()));
        podcaster_name.setText(podcaster_name.getText()+" "+this.event.getUser().getUsername());

        javafx.scene.image.Image image = new Image("http://127.0.0.1:8000/uploads/"+this.event.getImage(),true);
        System.out.println(this.event.getImage());
        event_image.setImage(image);



    }

    @FXML
    void participate(MouseEvent event)
    {
        User u = connectedUser.getUser();
        Event e = eventService.getEvent(this.event.getId());

        if (e.getStatus().equals(EventStatus.FINISHED)) {

            TextInputDialog td = new TextInputDialog("Give your Review");

            // setHeaderText
            td.setHeaderText("Event "+this.event.getName()+" Review");

            td.showAndWait();

            if (td.getResult().equals(""))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Review Cannot Be Empty !", ButtonType.OK);
                alert.showAndWait();

            }else
            {
                Review r = new Review();
                r.setEvent(this.event);
                r.setUser(connectedUser.getUser());
                r.setReview(td.getResult());

                if(reviewService.addReview(r)){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Review Added !", ButtonType.OK);
                    alert.showAndWait();
                }else
                {
                    System.out.println(reviewService.getReviewUser(connectedUser.getUser().getID()));
                    r=reviewService.getReviewUser(connectedUser.getUser().getID());
                    r.setReview(td.getResult());
                    reviewService.updateReview(r);
                }
            }

        }else{
            if(eventService.userParticipation(e, u)){
                participer.setStyle("-fx-background-color: #C70039");
                participer.setText("Annuler Participation");
            }else{
                eventService.userParticipationCancel(e, u);
                participer.setStyle("-fx-background-color: #50C787");
                participer.setText("Participer");
                participer.setOnAction(event1 -> {
                    String qrCodeText = "Event Name : "+e.getName()+" | Podcaster : "+e.getUser().getUsername()+" | Event Date : "+e.getDate().toString()
                            +"| Event Location / Link : "+e.getLocation()+" |";
                   
		String filePath = "qr.png";
		int size = 150;
		String fileType = "png";
		File qrFile = new File(filePath);
                    try {
                        createQRImage(qrFile, qrCodeText, size, fileType);
                    } catch (WriterException ex) {
                        Logger.getLogger(Item_Front_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Item_Front_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    try {
                        Mail.sendAttachement(connectedUser.getUser().getEmail(),"Event "+this.event.getName()+" Ticket",
                                "Thank you for your purchase\n" +
                                        "Your name : "+connectedUser.getUser().getUsername()+"\n" +
                                                "Your Email : "+connectedUser.getUser().getEmail()+"\n" +
                                                        "This is a Confirmation Email for participating in Event : "+this.event.getName()+"\n" +
                                                                "Please Bring and Show this email as a recipient when attending the Event to confirm your Identity\n" +
                                                                "Thank you for your Trust !</p>"+
                                                                "\n",true);
                    } catch (IOException ex) {
                        Logger.getLogger(Item_Front_Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }

    }

    boolean userParticipating(int userid,int eventid)
    {
        User u = connectedUser.getUser();
        Event e = eventService.getEvent(this.event.getId());
        if(!eventService.userParticipation(e, u)) {
            return true;
        }
        return false;

    }
    
    private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		// Make the BufferedImage that are to hold the QRCode
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		// Paint and save the image using the ByteMatrix
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, fileType, qrFile);
	}
}
