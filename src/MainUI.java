import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * @author uka
 *
 */
public class MainUI extends Application {

	Scene mainScene;

	Group group;

	Mediator mediator;

	final static int width = 480;
	final static int height = 480;

	final static int row = 8;
	final static int col = 8;

	final static int rowL = height / row;

	int currentPlayer = 1;

	int hoverX = -1;
	int hoverY = -1;

	/**
	 * 0 - empty 1 - white 2 - black
	 */
	int[][] map;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		map = new int[row][col];
		map[3][4] = 1;
		map[4][3] = 1;
		map[3][3] = 2;
		map[4][4] = 2;

		mediator = new Mediator();
		mediator.canPlace(2, 2, map, 1);

		primaryStage.setTitle("Mediator Pattern - Reversi");

		group = new Group();

		mainScene = new Scene(group, width, height);
		mainScene.setFill(Color.GRAY);
		mainScene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int cRow = (int) ((event.getSceneX() / rowL));
				int cCol = (int) ((event.getSceneY() / rowL));

				if (mediator.canPlace(cRow, cCol, map, currentPlayer)) {
					map[cRow][cCol] = currentPlayer;
					mediator.place(cRow, cCol, map, currentPlayer);
					currentPlayer = currentPlayer == 1 ? 2 : 1;

					hoverX = -1;
					hoverY = -1;

					draw();
				}
			}
		});

		mainScene.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hoverX = (int) ((event.getSceneX() / rowL));
				hoverY = (int) ((event.getSceneY() / rowL));

				if (!mediator.canPlace(hoverX, hoverY, map, currentPlayer)) {
					hoverX = -1;
					hoverY = -1;
				}

				draw();
			}
		});

		primaryStage.setScene(mainScene);

		primaryStage.show();

		draw();
	}

	void draw() {
		group.getChildren().clear();

		for (int i = 0; i < row; i++) {
			Line lineR = new Line(0, (i + 1) * rowL, width, (i + 1) * rowL);
			lineR.setStroke(Color.LIGHTGRAY);

			Line lineC = new Line((i + 1) * rowL, 0, (i + 1) * rowL, height);
			lineC.setStroke(Color.LIGHTGRAY);

			group.getChildren().addAll(lineR, lineC);
		}

		int radius = (rowL - 10) / 2;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 1) {
					Circle circle = new Circle((i) * rowL + rowL / 2, (j) * rowL + rowL / 2, radius);
					circle.setFill(Color.WHITE);

					group.getChildren().addAll(circle);
				} else if (map[i][j] == 2) {
					Circle circle = new Circle((i) * rowL + rowL / 2, (j) * rowL + rowL / 2, radius);
					circle.setFill(Color.BLACK);

					group.getChildren().addAll(circle);
				}
			}
		}

		if (hoverX != -1 && hoverY != -1) {
			Circle circle = new Circle(hoverX * rowL + rowL / 2, hoverY * rowL + rowL / 2, radius);
			if (currentPlayer == 1)
				circle.setStroke(Color.WHITE);
			else
				circle.setStroke(Color.BLACK);
			circle.setFill(null);
			group.getChildren().add(circle);
		}
	}
}