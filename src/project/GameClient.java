package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameClient extends Application {

	private static int n;
	private int row;
	private int column;
	int numberOfClicked = 1;
	int turn = 1;
	int tempi = 0;
	int tempj = 0;
	boolean colorIsBlue;
	boolean flag = false;
	private static cell[][] cell;
	Button[] l = new Button[4];


	TextField t = new TextField();
	Label label = new Label("enter the number");
	GridPane pane = new GridPane();
	VBox paneForSelah = new VBox();
	BorderPane borderPane = new BorderPane();

	@Override

	public void start(Stage stage) {

		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(50, 50, 50, 50));
		Button ok = new Button("Ok");
		bp.setRight(t);
		bp.setLeft(label);


		VBox vbox = new VBox();
		ComboBox<String> color = new ComboBox<>();
		color.getItems().addAll("Red" , "Blue");
		vbox.getChildren().addAll(color,ok);
		bp.setBottom(vbox);



		Scene s1 = new Scene(bp);
		Stage stage1 = new Stage();
		stage1.setScene(s1);
		stage1.show();

		ok.setOnAction(e -> {

			if(color.getValue().compareTo("Blue")==0){
				colorIsBlue=true;
				System.out.println("salam");

			}
			else
				colorIsBlue=false;
			PauseTransition time = new PauseTransition(Duration.seconds(5));
			time.setOnFinished(event -> stage1.close());
			time.play();
			n = Integer.valueOf(t.getText());
			cell = new cell[n][n];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					pane.add(cell[i][j] = new cell(i, j), j, i);
				}
			}

			Mohre m = new Mohre(new Player1());
			for (int a = 0; a < 4; a++) {
				l[a] = new Button();
				l[a].setText(m.selahName[a] + " : " + " No Mohre  Is Clicked  ");
			}
			Button save = new Button("save");
			paneForSelah.getChildren().addAll(l[0], l[1], l[2], l[3], save);
			borderPane.setBottom(paneForSelah);
			borderPane.setCenter(pane);

			Stage stage2 = new Stage();
			Scene scene = new Scene(borderPane, 500, 500);
			stage2.setTitle("Game");
			stage2.setScene(scene);
			stage2.show();

			Player1 player1 = new Player1();
			player1.setSarbaz1();
			player1.setFarmande1();
			player1.setShovaliye1();

			Player2 player2 = new Player2();
			player2.setSarbaz2();
			player2.setFarmande2();
			player2.setShovaliye2();

		});

		Button save = new Button("save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent r) {
				save();
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	class Player {
		public Player() {

		}
	}

	class Player1 extends Player {
		public void setSarbaz1() {
			for (int j = 0; j < n; j++) {
				cell[n - 2][j].setMohre(new Sarbaz(new Player1()));
			}
		}

		public void setFarmande1() {
			for (int k = 0; k < n / 2; k++) {
				Random r = new Random();
				int a = r.nextInt(n);
				if (cell[n - 1][a].getMohre() == null) {
					cell[n - 1][a].setMohre(new Farmande(new Player1()));
				} else {
					a = r.nextInt(n);
					while (cell[n - 1][a].getMohre() != null) {
						a = r.nextInt(n);
						if (cell[n - 1][a].getMohre() == null)
							break;
					}
					cell[n - 1][a].setMohre(new Farmande(new Player1()));
				}
			}
		}

		public void setShovaliye1() {
			for (int j = 0; j < n; j++) {
				if (cell[n - 1][j].getMohre() == null) {
					cell[n - 1][j].setMohre(new Shovaliye(new Player1()));
				}
			}
		}
	}

	class Player2 extends Player {

		public void setSarbaz2() {
			for (int j = 0; j < n; j++) {
				cell[1][j].setMohre(new Sarbaz(new Player2()));
			}
		}

		public void setFarmande2() {
			for (int k = 0; k < n / 2; k++) {
				Random r = new Random();
				int a = r.nextInt(n);
				if (cell[0][a].getMohre() == null) {
					cell[0][a].setMohre(new Farmande(new Player2()));
				} else {
					a = r.nextInt(n);
					while (cell[0][a].getMohre() != null) {
						a = r.nextInt(n);
						if (cell[0][a].getMohre() == null)
							break;
					}
					cell[0][a].setMohre(new Farmande(new Player2()));
				}
			}
		}

		public void setShovaliye2() {
			for (int j = 0; j < n; j++) {
				if (cell[0][j].getMohre() == null) {
					cell[0][j].setMohre(new Shovaliye(new Player2()));
				}
			}

		}
	}

	class Mohre {
		Selah[] selahList = new Selah[4];
		String[] selahName = { "Neyze", "Shamshir", "Chagho", "Tir" };
		private int x;
		private int y;
		private Player player;
		private String name;
		Random z = new Random();

		public Mohre(Player p) {
			for (int k = 0; k < 4; k++) {
				x = z.nextInt(10) + 1;
				y = z.nextInt(10) + 1;
				selahList[k] = new Selah(selahName[k], x, y);
			}
			this.player = p;
		}

		public void addSelah(Selah[] s) {
			for (int i = 0; i < 4; i++) {
				this.selahList[i].speed = s[i].getSpeed() + this.selahList[i].speed;
				this.selahList[i].strength = s[i].getStrength() + this.selahList[i].strength;
			}
		}

		public Player getPlayer() {
			return player;
		}

		void setPlayer(Player player) {
			this.player = player;
		}

		public Selah[] getSelah() {
			return selahList;
		}

		public String[] getSelahName() {
			return selahName;
		}

		public String getName() {
			return name;
		}
	}

	class Sarbaz extends Mohre {

		private String name = "sarbaz";

		public Sarbaz(Player p) {
			super(p);
		}

		public void HandleMouse() {

			if (row != n - 1) {
				if (cell[row + 1][column].getMohre() == null) {
					cell[row + 1][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row + 1][column].setIsColored(true);
				} else if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
					if (cell[row + 1][column].getMohre().getPlayer() instanceof Player2) {
						cell[row + 1][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
						cell[row + 1][column].setIsMohreHarif(true);
					}
				} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
					if (cell[row + 1][column].getMohre().getPlayer() instanceof Player1) {
						cell[row + 1][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
						cell[row + 1][column].setIsMohreHarif(true);
					}
				}
			}

			if (row != 0) {
				if (cell[row - 1][column].getMohre() == null) {
					cell[row - 1][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row - 1][column].setIsColored(true);
				} else if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
					if (cell[row - 1][column].getMohre().getPlayer() instanceof Player2) {
						cell[row - 1][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
						cell[row - 1][column].setIsMohreHarif(true);
					}
				} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
					if (cell[row - 1][column].getMohre().getPlayer() instanceof Player1) {
						cell[row - 1][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
						cell[row - 1][column].setIsMohreHarif(true);
					}
				}
			}

			numberOfClicked = 2;
		}

		public String getName() {
			return name;
		}
	}

	class Farmande extends Sarbaz {

		private String name = "Farmande";

		public Farmande(Player p) {
			super(p);
		}

		@Override
		public void HandleMouse() {
			super.HandleMouse();

			for (int a = 1; a < n; a++) {
				if (row + a == n || column + a == n) {
					break;
				} else if (cell[row + a][column + a].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row + a][column + a].getMohre().getPlayer() instanceof Player2) {
							cell[row + a][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row + a][column + a].setIsMohreHarif(true);
						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row + a][column + a].getMohre().getPlayer() instanceof Player1) {
							cell[row + a][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row + a][column + a].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}

				} else if (cell[row + a][column + a].getMohre() == null) {
					cell[row + a][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row + a][column + a].setIsColored(true);
				}

			}

			for (int a = 1; a < n; a++) {
				if (row - a == -1 || column - a == -1) {
					break;
				} else if (cell[row - a][column - a].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row - a][column - a].getMohre().getPlayer() instanceof Player2) {
							cell[row - a][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row - a][column - a].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row - a][column - a].getMohre().getPlayer() instanceof Player1) {
							cell[row - a][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row - a][column - a].setIsMohreHarif(true);
						}
						break;
					} else {
						break;
					}
				} else if (cell[row - a][column - a].getMohre() == null) {
					cell[row - a][column - a].setStyle("-fx-background-color: yellow;-fx-border-color:black");
					cell[row - a][column - a].setIsColored(true);
				}
			}

			for (int a = 1; a < n; a++) {
				if (row + a == n || column - a == -1) {
					break;
				} else if (cell[row + a][column - a].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row + a][column - a].getMohre().getPlayer() instanceof Player2) {
							cell[row + a][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row + a][column - a].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row + a][column - a].getMohre().getPlayer() instanceof Player1) {
							cell[row + a][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row + a][column - a].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}
				} else if (cell[row + a][column - a].getMohre() == null) {
					cell[row + a][column - a].setStyle("-fx-background-color: yellow;-fx-border-color:black");
					cell[row + a][column - a].setIsColored(true);
				}
			}

			for (int a = 1; a < n; a++) {
				if (row - a == -1 || column + a == n) {
					break;
				} else if (cell[row - a][column + a].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row - a][column + a].getMohre().getPlayer() instanceof Player2) {
							cell[row - a][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row - a][column + a].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row - a][column + a].getMohre().getPlayer() instanceof Player1) {
							cell[row - a][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row - a][column + a].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}
				} else if (cell[row - a][column + a].getMohre() == null) {
					cell[row - a][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row - a][column + a].setIsColored(true);
				}
			}
			numberOfClicked = 2;
		}

		public String getName() {
			return name;
		}
	}

	class Shovaliye extends Farmande {
		private String name = "Shovaliye";

		public Shovaliye(Player p) {
			super(p);
		}

		@Override
		public void HandleMouse() {
			super.HandleMouse();

			for (int a = 1; a < n; a++) {
				if (row - a == -1 || column == -1) {
					break;
				} else if (cell[row - a][column].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row - a][column].getMohre().getPlayer() instanceof Player2) {
							cell[row - a][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row - a][column].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row - a][column].getMohre().getPlayer() instanceof Player1) {
							cell[row - a][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row - a][column].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}
				} else if (cell[row - a][column].getMohre() == null) {
					cell[row - a][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row - a][column].setIsColored(true);
				}
			}

			for (int a = 1; a < n; a++) {
				if (row + a == n || column == n) {
					break;
				} else if (cell[row + a][column].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row + a][column].getMohre().getPlayer() instanceof Player2) {
							cell[row + a][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row + a][column].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row + a][column].getMohre().getPlayer() instanceof Player1) {
							cell[row + a][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row + a][column].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}
				} else if (cell[row + a][column].getMohre() == null) {
					cell[row + a][column].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row + a][column].setIsColored(true);
				}
			}

			for (int a = 1; a < n; a++) {
				if (row == -1 || column - a == -1) {
					break;
				} else if (cell[row][column - a].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row][column - a].getMohre().getPlayer() instanceof Player2) {
							cell[row][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row][column - a].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row][column - a].getMohre().getPlayer() instanceof Player1) {
							cell[row][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row][column - a].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}
				} else if (cell[row][column - a].getMohre() == null) {
					cell[row][column - a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row][column - a].setIsColored(true);
				}
			}

			for (int a = 1; a < n; a++) {
				if (row == n || column + a == n) {
					break;
				} else if (cell[row][column + a].getMohre() != null) {
					if (cell[row][column].getMohre().getPlayer() instanceof Player1) {
						if (cell[row][column + a].getMohre().getPlayer() instanceof Player2) {
							cell[row][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row][column + a].setIsMohreHarif(true);

						}
						break;
					} else if (cell[row][column].getMohre().getPlayer() instanceof Player2) {
						if (cell[row][column + a].getMohre().getPlayer() instanceof Player1) {
							cell[row][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
							cell[row][column + a].setIsMohreHarif(true);

						}
						break;
					} else {
						break;
					}
				} else if (cell[row][column + a].getMohre() == null) {
					cell[row][column + a].setStyle("-fx-background-color:yellow;-fx-border-color:black");
					cell[row][column + a].setIsColored(true);
				}
			}

			numberOfClicked = 2;
		}

		public String getName() {
			return name;
		}
	}

	class Selah {
		int speed;
		int strength;
		String name;
		private int score;

		public Selah() {

		}

		public Selah(String name, int speed, int strength) {
			this.name = name;
			this.speed = speed;
			this.strength = strength;
		}

		public int getSpeed() {
			return speed;
		}

		public int getStrength() {
			return strength;
		}

		public String getName() {
			return name;
		}

		public int getScore() {
			return speed * strength;
		}

		public String getInfo() {
			return name + "  :  " + "speed is :   " + String.valueOf(speed) + " " + "strength is :   "
					+ String.valueOf(strength);
		}
	}

	public void save() {
		try {
			File file = new File("save.txt");
			PrintWriter p = new PrintWriter(file);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (cell[i][j].getMohre() != null) {
						p.print(String.valueOf(i) + "  " + String.valueOf(j) + ",");
						p.print(cell[i][j].getMohre().getName() + ",");
						for (int k = 0; k < 4; k++) {
							p.print((cell[i][j].getMohre().getSelahName())[k] + ",");
						}
						for (int k = 0; k < 4; k++) {
							p.print((cell[i][j].getMohre().getSelah())[k].getInfo());
						}
					}
				}
			}
			p.close();
		} catch (FileNotFoundException e) {
		}
	}

	class cell extends Pane {
		private int i;
		private int j;
		private boolean isColored = false;
		private boolean IsMohreHarif = false;
		private Mohre mohre = null;
		private Player player = null;
		private Image imagee;
		private ImageView image;

		public cell(int i, int j) {
			this.i = i;
			this.j = j;
			setStyle("-fx-border-color: black");
			this.setPrefSize(2000, 2000);
			this.setOnMouseClicked(e -> {
				handleMouseClick();
			});
		}

		public void ShowSelah() {
			paneForSelah.getChildren().remove(0, 5);
			for (int a = 0; a < 4; a++) {
				l[a] = new Button();
				l[a].setText(this.getMohre().selahList[a].getInfo());
			}
			Button save = new Button("save");
			save.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent r) {
					save();
				}
			});

			paneForSelah.getChildren().addAll(l[0], l[1], l[2], l[3], save);
			borderPane.setBottom(paneForSelah);

		}

		public int GameOver1() {
			for (int a = 0; a < n; a++) {
				for (int b = 0; b < n; b++) {
					if (cell[a][b].getMohre() != null) {
						if (cell[a][b].getMohre().getPlayer() instanceof Player2) {
							return 0;
						}
					}
				}
			}
			Label text = new Label();
			text.setText("Player 1 is winner");
			Scene s = new Scene(text);
			Stage stage = new Stage();
			stage.setScene(s);
			stage.show();
			PauseTransition time = new PauseTransition(Duration.seconds(4));
			time.setOnFinished(event -> stage.close());
			time.play();
			turn = 2;
			return 0;
		}

		public int GameOver2() {
			for (int a = 0; a < n; a++) {
				for (int b = 0; b < n; b++) {
					if (cell[a][b].getMohre() != null) {
						if (cell[a][b].getMohre().getPlayer() instanceof Player1) {
							return 0;
						}
					}
				}
			}
			Label text = new Label();
			text.setText("Player 2 is winner");
			Scene s = new Scene(text);
			Stage stage = new Stage();
			stage.setScene(s);
			stage.show();
			PauseTransition time = new PauseTransition(Duration.seconds(4));
			time.setOnFinished(event -> stage.close());
			time.play();
			turn = 1;
			return 0;
		}

		public void handleMouseClick() {
			if (turn == 1) {
				if (numberOfClicked == 1) {
					if (this.getMohre() != null && this.getMohre().getPlayer() instanceof Player1) {

						tempi = row = this.getRow();
						tempj = column = this.getColumn();

						if (this.getMohre() instanceof Shovaliye) {
							((Shovaliye) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Farmande) {
							((Farmande) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Sarbaz) {
							((Sarbaz) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}
					} else if (this.getMohre() != null && this.getMohre().getPlayer() instanceof Player2) {
						this.ShowSelah();
					}
				}

				else if (numberOfClicked == 2) {
					if (this.getIsColored() == true && this.getMohre() == null) {
						if (cell[tempi][tempj].getMohre() instanceof Shovaliye) {
							this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
							this.setPlayer(cell[tempi][tempj].getPlayer());
							cell[tempi][tempj].setPlayer(null);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].getChildren().remove(0);
							turn = 2;
							GameOver1();
							GameOver2();
						} else if (cell[tempi][tempj].getMohre() instanceof Farmande) {
							this.setMohre((Farmande) cell[tempi][tempj].getMohre());
							this.setPlayer(cell[tempi][tempj].getPlayer());
							cell[tempi][tempj].setPlayer(null);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].getChildren().remove(0);
							turn = 2;
							GameOver1();
							GameOver2();
						} else if (cell[tempi][tempj].getMohre() instanceof Sarbaz) {
							this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
							this.setPlayer(cell[tempi][tempj].getPlayer());
							cell[tempi][tempj].setPlayer(null);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].getChildren().remove(0);
							turn = 2;
							GameOver1();
							GameOver2();
						}
					}

					else if (this.getMohre() == null && this.getIsColored() == false
							&& this.getIsMohreHarif() == false) {
						Label text = new Label();
						text.setText("invalid position");
						Scene s = new Scene(text);
						Stage stage3 = new Stage();
						stage3.setScene(s);
						stage3.show();
						PauseTransition time = new PauseTransition(Duration.seconds(1));
						time.setOnFinished(event -> stage3.close());
						time.play();
					}

					else if (this.getIsColored() == false && this.getIsMohreHarif() == false) {
						tempi = row = this.getRow();
						tempj = column = this.getColumn();

						if (this.getMohre() instanceof Shovaliye) {
							((Shovaliye) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Farmande) {
							((Farmande) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Sarbaz) {
							((Sarbaz) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}
					}

					else if (this.getIsMohreHarif() == true && cell[tempi][tempj].getMohre() instanceof Shovaliye) {
						if (this.getMohre() instanceof Farmande && (this.getMohre() instanceof Shovaliye) == false) {
							Media hit = new Media(new File("sword.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(hit);
							mediaPlayer.play();
							cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
							this.getChildren().remove(0);
							this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
							this.ShowSelah();
							cell[tempi][tempj].getChildren().remove(0);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].setPlayer(null);
							turn = 2;
							GameOver1();
							GameOver2();
						} else if (this.getMohre() instanceof Sarbaz
								&& (this.getMohre() instanceof Shovaliye) == false) {
							Media hit = new Media(new File("sword.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(hit);
							mediaPlayer.play();
							cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
							this.getChildren().remove(0);
							this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
							this.ShowSelah();
							cell[tempi][tempj].getChildren().remove(0);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].setPlayer(null);
							turn = 2;
							GameOver1();
							GameOver2();
						} else if (this.getMohre() instanceof Shovaliye) {
							l[0].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[0]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[0].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[1].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[1]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[1].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[2].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[2]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[2].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[3].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[3]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[3].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});
						}
					}

					else if (this.getIsMohreHarif() == true && cell[tempi][tempj].getMohre() instanceof Farmande) {
						if (this.getMohre() instanceof Sarbaz && (this.getMohre() instanceof Farmande) == false
								&& (this.getMohre() instanceof Shovaliye) == false) {
							Media hit = new Media(new File("sword.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(hit);
							mediaPlayer.play();
							cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
							this.getChildren().remove(0);
							this.setMohre((Farmande) cell[tempi][tempj].getMohre());
							this.ShowSelah();
							cell[tempi][tempj].getChildren().remove(0);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].setPlayer(null);
							turn = 2;
							GameOver1();
							GameOver2();
						} else if (this.getMohre() instanceof Farmande) {
							l[0].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[0]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[0].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[1].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[1]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[1].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[2].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[2]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[2].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[3].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[3]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[3].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});
						}

					}

					else if (this.getIsMohreHarif() == true && cell[tempi][tempj].getMohre() instanceof Sarbaz) {
						if (this.getMohre() instanceof Sarbaz && (this.getMohre() instanceof Shovaliye == false)
								&& (this.getMohre() instanceof Farmande == false)) {
							l[0].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[0]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[0].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[1].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[1]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[1].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[2].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[2]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[2].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

							l[3].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[3]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[3].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									cell[tempi][tempj].setPlayer(null);
									turn = 2;
									GameOver1();
									GameOver2();
								}
							});

						}
					}

					changeColor();
					numberOfClicked = 1;
				}

			} else if (turn == 2) {
				if (numberOfClicked == 1) {
					if (this.getMohre() != null && this.getMohre().getPlayer() instanceof Player2) {

						tempi = row = this.getRow();
						tempj = column = this.getColumn();

						if (this.getMohre() instanceof Shovaliye) {
							((Shovaliye) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Farmande) {
							((Farmande) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Sarbaz) {
							((Sarbaz) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}
					} else if (this.getMohre() != null && this.getMohre().getPlayer() instanceof Player1) {
						this.ShowSelah();
					}
				}

				else if (numberOfClicked == 2) {
					if (this.getIsColored() == true && this.getMohre() == null) {
						if (cell[tempi][tempj].getMohre() instanceof Shovaliye) {
							this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
							this.setPlayer(cell[tempi][tempj].getPlayer());
							cell[tempi][tempj].setPlayer(null);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].getChildren().remove(0);
							turn = 1;
							GameOver1();
							GameOver2();
						} else if (cell[tempi][tempj].getMohre() instanceof Farmande) {
							this.setMohre((Farmande) cell[tempi][tempj].getMohre());
							this.setPlayer(cell[tempi][tempj].getPlayer());
							cell[tempi][tempj].setPlayer(null);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].getChildren().remove(0);
							turn = 1;
							GameOver1();
							GameOver2();
						} else if (cell[tempi][tempj].getMohre() instanceof Sarbaz) {
							this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
							this.setPlayer(cell[tempi][tempj].getPlayer());
							cell[tempi][tempj].setPlayer(null);
							cell[tempi][tempj].setMohre(null);
							cell[tempi][tempj].getChildren().remove(0);
							turn = 1;
							GameOver1();
							GameOver2();
						}
					}
					else if (this.getMohre() == null && this.getIsColored() == false
							&& this.getIsMohreHarif() == false) {
						Label text = new Label();
						text.setText("invalid position");
						Scene s = new Scene(text);
						Stage stage3 = new Stage();
						stage3.setScene(s);
						stage3.show();
						PauseTransition time = new PauseTransition(Duration.seconds(1));
						time.setOnFinished(event -> stage3.close());
						time.play();
					}

					else if (this.getIsColored() == false && this.getIsMohreHarif() == false) {
						row = this.getRow();
						column = this.getColumn();

						if (this.getMohre() instanceof Shovaliye) {
							((Shovaliye) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Farmande) {
							((Farmande) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}

						else if (this.getMohre() instanceof Sarbaz) {
							((Sarbaz) this.getMohre()).HandleMouse();
							this.ShowSelah();
						}
					} else if (this.getIsMohreHarif() == true && cell[tempi][tempj].getMohre() instanceof Shovaliye) {
						if (this.getMohre() instanceof Farmande && (this.getMohre() instanceof Shovaliye) == false) {
							Media hit = new Media(new File("sword.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(hit);
							mediaPlayer.play();
							cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
							this.getChildren().remove(0);
							this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
							this.ShowSelah();
							cell[tempi][tempj].getChildren().remove(0);
							cell[tempi][tempj].setMohre(null);
							turn = 1;
							GameOver1();
							GameOver2();
						} else if (this.getMohre() instanceof Sarbaz
								&& (this.getMohre() instanceof Shovaliye) == false) {
							Media hit = new Media(new File("sword.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(hit);
							mediaPlayer.play();
							cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
							this.getChildren().remove(0);
							this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
							this.ShowSelah();
							cell[tempi][tempj].getChildren().remove(0);
							cell[tempi][tempj].setMohre(null);
							turn = 1;
							GameOver1();
							GameOver2();
						} else if (this.getMohre() instanceof Shovaliye) {
							l[0].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[0]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[0].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[1].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[1]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[1].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[2].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[2]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[2].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[3].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[3]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[3].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Shovaliye) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});
						}
						turn = 1;
					} else if (this.getIsMohreHarif() == true && cell[tempi][tempj].getMohre() instanceof Farmande) {
						if (this.getMohre() instanceof Sarbaz && (this.getMohre() instanceof Farmande) == false
								&& (this.getMohre() instanceof Shovaliye) == false) {
							Media hit = new Media(new File("sword.mp3").toURI().toString());
							MediaPlayer mediaPlayer = new MediaPlayer(hit);
							mediaPlayer.play();
							this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
							this.getChildren().remove(0);
							this.setMohre((Farmande) cell[tempi][tempj].getMohre());
							this.ShowSelah();
							cell[tempi][tempj].getChildren().remove(0);
							cell[tempi][tempj].setMohre(null);
							turn = 1;
							GameOver1();
							GameOver2();
						} else if (this.getMohre() instanceof Farmande) {
							l[0].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[0]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[0].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[1].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[1]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[1].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[2].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[2]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[2].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[3].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[3]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[3].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Farmande) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});
						}
						// turn = 1;
					} else if (this.getIsMohreHarif() == true && cell[tempi][tempj].getMohre() instanceof Sarbaz) {
						if (this.getMohre() instanceof Sarbaz && (this.getMohre() instanceof Shovaliye == false)
								&& (this.getMohre() instanceof Farmande == false)) {
							l[0].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								System.out.println("ok");
								if ((this.getMohre().getSelah())[0]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[0].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}});

							l[1].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[1]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[1].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[2].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[2]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[2].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

							l[3].setOnAction(e -> {
								Media hit = new Media(new File("sword.mp3").toURI().toString());
								MediaPlayer mediaPlayer = new MediaPlayer(hit);
								mediaPlayer.play();
								if ((this.getMohre().getSelah())[3]
										.getScore() <= (cell[tempi][tempj].getMohre().getSelah())[3].getScore()) {
									cell[tempi][tempj].getMohre().addSelah(this.getMohre().getSelah());
									this.getChildren().remove(0);
									this.setMohre((Sarbaz) cell[tempi][tempj].getMohre());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								} else {
									this.getMohre().addSelah(cell[tempi][tempj].getMohre().getSelah());
									cell[tempi][tempj].getChildren().remove(0);
									cell[tempi][tempj].setMohre(null);
									turn = 1;
									GameOver1();
									GameOver2();
								}
							});

						}
					}

					changeColor();
					numberOfClicked = 1;
				}
			}
		}

		public void changeColor() {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					cell[i][j].setStyle("-fx-background-color:white;-fx-border-color:black");
					cell[i][j].setIsColored(false);
					cell[i][j].setIsMohreHarif(false);
				}
			}
		}

		public void setMohre(Mohre mohre) {
			this.mohre = mohre;
			if(colorIsBlue){
			if (mohre instanceof Shovaliye && mohre.getPlayer() instanceof Player1) {
				imagee = new Image("shovalie.png");
				image = new ImageView(imagee);
				image.fitWidthProperty().bind(this.widthProperty());
				image.fitHeightProperty().bind(this.heightProperty());
				this.getChildren().add(image);
				this.setStyle("-fx-background-color:white;-fx-border-color:black");
			}

			else if (mohre instanceof Farmande && mohre.getPlayer() instanceof Player1) {
				imagee = new Image("farmande.png");
				image = new ImageView(imagee);
				image.fitWidthProperty().bind(this.widthProperty());
				image.fitHeightProperty().bind(this.heightProperty());
				this.getChildren().add(image);
			}

			else if (mohre instanceof Sarbaz && mohre.getPlayer() instanceof Player1) {
				imagee = new Image("sarbaz.png");
				image = new ImageView(imagee);
				image.fitWidthProperty().bind(this.widthProperty());
				image.fitHeightProperty().bind(this.heightProperty());
				this.getChildren().add(image);
			} else if (mohre instanceof Shovaliye && mohre.getPlayer() instanceof Player2) {
				imagee = new Image("shovalie2.png");
				image = new ImageView(imagee);
				image.fitWidthProperty().bind(this.widthProperty());
				image.fitHeightProperty().bind(this.heightProperty());
				this.getChildren().add(image);
			}

			else if (mohre instanceof Farmande && mohre.getPlayer() instanceof Player2) {
				imagee = new Image("farmande2.png");
				image = new ImageView(imagee);
				image.fitWidthProperty().bind(this.widthProperty());
				image.fitHeightProperty().bind(this.heightProperty());
				this.getChildren().add(image);
			}

			else if (mohre instanceof Sarbaz && mohre.getPlayer() instanceof Player2) {
				imagee = new Image("sarbaz2.png");
				image = new ImageView(imagee);
				image.fitWidthProperty().bind(this.widthProperty());
				image.fitHeightProperty().bind(this.heightProperty());
				this.getChildren().add(image);
			}
			}
			else{
				if (mohre instanceof Shovaliye && mohre.getPlayer() instanceof Player1) {
					imagee = new Image("shovalie2.png");
					image = new ImageView(imagee);
					image.fitWidthProperty().bind(this.widthProperty());
					image.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(image);
					this.setStyle("-fx-background-color:white;-fx-border-color:black");
				}

				else if (mohre instanceof Farmande && mohre.getPlayer() instanceof Player1) {
					imagee = new Image("farmande2.png");
					image = new ImageView(imagee);
					image.fitWidthProperty().bind(this.widthProperty());
					image.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(image);
				}

				else if (mohre instanceof Sarbaz && mohre.getPlayer() instanceof Player1) {
					imagee = new Image("sarbaz2.png");
					image = new ImageView(imagee);
					image.fitWidthProperty().bind(this.widthProperty());
					image.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(image);
				} else if (mohre instanceof Shovaliye && mohre.getPlayer() instanceof Player2) {
					imagee = new Image("shovalie.png");
					image = new ImageView(imagee);
					image.fitWidthProperty().bind(this.widthProperty());
					image.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(image);
				}

				else if (mohre instanceof Farmande && mohre.getPlayer() instanceof Player2) {
					imagee = new Image("farmande.png");
					image = new ImageView(imagee);
					image.fitWidthProperty().bind(this.widthProperty());
					image.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(image);
				}

				else if (mohre instanceof Sarbaz && mohre.getPlayer() instanceof Player2) {
					imagee = new Image("sarbaz.png");
					image = new ImageView(imagee);
					image.fitWidthProperty().bind(this.widthProperty());
					image.fitHeightProperty().bind(this.heightProperty());
					this.getChildren().add(image);
				}
			}
		}

		public void setPlayer(Player p) {
			player = p;
		}

		public void setIsColored(boolean x) {
			isColored = x;
		}

		public void setIsMohreHarif(boolean x) {
			IsMohreHarif = x;
		}

		public Player getPlayer() {
			return player;
		}

		public boolean getIsMohreHarif() {
			return IsMohreHarif;
		}

		public boolean getIsColored() {
			return isColored;
		}

		public Mohre getMohre() {
			return mohre;
		}

		public void getImage() {
			image = null;
		}

		public int getRow() {
			return i;
		}

		public int getColumn() {
			return j;
		}
	}
}
