package controller.board;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.login.Login;
import controller.main.Mainpage;
import dao.BoardDao;
import dao.ReplyDao;
import dto.Board;
import dto.Reply;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Boardread implements Initializable{
	Board board =  Boardcon.boardinstance;
	ObservableList<Reply> reply;
	public static Reply selectedreply;

    @FXML
    private Label lbltitle;
    
    @FXML
    private Button btndelete;

    @FXML
    private Button btnback;

    @FXML
    private Button btnupdate;

    @FXML
    private TextArea txtcontent;

    @FXML
    private ImageView imgshow;

    @FXML
    private ImageView imgsnaphot;

    @FXML
    private TextField txtreply;

    @FXML
    private Button btnreply;

    @FXML
    private Button btndeletereply;

    @FXML
    private TableView<Reply> tablereply;

    @FXML
    void accback(ActionEvent event) {
    	Mainpage.instance.loadpage("/view/board/board.fxml");
    }

    @FXML
    void accreply(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	if(txtreply.getText().equals("")) {
    		alert.setTitle("????");
    		alert.setHeaderText("???? ?????? ?????? ??????.");
    		alert.showAndWait();
    	}else if(btnreply.getText().equals("????")){
    		ReplyDao.replyDao.replymodify(selectedreply.getReplynum(), txtreply.getText());
    		alert.setTitle("????");
    		alert.setHeaderText("???? ?????? ????????????.");
    		alert.showAndWait();
    		initialize(null, null);
    	}else if(btnreply.getText().equals("????")){
    		Reply reply = new Reply(0, board.getBnum(), Login.member.getMnum(), txtreply.getText(), Login.member.getMid(), null);
    		ReplyDao.replyDao.replywrite(reply);
    		alert.setTitle("????");
    		alert.setHeaderText("?????? ????????????.");
    		txtreply.setText("");
    		alert.showAndWait();
    		initialize(null, null);
    	}
    }
    
    @FXML
    void accdeletereply(ActionEvent event) {
    	
    	if(Login.member.getMnum() ==  selectedreply.getMnum()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("????");
			alert.setHeaderText("?????? ?????????????????");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if(result.get() == ButtonType.OK) {
	    		ReplyDao.replyDao.delete(selectedreply.getReplynum());
	    		alert.setTitle("????");
	    		alert.setHeaderText("???? ?????? ??????????????.");
	    		alert.showAndWait();
	    		initialize(null, null);
	    	}
	    }else{
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("????");
			alert.setHeaderText("?????? ?????? ?????? ???? ?? ?? ????????.");
			alert.showAndWait();
		}
    }
    
    @FXML
    void accdelete(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("????");
		alert.setHeaderText("???? ?????????????????");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		BoardDao.boardDao.delete(Boardcon.boardinstance.getBnum());
    		alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("????");
    		alert.setHeaderText("?? ?????? ??????????????.");
    		alert.showAndWait();
    		Mainpage.instance.loadpage("/view/board/board.fxml");
    	}
    }

    @FXML
    void accupdate(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("????????");
    	alert.setHeaderText("???? ?????????????????");
    	Optional<ButtonType> result = alert.showAndWait();
    	if(result.get() == ButtonType.OK) {
    		Mainpage.instance.loadpage("/view/board/board_write.fxml");
    	}else return;
    	
    }

    @FXML
    void imgclicked(MouseEvent event) {
    	if(board.getBimgurl()!=null) {
	    	Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);	// ?? ???? ?? ???? ???? ???? ???? ???? ???? ????.
			stage.setTitle("?????? ????");
			stage.setWidth(1024);
			stage.setHeight(768);
			stage.setResizable(false);
			
			ImageView imageView = new ImageView(board.getBimgurl());
			imageView.setFitWidth(1024);
			imageView.setFitHeight(768);
			imageView.setOnMouseClicked( e -> stage.close() );
			
			StackPane pane = new StackPane();
			pane.getChildren().add(imageView);
			
			Scene scene = new Scene(pane, 1024, 768);
			stage.setScene(scene);
			stage.showAndWait();
    	}
    }

    @FXML
    void snapshotsclicked(MouseEvent event) {
    	if(board.getBsnapshoturl()!=null) {
    		
    		Stage stage = new Stage();
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setTitle("?????? ????");
    		stage.setWidth(1024);
    		stage.setHeight(768);
    		stage.setResizable(false);
    		
    		WebEngine engine = new WebEngine();
    		WebView view = new WebView();
    		engine = view.getEngine();
    		engine.load(board.getBlocation());
    		
    		Scene scene = new Scene(view, 1024, 768);
    		stage.setScene(scene);
    		stage.showAndWait();
    	}
    }
	
    public void setreplylist(ObservableList<Reply> reply) {
    		
    	TableColumn tc = tablereply.getColumns().get(0);
    	tc.setCellValueFactory(new PropertyValueFactory<>("replyid"));	
//////////////////////////////////////////////////////////////////////////////////////////    	
    	tc = tablereply.getColumns().get(1);	// ?????????? ?????? ?? ????????
    	tc.setCellValueFactory( new PropertyValueFactory<>("replycontent"));		

    	tc = tablereply.getColumns().get(2);	// ?????????? ?????? ?? ????????
    	tc.setCellValueFactory( new PropertyValueFactory<>("replydate"));
    	
    	tablereply.setItems(reply);
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if ( !(ReplyDao.replyDao.nullreplycheck( board.getBnum(), Login.member.getMnum() ) ) ) {
			ReplyDao.replyDao.viewcountup(board.getBview()+1, board.getBnum());	
			board.setBview(board.getBview()+1);	// ???? ?? ???????? ?????? 1 ????????
			Reply writeNullReply = new Reply(0, board.getBnum(), Login.member.getMnum(), null, Login.member.getMid() , null);	// null????(=?????? ????) ???????? ???? ??????
			ReplyDao.replyDao.replywrite(writeNullReply);// ???? ????
		}
		reply = ReplyDao.replyDao.replylist(board.getBnum());
		setreplylist(reply);	// ???????? ???? ???? ????????.
		
		lbltitle.setText(board.getBtitle());
		txtcontent.setText(board.getBcontent());
		
		if(board.getBimgurl()!=null) 
			imgshow.setImage(new Image(board.getBimgurl()));
    	
		if(board.getBsnapshoturl()!=null) 	
			imgsnaphot.setImage(new Image(board.getBsnapshoturl()));
		
		if(Login.member.getMnum()!=board.getMnum()) {	// ???????? mnum?? ???????? ?????? mnum?? ???? ?????? ?? ????, ???????? ????????
			btndelete.setVisible(false);
			btnupdate.setVisible(false);
		}else {
			btndelete.setVisible(true);
			btnupdate.setVisible(true);
		}
		btndeletereply.setVisible(false);
		btnreply.setText("????");
		txtcontent.setEditable(false);
		
		tablereply.setOnMouseClicked( e -> {
			Boolean blankselectcheck = false;
			String[] tmp=e.toString().split("', ",2);
			tmp=tmp[0].split("]'",2);

			if(tmp.length==1 || tmp[1]==null || ! (tmp[1].equals("null")) ) {
				blankselectcheck=false;	// ?????????? ?????? ???????????? ???????? ???? ?????? tmp[1]?? null???? ??????.
				btnreply.setText("????");
				btndeletereply.setVisible(false);
			}
			else blankselectcheck=true;	// ?? ???? ???????????? ?????? ?? tmp[1]?? "null"???????? ??????. 
			

			if(tablereply.getItems().toString().equals("[]") || blankselectcheck) {
				tablereply.getSelectionModel().clearSelection();
				selectedreply=null;
				btnreply.setText("????");
				btndeletereply.setVisible(false);
				return;
			}else{
				selectedreply = tablereply.getSelectionModel().getSelectedItem();
				if(selectedreply!=null && Login.member.getMnum()==selectedreply.getMnum()) {
					btndeletereply.setVisible(true);
					btnreply.setText("????");
				}
			}
		});
	}
}
