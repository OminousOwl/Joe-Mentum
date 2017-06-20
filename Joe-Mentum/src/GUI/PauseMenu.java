package GUI;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

class PauseMenu extends Parent {
	public PauseMenu() {
		VBox menu0 = new VBox(10);
		VBox menu1 = new VBox(10);

		menu0.setTranslateX(100);
		menu0.setTranslateY(200);

		menu1.setTranslateX(100);
		menu1.setTranslateY(200);

		final int offset = 400;

		menu1.setTranslateX(offset);

		MenuButton btnResume = new MenuButton("RESUME");
		btnResume.setOnMouseClicked(event -> {
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);
			ft.setOnFinished(evt -> setVisible(false));
			ft.play();
		});

		MenuButton btnExit = new MenuButton("EXIT");
		btnExit.setOnMouseClicked(event -> {
			System.exit(0);
		});

		MenuButton btnBack = new MenuButton("BACK");
		btnBack.setOnMouseClicked(event -> {
			getChildren().add(menu0);

			TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
			tt.setToX(menu1.getTranslateX() + offset);

			TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
			tt1.setToX(menu1.getTranslateX());

			tt.play();
			tt1.play();

			tt.setOnFinished(evt -> {
				getChildren().remove(menu1);
			});
		});

		menu0.getChildren().addAll(btnResume, btnExit);
		menu1.getChildren().addAll(btnBack);

		Rectangle bg = new Rectangle(800, 600);
		bg.setFill(Color.GREY);
		bg.setOpacity(0.4);

		getChildren().addAll(bg, menu0);
	}
}

class MenuButton extends StackPane {
	private Text text;

	public MenuButton(String name) {
		text = new Text(name);
		text.getFont();
		text.setFont(Font.font(20));
		text.setFill(Color.WHITE);

		Rectangle bg = new Rectangle(250, 30);
		bg.setOpacity(0.6);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));

		setAlignment(Pos.CENTER_LEFT);
		setRotate(-0.5);
		getChildren().addAll(bg, text);

		setOnMouseEntered(event -> {
			bg.setTranslateX(10);
			text.setTranslateX(10);
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		});

		setOnMouseExited(event -> {
			bg.setTranslateX(0);
			text.setTranslateX(0);
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		});

		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());

		setOnMousePressed(event -> setEffect(drop));
		setOnMouseReleased(event -> setEffect(null));
	}
}
